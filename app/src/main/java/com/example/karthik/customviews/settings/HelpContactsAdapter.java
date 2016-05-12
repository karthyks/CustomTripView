package com.example.karthik.customviews.settings;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karthik.customviews.R;
import com.example.karthik.customviews.drawable.CharacterDrawable;
import com.example.karthik.customviews.model.HelpContactsModel;
import com.github.vignesh_iopex.confirmdialog.Confirm;
import com.github.vignesh_iopex.confirmdialog.Dialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HelpContactsAdapter extends BaseExpandableListAdapter {

  private ContactTypeHolder contactType;
  private int parentResourceId;
  private int childResourceId;
  private static Set<HelpContactsModel> listHelpContacts;
  private HashMap<String, List<HelpContactsModel>> hashContacts;
  private ISettingsHelpView settingsView;

  public HelpContactsAdapter(ISettingsHelpView settingsView, ContactTypeHolder typeHolder,
                             int parentResourceId, int childResourceId) {
    this.settingsView = settingsView;
    this.contactType = typeHolder;
    this.parentResourceId = parentResourceId;
    this.childResourceId = childResourceId;
    listHelpContacts = contactType.getAllContacts();
    this.hashContacts = contactType.buildEmergencyContacts();
  }

  @Override
  public int getGroupCount() {
    return contactType.getContactTypes().length;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return contactType.getHashMap().get(contactType.getContactTypes()[groupPosition]).size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    if (groupPosition < hashContacts.size())
      return contactType.getContactTypes()[groupPosition];
    return null;
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    if (groupPosition < contactType.getContactTypes().length
        && childPosition < contactType.getHashMap()
        .get(contactType.getContactTypes()[groupPosition]).size()) {
      return contactType.getHashMap().get(contactType.getContactTypes()[groupPosition])
          .get(childPosition);
    }

    return null;
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public boolean hasStableIds() {
    return false;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
                           View convertView, ViewGroup parent) {
    String waypoint = (String) getGroup(groupPosition);
    settingsView.getExpandableListView().expandGroup(groupPosition);
    if (waypoint != null) {
      ParentViewHolder parentViewHolder;
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) settingsView.getActivity()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(parentResourceId, null);
        parentViewHolder = new ParentViewHolder(convertView);
        convertView.setTag(parentViewHolder);
      } else {
        parentViewHolder = (ParentViewHolder) convertView.getTag();
      }
      parentViewHolder.groupName.setText(waypoint);
    }
    return convertView;
  }

  @Override
  public View getChildView(int groupPosition, int childPosition,
                           boolean isLastChild, View convertView, ViewGroup parent) {
    final HelpContactsModel emgContact = (HelpContactsModel) getChild(groupPosition,
        childPosition);
    if (emgContact != null) {
      ChildViewHolder childViewHolder;
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) settingsView.getActivity()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(childResourceId, null);
        childViewHolder = new ChildViewHolder(convertView);
        convertView.setTag(childViewHolder);
      } else {
        childViewHolder = (ChildViewHolder) convertView.getTag();
      }
      childViewHolder.memberName.setText(emgContact.getItemName());
      childViewHolder.memberPhone.setText(emgContact.getItemNumber());
      CharacterDrawable drawable = new CharacterDrawable(emgContact.getItemName().charAt(0),
          0xFF805781);
      childViewHolder.memberImage.setImageDrawable(drawable);
      childViewHolder.memberDelete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          Confirm.using(settingsView.getActivity()).ask("Do you want to delete this Contact?")
              .onPositive("Yes", new Dialog.OnClickListener() {
                @Override public void onClick(Dialog dialog, int which) {
                  settingsView.onDeleteContact(emgContact);
                }
              }).onNegative("No", new Dialog.OnClickListener() {
            @Override public void onClick(Dialog dialog, int which) {
            }
          }).build().show();
        }


      });
    }
    return convertView;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return false;
  }

  public static class ContactTypeHolder {
    private HashMap<String, List<HelpContactsModel>> hashMap;
    private String[] contactTypes;
    private Set<HelpContactsModel> allContacts;

    public String[] getContactTypes() {
      return contactTypes;
    }

    public HashMap<String, List<HelpContactsModel>> getHashMap() {
      return hashMap;
    }

    public ContactTypeHolder(Set<HelpContactsModel> listContacts) {
      this.allContacts = listContacts;
      hashMap = new HashMap<>();
    }

    public Set<HelpContactsModel> getAllContacts() {
      return this.allContacts;
    }

    public HashMap<String, List<HelpContactsModel>> buildEmergencyContacts() {
      listHelpContacts = allContacts;
      Set<String> contactTypesSet = new HashSet<>();
      for (HelpContactsModel helpContacts : listHelpContacts) {
        List<HelpContactsModel> typeList = hashMap.get(helpContacts.getItemType());
        if (typeList == null) {
          typeList = new ArrayList<>();
        }
        contactTypesSet.add(helpContacts.getItemType());
        typeList.add(helpContacts);
        hashMap.put(helpContacts.getItemType(), typeList);
      }
      contactTypes = contactTypesSet.toArray(new String[contactTypesSet.size()]);
      return hashMap;
    }
  }

  private class ParentViewHolder {
    public TextView groupName;

    public ParentViewHolder(View view) {
      groupName = (TextView) view.findViewById(R.id.tv_group_name);
    }
  }

  private class ChildViewHolder {
    public TextView memberName;
    public TextView memberPhone;
    public ImageView memberDelete;
    public ImageView memberImage;

    public ChildViewHolder(View view) {
      memberName = (TextView) view.findViewById(R.id.contact_name);
      memberPhone = (TextView) view.findViewById(R.id.contact_phone);
      memberImage = (ImageView) view.findViewById(R.id.contact_image);
      memberDelete = (ImageView) view.findViewById(R.id.contact_delete);
    }
  }
}
