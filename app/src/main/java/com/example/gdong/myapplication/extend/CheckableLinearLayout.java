package com.example.gdong.myapplication.extend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Checkable;
import android.widget.LinearLayout;


public class CheckableLinearLayout extends LinearLayout implements Checkable{
    private static final int[] CHECKED_STATE_SET = { android.R.attr.state_checked };
    /*private*/ static final int[] SELECTED_STATE_SET = { android.R.attr.state_selected };
    /*private*/ static final int[] PRESSED_STATE_SET = { android.R.attr.state_pressed };
    private boolean mChecked = false;
    private boolean mSelected = false;
    private  boolean mPressed = false;
    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean isChecked() {
        return mChecked;
    }

    public boolean isSelected() {
        return mSelected;
    }

    public void setChecked(boolean b) {
        if (b != mChecked) {
            mChecked = b;
            refreshDrawableState();
        }
    }

    @Override
    public void setSelected(boolean selected) {
        if (selected != mChecked) {
            mChecked = selected;
            refreshDrawableState();
        }
    }

    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);
        if (pressed != mPressed) {
            mPressed = pressed;
            refreshDrawableState();
        }
    }
    public void toggle() {
        setChecked(!mChecked);
        setSelected(!mSelected);
    }

    @SuppressLint("NewApi")
	@Override
    public int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()) {
            mergeDrawableStates(drawableState, CHECKED_STATE_SET);
        }
        if (mPressed) {
            setBackgroundColor(Color.rgb(26,196,250));
            //setBackgroundResource(R.drawable.abc_list_focused_holo);
        }else{
            setBackground(null);
        }
        return drawableState;
    }

}
