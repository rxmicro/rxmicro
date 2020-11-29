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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;

import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.HTTP_BODY;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.QUERY_OR_HTTP_BODY;
import static io.rxmicro.annotation.processor.rest.model.converter.ReaderType.QUERY_STRING;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class ModelReaderConfigurator {

    private final boolean headersPresents;

    private final boolean internalsPresents;

    private final boolean pathVariablesPresents;

    private final boolean queryParamsPresents;

    private final boolean bodyParamsPresents;

    public ModelReaderConfigurator(final RestObjectModelClass modelClass,
                                   final ReaderType readerType) {
        this.headersPresents = modelClass.isHeadersPresent();
        this.internalsPresents = modelClass.isInternalsPresent();
        this.pathVariablesPresents = modelClass.isPathVariablesPresent();
        this.queryParamsPresents = modelClass.isParamsPresent() && (readerType == QUERY_STRING || readerType == QUERY_OR_HTTP_BODY);
        this.bodyParamsPresents = modelClass.isParamsPresent() && (readerType == HTTP_BODY || readerType == QUERY_OR_HTTP_BODY);
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isHeadersPresents() {
        return headersPresents;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isInternalsPresents() {
        return internalsPresents;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isPathVariablesPresents() {
        return pathVariablesPresents;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isQueryParamsPresents() {
        return queryParamsPresents;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isBodyParamsPresents() {
        return bodyParamsPresents;
    }

    @UsedByFreemarker("$$RestJsonModelReaderTemplate.javaftl")
    public boolean isWithoutFields() {
        return !headersPresents && !internalsPresents && !pathVariablesPresents && !queryParamsPresents && !bodyParamsPresents;
    }

    @Override
    public String toString() {
        return "ModelReaderConfigurator{" +
                "headersPresents=" + headersPresents +
                ", internalsPresents=" + internalsPresents +
                ", pathVariablePresents=" + pathVariablesPresents +
                ", queryParamsPresents=" + queryParamsPresents +
                ", bodyParamsPresents=" + bodyParamsPresents +
                '}';
    }
}
