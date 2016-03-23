package com.example.karthik.customviews.other;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.karthik.customviews.R;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;

public class GridImageAdapter extends BaseAdapter {

  private Context context;

  public GridImageAdapter(Context context) {
    this.context = context;
  }

  @Override public int getCount() {
    return cabPicsUrl().size();
  }

  @Override public Object getItem(int position) {
    return null;
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ImageView imageView;

    if (convertView == null) {
      imageView = new ImageView(context);
      imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      imageView.setPadding(8, 8, 8, 8);
    }
    else
    {
      imageView = (ImageView) convertView;
    }
    Picasso.with(context).load(cabPicsUrl().get(position)).into(imageView);
    return imageView;
  }

  private static List<Integer> cabPicsUrl() {
    List<Integer> cabUrl = new LinkedList<>();
    cabUrl.add(R.drawable.ic_taxi);
    cabUrl.add(R.drawable.ic_taxi);
    cabUrl.add(R.drawable.ic_taxi);
    return cabUrl;
  }
}
