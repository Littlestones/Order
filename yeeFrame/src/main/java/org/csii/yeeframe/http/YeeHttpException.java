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

package org.csii.yeeframe.http;

/**
 * 整个框架异常的基类
 */
@SuppressWarnings("serial")
public class YeeHttpException extends Exception {
    public final NetworkResponse networkResponse;

    public YeeHttpException() {
        networkResponse = null;
    }

    public YeeHttpException(NetworkResponse response) {
        networkResponse = response;
    }

    public YeeHttpException(String exceptionMessage) {
        super(exceptionMessage);
        networkResponse = null;
    }

    public YeeHttpException(String exceptionMessage, NetworkResponse response) {
        super(exceptionMessage);
        networkResponse = response;
    }

    public YeeHttpException(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
        networkResponse = null;
    }

    public YeeHttpException(Throwable cause) {
        super(cause);
        networkResponse = null;
    }
}
