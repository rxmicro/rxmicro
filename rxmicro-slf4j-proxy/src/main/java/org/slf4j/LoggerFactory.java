/*
 * Copyright (c) 2020. http://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.slf4j;

import org.slf4j.internal.Slf4jLoggerProxy;

/**
 * Unfortunately some db drivers removed support of JUL,
 * so the RxMicro framework requires a org.slf4j proxy to enable logging without slf4j-api
 *
 * Read more: https://github.com/mongodb/mongo-java-driver/commit/6a163f715fe08ed8d39acac3d11c896ae547df73
 *
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class LoggerFactory {

    public static Logger getLogger(String name) {
        return new Slf4jLoggerProxy(name);
    }

    public static Logger getLogger(Class<?> clazz) {
        return getLogger(clazz.getName());
    }

    public static ILoggerFactory getILoggerFactory() {
        throw new UnsupportedOperationException("Proxy does not contain ILoggerFactory implementation");
    }
}
