package com.project.stephencao.myslidingmenu.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.nineoldandroids.view.ViewHelper;
import com.project.stephencao.myslidingmenu.R;

public class SlidingMenu extends HorizontalScrollView {
    private LinearLayout mWrapper;
    private ViewGroup mMenu;
    private ViewGroup mContent;
    private boolean isExpanding = false; // check the menu status
    private int mScreenWidth;
    private int mMenuRightPadding = 50; // unit dp
    private boolean once = false;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        mScreenWidth = displayMetrics.widthPixels;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        mMenuRightPadding = (int) array.getDimension(R.styleable.SlidingMenu_menu_right_padding,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics()));
        array.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                break;
            }
            case MotionEvent.ACTION_UP: {
                int scrollX = getScrollX();
                if (scrollX >= mMenu.getWidth() / 2) {
                    this.smoothScrollTo(mMenu.getWidth(), 0);
                    isExpanding = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isExpanding = true;
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    /**
     * Hide the menu by setting its offset
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenu.getWidth(), 0);
        }
    }

    /**
     * Measure view group's width and height and its children's size
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWrapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWrapper.getChildAt(0);
            mContent = (ViewGroup) mWrapper.getChildAt(1);
            mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void expandMenu() {
        if (!isExpanding) {
            this.smoothScrollTo(0, 0);
            isExpanding = true;
        }
    }

    public void shrinkMenu() {
        if (isExpanding) {
            this.smoothScrollTo(mMenu.getWidth(), 0);
            isExpanding = false;
        }
    }

    public void toggleMenu() {
        if (isExpanding) {
            shrinkMenu();
        } else {
            expandMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float offsetFactor = l * 1.0f / mMenu.getWidth();
        //mode 2
        ViewHelper.setTranslationX(mMenu, mMenu.getWidth() * offsetFactor * 0.7f);
        // mode 3
        ViewHelper.setScaleX(mMenu, 0.7f + (1 - offsetFactor) * 0.3f);
        ViewHelper.setScaleY(mMenu, 0.7f + (1 - offsetFactor) * 0.3f);
        ViewHelper.setAlpha(mMenu, 0.6f + (1 - offsetFactor) * 0.4f);
        float contentOffsetFactor = 0.7f + 0.3f * offsetFactor;
        ViewHelper.setPivotX(mContent, 0);
        ViewHelper.setPivotY(mContent, mContent.getHeight() / 2.0f);
        ViewHelper.setScaleX(mContent, contentOffsetFactor);
        ViewHelper.setScaleY(mContent, contentOffsetFactor);
    }
}
