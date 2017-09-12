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
package org.csii.yeeframe.bitmap;

import org.csii.yeeframe.bitmap.ImageDisplayer.ImageCache;
import org.csii.yeeframe.http.HttpConfig;
import org.csii.yeeframe.utils.YeeLoger;

/**
 * Bitmap配置器
 * 
 * @author yee.zh
 * 
 */
public class BitmapConfig {

    public boolean isDEBUG = YeeLoger.DEBUG_LOG;

    /** 新版图片缓存与http缓存处于同一目录 */
    @Deprecated
    public static String CACHEPATH = HttpConfig.CACHEPATH;

    /** 磁盘缓存大小 */
    public static int DISK_CACHE_SIZE = 10 * 1024 * 1024;
    /** 磁盘缓存器 **/
    public static ImageCache mMemoryCache;

    public int cacheTime = 1440000;
    // 为了防止网速很快的时候速度过快而造成先显示加载中图片，然后瞬间显示网络图片的闪烁问题
    public long delayTime = 100;

    public BitmapConfig() {
        if (mMemoryCache == null) {
            mMemoryCache = new BitmapMemoryCache();
        }
    }
}
