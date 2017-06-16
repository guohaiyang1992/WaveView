package com.ghy.wave.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.ghy.wave.R;
import com.ghy.wave.utils.DisplayUtils;
import com.ghy.wave.utils.UnitUtils;


public class WaveView extends View {
    //画笔
    private Paint mPaint;
    //线的路径
    private Path mPath;
    //水波长
    private int mItemWaveLength = 2000;//默认值
    //偏移位移（0，波长）
    private int dx;
    //每增加一个的offset
    private int offset = -600;//默认值
    private int DEFAULT_COLOR = 0x4037B3FA;
    //颜色，需要透明度，否则后面的看不到了
    private int color = DEFAULT_COLOR;

    private int waveCount = 2;//默认值是2

    private int duration = 2000;//默认值

    private int waveHegiht = 80;//默认值

    private int originY = 0;

    private int halfWaveLen = 0;

    //动画
    ValueAnimator anim;

    private boolean isAutoRun = true;


    public WaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig();
        initUserConfig(attrs);
        UnitUtils.init(context);
        DisplayUtils.init(context);


    }

    /**
     * 初始化用户配置
     *
     * @param attrs
     */
    private void initUserConfig(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WaveView);
        int count = a.getIndexCount();
        for (int i = 0; i < count; i++) {
            int attr = a.getIndex(i);
            if (attr == R.styleable.WaveView_wave_count) {
                waveCount = a.getInt(attr, 2);
            } else if (attr == R.styleable.WaveView_wave_color) {
                color = a.getColor(attr, DEFAULT_COLOR);
            } else if (attr == R.styleable.WaveView_wave_duration) {
                duration = a.getInt(attr, 2000);
            } else if (attr == R.styleable.WaveView_wave_anim_auto_run) {
                isAutoRun = a.getBoolean(attr, true);
            }
        }
        //合理化数据
//        if (waveCount < 2) {
//            waveCount = 2;
//        }

        updateColor(color);

        if (duration <= 0) {
            duration = 2000;
        }
    }

    private void updateColor(int color) {
        if (Color.alpha(color) == 0 || Color.alpha(color) > 64) {
            color = Color.argb(64, Color.red(color), Color.green(color), Color.blue(color));
            mPaint.setColor(color);
        }
    }

    /**
     * 初始化默认配置
     */
    private void initConfig() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(DEFAULT_COLOR);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //增加平滑度
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        //关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        makePath(canvas);

    }


    private void makePath(Canvas canvas) {

        for (int j = 0; j < waveCount; j++) {
            mPath.reset();
            //移动到起点位置，此处起点为负数是因为，后面会根据起点进行继续重复绘制，如果是正数，则前面会有空白，所以offset 也是负数
            mPath.moveTo(-mItemWaveLength + dx + offset * j, originY);
            for (int i = -mItemWaveLength; i <= Math.max(getWidth(), getHeight()) + mItemWaveLength; i += mItemWaveLength) {
                mPath.rQuadTo(halfWaveLen / 2, waveHegiht * -1, halfWaveLen, 0);
                mPath.rQuadTo(halfWaveLen / 2, waveHegiht, halfWaveLen, 0);
            }
            mPath.lineTo(getWidth(), getHeight());
            mPath.lineTo(0, getHeight());
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }

    }


    /**
     * 开启动画
     */
    public void startAnim() {
        ifNotCreateAnim();
        if (anim.isRunning()) {
            return;
        } else {
            anim.start();
        }
    }

    /**
     * 结束动画
     */
    public void endAnim() {
        if (anim != null && anim.isRunning()) {
            anim.end();
            anim = null;
        }
    }

    /**
     * 暂停动画
     * api >=19
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseAnim() {
        if (anim != null && anim.isRunning()) {
            anim.pause();
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
        endAnim();
        startAnim();
    }

    public void setColor(int color) {
        this.color = color;
        updateColor(color);
    }


    public void setWaveCount(int waveCount) {
        this.waveCount = waveCount;
    }

    /**
     * 恢复动画
     * api >=19
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeAnim() {
        if (anim != null && anim.isPaused()) {
            anim.resume();
        }
    }

    private void ifNotCreateAnim() {
        if (anim == null) {
            anim = ValueAnimator.ofInt(0, mItemWaveLength);
            anim.setDuration(duration);
            anim.setRepeatCount(ValueAnimator.INFINITE);
            anim.setInterpolator(new LinearInterpolator());
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    dx = (int) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {//固定值或者match-parent
            width = widthSize;
        } else {
            width = DisplayUtils.getWidth();//warp_content
        }

        if (heightMode == MeasureSpec.EXACTLY) {//固定值或者match-parent
            height = heightSize;
        } else {
            height = (int) (DisplayUtils.getHeight() * 0.05);
        }
        setMeasuredDimension(width, height);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int value = Math.max(w, h);
        mItemWaveLength = (int) (value * 1.85);
        halfWaveLen = mItemWaveLength / 2;
        offset = -mItemWaveLength * 1 / 3;
        waveHegiht = (int) (h * 0.8);
        originY = h - 50;
        notifyAnim();
    }

    private void notifyAnim() {
        if (isAutoRun) {
            startAnim();
        }
    }
}