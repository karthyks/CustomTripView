package com.example.karthik.customviews.settings;

import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import com.example.karthik.customviews.model.HelpContactsModel;

import java.util.Set;

public interface ISettingsHelpView {

  void injectViews();

  void updateListView(Set<HelpContactsModel> helpContactList);

  void enterContact();

  void importContact();

  ExpandableListView getExpandableListView();

  AppCompatActivity getActivity();

  void onDeleteContact(HelpContactsModel emgContact);
}
