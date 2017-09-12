package org.csii.yeeframe.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import org.csii.yeeframe.utils.YeeLoger;

/**
 * Created by yyp on 2016/11/11.
 */
public abstract class SupportFragmentDialog extends DialogFragment implements View.OnClickListener {


    protected View fragmentRootView;

    protected abstract View inflaterView(LayoutInflater inflater, ViewGroup container, Bundle bundle);

    /**
     * initialization widget, you should look like parentView.findviewbyid(id);
     * call method
     *
     * @param parentView
     */
    protected void initWidget(View parentView) {
    }

    /**
     * initialization data
     */
    protected void initData() {
    }

    /**
     * widget click method
     */
    protected void widgetClick(View v) {
    }

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRootView = inflaterView(inflater, container, savedInstanceState);
        AnnotateUtil.initBindView(this, fragmentRootView);
        initData();
        initWidget(fragmentRootView);

        return fragmentRootView;
    }

    protected <T extends View> T bindView(int id) {
        return (T) fragmentRootView.findViewById(id);
    }

    protected <T extends View> T bindView(int id, boolean click) {
        T view = (T) fragmentRootView.findViewById(id);
        if (click) {
            view.setOnClickListener(this);
        }
        return view;
    }

    /***************************************************************************
     * print Fragment callback methods
     ***************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YeeLoger.state(this.getClass().getName(), "---------onCreateView ");
        setStyle(android.support.v4.app.DialogFragment.STYLE_NORMAL, android.R.style.Theme_Holo_Light_Dialog_NoActionBar);
        setCancelable(false);
    }

    @Override
    public void onResume() {
        YeeLoger.state(this.getClass().getName(), "---------onResume ");
        super.onResume();
        Window window = getDialog().getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Display mDisplay = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        double mWidth = mDisplay.getWidth() - 180;
        double mHeight = mDisplay.getHeight() - 180;
        window.setLayout((int) mWidth, (int) mHeight);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onPause() {
        YeeLoger.state(this.getClass().getName(), "---------onPause ");
        super.onPause();
    }

    @Override
    public void onStop() {
        YeeLoger.state(this.getClass().getName(), "---------onStop ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        YeeLoger.state(this.getClass().getName(), "---------onDestroy ");
        super.onDestroyView();
    }

}
