package com.example.karthik.customviews.custom;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CustomButtonDrawable extends Drawable {
  public static final int NORMAL_STATE = 0;
  public static final int PRESSED_STATE = 1;
  private Paint paint;
  private Bitmap bitmap;
  private int width;
  private int height;
  private String buttonText;
  private int textSize;
  private int buttonState;

  public CustomButtonDrawable(Resources res, int drawable, int color, int width, int height,
                              String buttonText, int buttonTextSize, int buttonState) {
    this.width = width;
    this.height = height;
    this.buttonText = buttonText;
    this.textSize = buttonTextSize;
    this.buttonState = buttonState;
    paint = new Paint();
    paint.setColor(color);
    paint.setStyle(Paint.Style.FILL);
    paint.setAntiAlias(true);
    bitmap = BitmapFactory.decodeResource(res, drawable);
  }

  @Override public void draw(Canvas canvas) {
    int circleX = width / 2;
    int circleY = height / 3;
    int circleRadius = width / 3;
    canvas.drawCircle(circleX, circleY, circleRadius, paint);
    Paint bitmapPaint = new Paint();
    bitmapPaint.setColor(Color.WHITE);
    bitmapPaint.setStyle(Paint.Style.FILL);
    bitmapPaint.setAntiAlias(true);
    int startRect = circleX - circleRadius;
    if(buttonState == NORMAL_STATE) {
      float[] colorTransform = {
          0, 0f, 0, 0, 0,
          0, 0, 0f, 0, 0,
          0, 0, 0, 0f, 0,
          0, 0, 0, 1f, 0
      };
      ColorMatrix colorMatrix  = new ColorMatrix();
      colorMatrix.setSaturation(0f);
      colorMatrix.set(colorTransform);
      ColorMatrixColorFilter colorFilter = new ColorMatrixColorFilter(colorMatrix);
      bitmapPaint.setColorFilter(colorFilter);
    }
    canvas.drawBitmap(bitmap, new Rect(0, 0, width, height),
        new Rect(startRect, 0, width - startRect, height - height / 3), bitmapPaint);

    Paint textPaint = new Paint();
    textPaint.setColor(Color.WHITE);
    textPaint.setStyle(Paint.Style.FILL);
    textPaint.setTextSize(textSize);
    textPaint.setAntiAlias(true);
    if(buttonText.length() < 5) {
      canvas.drawText(buttonText, startRect * 1.75f, (height - height / 3) + textSize, textPaint);
    } else {
      canvas.drawText(buttonText, startRect / 1.75f, (height - height / 3) + textSize, textPaint);
    }
  }

  @Override public void setAlpha(int alpha) {

  }

  @Override public void setColorFilter(ColorFilter colorFilter) {

  }

  @Override public int getOpacity() {
    return 0;
  }
}
