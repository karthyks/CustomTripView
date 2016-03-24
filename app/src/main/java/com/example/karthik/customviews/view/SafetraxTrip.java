package com.example.karthik.customviews.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.example.karthik.customviews.custom.AbstractTripView;
import com.example.karthik.customviews.R;

public class SafetraxTrip extends FrameLayout implements IRootViewCallback {

  private static final String TAG = SafetraxTrip.class.getSimpleName();
  private static final int CONTENT_TRIP_LIST = 0;
  private static final int CONTENT_TRIP_OVERVIEW = 0;
  private static final int CONTENT_WAYPOINT_DETAILS = 1;
  private static final int CONTENT_MAP_VIEW = 2;

  private ViewFlipper tripRootView;
  private TripOverView tripOverView;
  private WayPointOverView wayPointOverView;

  public SafetraxTrip(Context context) {
    super(context);
    init();
  }

  public SafetraxTrip(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public SafetraxTrip(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public SafetraxTrip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init();
  }

  private void init() {
    Log.d(TAG, "init: ");
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    this.setLayoutParams(layoutParams);
    this.setFocusableInTouchMode(true);
    this.tripOverView = new TripOverView(getContext(), this, R.layout.layout_trip_over_view, this);
    this.wayPointOverView = new WayPointOverView(getContext(), this, R.layout.layout_waypoint_view,
        this);
    this.addView(getViewContent());
  }

  private View getViewContent() {
    View view = LayoutInflater.from(getContext()).inflate(R.layout.view_safetrax_trip, this,
        false);
    tripRootView = (ViewFlipper) view.findViewById(R.id.flipper_trip_root);
    addViews();
    return view;
  }

  private void addViews() {
    tripRootView.addView(tripOverView.getView());
    tripRootView.addView(wayPointOverView.getView());
    displayChild(tripOverView, CONTENT_TRIP_OVERVIEW);
  }

  public void displayChild(AbstractTripView abstractTripView, int childIndex) {
    tripRootView.setDisplayedChild(childIndex);
    abstractTripView.onRenderView();
  }

  @Override public boolean onKeyDown(int keyCode, KeyEvent event) {
    Log.d(TAG, "onKeyDown: " + tripRootView.getDisplayedChild());
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      switch (tripRootView.getDisplayedChild()) {
        case CONTENT_TRIP_OVERVIEW:
          //displayChild(tripListView, CONTENT_TRIP_LIST);
          tripOverView.onDestroyView();
          break;
        case CONTENT_WAYPOINT_DETAILS:
          displayChild(tripOverView, CONTENT_TRIP_OVERVIEW);
          wayPointOverView.onDestroyView();
          return true;
        case CONTENT_MAP_VIEW:
          return true;
        default:
      }
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override public void onMapClick() {

  }

  @Override public void onCallDriver() {

  }

  @Override public void onTripStatusChanged(int status) {

  }

  @Override public void onDialogPositive(int dialogID) {
    switch (dialogID) {
      case IRootViewCallback.DIALOG_WAITING_FOR_CAB:
        Log.d(TAG, "onDialogPositive: ");
        break;
      case IRootViewCallback.DIALOG_BOARDED_CAB:
        break;
      case IRootViewCallback.DIALOG_REACHED_DESTINATION:
        break;
      default:
    }
  }

  @Override public void onDialogNegative(int dialogID) {
    switch (dialogID) {
      case IRootViewCallback.DIALOG_WAITING_FOR_CAB:
        Log.d(TAG, "onDialogNegative: ");
        break;
      case IRootViewCallback.DIALOG_BOARDED_CAB:
        break;
      case IRootViewCallback.DIALOG_REACHED_DESTINATION:
        break;
      default:
    }
  }

  @Override public void onWaypointClick(int position) {
    wayPointOverView.injectData(" " + position, String.valueOf(position));
    displayChild(wayPointOverView, CONTENT_WAYPOINT_DETAILS);
  }
}
