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
package org.csii.yeeframe.ui;

import android.app.FragmentTransaction;
import android.os.Bundle;

import org.csii.yeeframe.utils.YeeLoger;

/**
 * Application's base Fragment,you should inherit it for your Fragment<br>
 * <p/>
 * <b>创建时间</b> 2014-5-28
 *
 * @author yee.zh
 * @version 1.0
 */
public abstract class YeeFragment extends FrameFragment {

    /***************************************************************************
     * print Fragment callback methods
     ***************************************************************************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YeeLoger.state(this.getClass().getName(), "---------onCreateView ");
    }

    @Override
    public void onResume() {
        YeeLoger.state(this.getClass().getName(), "---------onResume ");
        super.onResume();
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

    protected void switchFragmentAddStack(int fl, FrameFragment fragment, String mItemName) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(fl, fragment, mItemName)
                .commit();
    }

    protected void switchFragment(int fl, FrameFragment fragment, String mItemName) {
        getFragmentManager()
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .replace(fl, fragment, mItemName)
                .commit();
    }
}
