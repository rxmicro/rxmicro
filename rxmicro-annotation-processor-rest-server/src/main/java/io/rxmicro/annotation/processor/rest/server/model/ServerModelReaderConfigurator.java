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

import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.converter.ReaderType;

import java.util.function.Predicate;

/**
 * @author nedis
 * @since 0.7.2
 */
public final class ServerModelReaderConfigurator {

    //private final RestObjectModelClass modelClass;

    private final boolean headersPresents;

    private final boolean internalsPresents;

    private final boolean pathVariablesPresents;

    private final boolean queryParamsPresents;

    private final boolean bodyParamsPresents;

    public ServerModelReaderConfigurator(final RestObjectModelClass modelClass,
                                         final ReaderType readerType) {
        /*this.modelClass = modelClass;
        this.headersPresents = modelClass.isHeadersPresent();
        this.internalsPresents = modelClass.isInternalsPresent();
        this.pathVariablesPresents = modelClass.isPathVariablesPresent();*/
        /*this.queryParamsPresents = modelClass.isParamEntriesPresent() && (readerType == QUERY_STRING || readerType == QUERY_OR_HTTP_BODY);
        this.bodyParamsPresents = modelClass.isParamEntriesPresent() && (readerType == HTTP_BODY || readerType == QUERY_OR_HTTP_BODY);*/
        this.headersPresents = isPresent(modelClass, RestObjectModelClass::isHeadersPresent);
        this.internalsPresents = isPresent(modelClass, RestObjectModelClass::isInternalsPresent);
        this.pathVariablesPresents = isPresent(modelClass, RestObjectModelClass::isPathVariablesPresent);
        if (isPresent(modelClass, RestObjectModelClass::isParamEntriesPresent)) {
            this.queryParamsPresents = readerType.isQueryPresent();
            this.bodyParamsPresents = readerType.isHttpBodyPresent();
        } else {
            this.queryParamsPresents = false;
            this.bodyParamsPresents = false;
        }
    }

    private static boolean isPresent(final RestObjectModelClass modelClass,
                                     final Predicate<RestObjectModelClass> predicate) {
        if (predicate.test(modelClass)) {
            return true;
        }
        for (final ObjectModelClass<RestModelField> parent : modelClass.getAllParents()) {
            if (predicate.test((RestObjectModelClass) parent)) {
                return true;
            }
        }
        return false;
    }

    @UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isHeadersPresents() {
        return headersPresents;
    }

    @UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isInternalsPresents() {
        return internalsPresents;
    }

    @UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isPathVariablesPresents() {
        return pathVariablesPresents;
    }

    @UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isQueryParamsPresents() {
        return queryParamsPresents;
    }

    @UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isBodyParamsPresents() {
        return bodyParamsPresents;
    }

    /*@UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isWithoutFields() {
        return !queryParamsPresents && !bodyParamsPresents &&
                !isPresent(modelClass, RestObjectModelClass::isHeadersPresent) &&
                !isPresent(modelClass, RestObjectModelClass::isInternalsPresent) &&
                !isPresent(modelClass, RestObjectModelClass::isPathVariablesPresent);
    }*/

    /*@UsedByFreemarker("$$RestJsonServerModelReaderTemplate.javaftl")
    public boolean isWithoutFields() {
        return !headersPresents && !internalsPresents && !pathVariablesPresents && !queryParamsPresents && !bodyParamsPresents;
    }*/

    @Override
    public String toString() {
        return "ModelReaderConfigurator{" +
                "headers=" + headersPresents +
                ", internals=" + internalsPresents +
                ", pathVariable=" + pathVariablesPresents +
                ", queryParams=" + queryParamsPresents +
                ", bodyParams=" + bodyParamsPresents +
                '}';
    }
}
