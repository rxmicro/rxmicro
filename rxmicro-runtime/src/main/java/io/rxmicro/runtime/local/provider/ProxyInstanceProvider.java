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

package io.rxmicro.runtime.local.provider;

import io.rxmicro.runtime.local.InstanceProvider;

import java.util.function.Consumer;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class ProxyInstanceProvider<T> implements InstanceProvider<T> {

    private final InstanceProvider<T> instanceProvider;

    private Consumer<T> afterConstructCallback;

    public ProxyInstanceProvider(final InstanceProvider<T> instanceProvider,
                                 final Consumer<T> afterConstructCallback) {
        this.instanceProvider = require(instanceProvider);
        this.afterConstructCallback = require(afterConstructCallback);
    }

    @Override
    public Class<? extends T> getType() {
        return instanceProvider.getType();
    }

    @Override
    public T getInstance() {
        final T instance = instanceProvider.getInstance();
        if (afterConstructCallback != null) {
            afterConstructCallback.accept(instance);
            afterConstructCallback = null;
        }
        return instance;
    }
}
