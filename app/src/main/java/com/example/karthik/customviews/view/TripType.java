package com.example.karthik.customviews.view;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.karthik.customviews.R;

public class TripType extends LinearLayout {
  public static final String TAG = TripType.class.getSimpleName();

  private TextView textViewTripTypeHeader;
  private TextView textViewPickUpPoint;
  private TextView textViewDriverName;
  private IRootViewCallback callback;
  private Context context;

  public TripType(Context context, IRootViewCallback callback) {
    super(context);
    this.context = context;
    this.callback = callback;
    init();
  }

  private void init() {
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
    this.setLayoutParams(layoutParams);
    this.addView(getViewContent());
  }

  private View getViewContent() {
    View view = LayoutInflater.from(context).inflate(R.layout.include_trip_type, this, false);
    textViewTripTypeHeader = (TextView) view.findViewById(R.id.txt_trip_type_header);
    textViewPickUpPoint = (TextView) view.findViewById(R.id.txt_pickup_point);
    textViewDriverName = (TextView) view.findViewById(R.id.txt_driver_name);
    view.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View v) {
        callback.onTripListItemClick("TRIP_ID");
      }
    });
    return view;
  }

  public TripType build(String tripType, String tripTime, String point, String driverName) {
    buildTripHeader(tripType, tripTime);
    buildPickUpPoint(point);
    buildDriverName(driverName);
    return this;
  }

  private void buildTripHeader(String tripType, String tripTime) {
    this.textViewTripTypeHeader.setText(String.format("%s at %s", tripType, tripTime));
  }

  private void buildPickUpPoint(String point) {
    this.textViewPickUpPoint.setText(String.format("%s%s", Html.fromHtml("<b>Point: </b>"), point));
  }

  private void buildDriverName(String driverName) {
    this.textViewDriverName.setText(String.format("%s%s", Html.fromHtml("<b>Driver:</b> "),
        driverName));
  }
}
