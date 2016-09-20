package comtwo.pokercc.a123goview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

/**
 * TODO: document your custom view class.
 */
public class OneTwoThreeGoView extends View {

    public static final String TAG = "OneTwoThreeGoView";
    private String mExampleString; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
//    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    //    private float mTextWidth;
//    private float mTextHeight;
    private Paint mLinePaint;
    private int mAnimatedValue;

    private int mLineColor = 0xffd8d8d7;
    private int mTextColor = 0xfffefefc;
    private int mShadowColor = 0xff595958;
    private TextPaint mMyTextPaint;
    private Point mCenterPoint;
    private Paint mShadowPaint;
    private ValueAnimator mValueAnimator;


    private String[] mStrings = {"3", "2", "1", "ACTION"};
    private SoundPool mSoundPool;
    private int mVolumeId;

    public OneTwoThreeGoView(Context context) {
        super(context);
        init(null, 0);
    }

    public OneTwoThreeGoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public OneTwoThreeGoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.OneTwoThreeGoView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.OneTwoThreeGoView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.OneTwoThreeGoView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.OneTwoThreeGoView_exampleDimension,
                mExampleDimension);

//        if (a.hasValue(R.styleable.OneTwoThreeGoView_exampleDrawable)) {
//            mExampleDrawable = a.getDrawable(
//                    R.styleable.OneTwoThreeGoView_exampleDrawable);
//            mExampleDrawable.setCallback(this);
//        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
//        invalidateTextPaintAndMeasurements();


        // set up a line for draw lines and circles
        mLinePaint = new Paint();
        mLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(dp2px(2));


        // set up a lint for draw shadow
        mShadowPaint = new Paint();
        mShadowPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setColor(mShadowColor);
        mShadowPaint.setStyle(Paint.Style.FILL);

        // set up a textPainf to draw text
        mMyTextPaint = new TextPaint();
        mMyTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mMyTextPaint.setTextAlign(Paint.Align.CENTER);
        mMyTextPaint.setColor(mTextColor);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                performStart();
            }
        });


        // init sound
        mSoundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 100);
        mVolumeId = mSoundPool.load(getContext(), R.raw.sound123action, 1);
    }


//    private void invalidateTextPaintAndMeasurements() {
//        mTextPaint.setTextSize(mExampleDimension);
//        mTextPaint.setColor(mExampleColor);
//        mTextWidth = mTextPaint.measureText(mExampleString);
//
//        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;
        mCenterPoint = new Point(paddingLeft + contentWidth / 2, paddingTop + contentHeight / 2);

//        // Draw the text.
//        canvas.drawText(mExampleString,
//                paddingLeft + (contentWidth - mTextWidth) / 2,
//                paddingTop + (contentHeight + mTextHeight) / 2,
//                mTextPaint);
//
//        // Draw the example drawable on top of the text.
//        if (mExampleDrawable != null) {
//            mExampleDrawable.setBounds(paddingLeft, paddingTop,
//                    paddingLeft + contentWidth, paddingTop + contentHeight);
//            mExampleDrawable.draw(canvas);
//        }


        //draw arc
        float radiusLarge = Math.min(getWidth(), getHeight()) * 0.3f;
        RectF largetRectf = new RectF(mCenterPoint.x - radiusLarge, mCenterPoint.y - radiusLarge, mCenterPoint.x + radiusLarge, mCenterPoint.y + radiusLarge);
        if (mAnimatedValue <= (mStrings.length - 1) * 20 && mAnimatedValue != 0) {
            int sweepAngle = (mAnimatedValue * 18) % 360;
            if (sweepAngle == 0) sweepAngle = 360;
            canvas.drawArc(largetRectf, -90, sweepAngle, true, mShadowPaint);
        }


        //draw lines
        canvas.drawLine(paddingLeft, mCenterPoint.y, getWidth() - paddingRight, mCenterPoint.y, mLinePaint);
        canvas.drawLine(mCenterPoint.x, paddingTop + 120, mCenterPoint.x, getHeight() - paddingBottom - 120, mLinePaint);


        // draw circles
        mLinePaint.setStyle(Paint.Style.STROKE);

        canvas.drawArc(largetRectf, 0, 360, false, mLinePaint);


        mLinePaint.setStyle(Paint.Style.STROKE);
        float radiusSmall = radiusLarge * 0.9f;
        RectF smallRectf = new RectF(mCenterPoint.x - radiusSmall, mCenterPoint.y - radiusSmall, mCenterPoint.x + radiusSmall, mCenterPoint.y + radiusSmall);
        canvas.drawArc(smallRectf, 0, 360, false, mLinePaint);


        //draw text
        drawCountDownText(canvas);


    }

    public void performStart() {
        // change 20times per seconds
        mValueAnimator = ValueAnimator.ofInt(0, mStrings.length * 20 - 1).setDuration(mStrings.length * 1000);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (Integer) animation.getAnimatedValue();
                postInvalidate();

            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                playSound();

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mValueAnimator.start();
    }

    private void drawCountDownText(Canvas canvas) {
        int index = mAnimatedValue / 20;
        String text = mStrings[index];

        int textSize = dp2px(110);
        float width = mMyTextPaint.measureText(text);

        Log.i(TAG, mStrings[mStrings.length - 1] + " 's width:" + width);
        if (index == mStrings.length - 1) {
            textSize = (int) (width / (mStrings[mStrings.length - 1].length()));
            textSize = dp2px(75);
        }
        mMyTextPaint.setTextSize(textSize);
        mMyTextPaint.setStrokeWidth(dp2px(4));
        mMyTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
//        mMyTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
        Paint.FontMetrics fontMetrics = mMyTextPaint.getFontMetrics();
        float textHight = fontMetrics.bottom;
        canvas.drawText(text, mCenterPoint.x, mCenterPoint.y + textHight, mMyTextPaint);

    }


    private void playSound() {
        AudioManager mgr = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        int streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mVolumeId, streamVolume, streamVolume, 1, 0, 1f);


    }

    private int dp2px(int dp) {
        return (int) (getContext().getResources().getDisplayMetrics().density * dp + 0.5);
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
//    public void setExampleString(String exampleString) {
//        mExampleString = exampleString;
//        invalidateTextPaintAndMeasurements();
//    }
//
//    /**
//     * Gets the example color attribute value.
//     *
//     * @return The example color attribute value.
//     */
//    public int getExampleColor() {
//        return mExampleColor;
//    }
//
//    /**
//     * Sets the view's example color attribute value. In the example view, this color
//     * is the font color.
//     *
//     * @param exampleColor The example color attribute value to use.
//     */
//    public void setExampleColor(int exampleColor) {
//        mExampleColor = exampleColor;
//        invalidateTextPaintAndMeasurements();
//    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
//    public void setExampleDimension(float exampleDimension) {
//        mExampleDimension = exampleDimension;
//        invalidateTextPaintAndMeasurements();
//    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
//    public Drawable getExampleDrawable() {
//        return mExampleDrawable;
//    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
//    public void setExampleDrawable(Drawable exampleDrawable) {
//        mExampleDrawable = exampleDrawable;
//    }
}
