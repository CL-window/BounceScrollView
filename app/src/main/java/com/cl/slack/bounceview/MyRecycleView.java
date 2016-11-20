package com.cl.slack.bounceview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.Scroller;

/** ScrollView与RecyclerView 嵌套
 * Created by slack on 2016/11/18.
 */

public class MyRecycleView extends RecyclerView {

//    private Scroller mScroller;

    public MyRecycleView(Context context) {
        this(context,null);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        mScroller = new Scroller(context);
    }

//    @Override
//    public void computeScroll() {
//        // 重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
//        if ( mScroller.computeScrollOffset()) {
//            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
//            invalidate();
//        }
//    }


    // RecyclerView does not support scrolling to an absolute position
//    public void startScroll(int startX, int startY, int dx, int dy, int duration){
//        mScroller.startScroll(startX,  startY,  dx,  dy,  duration);
//        invalidate();
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
