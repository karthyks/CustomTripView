package com.example.karthik.customviews.view;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.karthik.customviews.custom.AbstractTripView;
import com.example.karthik.customviews.R;
import com.example.karthik.customviews.custom.CircleTransformation;
import com.example.karthik.customviews.other.GridImageAdapter;
import com.karthyks.bottombarview.drawable.BBNDrawable;
import com.karthyks.bottombarview.views.BottomBarButton;
import com.karthyks.bottombarview.views.BottomBarView;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class TripOverView extends AbstractTripView implements View.OnClickListener,
    NestedScrollView.OnScrollChangeListener, AdapterView.OnItemClickListener {

  private static final String TAG = TripOverView.class.getSimpleName();
  private static final String USER_IMAGE = "http://www.twiki" +
      ".org/p/pub/Main/UserProfileHeader/default-user-profile.jpg";
  private ListView listViewWaypointsList;
  private FloatingActionButton fabMapView;

  private Button btnMap;
  private TextView textViewTripInfo;
  private TextView textViewHeader;
  private NestedScrollView nestedScrollView;
  private CollapsingToolbarLayout collapsingToolbar;
  private GridView gridView;
  private ImageView driverImage;

  private BottomBarButton bbnWaiting;
  private BottomBarButton bbnBoarded;
  private BottomBarButton bbnReached;

  private BottomBarView bottomBarView;

  public TripOverView(Context context, ViewGroup rootView, int layoutID,
                      IRootViewCallback callback) {
    super(context, rootView, layoutID, callback);
    injectViews(getView());
  }

  @Override public void onRenderView() {
    getActionBar().hide();
    fabMapView.setOnClickListener(this);
    gridView.setAdapter(new GridImageAdapter(getContext()));
    Picasso.with(getContext()).load(USER_IMAGE)
        .transform(new CircleTransformation())
        .into(driverImage);
    List<WayPoint> waypoints = new LinkedList<>();
    for (int i = 0; i < 6; i++) {
      WayPoint wayPoint = new WayPoint();
      wayPoint.setName("Waypoint " + i);
      wayPoint.setTime("80:80");
      waypoints.add(wayPoint);
    }
    listViewWaypointsList.setAdapter(new WaypointAdapter(getContext(), waypoints));
    listViewWaypointsList.setOnItemClickListener(this);
    unCollapseListView(listViewWaypointsList);
  }

  private void injectViews(View view) {
    collapsingToolbar = (CollapsingToolbarLayout) view.findViewById(R.id.collapse_toolbar);
    btnMap = (Button) view.findViewById(R.id.btn_map);
    btnMap.setOnClickListener(this);
    driverImage = (ImageView) view.findViewById(R.id.user_display_image);
    textViewHeader = (TextView) view.findViewById(R.id.text_trip_header);
    textViewTripInfo = (TextView) view.findViewById(R.id.header_trip_info);
    nestedScrollView = (NestedScrollView) view.findViewById(R.id.nested_scroll_view);
    nestedScrollView.setOnScrollChangeListener(this);
    listViewWaypointsList = (ListView) view.findViewById(R.id.list_waypoints);
    fabMapView = (FloatingActionButton) view.findViewById(R.id.fab_map);
    gridView = (GridView) view.findViewById(R.id.grid_cab_pics);

    bottomBarView = (BottomBarView) view.findViewById(R.id.bottom_bar);
    bottomBarView.setBgColor(Color.GRAY);
    bottomBarView.addAsChild(R.layout.include_bottom_bar_buttons);

    bbnWaiting = (BottomBarButton) view.findViewById(R.id.btn_waiting);
    bbnBoarded = (BottomBarButton) view.findViewById(R.id.btn_boarded);
    bbnReached = (BottomBarButton) view.findViewById(R.id.btn_reached);
    buildBottomBarButtons();
  }

  private void buildBottomBarButtons() {
    bbnWaiting.setButtonDrawables(new BBNDrawable(getContext(),R.drawable.ic_waiting_pressed,
            BBNDrawable.NORMAL_STATE), new BBNDrawable(getContext(), R.drawable.ic_waiting_pressed,
        BBNDrawable.PRESSED_STATE))
        .setButtonText("Waiting")
        .setTextColors(ContextCompat.getColor(getContext(),
            com.karthyks.bottombarview.R.color.bottomBarTextNormal),
            ContextCompat.getColor(getContext(),
                com.karthyks.bottombarview.R.color.bottomBarTextPressed))
        .setBgColor(Color.GRAY)
        .build();
    bbnBoarded.setButtonDrawables(new BBNDrawable(getContext(),
            com.karthyks.bottombarview.R.drawable.ic_local_taxi,
            BBNDrawable.NORMAL_STATE),
        new BBNDrawable(getContext(), com.karthyks.bottombarview.R.drawable.ic_local_taxi,
            BBNDrawable.PRESSED_STATE))
        .setButtonText("Boarded")
        .setTextColors(ContextCompat.getColor(getContext(),
            com.karthyks.bottombarview.R.color.bottomBarTextNormal),
            ContextCompat.getColor(getContext(),
                com.karthyks.bottombarview.R.color.bottomBarTextPressed))
        .build();
    bbnReached.setButtonDrawables(new BBNDrawable(getContext(),
            com.karthyks.bottombarview.R.drawable.ic_local_taxi,
            BBNDrawable.NORMAL_STATE),
        new BBNDrawable(getContext(), com.karthyks.bottombarview.R.drawable.ic_local_taxi,
            BBNDrawable.PRESSED_STATE))
        .setButtonText("Reached")
        .setTextColors(ContextCompat.getColor(getContext(),
            com.karthyks.bottombarview.R.color.bottomBarTextNormal),
            ContextCompat.getColor(getContext(),
                com.karthyks.bottombarview.R.color.bottomBarTextPressed))
        .build();
    bbnWaiting.setOnClickListener(this);
    bbnBoarded.setOnClickListener(this);
    bbnReached.setOnClickListener(this);
  }

  public void unCollapseListView(ListView listView) {
    BaseAdapter adapter = (BaseAdapter) listView.getAdapter();
    if (adapter == null) {
      return;
    }
    ViewGroup vg = listView;
    int totalHeight = 0;
    for (int i = 0; i < adapter.getCount(); i++) {
      View listItem = adapter.getView(i, null, vg);
      listItem.measure(0, 0);
      totalHeight += listItem.getMeasuredHeight();
    }

    ViewGroup.LayoutParams par = listView.getLayoutParams();
    par.height = totalHeight + (listView.getDividerHeight() * (adapter.getCount() - 1));
    listView.setLayoutParams(par);
    listView.requestLayout();
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.fab_map) {
      alertDialog("Do you want to go to Map Screen", new DialogInterface.OnClickListener() {
        @Override public void onClick(DialogInterface dialog, int which) {
          switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
              getCallback().onDialogPositive(IRootViewCallback.DIALOG_WAITING_FOR_CAB);
              break;
            case DialogInterface.BUTTON_NEGATIVE:
              Log.d(TAG, "onClick: No");
              getCallback().onDialogNegative(IRootViewCallback.DIALOG_WAITING_FOR_CAB);
              break;
            default:
          }
        }
      }).show();
    }

    if (v.getId() == R.id.btn_waiting) {
      if (bbnWaiting.isPressedState()) {
        bbnWaiting.setPressedState(false);
      } else {
        bbnWaiting.setPressedState(true);
      }
    } else if (v.getId() == R.id.btn_boarded) {
      if (bbnBoarded.isPressedState()) {
        bbnBoarded.setPressedState(false);
      } else {
        bbnBoarded.setPressedState(true);
      }
    } else if (v.getId() == R.id.btn_reached) {
      if (bbnReached.isPressedState()) {
        bbnReached.setPressedState(false);
      } else {
        bbnReached.setPressedState(true);
      }
    }

  }

  @Override public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX,
                                       int oldScrollY) {
    Log.d(TAG, "onScrollChange: " + scrollY + " : " + oldScrollY);
    int tripHeaderWindowPos[] = new int[2];
    textViewTripInfo.getLocationOnScreen(tripHeaderWindowPos);
    if (tripHeaderWindowPos[1] <= 120) {
      collapsingToolbar.setTitle(textViewTripInfo.getText());
      textViewTripInfo.setVisibility(View.INVISIBLE);
      btnMap.setVisibility(View.VISIBLE);
    } else {
      collapsingToolbar.setTitle("");
      textViewTripInfo.setVisibility(View.VISIBLE);
      btnMap.setVisibility(View.INVISIBLE);
    }

    if(scrollY > oldScrollY) {
      bottomBarView.setVisibility(View.INVISIBLE);
    } else {
      bottomBarView.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public void onDestroyView() {
    fabMapView.setOnClickListener(null);
    gridView.setAdapter(null);
    listViewWaypointsList.setAdapter(null);
    listViewWaypointsList.setOnItemClickListener(null);
//    bbnWaiting = null;
//    bbnBoarded = null;
//    bbnReached = null;
//    bottomBarView = null;
  }

  @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    getCallback().onWaypointClick(position);
  }

  private class WaypointAdapter extends BaseAdapter {
    List<WayPoint> waypoints;
    Context context;

    public WaypointAdapter(Context context, List<WayPoint> waypoints) {
      this.waypoints = waypoints;
      this.context = context;
    }

    @Override public int getCount() {
      return waypoints.size();
    }

    @Override public Object getItem(int position) {
      return waypoints.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
      WayPointRenderer wayPointRenderer;
      final WayPoint wayPoint = (WayPoint) getItem(position);
      if (convertView == null) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(
            Context.LAYOUT_INFLATER_SERVICE);
        int viewId = R.layout.waypoint_row;
        convertView = inflater.inflate(viewId, parent, false);
        wayPointRenderer = new WayPointRenderer(convertView);
        convertView.setTag(wayPointRenderer);
      } else {
        wayPointRenderer = (WayPointRenderer) convertView.getTag();
      }
      wayPointRenderer.renderView(wayPoint);
      return convertView;
    }

    public class WayPointRenderer {
      public TextView stopName;
      public TextView scheduledTime;

      public WayPointRenderer(View view) {
        stopName = (TextView) view.findViewById(R.id.text_waypoint_name);
        scheduledTime = (TextView) view.findViewById(R.id.text_waypoint_time);
      }

      public void renderView(WayPoint wayPoint) {
        stopName.setText(wayPoint.getName());
        scheduledTime.setText(wayPoint.getTime());
      }
    }
  }
}
