package com.lxy.custom.animation;

/**
 * Created by lxy on 2017/6/12.
 */

public interface ScrollInterface {

    /**
     *
     * @param ratio 0-1
     */
    void onScrollAnimation(float ratio);

    /**
     * view 属性重置
     */
    void onResetScroll();
}
