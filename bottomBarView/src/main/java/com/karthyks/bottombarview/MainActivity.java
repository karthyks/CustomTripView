package com.karthyks.bottombarview;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.karthyks.bottombarview.drawable.BBNDrawable;
import com.karthyks.bottombarview.views.BottomBarButton;
import com.karthyks.bottombarview.views.BottomBarView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = MainActivity.class.getSimpleName();
  private BottomBarButton bbnWaiting;
  private BottomBarButton bbnBoarded;
  private BottomBarButton bbnReached;

  private BottomBarView bottomBarView;
  private ScrollView scrollView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bottomBarView = (BottomBarView) findViewById(R.id.bottom_bar_nav);
    bottomBarView.addAsChild(R.layout.include_button_layout);
    scrollView = (ScrollView) findViewById(R.id.scrollView);
    scrollView.getViewTreeObserver().addOnScrollChangedListener(
        new ViewTreeObserver.OnScrollChangedListener() {
          int scrollY = 0;

          @Override
          public void onScrollChanged() {
            if (scrollView.getScrollY() > scrollY) {
              scrollY = scrollView.getScrollY();
              bottomBarView.setVisibility(View.INVISIBLE);
              Log.d(TAG, "onScrollChanged: upwards");
            } else {
              scrollY = scrollView.getScrollY();
              bottomBarView.setVisibility(View.VISIBLE);
              Log.d(TAG, "onScrollChanged: downwards");
            }
          }
        });
    injectViews();
  }

  private void injectViews() {
    bbnWaiting = (BottomBarButton) findViewById(R.id.bbn_waiting);
    bbnBoarded = (BottomBarButton) findViewById(R.id.bbn_boarded);
    bbnReached = (BottomBarButton) findViewById(R.id.bbn_reached);

    bbnWaiting.setButtonDrawables(new BBNDrawable(this, R.drawable.ic_local_taxi,
            BBNDrawable.NORMAL_STATE),
        new BBNDrawable(this, R.drawable.ic_local_taxi, BBNDrawable.PRESSED_STATE))
        .setButtonText("Waiting")
        .setTextColors(ContextCompat.getColor(this, R.color.bottomBarTextNormal), ContextCompat
            .getColor(this, R.color.bottomBarTextPressed))
        .build();
    bbnBoarded.setButtonDrawables(new BBNDrawable(this, R.drawable.ic_local_taxi,
            BBNDrawable.NORMAL_STATE),
        new BBNDrawable(this, R.drawable.ic_local_taxi, BBNDrawable.PRESSED_STATE))
        .setButtonText("Boarded")
        .setTextColors(ContextCompat.getColor(this, R.color.bottomBarTextNormal), ContextCompat
            .getColor(this, R.color.bottomBarTextPressed))
        .build();
    bbnReached.setButtonDrawables(new BBNDrawable(this, R.drawable.ic_local_taxi,
            BBNDrawable.NORMAL_STATE),
        new BBNDrawable(this, R.drawable.ic_local_taxi, BBNDrawable.PRESSED_STATE))
        .setButtonText("Reached")
        .setTextColors(ContextCompat.getColor(this, R.color.bottomBarTextNormal), ContextCompat
            .getColor(this, R.color.bottomBarTextPressed))
        .build();
    bbnWaiting.setOnClickListener(this);
    bbnBoarded.setOnClickListener(this);
    bbnReached.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    if (v.getId() == R.id.bbn_waiting) {
      if (bbnWaiting.isPressedState()) {
        bbnWaiting.setPressedState(false);
      } else {
        bbnWaiting.setPressedState(true);
      }
    } else if (v.getId() == R.id.bbn_boarded) {
      if (bbnBoarded.isPressedState()) {
        bbnBoarded.setPressedState(false);
      } else {
        bbnBoarded.setPressedState(true);
      }
    } else if (v.getId() == R.id.bbn_reached) {
      if (bbnReached.isPressedState()) {
        bbnReached.setPressedState(false);
      } else {
        bbnReached.setPressedState(true);
      }
    }
  }
}
