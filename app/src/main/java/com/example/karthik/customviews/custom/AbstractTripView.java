package com.example.karthik.customviews.custom;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.karthik.customviews.view.IRootViewCallback;

public abstract class AbstractTripView {
  private Context context;
  private View view;
  private IRootViewCallback callback;

  protected AbstractTripView(Context context, ViewGroup rootView, int layoutID,
                             IRootViewCallback callback) {
    this.context = context;
    this.view = LayoutInflater.from(context).inflate(layoutID, rootView, false);
    this.callback = callback;
  }

  public abstract void onRenderView();

  public abstract void onDestroyView();

  public ActionBar getActionBar() {
    ActionBar actionBar = ((AppCompatActivity) context).getSupportActionBar();
    if (actionBar != null) {
      return actionBar;
    } else {
      return null;
    }
  }

  public View getView() {
    return view;
  }

  public Context getContext() {
    return context;
  }

  public IRootViewCallback getCallback() {
    return callback;
  }

  public AlertDialog.Builder alertDialog(String message,
                                            DialogInterface.OnClickListener onClickListener) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
    alertDialogBuilder.setPositiveButton("Yes", onClickListener)
        .setNegativeButton("No", onClickListener)
        .setMessage(message)
        .create();
    return alertDialogBuilder;
  }
}
