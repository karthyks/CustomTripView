package com.example.karthik.customviews.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karthik.customviews.R;
import com.example.karthik.customviews.custom.AbstractTripView;

public class TripListView extends AbstractTripView {
  public static final String TAG = TripListView.class.getSimpleName();

  private TripListBuilder tripListBuilder;

  protected TripListView(Context context, ViewGroup rootView, int layoutID,
                         IRootViewCallback callback) {
    super(context, rootView, layoutID, callback);
    injectViews(getView());
  }

  private void injectViews(View view) {
    TextView textStaticHeader = (TextView) view.findViewById(R.id.txt_static_header);
    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.trip_list);
    tripListBuilder = new TripListBuilder(recyclerView, textStaticHeader)
        .with(getContext(), getCallback());
  }

  @Override public void onRenderView() {
    Log.d(TAG, "onRenderView: ");
  }

  public TripListBuilder getTripListBuilder() {
    return tripListBuilder;
  }

  @Override public void onDestroyView() {
    tripListBuilder = null;
  }
}
