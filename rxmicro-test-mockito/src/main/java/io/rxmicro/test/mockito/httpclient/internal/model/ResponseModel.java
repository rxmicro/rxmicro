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

package io.rxmicro.test.mockito.httpclient.internal.model;

import io.rxmicro.test.mockito.httpclient.HttpResponseMock;

import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ResponseModel {

    private final HttpResponseMock httpResponseMock;

    private final Throwable throwable;

    public ResponseModel(final HttpResponseMock httpResponseMock) {
        this.httpResponseMock = require(httpResponseMock);
        this.throwable = null;
    }

    public ResponseModel(final Throwable throwable) {
        this.throwable = require(throwable);
        this.httpResponseMock = null;
    }

    public Optional<HttpResponseMock> getHttpResponseMock() {
        return Optional.ofNullable(httpResponseMock);
    }

    public Optional<Throwable> getThrowable() {
        return Optional.ofNullable(throwable);
    }
}
