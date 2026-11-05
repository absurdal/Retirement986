package com.retirement986.widget;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import java.time.LocalDate;
import java.time.Period;

/** Draws original, Porsche-986-inspired instrumentation; no copyrighted assets required. */
public final class GaugeRenderer {
 private static final int RED=Color.rgb(200,35,36);
 private static final int DARK=Color.rgb(31,34,38);
 private static final int INK=Color.rgb(36,39,43);
 private static final int SILVER=Color.rgb(223,225,224);
 private static final Paint p=new Paint(Paint.ANTI_ALIAS_FLAG);
 private GaugeRenderer(){}
 private static void color(int c){p.reset();p.setAntiAlias(true);p.setColor(c);}
 private static void text(Canvas c,String s,float x,float y,float size,int color,boolean bold){
  color(color);p.setTypeface(bold?Typeface.create("sans-serif-condensed",Typeface.BOLD):Typeface.create("sans-serif-condensed",Typeface.NORMAL));
  p.setTextAlign(Paint.Align.CENTER);p.setTextSize(size);c.drawText(s,x,y,p);
 }
 private static void ring(Canvas c,float x,float y,float r){
  color(Color.rgb(10,12,15));c.drawCircle(x,y,r+8,p);
  color(Color.rgb(105,109,112));p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(6);c.drawCircle(x,y,r+4,p);
  color(SILVER);p.setShader(new RadialGradient(x-r*.25f,y-r*.3f,r*1.8f,new int[]{Color.WHITE,Color.rgb(229,230,229),Color.rgb(167,172,175)},null,Shader.TileMode.CLAMP));p.setStyle(Paint.Style.FILL);c.drawCircle(x,y,r,p);p.setShader(null);
  color(Color.rgb(103,107,110));p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(1.8f);c.drawCircle(x,y,r-3,p);p.setStyle(Paint.Style.FILL);
 }
 private static void ticks(Canvas c,float x,float y,float r,int count){
  for(int i=0;i<=count;i++){
   float a=(float)Math.toRadians(140+260.0*i/count);
   float x1=x+(float)Math.cos(a)*r*.79f,y1=y+(float)Math.sin(a)*r*.79f;
   float x2=x+(float)Math.cos(a)*r*(i%5==0?.91f:.87f),y2=y+(float)Math.sin(a)*r*(i%5==0?.91f:.87f);
   color(INK);p.setStrokeWidth(i%5==0?3.2f:1.5f);p.setStrokeCap(Paint.Cap.ROUND);c.drawLine(x1,y1,x2,y2,p);
  }
 }
 private static void needle(Canvas c,float x,float y,float r,float fraction){
  double a=Math.toRadians(140+260*Math.max(0,Math.min(1,fraction)));
  float dx=(float)Math.cos(a),dy=(float)Math.sin(a);
  color(RED);p.setStrokeWidth(Math.max(3,r*.035f));p.setStrokeCap(Paint.Cap.ROUND);
  c.drawLine(x-dx*r*.13f,y-dy*r*.13f,x+dx*r*.72f,y+dy*r*.72f,p);
  color(INK);c.drawCircle(x,y,r*.085f,p);color(RED);c.drawCircle(x,y,r*.045f,p);
 }
 private static void dial(Canvas c,float x,float y,float r,String label,String number,String sub,float fraction){
  ring(c,x,y,r);ticks(c,x,y,r,40);
  text(c,label,x,y-r*.46f,r*.16f,INK,true);
  text(c,number,x,y+r*.42f,r*.27f,INK,true);
  text(c,sub,x,y+r*.58f,r*.115f,Color.rgb(83,87,89),false);
  needle(c,x,y,r,fraction);
 }
 public static Bitmap draw(int w,int h,long days){
  Bitmap b=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
  Canvas c=new Canvas(b);c.drawColor(Color.TRANSPARENT);
  float s=Math.min(w/1000f,h/440f);
  c.save();c.translate((w-1000*s)/2,(h-440*s)/2);c.scale(s,s);
  color(DARK);c.drawRoundRect(new RectF(4,4,996,436),34,34,p);
  color(Color.rgb(11,12,14));p.setStyle(Paint.Style.STROKE);p.setStrokeWidth(9);c.drawRoundRect(new RectF(10,10,990,430),30,30,p);p.setStyle(Paint.Style.FILL);
  long remaining=Math.max(0,days);
  LocalDate today=LocalDate.now();
  Period period=today.isBefore(RetirementWidget.RETIREMENT)?Period.between(today,RetirementWidget.RETIREMENT):Period.ZERO;
  int months=period.getYears()*12+period.getMonths();
  float progress=1f-(float)Math.min(3650,remaining)/3650f; // configurable 10-year visual scale
  dial(c,184,226,153,"LATA / MIES.",period.getYears()+" L "+period.getMonths()+" M","DO EMERYTURY",1-Math.min(1,remaining/3650f));
  dial(c,500,216,193,"DNI DO EMERYTURY",Long.toString(remaining),"07 · 11 · 2028",progress);
  dial(c,816,226,153,"OSTATNI ROK",Long.toString(Math.min(365,remaining)),"DNI W OSTATNIM ROKU",1-Math.min(1,remaining/365f));
  text(c,"RETIREMENT 986",500,414,22,Color.rgb(190,193,196),true);
  c.restore();return b;
 }
}
