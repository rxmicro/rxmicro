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

package io.rxmicro.rest.client.detail;

import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.rest.local.FromStringValueConverter;

import java.util.List;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class ModelReader<T> extends FromStringValueConverter {

    public T readSingle(final ClientHttpResponse response) {
        throw new AbstractMethodError("Annotation processor did not generate an implementation of this method");
    }

    public List<T> readList(final ClientHttpResponse response) {
        throw new AbstractMethodError("Annotation processor did not generate an implementation of this method");
    }
}
