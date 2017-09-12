/*
 * Copyright (c) 2014,CSII.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.csii.yeeframe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import org.csii.yeeframe.ui.FrameActivity;
import org.csii.yeeframe.ui.YeeActivityStack;
import org.csii.yeeframe.utils.YeeLoger;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * @author yee.zh
 * @version 1.0
 */
public abstract class YeeActivity extends FrameActivity {

    /**
     * 当前Activity状态
     */
    public static enum ActivityState {
        RESUME, PAUSE, STOP, DESTROY
    }

    public Activity aty;
    /** Activity状态 */
    public ActivityState activityState = ActivityState.DESTROY;

    /***************************************************************************
     * 
     * print Activity callback methods
     * 
     ***************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        aty = this;
        YeeActivityStack.create().addActivity(this);
        YeeLoger.state(this.getClass().getName(), "---------onCreate ");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        YeeLoger.state(this.getClass().getName(), "---------onStart ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityState = ActivityState.RESUME;
        YeeLoger.state(this.getClass().getName(), "---------onResume ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityState = ActivityState.PAUSE;
        YeeLoger.state(this.getClass().getName(), "---------onPause ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityState = ActivityState.STOP;
        YeeLoger.state(this.getClass().getName(), "---------onStop ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        YeeLoger.state(this.getClass().getName(), "---------onRestart ");
    }

    @Override
    protected void onDestroy() {
        activityState = ActivityState.DESTROY;
        YeeLoger.state(this.getClass().getName(), "---------onDestroy ");
        super.onDestroy();
        YeeActivityStack.create().finishActivity(this);
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls) {
        showActivity(aty, cls);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Intent it) {
        showActivity(aty, it);
        aty.finish();
    }

    /**
     * skip to @param(cls)，and call @param(aty's) finish() method
     */
    @Override
    public void skipActivity(Activity aty, Class<?> cls, Bundle extras) {
        showActivity(aty, cls, extras);
        aty.finish();
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Intent it) {
        aty.startActivity(it);
    }

    /**
     * show to @param(cls)，but can't finish activity
     */
    @Override
    public void showActivity(Activity aty, Class<?> cls, Bundle extras) {
        Intent intent = new Intent();
        intent.putExtras(extras);
        intent.setClass(aty, cls);
        aty.startActivity(intent);
    }
    /**
     * 重写onActivityResult方法，使二个或多个fragment嵌套使用时能收到onActivityResult回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
                Log.w(TAG, "Activity result fragment index out of range: 0x"
                        + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
                Log.w(TAG, "Activity result no fragment exists for index: 0x"
                        + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }
    }
        /*** 递归调用，对所有子Frag0ment生效
         * @param frag
         * @param requestCode
         * @param resultCode
         * @param data
         */
    private void handleResult (Fragment frag,int requestCode, int resultCode, Intent data){
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }
}
