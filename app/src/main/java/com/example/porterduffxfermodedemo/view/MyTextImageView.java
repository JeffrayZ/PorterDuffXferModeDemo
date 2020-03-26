package com.example.porterduffxfermodedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.porterduffxfermodedemo.R;

/**
 * @ProjectName: PorterDuffXferModeDemo
 * @Package: com.example.porterduffxfermodedemo.view
 * @ClassName: MyTextImageView
 * @Description: 图片文字
 * @Author: Jeffray
 * @CreateDate: 2020/3/26 14:55
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/26 14:55
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MyTextImageView extends View {
    private Context context;
    private Paint paint;

    public MyTextImageView(Context context) {
        this(context, null);
    }

    public MyTextImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public MyTextImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureWidth(heightMeasureSpec));
    }

    private int measureWidth(int width) {
        int size = MeasureSpec.getSize(width);
        int mode = MeasureSpec.getMode(width);
        if (MeasureSpec.EXACTLY == mode) {
            return size;
        } else {
            if (MeasureSpec.AT_MOST == mode) {
                return size < 200 ? size : 200;
            }
            return 200;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建一个新画布，然后在新画布上进行绘制
        int layerId = canvas.saveLayer(0, 0, getWidth(),getHeight() , null);
        paint.setTextSize(100);
        //  这里使用STROKE模式通过设置StrokeWidth增加文字的宽度
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(8);
        paint.setTextAlign(Paint.Align.CENTER);
//        float i = paint.measureText("图片文字");
        int baseline = (int) ((getMeasuredHeight() - (paint.descent() - paint.ascent())) / 2 - paint.ascent());

//        (getWidth() - i)  / 2
        // 文字居中
        canvas.drawText("图片文字",getWidth() / 2,baseline,paint);


        // 设置取上层交集显示
        PorterDuffXfermode porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        paint.setXfermode(porterDuffXfermode);


        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coco2dxcplus);
        // 缩放图片至控件大小
        bitmap = Bitmap.createScaledBitmap(bitmap,getWidth(),getHeight(),true);
        canvas.drawBitmap(bitmap,5,5,paint);
        paint.setXfermode(null);
        // 将绘制完的画布贴到控件上
        canvas.restoreToCount(layerId);
    }
}
