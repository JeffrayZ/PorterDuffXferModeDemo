package com.example.porterduffxfermodedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.porterduffxfermodedemo.R;

/**
 * @ProjectName: PorterDuffXferModeDemo
 * @Package: com.example.porterduffxfermodedemo.view
 * @ClassName: TestXfermodeView
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2020/3/26 10:18
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/26 10:18
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class TestXfermodeView extends View {
    private Context context;
    public TestXfermodeView(Context context) {
        super(context);
        this.context = context;
    }

    public TestXfermodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public TestXfermodeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 这里的canvas本身就存在一个默认的bitmap
        // 所以我们直接绘制任何东西都能显示出来
        super.onDraw(canvas);
        // 画笔抗锯齿
//        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画笔颜色
//        p.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        // 在画布上画圆
//        canvas.drawCircle(100,100,100,p);

        /**
         * 当前的画布背景也设置成透明
         */
        canvas.drawColor(Color.TRANSPARENT);


        Bitmap circle = getCircleBitmap();
        Bitmap rectangle = getRectangleBitmap();

        /**
         * 开启硬件离屏缓存
         */
        setLayerType(LAYER_TYPE_HARDWARE, null);
        Paint paint = new Paint();

        canvas.drawBitmap(rectangle,100,100,paint);


        // SRC 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        // DST 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST));
        // SRC_IN 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        // DST_IN 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        // XOR 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        // CLEAR 有效果
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        // SRC_OUT 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        // SRC_ATOP 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        // SRC_OVER 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        // DST_OUT 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        // DST_ATOP 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP));
        // DST_OVER 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OVER));
        // ADD 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.ADD));
        // MULTIPLY 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        // DARKEN 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DARKEN));
        // OVERLAY 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.OVERLAY));
        // SCREEN 有效果
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));



        canvas.drawBitmap(circle,0,0,paint);

    }

    private Bitmap getRectangleBitmap() {
        /**
         * bm1 在bitmap上面画正方形
         */
        Bitmap rectangle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas c1 = new Canvas(rectangle);
        Paint p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p1.setColor(ContextCompat.getColor(context,R.color.colorAccent));
        /**
         * 设置透明
         */
        c1.drawARGB(0, 0, 0, 0);
        c1.drawRect(0, 0, 200, 200, p1);
        return rectangle;
    }

    private Bitmap getCircleBitmap() {
        // 新建一个支持透明的位图 circle,这个bitmap是用来保存画的东西的
        Bitmap circle = Bitmap.createBitmap(200,200, Bitmap.Config.ARGB_8888);
        // 在画布上绘制这个bitmap，如果没有bitmap，我们绘制的任何东西都无法显示出来
        Canvas c = new Canvas(circle);
        // 设置画布颜色透明,，因为画布透明，所以我们只能看到绘制的圆
        c.drawARGB(0,0,0,0);
        // 画笔抗锯齿
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 画笔颜色
        p.setColor(ContextCompat.getColor(context, R.color.colorPrimary));
        // 在画布上画圆
        c.drawCircle(100,100,100,p);
        return circle;
    }
}
