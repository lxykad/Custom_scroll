package com.lxy.custom.animation;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by lxy on 2017/6/12.
 */

public class CustomScrollView extends ScrollView {

    private CustomAnimationLayout mContentLayout;

    public CustomScrollView(Context context) {
        this(context, null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //渲染完毕 获取scrollview 里面的LinearLayout ，构造方法里 获取时 还没添加LinearLayout 进去
        mContentLayout = (CustomAnimationLayout) getChildAt(0);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

       // View firstChild = mContentLayout.getChildAt(0);
       // firstChild.getLayoutParams().height = getHeight();

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        int customSrollViewHeight = getHeight();


        for (int i = 0; i < mContentLayout.getChildCount(); i++) {

            View child = mContentLayout.getChildAt(i);

            if (!(child instanceof ScrollInterface)) {
                continue;
            }
            ScrollInterface scrollInterface = (ScrollInterface) child;

            //child 距离 scroll 顶部的高度
            int childToScrollTop = child.getTop();
            int childHeight = child.getHeight();

            // t 为scroll 滑出去屏幕的高度
            int childTop = childToScrollTop - t;//得到child 距离屏幕顶部的高度

           // System.out.println("ratio===childTop: " + childTop + "==customSrollViewHeight:" + customSrollViewHeight);

            //当child 滑进屏幕的时候 执行动画
            if (childTop <= customSrollViewHeight) {
                int visibleGap = customSrollViewHeight - childTop;
                // 确保 ratio 0-1
                scrollInterface.onScrollAnimation(formatRatio(visibleGap / (float) childHeight, 1.0f, 0f));

            } else {
                scrollInterface.onResetScroll();
            }

        }

    }

    public static float formatRatio(float value, float max, float min) {

        return Math.max(Math.max(value, max), min);
    }


}
