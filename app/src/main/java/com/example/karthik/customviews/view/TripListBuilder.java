package com.example.karthik.customviews.view;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.karthik.customviews.other.RecyclerViewPositionHelper;
import com.example.karthik.customviews.other.TripListAdapter;

public class TripListBuilder {
  private static final String TAG = TripListBuilder.class.getSimpleName();
  private RecyclerView recyclerView;
  private RecyclerView.LayoutManager mLayoutManager;
  private RecyclerViewPositionHelper mRecyclerViewHelper;
  private TextView textStaticHeader;
  private int headerEndPosition;
  private int headerStartPosition;

  public TripListBuilder(RecyclerView recyclerView, TextView textStaticHeader) {
    this.recyclerView = recyclerView;
    this.textStaticHeader = textStaticHeader;
  }

  public TripListBuilder with(Context context, IRootViewCallback callback) {
    recyclerView.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(context);
    recyclerView.setLayoutManager(mLayoutManager);
    recyclerView.setAdapter(new TripListAdapter(TripListAdapter.getListHeaders(), callback));
    textStaticHeader.setVisibility(View.VISIBLE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
        @Override public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX,
                                             int oldScrollY) {
          manageScroll(scrollY, oldScrollY);
        }
      });
    } else {
      recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
        @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
          super.onScrollStateChanged(recyclerView, newState);
        }

        @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
          super.onScrolled(recyclerView, dx, dy);
          manageScroll(dy, 0);
        }
      });
    }
    return this;
  }

  private void manageScroll(int y, int oldY) {
    mRecyclerViewHelper = RecyclerViewPositionHelper.createHelper(recyclerView);
    int firstVisibleItem, firstCompletelyVisibleItem;
    firstVisibleItem = mRecyclerViewHelper.findFirstVisibleItemPosition();
    firstCompletelyVisibleItem = mRecyclerViewHelper.findFirstCompletelyVisibleItemPosition();
    if (y > oldY) {
      Log.d(TAG, "manageScroll: Up");
      if (firstCompletelyVisibleItem == -1) {
        firstCompletelyVisibleItem = firstVisibleItem;
      }
      if (mRecyclerViewHelper.getItemStartPosition(firstCompletelyVisibleItem)
          <= headerStartPosition) {
        textStaticHeader.setText(mRecyclerViewHelper.getHeaderText(
            firstCompletelyVisibleItem));
        mRecyclerViewHelper.makeHeaderInvisible(firstCompletelyVisibleItem);
      } else {
        textStaticHeader.setText(mRecyclerViewHelper.getHeaderText(firstVisibleItem));
        mRecyclerViewHelper.makeHeaderInvisible(firstVisibleItem);
      }
      if (mRecyclerViewHelper.getBottomHeaderEndPosition() <= headerEndPosition &&
          mRecyclerViewHelper.getBottomHeaderEndPosition() >= headerStartPosition) {
        mRecyclerViewHelper.makeVisibleBottomHeader(firstVisibleItem);
        textStaticHeader.setVisibility(View.INVISIBLE);
      } else {
        mRecyclerViewHelper.disableAllBottomHeader();
        textStaticHeader.setVisibility(View.VISIBLE);
      }
    } else {
      Log.d(TAG, "manageScroll: Down");
      if (firstCompletelyVisibleItem == -1) {
        firstCompletelyVisibleItem = firstVisibleItem + 1;
      }
      try {
        if (headerEndPosition <= mRecyclerViewHelper.getTopHeaderStartPosition(firstVisibleItem)) {
          textStaticHeader.setVisibility(View.INVISIBLE);
        }

        if (mRecyclerViewHelper.getTopHeaderStartPosition(firstCompletelyVisibleItem) >=
            headerEndPosition) {
          mRecyclerViewHelper.makeHeaderInvisible(firstVisibleItem);
        } else {
          textStaticHeader.setVisibility(View.INVISIBLE);
          mRecyclerViewHelper.makeHeaderVisible(firstCompletelyVisibleItem);
        }

        if (mRecyclerViewHelper.getHeaderTextView(firstCompletelyVisibleItem).getVisibility() ==
            View.VISIBLE && (firstCompletelyVisibleItem - 1) > 0) {
          mRecyclerViewHelper.makeVisibleBottomHeader(firstCompletelyVisibleItem - 1);
        }

        if (mRecyclerViewHelper.getBottomHeaderEndPosition() >= headerEndPosition) {
          mRecyclerViewHelper.disableAllBottomHeader();
          textStaticHeader.setText(mRecyclerViewHelper.getHeaderText(firstVisibleItem));
          textStaticHeader.setVisibility(View.VISIBLE);
        }
      } catch (Exception e) {
        Log.d("Scrolling exception", "manageScroll: " + e.toString());
      }
    }
  }

  public void getHeaderEndPosition() {
    int[] pos = new int[2];
    textStaticHeader.getLocationOnScreen(pos);
    pos[1] += textStaticHeader.getHeight();
    headerEndPosition = pos[1];
    Log.d(TAG, "getHeaderEndPosition: " + pos[1]);
  }
}
