package com.example.karthik.customviews.settings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.karthik.customviews.R;
import com.example.karthik.customviews.model.HelpContactsModel;

import java.util.Set;

public class SettingsHelpActivity extends AppCompatActivity implements ISettingsHelpView,
    ISettingsCallback, View.OnClickListener, ExpandableListView.OnGroupCollapseListener {
  public static final String TAG = SettingsHelpActivity.class.getSimpleName();
  private final static int PICK_CONTACT = 1;

  private ExpandableListView expandableListView;
  private HelpContactsAdapter helpContactsAdapter;
  private String[] groupItems;
  private CoordinatorLayout coordinatorLayout;
  private int selectedGroupItem;

  private Dialog insertContactDialog;
  private EditText etDialogName;
  private EditText etDialogPhone;

  private ISettingsHelpPresenter settingsHelpPresenter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_settings);
    settingsHelpPresenter = new SettingsHelpPresenter(this, new SettingsHelpInteractor(this, this),
        this);
    injectViews();
  }

  @Override
  public void injectViews() {
    coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_help_contacts);
    groupItems = getResources().getStringArray(R.array.emergency_groups);
    expandableListView = (ExpandableListView) findViewById(R.id.elv_help_contacts);
    expandableListView.setGroupIndicator(null);
    insertContactDialog = new Dialog(this);
    insertContactDialog.setContentView(R.layout.dialog_insert_contact);
    insertContactDialog.setTitle("Enter the Contact Details");
    etDialogName = (EditText) insertContactDialog.findViewById(R.id.dialog_name);
    etDialogPhone = (EditText) insertContactDialog.findViewById(R.id.dialog_phone);
    Button dialogAdd = (Button) insertContactDialog.findViewById(R.id.btn_enter_contact);
    dialogAdd.setOnClickListener(this);
    settingsHelpPresenter.onViewReady();
  }

  @Override
  public ExpandableListView getExpandableListView() {
    return expandableListView;
  }

  @Override
  public AppCompatActivity getActivity() {
    return this;
  }

  @Override
  public void onDeleteContact(HelpContactsModel emgContact) {
    settingsHelpPresenter.deleteContact(emgContact);
  }

  @Override
  public void updateListView(Set<HelpContactsModel> helpContactList) {
    helpContactsAdapter = new HelpContactsAdapter(this,
        new HelpContactsAdapter.ContactTypeHolder(helpContactList),
        R.layout.settings_help_group_row, R.layout.settings_help_group_children_row);
    expandableListView.setAdapter(helpContactsAdapter);
  }

  @Override
  public void onError(int code) {
    if (insertContactDialog != null)
      insertContactDialog.dismiss();
    switch (code) {
      case ERROR_VALID_PHONE_NUMBER:
        Snackbar.make(coordinatorLayout, "Please enter a valid phone number!",
            Snackbar.LENGTH_LONG).show();
        break;
      case ERROR_ENTER_BOTH_FIELDS:
        Snackbar.make(coordinatorLayout, "Please enter both fields!",
            Snackbar.LENGTH_LONG).show();
        break;
      default:
    }
  }

  @Override
  public void onSuccess(int code) {
    if (insertContactDialog != null)
      insertContactDialog.dismiss();
    switch (code) {
      case SUCCESS_CONTACTS_ADDED:
        Snackbar.make(coordinatorLayout, "Selected contact has been added",
            Snackbar.LENGTH_LONG).show();
        break;
      default:
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.settings_help, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.btn_insert_contact) {
      selectedGroupItem = 0;
      AlertDialog.Builder alert = new AlertDialog.Builder(this);
      alert.setTitle("Choose group")
          .setSingleChoiceItems(groupItems, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              selectedGroupItem = which;
            }
          })
          .setPositiveButton("Import Contact", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              importContact();
            }
          })
          .setNegativeButton("New Contact", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              enterContact();
            }
          })
          .show();
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }

  @Override
  public void enterContact() {
    insertContactDialog.show();
  }

  @Override
  public void importContact() {
    try {
      Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
      intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
      startActivityForResult(intent, PICK_CONTACT);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btn_enter_contact) {
      settingsHelpPresenter.onEnterContact(etDialogName.getText().toString(),
          etDialogPhone.getText().toString());
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (PICK_CONTACT == requestCode && RESULT_OK == resultCode && data != null) {
      settingsHelpPresenter.getContactFromUri(data.getData());
    }
  }

  @Override
  public String getSelectedContactType() {
    return groupItems[selectedGroupItem];
  }

  @Override
  public void onGroupCollapse(int groupPosition) {
    expandableListView.collapseGroup(groupPosition);
  }
}
