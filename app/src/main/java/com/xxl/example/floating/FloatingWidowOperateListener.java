package com.xxl.example.floating;

/**
 * 悬浮窗操作事件监听
 *
 * @author xxl.
 * @date 2019/11/21.
 */
public interface FloatingWidowOperateListener {
    /**
     * 点击，只会调用一次，在手指抬起的时候调用
     */
    void onClick();

    /**
     * 拖动,只会调用一次，在手指抬起的时候调用
     */
    void onDrag();

    /**
     * 拖动中,开始拖动就会执行，会执行多次
     */
    void onDragging();
}
