package com.example.karthik.customviews.settings;

public interface ISettingsCallback {
  int ERROR_VALID_PHONE_NUMBER = 400;
  int ERROR_ENTER_BOTH_FIELDS = 401;
  int SUCCESS_CONTACTS_ADDED = 200;

  void onError(int code);

  void onSuccess(int code);

  String getSelectedContactType();
}
