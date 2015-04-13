/*
 * Copyright (C) 2015 SharkAndroid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package com.actions.tools;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.SensorEvent;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class SensorHost extends View implements View.OnTouchListener {
	
  Display display = null;
  RectF rectf = null;
  Paint paint = null;
  float vX = 0.0F;
  float vY = 0.0F;
  float vZ = 0.0F;

  public SensorHost(Context paramContext, AttributeSet paramAttributeSet) {
    this(paramContext, paramAttributeSet, 0);
  }

  public SensorHost(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
    super(paramContext, paramAttributeSet, paramInt);
    
    display = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay();
    paint = new Paint();
    rectf = new RectF();
  }

  @Override
  public void onDraw(Canvas paramCanvas) {
    super.onDraw(paramCanvas);
    
    int i = getWidth();
    int j = getHeight();
    paramCanvas.translate(i / 2, j / 2);
    paint.setAntiAlias(true);
    paint.setColor(-1);
    paint.setStyle(Paint.Style.STROKE);
    paint.setAlpha(255);
    int k = Math.min(i, j);
    rectf.left = (-k / 2);
    rectf.top = (-k / 2);
    rectf.right = (k / 2);
    rectf.bottom = (k / 2);
    paramCanvas.drawArc(rectf, 0.0F, 360.0F, false, paint);
    paramCanvas.drawLine(-k / 2, 0.0F, k / 2, 0.0F, paint);
    paramCanvas.drawLine(0.0F, -k / 2, 0.0F, k / 2, paint);
    int m = 90 * display.getRotation();
    if (m == 90) {
      float f2 = vX;
      vX = (-vY);
      vY = f2;
    }
    if (m == 180) {
      vX = (-vX);
      vY = (-vY);
    }
    if (m == 270) {
      float f1 = vX;
      vX = vY;
      vY = (-f1);
    }
    paint.setStyle(Paint.Style.FILL);
    paramCanvas.drawCircle(-i / 2 * vX / 10.0F, j / 2 * vY / 10.0F, 20.0F - vZ, paint);
  }

  protected void onMeasure(int paramInt1, int paramInt2) {
    int i = View.MeasureSpec.getSize(paramInt2);
    int j = 4 * Math.min(View.MeasureSpec.getSize(paramInt1), i) / 5;
    setMeasuredDimension(j, j);
  }

  public void onSensorChanged(SensorEvent paramSensorEvent) {
    if (paramSensorEvent.values.length == 3) {
      vX = paramSensorEvent.values[0];
      vY = paramSensorEvent.values[1];
      vZ = paramSensorEvent.values[2];
      invalidate();
    }
  }

  @Override
  public boolean onTouch(View v, MotionEvent event) {
	  // TODO Auto-generated method stub
	  return false;
  }
}