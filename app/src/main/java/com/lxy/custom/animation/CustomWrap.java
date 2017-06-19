package com.lxy.custom.animation;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by lxy on 2017/6/12.
 */

public class CustomWrap extends FrameLayout implements ScrollInterface {

    public int mFromBgColor;
    public int mToBgColor;
    public int mTranslationValue;
    public boolean mCanAlpha;
    public boolean mCanScaleX;
    public boolean mCanScaleY;

    public int mWidth;// this view
    public int mHeight;

    public static final int TRANSLATION_FROM_TOP = 0X01;
    public static final int TRANSLATION_FROM_BOTTOM = 0X02;
    public static final int TRANSLATION_FROM_LEFT = 0X03;
    public static final int TRANSLATION_FROM_RIGHT = 0X04;


    public CustomWrap(@NonNull Context context) {
        this(context, null);
    }

    public CustomWrap(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomWrap(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;

        onResetScroll();
    }

    @Override
    public void onScrollAnimation(float ratio) {

        System.out.println("ratio======"+ratio);

        if (mCanAlpha) {
            setAlpha(ratio);
        }
        if (mCanScaleX) {
            setScaleX(ratio);
        }
        if (mCanScaleY) {
            setScaleY(ratio);
        }

        if (getTranslationDirection(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight * (1 - ratio));
        }
        if (getTranslationDirection(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight * (1 - ratio));
        }
        if (getTranslationDirection(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth * (1 - ratio));
        }
        if (getTranslationDirection(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth * (1 - ratio));
        }

    }

    @Override
    public void onResetScroll() {

        if (mCanAlpha) {
            setAlpha(0);
        }
        if (mCanScaleX) {
            setScaleX(0);
        }
        if (mCanScaleY) {
            setScaleY(0);
        }

        if (getTranslationDirection(TRANSLATION_FROM_BOTTOM)) {
            setTranslationY(mHeight);
        }
        if (getTranslationDirection(TRANSLATION_FROM_TOP)) {
            setTranslationY(-mHeight);
        }
        if (getTranslationDirection(TRANSLATION_FROM_LEFT)) {
            setTranslationX(-mWidth);
        }
        if (getTranslationDirection(TRANSLATION_FROM_RIGHT)) {
            setTranslationX(mWidth);
        }
    }

    public boolean getTranslationDirection(int translationFrom) {

        if (translationFrom == -1) {
            return false;
        }

        return (mTranslationValue & translationFrom) == translationFrom;
    }
}
