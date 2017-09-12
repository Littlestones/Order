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
package org.csii.yeeframe.utils;

/**
 * 
 * @author yee.zh
 */
public final class YeeConfig {

    public static final double VERSION = 2.238;

    /** 错误处理广播 */
    public static final String RECEIVER_ERROR = YeeConfig.class.getName()
            + "org.yee.android.frame.error";
    /** 无网络警告广播 */
    public static final String RECEIVER_NOT_NET_WARN = YeeConfig.class.getName()
            + "org.yee.android.frame.notnet";
    /** preference键值对 */
    public static final String SETTING_FILE = "yeeframe_preference";
    public static final String ONLY_WIFI = "only_wifi";
}
