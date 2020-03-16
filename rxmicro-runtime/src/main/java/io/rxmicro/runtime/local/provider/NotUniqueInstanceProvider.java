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
import io.rxmicro.runtime.local.error.NotUniqueInstanceException;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class NotUniqueInstanceProvider<T> implements InstanceProvider<T> {

    private final Class<T> type;

    public NotUniqueInstanceProvider(final Class<T> type) {
        this.type = require(type);
    }

    @Override
    public Class<? extends T> getType() {
        return type;
    }

    @Override
    public T getInstance() {
        throw new NotUniqueInstanceException(type);
    }
}
