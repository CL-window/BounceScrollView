package com.cl.slack.bounceview;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;

/**
 * 左右反弹效果
 * Created by slack on 2016/11/18.
 */

public class BounceHorizontalScrollView extends HorizontalScrollView {

    private static final String TAG = "BounceHorizontalScrollView";

    private ScrollCallback mCallback;

    /**
     * 包含的View
     */
    private View innerView;
    /**
     * 存储正常时的位置
     */
    private Rect mRect = new Rect();

    private int downX, tempX, moveX;

    private boolean isFirstTouch = true;

    private boolean hasCallback = false;

    public BounceHorizontalScrollView(Context context) {
        this(context, null);
    }

    public BounceHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isFirstTouch = true;
        // 取消滑动到顶部或底部时边缘的黄色或蓝色底纹
        if (Build.VERSION.SDK_INT >= 9) {
            this.setOverScrollMode(View.OVER_SCROLL_NEVER);
        }
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            innerView = getChildAt(0);
        }
        super.onFinishInflate();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
        boolean intercepted = false;
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                intercepted = false;
                break;
            case MotionEvent.ACTION_MOVE:
                intercepted = true;
                break;
            case MotionEvent.ACTION_UP:
                intercepted = false;
                break;
        }
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView != null) {
            handleTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    private void handleTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {

            case MotionEvent.ACTION_MOVE:

                if (isFirstTouch) {
                    downX = (int) event.getRawX();
                    moveX = downX;
                    isFirstTouch = false;
                }

                // need move...
                if ((tempX = (int) event.getRawX() - moveX) != 0) {

                    if (mRect.isEmpty()) {
                        /**
                         * 记录移动前的位置
                         */
                        mRect.set(innerView.getLeft(), innerView.getTop(),
                                innerView.getRight(), innerView.getBottom());
                    }
                    // 移动 1/2
                    innerView.layout(innerView.getLeft() + tempX / 2, innerView.getTop(),
                            innerView.getRight() + tempX / 2, innerView.getBottom());

                }

                if (needCallBack(tempX)) {
                    Log.i("slack","needCallBack....");
                    if (mCallback != null) {
                        if (!hasCallback) {
                            hasCallback = true;
                            resetPosition();
                            mCallback.callback();
                        }
                    }
                }

                moveX = (int) event.getRawX();

                break;
            // 反弹回去
            case MotionEvent.ACTION_UP:
                isFirstTouch = true;
                if (!mRect.isEmpty()) {
                    resetPosition();
                }
                break;

        }
    }

    /**
     * 移动超过屏幕宽度1/3，就回调
     * getMeasuredHeight():the inner view height
     * getHeight()：the screen hight
     * @param moveX
     * @return
     */
    private boolean needCallBack(int moveX) {
        if (moveX != 0 && innerView.getLeft() > getWidth() / 3) {
            return true;
        }
        return false;
    }

    public void setCallback(ScrollCallback callback) {
        this.mCallback = callback;
    }

    private void resetPosition() {
        Log.i("slack",innerView.getLeft()+" , "+mRect.left);
        Animation animation = new TranslateAnimation(innerView.getLeft(), mRect.left, 0, 0);
        animation.setDuration(300);
        animation.setFillAfter(true);
        innerView.startAnimation(animation);
        innerView.layout(mRect.left, mRect.top, mRect.right, mRect.bottom);
        mRect.setEmpty();
    }


}
