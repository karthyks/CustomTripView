package com.example.karthik.customviews.settings;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import com.example.karthik.customviews.model.HelpContactsModel;

import java.util.HashSet;
import java.util.Set;

public class SettingsHelpInteractor implements ISettingsHelpInteractor {

  private Context context;
  private ISettingsCallback callback;
  private Set<HelpContactsModel> helpContactsModelsList;

  public SettingsHelpInteractor(Context context, ISettingsCallback callback) {
    this.context = context;
    this.callback = callback;
    helpContactsModelsList = new HashSet<>();
    helpContactsModelsList.add(new HelpContactsModel("name", "900000000", "Personal"));
    helpContactsModelsList.add(new HelpContactsModel("name", "900000001", "Personal"));
    helpContactsModelsList.add(new HelpContactsModel("name", "900000002", "Friends"));
    helpContactsModelsList.add(new HelpContactsModel("name", "900000003", "Office"));
  }

  @Override
  public void getContactFromUri(Uri uri) {
    String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER,
        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};
    Cursor cursor = context.getContentResolver()
        .query(uri, projection, null, null, null);
    assert cursor != null;
    cursor.moveToFirst();
    String name = cursor.getString(
        cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
    String number = cursor.getString(
        cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
    cursor.close();
    if (name.isEmpty() || number.isEmpty()) {
      callback.onError(ISettingsCallback.ERROR_VALID_PHONE_NUMBER);
    } else {
      helpContactsModelsList.add(new HelpContactsModel(name, number,
          callback.getSelectedContactType()));
    }
  }

  @Override
  public Set<HelpContactsModel> getHelpContactsList() {
    return this.helpContactsModelsList;
  }

  @Override
  public void removeContactFromList(HelpContactsModel emgContact) {
    if (helpContactsModelsList.contains(emgContact)) {
      helpContactsModelsList.remove(emgContact);
    }
  }

  @Override
  public void addContact(String name, String phone, String selectedContactType) {
    if (helpContactsModelsList != null) {
      helpContactsModelsList.add(new HelpContactsModel(name, phone, selectedContactType));
    }
  }
}
