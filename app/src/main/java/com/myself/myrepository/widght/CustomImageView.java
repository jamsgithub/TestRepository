package com.myself.myrepository.widght;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

/**
 * @Created by Jams
 * @Created time 2018-4-12 10:34
 * @dese todo
 * \n
 * @UpUser by Administrator
 * @UpDate 2018-4-12 10:34
 * @dese todo
 */

public class CustomImageView extends AppCompatImageView {
    private Drawable leftImage;
    private Drawable rightImage;
    private int screenWid, screenHei, viewWid, viewHei;
    private float mDownX;
    private float mDownY;
    private float mMoveX;
    private float mMoveY;
    private boolean isClickDrag;
    private float mLeftMar;
    private float mTopMar;
    private int touchSlop = 0;

    public CustomImageView(Context context) {
        super(context);
        measureScreen(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        measureScreen(context);
    }

    public CustomImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        measureScreen(context);
    }

    private void measureScreen(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWid = wm.getDefaultDisplay().getWidth();
        screenHei = wm.getDefaultDisplay().getHeight();
    }

    private void measureView() {
        viewWid = getWidth();
        viewHei = getHeight();
    }

    public void setLeftImage(Drawable leftDra , Drawable rightDra) {
        leftImage = leftDra;
        rightImage = rightDra;
        setBackgroundDrawable(leftImage);
    }
    public void setParentSize(int width , int height){
        screenHei = height;
        screenWid = width;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        measureView();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getRawX();
                mDownY = event.getRawY();
                RelativeLayout.LayoutParams params1 = (RelativeLayout.LayoutParams) getLayoutParams();
                mLeftMar = mDownX - params1.leftMargin;
                mTopMar = mDownY - params1.topMargin;
                Log.i("onTouchEvent", "ActionDown:" + mDownX + "   " + mDownY);
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveX = event.getRawX();
                mMoveY = event.getRawY();
                float distX = mMoveX - mLeftMar;
                float distY = mMoveY - mTopMar;
                Log.i("onTouchEvent", "ActionMove:" + mMoveX + "  " + mMoveY);
                //                move(mMoveX , mMoveY);
                if (Math.sqrt(distX * distX + distY * distY) > 10) {
                    isClickDrag = false;
                }
//                moveTo(mMoveX, mMoveY);
                marginTo(mMoveX , mMoveY);
                break;
            case MotionEvent.ACTION_UP:
                /*float UpX = event.getRawX();
                float UpY = event.getRawY();
                if (UpY > screenHei - viewHei){
                    UpY = screenHei - viewHei;
                }
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                if (Math.abs(UpX) < (screenWid / 2 - viewWid / 2)) {
                    Log.i("CustomImageView", "toLeft");
                    params.setMargins(0, (int) (Math.abs(UpY) + .5f - mDownY),
                            (int)(screenHei - viewWid), (int)(screenHei - Math.abs(UpY)+ .5f));

                    //                    scrollTo(0 , (int)(Math.abs(UpY) - mDownY + .5f));
                } else {
                    Log.i("CustomImageView", "toRight");
                    params.setMargins(screenWid - viewWid, (int) (Math.abs(UpY) + .5f - mDownY),0
                            , (int)(screenHei - Math.abs(UpX) + .5f));
                    //                    scrollTo(screenWid - viewWid , (int)(Math.abs(UpY) - mDownY + .5f));
                }
                setLayoutParams(params);*/
                //                requestLayout();
                //moveTo(UpX, UpY);
//                invalidate();
                int UpX = (int) (event.getRawX() + .5f);
                int UPy = (int) (event.getRawY() + .5f);
                toLeftRight(UpX , UPy);

                break;
        }
        return true;
    }

    private void marginTo(float x , float y){

        int distX =  (int) (.5f + mMoveX - mLeftMar);
        int distY = (int) (.5f + mMoveY - mTopMar);
        int minX = getWidth();
        int maxX = screenWid - getWidth();
        int minY=DisplayUtil.getStatusBarHeight((Activity) getContext());
        int value = (int)(.5f + TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP , 53 , getResources().getDisplayMetrics()));
        int maxY=screenHei-minY-getHeight();
        if (x < minX){
            x = minX;
        }else if (distX > maxX){
            distX = maxX;
        }

        if(y<minY+getHeight()/2){
            distY=0;
        }else if(y>maxY){
            distY=maxY;
        }
        int distance= (int) Math.sqrt(Math.pow(distX,2)+Math.pow(distY,2));
        //改变view的margin属性，从而移动view
        if(distance>touchSlop+10){
            isClickDrag=true;
            Log.i("onTouchEvent" , "MarginTo : " + distX + "   " + distY);
            RelativeLayout.LayoutParams layoutParams =(RelativeLayout.LayoutParams)getLayoutParams();
            layoutParams.leftMargin = distX;
            layoutParams.topMargin = distY;
            setLayoutParams(layoutParams);
        }
    }

    private void toLeftRight( int rawX , int rawY){
        //这里做窗体吸附工作
        if(isClickDrag){
            isClickDrag=false;
            if(rawX>=screenWid/2){
                if(rawX>screenWid-getWidth()){
                    rawX=screenWid-getWidth();
                }
                setBackgroundDrawable(rightImage);
                rawX = screenWid - getWidth();
                startAnimaLeft(rawX,screenWid-getWidth());
               /* RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                viewWid = getWidth();
                viewHei = getHeight();
                params.leftMargin = screenWid - viewWid;
                params.topMargin = (int) (screenHei - viewHei - mTopMar + .5f);
                setLayoutParams(params);*/

            }else {
                int from;
                if(rawX<getWidth()){
                    from=0;
                }else {
                    from=rawX-getWidth();
                }
                setBackgroundDrawable(leftImage);
                from = rawX - getWidth();
                startAnimaLeft(from,0);
                /*RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
                viewWid = getWidth();
                viewHei = getHeight();
                params.leftMargin = screenWid - viewWid;
                params.topMargin = (int) (screenHei - viewHei - mTopMar + .5f);
                setLayoutParams(params);*/
            }
        }else {
           /* if(listener!=null){
                listener.onClick(this);
            }*/
        }
    }

    private void moveTo(float x, float y) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
//        int left = (int) (x - viewWid / 2 + .5f);
//        int top = (int) (y - viewHei / 2 + .5f);
        int left = (int) (Math.abs(x) + .5f - mDownX);
        int top = (int) (Math.abs(y) + .5f - mDownY);
        if (left < 0)
            left = 0;
        if (top < 0)
            top = (int)(Math.abs(y) + .5f);

        if (left > screenWid - viewWid)
            left = screenWid - viewWid;
        if (top > screenHei - viewHei)
            top = screenHei - viewHei;
        Log.i("onTouchEvent" , "MoveTo:  " + "Left: " + left + "   Top: " + top);

        params.setMargins(left, top, screenWid - left - viewWid, screenHei - viewHei - top);
        setLayoutParams(params);
                invalidate();
//        requestLayout();

    }

    private void move(float x, float y) {
        int left = getLeft();
        int right = getRight();
        //        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
        int Leftx = 0;
        int LeftY = 0;
        if (x - mDownX < 0) {
            Leftx = (int) (Math.abs(x) - mDownX + .5f);
        } else {
            Leftx = (int) (x - mDownX + .5f);
        }

        if (y - mDownY < 0) {
            LeftY = (int) (Math.abs(y) - mDownY + .5f);
        } else {
            LeftY = (int) (y - mDownY + .5f);
        }
        /*if (x < 0 ){
//            left = (int) (Math.abs(mDownX + x) + .5f);
            Leftx = (int) (mDownX + x +.5f);
        }else {
            Leftx = (int) (mDownX - x + .5f);
        }

        if (y < 0){
            LeftY = (int)(mDownY + y + .5f);
        }else {
            LeftY = (int)(y - mDownY + .5f);
        }*/
        Log.i("CustomImageView", "Leftx: " + Leftx + "   LeftY：" + LeftY);
        scrollBy(Math.abs(Leftx), Math.abs(LeftY));
        invalidate();
    }

    private void startAnimaLeft(int from,int to){
        ObjectAnimator oa=ObjectAnimator.ofInt(new LayoutWapper(this),"leftMargin",from,to);
        oa.setDuration(500);
        oa.setInterpolator(new DecelerateInterpolator());
        oa.setStartDelay(100); oa.start();
    }

    private class LayoutWapper {
        private View target;
        public LayoutWapper(View target) {
            this.target = target;
        }
        public void setLeftMargin(int value) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)getLayoutParams();
            layoutParams.leftMargin = value;
            target.setLayoutParams(layoutParams);
        }
    }
}
