package com.example.karthik.customviews.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.karthik.customviews.R;

public class CustomRoundedButton extends Button implements View.OnTouchListener {

  private int buttonBackgroundImage;
  private String buttonText;
  private int buttonTextSize;
  private CustomButtonDrawable customButtonDrawableNormal;
  private CustomButtonDrawable customButtonDrawablePressed;

  public CustomRoundedButton(Context context) {
    super(context);
  }

  public CustomRoundedButton(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(attrs);
  }

  public CustomRoundedButton(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(attrs);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public CustomRoundedButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(attrs);
  }

  private void init(AttributeSet attrs) {
    TypedArray atrArr = getContext().getTheme().obtainStyledAttributes(attrs,
        R.styleable.CustomRoundedButton, 0, 0);
    buttonBackgroundImage = atrArr.getResourceId(R.styleable.CustomRoundedButton_button_background,
        0);
    buttonText = atrArr.getString(R.styleable.CustomRoundedButton_button_text);
    buttonTextSize = atrArr.getInteger(R.styleable.CustomRoundedButton_button_text_size, 20);
    if(buttonText == null) {
      buttonText = "";
    }
    atrArr.recycle();
  }

  private void renderButtons(int width, int height) {
    customButtonDrawableNormal = new CustomButtonDrawable(getResources(), buttonBackgroundImage,
        Color.GRAY, width, height, buttonText, buttonTextSize, CustomButtonDrawable.NORMAL_STATE);
    customButtonDrawablePressed = new CustomButtonDrawable(getResources(), buttonBackgroundImage,
        Color.YELLOW, width, height, buttonText, buttonTextSize,
        CustomButtonDrawable.PRESSED_STATE);
    if (buttonBackgroundImage != 0) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(customButtonDrawableNormal);
      } else {
        setBackgroundDrawable(customButtonDrawableNormal);
      }
      setOnTouchListener(this);
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    renderButtons(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
  }

  @Override public boolean onTouch(View v, MotionEvent event) {
    if (event.getAction() == MotionEvent.ACTION_DOWN) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(customButtonDrawablePressed);
      } else {
        setBackgroundDrawable(customButtonDrawablePressed);
      }
    }
    if (event.getAction() == MotionEvent.ACTION_UP) {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
        setBackground(customButtonDrawableNormal);
      } else {
        setBackgroundDrawable(customButtonDrawableNormal);
      }
    }
    return false;
  }
}
