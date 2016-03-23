package com.example.karthik.customviews.view;

public interface IRootViewCallback {
  int WAITING_FOR_CAB = 0;
  int BOARDED_CAB = 1;
  int REACHED_DESTINATION = 2;

  int DIALOG_WAITING_FOR_CAB = 0;
  int DIALOG_BOARDED_CAB = 1;
  int DIALOG_REACHED_DESTINATION = 2;

  void onMapClick();

  void onCallDriver();

  void onTripStatusChanged(int status);

  void onDialogPositive(int dialogID);

  void onDialogNegative(int dialogID);

  void onWaypointClick(int position);
}
