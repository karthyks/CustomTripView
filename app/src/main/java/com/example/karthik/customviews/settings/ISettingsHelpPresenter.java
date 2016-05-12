package com.example.karthik.customviews.settings;

import android.net.Uri;

import com.example.karthik.customviews.model.HelpContactsModel;

public interface ISettingsHelpPresenter {
  String PATTERN = "^[+]?[0]?[0-9]{10,13}$";

  void onViewReady();

  void onEnterContact(String name, String phone);

  void getContactFromUri(Uri contactUri);

  void deleteContact(HelpContactsModel emgContact);
}
