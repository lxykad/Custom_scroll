package com.lxy.custom.animation;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by lxy on 2017/6/12.
 * 用来获取子控件的自定义属性，再给包一层framlayout
 */

public class CustomAnimationLayout extends LinearLayout {


    public CustomAnimationLayout(Context context) {
        this(context, null);
        //setOrientation(VERTICAL);
    }

    public CustomAnimationLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        //setOrientation(VERTICAL);
    }

    public CustomAnimationLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(VERTICAL);

    }


    /**
     * 获取xml里配置的属性  返回的属性在addview 里的params 的参数里
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {

        MyLayoutParams myLayoutParams = new MyLayoutParams(getContext(), attrs);

        return myLayoutParams;

        //return super.generateLayoutParams(attrs);
    }

    /**
     * 线性布局 渲染的时候，会new 一个出来，然后把子view add进去，这里重写addview方法，在addview方法调用之前添加一层wrap
     *
     * @param child
     */
    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        MyLayoutParams myLayoutParams = (MyLayoutParams) params;

        if (!hasCustomAttrs(myLayoutParams)) {//!hasCustomAttrs(myLayoutParams)

            super.addView(child, index, params);
            System.out.println("ratio=======noCustomAttrs");

        } else {
            CustomWrap wrap = new CustomWrap(child.getContext());
            //获取child 的自定义属性

            wrap.mCanAlpha = myLayoutParams.mCanAlpha;
            wrap.mCanScaleX = myLayoutParams.mCanScaleX;
            wrap.mCanScaleY = myLayoutParams.mCanScaleY;
            wrap.mFromBgColor = myLayoutParams.mFromBgColor;
            wrap.mToBgColor = myLayoutParams.mToBgColor;
            wrap.mTranslationValue = myLayoutParams.mTranslationValue;

            wrap.addView(child);
            System.out.println("ratio=======hasCustomAttrs");

            super.addView(wrap, index, params);
        }

    }

    public boolean hasCustomAttrs(MyLayoutParams params) {

       /* boolean b = params.mFromBgColor != -1
                || params.mToBgColor != -1
                || params.mTranslationValue != -1
                || params.mCanAlpha
                || params.mCanScaleX
                || params.mCanScaleY;

         return b;*/


        if (params.mFromBgColor != -1 || params.mToBgColor != -1 || params.mTranslationValue == -1) {

            return true;
        } else if (params.mCanAlpha || params.mCanScaleX || params.mCanScaleY) {
            return true;
        } else {
            return false;
        }

    }

    public static class MyLayoutParams extends LinearLayout.LayoutParams {

        public int mFromBgColor;
        public int mToBgColor;
        public int mTranslationValue;
        public boolean mCanAlpha;
        public boolean mCanScaleX;
        public boolean mCanScaleY;


        public MyLayoutParams(Context ctx, AttributeSet attrs) {
            super(ctx, attrs);

            //获取child的自定义属性
            TypedArray array = ctx.obtainStyledAttributes(attrs, R.styleable.CustomAnimationLayout);

            mFromBgColor = array.getColor(R.styleable.CustomAnimationLayout_from_bg_color, -1);
            mToBgColor = array.getColor(R.styleable.CustomAnimationLayout_to_bg_color, -1);
            mTranslationValue = array.getInt(R.styleable.CustomAnimationLayout_can_alpha, -1);

            mCanAlpha = array.getBoolean(R.styleable.CustomAnimationLayout_can_alpha, false);
            mCanScaleX = array.getBoolean(R.styleable.CustomAnimationLayout_can_scaleX, false);
            mCanScaleY = array.getBoolean(R.styleable.CustomAnimationLayout_can_scaleY, false);

            array.recycle();

        }
    }

}
