package com.cl.slack.bounceview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * 上下反弹效果 fail
 * Created by slack on 2016/11/18.
 */
@Deprecated
public class BounceScrollView extends ScrollView {

    private static final String TAG = "BounceScrollView";

    /**
     * 包含的View
     */
    private View innerView;
    /**
     * 存储正常时的位置
     */

    private int downY, tempY, moveY;

    private boolean isFirstTouch = true;

    public BounceScrollView(Context context) {
        this(context, null);
    }

    public BounceScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BounceScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
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


    /**
     * 生成视图工作完成
     */
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
                    downY = (int) event.getRawY();
                    moveY = downY;
                    isFirstTouch = false;
                }

                // need move...
                if ((tempY = (int) event.getRawY() - moveY) != 0) {
                    Log.i(TAG, "ACTION_MOVE..." + tempY);
                    innerView.offsetTopAndBottom(tempY);
                }

                moveY = (int) event.getRawY();

                break;
            // 反弹回去 太快了
            case MotionEvent.ACTION_UP:
                moveY = (int) event.getY();
                Log.i(TAG, "ACTION_UP..." + (moveY - downY));
                isFirstTouch = true;
                // just too fast
                innerView.offsetTopAndBottom(downY - moveY);
                break;

        }
    }


}
