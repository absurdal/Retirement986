package com.retirement986.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class RetirementWidget extends AppWidgetProvider {
 public static final LocalDate RETIREMENT = LocalDate.of(2028,11,7);
 @Override public void onUpdate(Context context, AppWidgetManager manager, int[] ids) {
  for(int id: ids) update(context,manager,id);
 }
 @Override public void onAppWidgetOptionsChanged(Context c,AppWidgetManager m,int id,Bundle opts){ update(c,m,id); }
 @Override public void onReceive(Context c, Intent intent) {
  super.onReceive(c,intent);
  String action=intent.getAction();
  if(Intent.ACTION_DATE_CHANGED.equals(action)||Intent.ACTION_TIME_CHANGED.equals(action)||Intent.ACTION_TIMEZONE_CHANGED.equals(action)){
   AppWidgetManager m=AppWidgetManager.getInstance(c);
   for(int id:m.getAppWidgetIds(new ComponentName(c,RetirementWidget.class))) update(c,m,id);
  }
 }
 public static void update(Context c,AppWidgetManager m,int id){
  Bundle opts=m.getAppWidgetOptions(id);
  int w=Math.max(250,opts.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH,300));
  int h=Math.max(130,opts.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT,150));
  // Render within a bounded bitmap to avoid RemoteViews binder limits.
  float ratio=(float)w/h;
  int pxW=Math.min(1000,Math.max(600,w*2));
  int pxH=Math.min(500,Math.max(240,Math.round(pxW/ratio)));
  long days=ChronoUnit.DAYS.between(LocalDate.now(),RETIREMENT);
  Bitmap bmp=GaugeRenderer.draw(pxW,pxH,days);
  RemoteViews views=new RemoteViews(c.getPackageName(),R.layout.widget_layout);
  views.setImageViewBitmap(R.id.gauges,bmp);
  Intent open=new Intent(c,MainActivity.class);
  PendingIntent pi=PendingIntent.getActivity(c,0,open,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);
  views.setOnClickPendingIntent(R.id.gauges,pi);
  m.updateAppWidget(id,views);
  bmp.recycle();
 }
}
