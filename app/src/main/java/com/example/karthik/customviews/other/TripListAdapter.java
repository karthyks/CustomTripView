package com.example.karthik.customviews.other;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karthik.customviews.R;
import com.example.karthik.customviews.view.IRootViewCallback;

import java.util.LinkedList;
import java.util.List;

public class TripListAdapter extends RecyclerView.Adapter<TripListAdapter.TripViewHolder> {

  private List<String> headerList;
  private IRootViewCallback callback;
  private Context context;
  private View view;

  public TripListAdapter(List<String> headerList, IRootViewCallback callback) {
    this.headerList = headerList;
    this.callback = callback;
  }

  @Override public TripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_trip_row_item,
        parent, false);
    TripViewHolder tripViewHolder = new TripViewHolder(view);
    return tripViewHolder;
  }

  @Override public void onBindViewHolder(TripViewHolder holder, int position) {
    holder.textViewHeader.setText(headerList.get(position));
    holder.textViewBottomHeader.setText(headerList.get(position));
    if(position % 2 == 0) {
      holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.list_light));
    } else {
      holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.list_dark));
    }
  }

  @Override public int getItemCount() {
    return headerList.size();
  }

  public static class TripViewHolder extends RecyclerView.ViewHolder {

    private TextView textViewHeader;
    private TextView textViewBottomHeader;
    private View cardView1;
    private View cardView2;

    public TripViewHolder(View itemView) {
      super(itemView);
      textViewHeader = (TextView) itemView.findViewById(R.id.txt_header);
      textViewBottomHeader = (TextView) itemView.findViewById(R.id.txt_header_bottom);
      cardView1 = itemView.findViewById(R.id.card_1);
      cardView2 = itemView.findViewById(R.id.card_2);
    }

    public TextView getTextViewHeader() {
      return textViewHeader;
    }

    public TextView getTextViewBottomHeader() {
      return textViewBottomHeader;
    }
  }

  public static List<String> getListHeaders() {
    List<String> strings = new LinkedList<>();
    strings.add("Header0");
    strings.add("Header1");
    strings.add("Header2");
    strings.add("Header3");
    strings.add("Header4");
    strings.add("Header5");
    strings.add("Header6");
    strings.add("Header7");
    strings.add("Header8");
    strings.add("Header9");
    return strings;
  }
}
