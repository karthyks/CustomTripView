package com.example.karthik.customviews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.example.karthik.customviews.R;
import com.example.karthik.customviews.logging.LogDog;
import com.example.karthik.customviews.other.ExampleActivity;
import com.example.karthik.customviews.settings.SettingsHelpActivity;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = MainActivity.class.getSimpleName();

  private Button button;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Fabric.with(this, new Crashlytics());
    setContentView(R.layout.activity_main);
    injectViews();
    try {
      button.setOnClickListener(this);
    } catch (Exception e) {
      Crashlytics.logException(new Throwable(new LogDog(this).logAll(TAG)));
    }
  }

  private void injectViews() {
    Log.d(TAG, "injectViews: ");
    findViewById(R.id.button).setOnClickListener(this);
  }

  public static Intent getIntent(Context activity) {
    Log.d(TAG, "getIntent: " + activity.getClass().getPackage().getName());
    return new Intent(activity, MainActivity.class);
  }

  @Override public void onClick(View v) {
    new LogDog(this).logAll(TAG);
//    startActivity(ExampleActivity.getIntent(this));
    startActivity(new Intent(this, SettingsHelpActivity.class));
  }
}
