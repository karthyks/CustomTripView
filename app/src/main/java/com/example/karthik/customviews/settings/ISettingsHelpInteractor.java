package com.example.karthik.customviews.settings;

import android.net.Uri;

import com.example.karthik.customviews.model.HelpContactsModel;

import java.util.Set;

public interface ISettingsHelpInteractor {

  void getContactFromUri(Uri uri);

  Set<HelpContactsModel> getHelpContactsList();

  void removeContactFromList(HelpContactsModel emgContact);

  void addContact(String name, String phone, String selectedContactType);
}
