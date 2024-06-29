/*
 * Copyright (c) 2020. https://rxmicro.io
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

package io.rxmicro.test.local.component;

import io.rxmicro.test.local.component.builder.BlockingHttpClientBuilder;

/**
 * @author nedis
 * @since 0.7
 */
public final class StatelessComponentFactory {

    private static volatile ServerPortHelper serverPortHelper;

    private static volatile BlockingHttpClientBuilder blockingHttpClientBuilder;

    private static volatile ConfigResolver configResolver;

    public static ServerPortHelper getServerPortHelper() {
        if (serverPortHelper == null) {
            synchronized (StatelessComponentFactory.class) {
                if (serverPortHelper == null) {
                    serverPortHelper = new ServerPortHelper();
                }
            }
        }
        return serverPortHelper;
    }

    public static BlockingHttpClientBuilder getBlockingHttpClientBuilder() {
        if (blockingHttpClientBuilder == null) {
            synchronized (StatelessComponentFactory.class) {
                if (blockingHttpClientBuilder == null) {
                    blockingHttpClientBuilder = new BlockingHttpClientBuilder();
                }
            }
        }
        return blockingHttpClientBuilder;
    }

    public static ConfigResolver getConfigResolver() {
        if (configResolver == null) {
            synchronized (StatelessComponentFactory.class) {
                if (configResolver == null) {
                    configResolver = new ConfigResolver();
                }
            }
        }
        return configResolver;
    }

    private StatelessComponentFactory() {
    }
}
