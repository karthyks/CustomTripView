package com.example.karthik.customviews.other;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class RecyclerViewPositionHelper {

  private static final String TAG = RecyclerViewPositionHelper.class.getSimpleName();
  final RecyclerView recyclerView;
  final RecyclerView.LayoutManager layoutManager;

  RecyclerViewPositionHelper(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
    this.layoutManager = recyclerView.getLayoutManager();
  }

  public static RecyclerViewPositionHelper createHelper(RecyclerView recyclerView) {
    if (recyclerView == null) {
      throw new NullPointerException("Recycler View is null");
    }
    return new RecyclerViewPositionHelper(recyclerView);
  }

  /**
   * Returns the adapter item count.
   *
   * @return The total number on items in a layout manager
   */
  public int getItemCount() {
    return layoutManager == null ? 0 : layoutManager.getItemCount();
  }

  public int getItemHeight(int position) {
    if (recyclerView.findViewHolderForAdapterPosition(position) != null) {
      return recyclerView.findViewHolderForAdapterPosition(position).itemView.getHeight();
    }
    return 0;
  }

  public String getHeaderText(int position) {
    String text;
    if (recyclerView.findViewHolderForAdapterPosition(position) != null) {
      text = getViewHolder(position).getTextViewHeader().getText().toString();
      return text;
    }
    return "";
  }

  public TextView getHeaderTextView(int position) {
    if (recyclerView.findViewHolderForAdapterPosition(position) != null) {
      return getViewHolder(position).getTextViewHeader();
    }
    return null;
  }

  public TextView getBottomHeaderTextView(int position) {
    if (recyclerView.findViewHolderForAdapterPosition(position) != null) {
      return getViewHolder(position).getTextViewBottomHeader();
    }
    return null;
  }

  public TripListAdapter.TripViewHolder getViewHolder(int position) {
    if (recyclerView.findViewHolderForAdapterPosition(position) != null) {
      return ((TripListAdapter.TripViewHolder) recyclerView.findViewHolderForAdapterPosition
          (position));
    }
    return null;
  }

  public void makeHeaderVisible(int position) {
    getHeaderTextView(position).setVisibility(View.VISIBLE);
  }

  public void makeHeaderInvisible(int position) {
    for (int i = 0; i < getItemCount(); i++) {
      if (getHeaderTextView(i) != null) {
        if (i != position) {
          getHeaderTextView(i).setVisibility(View.VISIBLE);
        } else {
          getHeaderTextView(i).setVisibility(View.INVISIBLE);
        }
      }
    }
  }

  public void makeVisibleBottomHeader(int position) {
    for (int i = 0; i < getItemCount(); i++) {
      if (getBottomHeaderTextView(i) != null) {
        if (i != position) {
          getBottomHeaderTextView(i).setVisibility(View.INVISIBLE);
        } else {
          getBottomHeaderTextView(i).setVisibility(View.VISIBLE);
        }
      }
    }
  }

  public void disableAllBottomHeader() {
    for (int i = 0; i < getItemCount(); i++) {
      if (getBottomHeaderTextView(i) != null) {
        getBottomHeaderTextView(i).setVisibility(View.INVISIBLE);
      }
    }
  }

  public int getTopHeaderStartPosition(int pos) {
    int[] top = new int[2];
    getHeaderTextView(pos).getLocationOnScreen(top);
    return top[1];
  }

  public int getTopHeaderEndPosition(int pos) {
    int[] top = new int[2];
    getHeaderTextView(pos).getLocationOnScreen(top);
    top[1] += getHeaderTextView(pos).getHeight();
    return top[1];
  }

  public int getBottomHeaderStartPosition() {
    int[] bottom = new int[2];
    getBottomHeaderTextView(findFirstVisibleItemPosition()).getLocationOnScreen(bottom);
    return bottom[1];
  }

  public int getBottomHeaderEndPosition() {
    int[] bottom = new int[2];
    getBottomHeaderTextView(findFirstVisibleItemPosition()).getLocationOnScreen(bottom);
    bottom[1] += getBottomHeaderTextView(findFirstVisibleItemPosition()).getHeight();
    return bottom[1];
  }

  public int getItemStartPosition(int position) {
    int[] bottom = new int[2];
    recyclerView.findViewHolderForAdapterPosition(position).itemView
        .getLocationOnScreen(bottom);
    return bottom[1];
  }

  public int getItemEndPosition(int position) {
    int[] bottom = new int[2];
    recyclerView.findViewHolderForAdapterPosition(position).itemView
        .getLocationOnScreen(bottom);
    bottom[1] += getItemHeight(position);
    return bottom[1];
  }

  /**
   * Returns the adapter position of the first visible view. This position does not include
   * adapter changes that were dispatched after the last layout pass.
   *
   * @return The adapter position of the first visible item or {@link RecyclerView#NO_POSITION} if
   * there aren't any visible items.
   */
  public int findFirstVisibleItemPosition() {
    final View child = findOneVisibleChild(0, layoutManager.getChildCount(), false, true);
    return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
  }

  /**
   * Returns the adapter position of the first fully visible view. This position does not include
   * adapter changes that were dispatched after the last layout pass.
   *
   * @return The adapter position of the first fully visible item or
   * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
   */
  public int findFirstCompletelyVisibleItemPosition() {
    final View child = findOneVisibleChild(0, layoutManager.getChildCount(), true, false);
    return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
  }

  /**
   * Returns the adapter position of the last visible view. This position does not include
   * adapter changes that were dispatched after the last layout pass.
   *
   * @return The adapter position of the last visible view or {@link RecyclerView#NO_POSITION} if
   * there aren't any visible items
   */
  public int findLastVisibleItemPosition() {
    final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, false, true);
    return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
  }

  /**
   * Returns the adapter position of the last fully visible view. This position does not include
   * adapter changes that were dispatched after the last layout pass.
   *
   * @return The adapter position of the last fully visible view or
   * {@link RecyclerView#NO_POSITION} if there aren't any visible items.
   */
  public int findLastCompletelyVisibleItemPosition() {
    final View child = findOneVisibleChild(layoutManager.getChildCount() - 1, -1, true, false);
    return child == null ? RecyclerView.NO_POSITION : recyclerView.getChildAdapterPosition(child);
  }

  View findOneVisibleChild(int fromIndex, int toIndex, boolean completelyVisible,
                           boolean acceptPartiallyVisible) {
    OrientationHelper helper;
    if (layoutManager.canScrollVertically()) {
      helper = OrientationHelper.createVerticalHelper(layoutManager);
    } else {
      helper = OrientationHelper.createHorizontalHelper(layoutManager);
    }

    final int start = helper.getStartAfterPadding();
    final int end = helper.getEndAfterPadding();
    final int next = toIndex > fromIndex ? 1 : -1;
    View partiallyVisible = null;
    for (int i = fromIndex; i != toIndex; i += next) {
      final View child = layoutManager.getChildAt(i);
      final int childStart = helper.getDecoratedStart(child);
      final int childEnd = helper.getDecoratedEnd(child);
      if (childStart < end && childEnd > start) {
        if (completelyVisible) {
          if (childStart >= start && childEnd <= end) {
            return child;
          } else if (acceptPartiallyVisible && partiallyVisible == null) {
            partiallyVisible = child;
          }
        } else {
          return child;
        }
      }
    }
    return partiallyVisible;
  }
}
