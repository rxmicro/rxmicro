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

import java.util.function.Supplier;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class LazyInstanceProvider<T> implements InstanceProvider<T> {

    private Class<? extends T> type;

    private Supplier<T> supplier;

    private T instance;

    public LazyInstanceProvider(final Class<? extends T> type,
                                final Supplier<T> supplier) {
        this.type = require(type);
        this.supplier = require(supplier);
    }

    @Override
    public Class<? extends T> getType() {
        return type;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getInstance() {
        if (instance == null) {
            instance = require(supplier.get());
            type = (Class<? extends T>) instance.getClass();
            supplier = null;
        }
        return instance;
    }

    @Override
    public String toString() {
        return "LazyInstanceProvider{" +
                "type=" + type +
                '}';
    }
}
