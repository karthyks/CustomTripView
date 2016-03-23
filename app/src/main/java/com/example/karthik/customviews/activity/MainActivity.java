package com.example.karthik.customviews.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.karthik.customviews.R;
import com.example.karthik.customviews.other.ExampleActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = MainActivity.class.getSimpleName();

  private Button button;
  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    injectViews();
  }

  private void injectViews() {
    Log.d(TAG, "injectViews: ");
    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(this);
  }

  public static Intent getIntent(Context activity) {
    Log.d(TAG, "getIntent: " + activity.getClass().getPackage().getName());
    return new Intent(activity, MainActivity.class);
  }

  @Override public void onClick(View v) {
    startActivity(ExampleActivity.getIntent(this));
  }
}
