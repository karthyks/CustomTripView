package com.example.karthik.customviews.other;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.karthik.customviews.R;
import com.example.karthik.customviews.activity.MainActivity;

public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {

  private static final String TAG = ExampleActivity.class.getSimpleName();
  private static final String EXTRA_CLASS = "extra_class";
  private Button button;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_example);
    //injectViews();
  }

  private void injectViews() {
    button = (Button) findViewById(R.id.button);
    button.setOnClickListener(this);
  }

  @Override public void onClick(View v) {
    startActivity(MainActivity.getIntent(this));
  }

  public static Intent getIntent(Context activity) {
    Log.d(TAG, "getIntent: " + activity.getClass().getSimpleName());
    Intent intent = new Intent();
    intent.putExtra(EXTRA_CLASS, activity.getClass());
    Class<?> c = null;
    try {
      c = Class.forName(activity.getClass().getPackage().getName() + "." + activity
          .getClass().getSimpleName());
      Log.d(TAG, "Class<?>: " + c );
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return new Intent(activity, ExampleActivity.class);
  }
}
