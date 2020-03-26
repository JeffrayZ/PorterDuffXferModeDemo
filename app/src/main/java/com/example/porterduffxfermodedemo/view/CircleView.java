package com.example.porterduffxfermodedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.porterduffxfermodedemo.R;

/**
 * @ProjectName: PorterDuffXferModeDemo
 * @Package: com.example.porterduffxfermodedemo.view
 * @ClassName: CircleView
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2020/3/26 11:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/26 11:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class CircleView extends View {

    private Context context;
    private Bitmap bitmap;
    private Bitmap srcBitmap;
    private Bitmap dstBitmap;
    private Paint srcPaint;
    private Paint dstPaint;
    private Paint paint;

    public CircleView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        this.context = context;
        // 原图初始化
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        // 绘制原图的位图Bitmap
        srcBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        dstBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());

        paint = new Paint();
        srcPaint = new Paint();
        dstPaint = new Paint();
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        this.context = context;
        // 原图初始化
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        // 绘制原图的位图Bitmap
        srcBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        dstBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());

        paint = new Paint();
        srcPaint = new Paint();
        dstPaint = new Paint();
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        this.context = context;
        // 原图初始化
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        srcBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        dstBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());

        paint = new Paint();
        srcPaint = new Paint();
        dstPaint = new Paint();
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        this.context = context;
        // 原图初始化
        bitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);

        srcBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        dstBitmap = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());

        paint = new Paint();
        srcPaint = new Paint();
        dstPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int sc=canvas.saveLayer(0,0,getWidth(),getHeight(),paint);

//        // 第一步：画第一个图层
//        Canvas canvas1 = new Canvas(srcBitmap);
//        canvas1.drawBitmap(bitmap,0,0,srcPaint);
//        canvas1.drawColor(Color.TRANSPARENT);
//        canvas.drawBitmap(srcBitmap,0,0,paint);
//
//        // 第二步：设置混合模式
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
//
//        // 第三步：画第二个图层
//        Canvas canvas2 = new Canvas(dstBitmap);
//        canvas2.drawColor(Color.TRANSPARENT);
//        canvas2.drawCircle(dstBitmap.getWidth()/2,dstBitmap.getHeight()/2,dstBitmap.getHeight()/3,dstPaint);
//        canvas.drawBitmap(dstBitmap,0,0,paint);




        // 第一步：画第一个图层
        Canvas canvas2 = new Canvas(dstBitmap);
//        canvas2.drawColor(Color.TRANSPARENT);
        canvas2.drawCircle(dstBitmap.getWidth()/2,dstBitmap.getHeight()/2,dstBitmap.getHeight()/3,dstPaint);
        canvas.drawBitmap(dstBitmap,0,0,paint);

        // 第二步：设置混合模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 第三步：画第二个图层
        Canvas canvas1 = new Canvas(srcBitmap);
        canvas1.drawBitmap(bitmap,0,0,srcPaint);
//        canvas1.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(srcBitmap,0,0,paint);

        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }
}
