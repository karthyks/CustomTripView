package com.example.karthik.customviews.settings;

import android.net.Uri;

import com.example.karthik.customviews.model.HelpContactsModel;

public class SettingsHelpPresenter implements ISettingsHelpPresenter {

  private ISettingsHelpView settingsHelpView;
  private ISettingsHelpInteractor settingsHelpInteractor;
  private ISettingsCallback callback;

  public SettingsHelpPresenter(ISettingsHelpView settingsHelpView,
                               ISettingsHelpInteractor settingsHelpInteractor,
                               ISettingsCallback callback) {
    this.settingsHelpView = settingsHelpView;
    this.settingsHelpInteractor = settingsHelpInteractor;
    this.callback = callback;
  }

  @Override
  public void onViewReady() {
    settingsHelpView.updateListView(settingsHelpInteractor.getHelpContactsList());
  }

  @Override
  public void getContactFromUri(Uri contactUri) {
    settingsHelpInteractor.getContactFromUri(contactUri);
    settingsHelpView.updateListView(settingsHelpInteractor.getHelpContactsList());
  }

  @Override
  public void deleteContact(HelpContactsModel emgContact) {
    settingsHelpInteractor.removeContactFromList(emgContact);
    settingsHelpView.updateListView(settingsHelpInteractor.getHelpContactsList());
  }

  @Override
  public void onEnterContact(String name, String phone) {
    if ((phone.matches(PATTERN) && !name.isEmpty())) {
      settingsHelpInteractor.addContact(name, phone, callback.getSelectedContactType());
      settingsHelpView.updateListView(settingsHelpInteractor.getHelpContactsList());
      callback.onSuccess(ISettingsCallback.SUCCESS_CONTACTS_ADDED);
    } else if (name.isEmpty() || phone.isEmpty()) {
      callback.onError(ISettingsCallback.ERROR_ENTER_BOTH_FIELDS);
    } else {
      callback.onError(ISettingsCallback.ERROR_VALID_PHONE_NUMBER);
    }
  }
}
