package com.example.porterduffxfermodedemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.porterduffxfermodedemo.R;

/**
 * @ProjectName: PorterDuffXferModeDemo
 * @Package: com.example.porterduffxfermodedemo.view
 * @ClassName: Guaguaka
 * @Description: java类作用描述
 * @Author: Jeffray
 * @CreateDate: 2020/3/26 16:14
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/26 16:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class Guaguaka extends View {
    /**
     * 绘制线条的Paint,即用户手指绘制Path
     */
    private Paint mOutterPaint = new Paint();
    /**
     * 记录用户绘制的Path
     */
    private Path mPath = new Path();
    /**
     * 内存中创建的Canvas
     */
    private Canvas mCanvas;
    /**
     * mCanvas绘制内容在其上
     */
    private Bitmap mBitmap;

    private int mLastX;
    private int mLastY;
    private Bitmap mBackBitmap;

    public Guaguaka(Context context) {
        this(context, null);
    }

    public Guaguaka(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Guaguaka(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public Guaguaka(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mBackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coco2dxcplus);
        mBackBitmap = Bitmap.createScaledBitmap(mBackBitmap, width, height, true);
        // 初始化bitmap
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        // 设置画笔
        mOutterPaint.setColor(Color.RED);
        mOutterPaint.setAntiAlias(true);
        mOutterPaint.setDither(true);
        mOutterPaint.setStyle(Paint.Style.STROKE);
        mOutterPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        mOutterPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        // 设置画笔宽度
        mOutterPaint.setStrokeWidth(100);
        mCanvas.drawColor(Color.parseColor("#c0c0c0"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBackBitmap, 0, 0, null);
        if(!isComplete){ // 如果刮完了，就直接显示图片，不在显示遮罩层
            // 为什么要加这行代码，就是因为我们绘制的路径在另一个bitmap上面，所以要把那个bitmap绘制到当前的canvas上
            canvas.drawBitmap(mBitmap, 0, 0, null);
            drawPath();
        }
    }

    /**
     * 绘制线条
     */
    private void drawPath() {
        mOutterPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawPath(mPath, mOutterPaint);
    }

    // 实现方式1 >>>>>>>>>
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//
//        switch (action) {
//            case MotionEvent.ACTION_DOWN:
//                mPath.moveTo(x, y);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                mPath.lineTo(x, y);
//                break;
//            case MotionEvent.ACTION_UP:
//                mPath.lineTo(x, y);
//                break;
//            default:
//                break;
//        }
//        invalidate();
//        return true;
//    }

    // 实现方式2 贝塞尔曲线 >>>>>>>>>
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - mLastX);
                float dy = Math.abs(y - mLastY);
                if (dx >= 3 || dy >= 3) {
                    // 设置贝塞尔曲线的操作点为起点和终点的一半
                    float cX = (x + mLastX) / 2;
                    float cY = (y + mLastY) / 2;
                    // 二次贝塞尔，实现平滑曲线；previousX, previousY为操作点，cX, cY为终点
                    mPath.quadTo(mLastX, mLastY, cX, cY);
                    mLastX = x;
                    mLastY = y;
                }
            case MotionEvent.ACTION_UP:
                post(mRunnable);
                mPath.lineTo(mLastX, mLastY);
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private Runnable mRunnable = new Runnable() {
        private int[] mPixels;

        @Override
        public void run() {

            int w = getWidth();
            int h = getHeight();

            float wipeArea = 0;
            float totalArea = w * h;

            Bitmap bitmap = mBitmap;

            mPixels = new int[w * h];

            /**
             * 拿到所有的像素信息
             */
            bitmap.getPixels(mPixels, 0, w, 0, 0, w, h);

            /**
             * 遍历统计擦除的区域
             */
            for (int i = 0; i < w; i++) {
                for (int j = 0; j < h; j++) {
                    int index = i + j * w;
                    if (mPixels[index] == 0) {
                        wipeArea++;
                    }
                }
            }

            /**
             * 根据所占百分比，进行一些操作
             */
            if (wipeArea > 0 && totalArea > 0) {
                int percent = (int) (wipeArea * 100 / totalArea);
                Log.e("TAG", percent + "");

                if (percent > 20) { // 刮百分之20以上就默认为已经刮完
                    isComplete = true;
                    postInvalidate();
                }
            }
        }

    };

    private static Boolean isComplete = false;
}
