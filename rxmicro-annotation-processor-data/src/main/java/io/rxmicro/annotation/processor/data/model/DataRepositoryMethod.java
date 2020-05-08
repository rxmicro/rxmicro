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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.annotation.processor.common.model.method.MethodBody;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class DataRepositoryMethod {

    private final DataRepositoryMethodSignature methodSignature;

    private final MethodBody body;

    public DataRepositoryMethod(final DataRepositoryMethodSignature methodSignature,
                                final MethodBody body) {
        this.methodSignature = require(methodSignature);
        this.body = require(body);
    }

    public Set<TypeMirror> getParamEntityClasses() {
        return methodSignature.getParamEntityClasses();
    }

    public Set<TypeMirror> getReturnEntityClasses() {
        return methodSignature.getReturnEntityClasses();
    }

    @UsedByFreemarker
    public String getReturnType() {
        return methodSignature.getMethodResult().getHumanReadableReturnType();
    }

    @UsedByFreemarker
    public String getName() {
        return methodSignature.getMethod().getSimpleName().toString();
    }

    @UsedByFreemarker
    public String getParams() {
        return methodSignature.getParams().stream()
                .map(e -> format("final ? ?", e.getSimpleType(), e.getName()))
                .collect(joining(", "));
    }

    @UsedByFreemarker
    public List<String> getBodyLines() {
        return body.getLines();
    }
}
