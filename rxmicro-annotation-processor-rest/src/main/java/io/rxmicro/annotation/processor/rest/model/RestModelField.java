/*
 * Copyright 2019 http://rxmicro.io
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

package io.rxmicro.annotation.processor.rest.model;

import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.ModelField;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.constraint.Nullable;

import java.lang.annotation.Annotation;

import static io.rxmicro.annotation.processor.common.util.Annotations.defaultAnnotationInstance;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.http.HttpHeaders.REQUEST_ID;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class RestModelField extends ModelField {

    private final HttpModelType httpModelType;

    private final InternalType internalType;

    private final boolean repeat;

    public RestModelField(final AnnotatedModelElement annotatedModelElement,
                          final HttpModelType httpModelType,
                          final String modelName,
                          final boolean repeat) {
        super(annotatedModelElement, modelName);
        this.httpModelType = require(httpModelType);
        this.internalType = null;
        this.repeat = repeat;
    }

    public RestModelField(final AnnotatedModelElement annotatedModelElement,
                          final HttpModelType httpModelType,
                          final String modelName) {
        this(annotatedModelElement, httpModelType, modelName, false);
    }

    public RestModelField(final AnnotatedModelElement annotatedModelElement,
                          final InternalType internalType) {
        super(annotatedModelElement, internalType.name());
        this.httpModelType = HttpModelType.internal;
        this.internalType = internalType;
        this.repeat = false;
    }

    @UsedByFreemarker
    public boolean isRepeat() {
        return repeat;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Annotation> T getAnnotation(final Class<T> annotationClass) {
        if (annotationClass == Nullable.class) {
            if (isHttpPathVariable()) {
                return (T) defaultAnnotationInstance(Nullable.class);
            } else if (isHttpHeader() && REQUEST_ID.equalsIgnoreCase(getModelName())) {
                return (T) defaultAnnotationInstance(Nullable.class);
            }
        }
        return super.getAnnotation(annotationClass);
    }

    @UsedByFreemarker
    public HttpModelType getHttpModelType() {
        return httpModelType;
    }

    @UsedByFreemarker
    public boolean isHttpHeader() {
        return httpModelType == HttpModelType.header;
    }

    @UsedByFreemarker
    public boolean isHttpParameter() {
        return httpModelType == HttpModelType.parameter;
    }

    @UsedByFreemarker
    public boolean isHttpPathVariable() {
        return httpModelType == HttpModelType.path;
    }

    public boolean isInternalType() {
        return httpModelType == HttpModelType.internal;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isRemoteAddress() {
        return isInternalType() && internalType == InternalType.remote_address;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isRemoteAddressStringType() {
        return String.class.getName().equals(getFieldClass().toString());
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isRequestBody() {
        return isInternalType() && internalType == InternalType.request_body;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isHttpRequest() {
        return isInternalType() && internalType == InternalType.http_request;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isHttpHeaders() {
        return isInternalType() && internalType == InternalType.http_headers;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isHttpVersion() {
        return isInternalType() && internalType == InternalType.http_version;
    }

    @UsedByFreemarker("$$RestJsonModelWriterTemplate.javaftl")
    public boolean isResponseStatus() {
        return isInternalType() && internalType == InternalType.response_status;
    }

    @UsedByFreemarker("$$RestJsonModelWriterTemplate.javaftl")
    public boolean isResponseBody() {
        return isInternalType() && internalType == InternalType.response_body;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isRequestUrl() {
        return isInternalType() && internalType == InternalType.request_url;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isRequestMethod() {
        return isInternalType() && internalType == InternalType.request_method;
    }
}
