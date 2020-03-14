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

package io.rxmicro.annotation.processor.common.model.method;

import io.rxmicro.annotation.processor.common.util.Names;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.type.TypeMirror;
import java.util.List;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.util.stream.Collectors.joining;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class MethodName {

    private final String simpleName;

    private final List<TypeMirror> parameterTypes;

    private final boolean isOverloaded;

    public MethodName(final String simpleName,
                      final List<TypeMirror> parameterTypes,
                      final boolean isOverloaded) {
        this.simpleName = require(simpleName);
        this.parameterTypes = require(parameterTypes);
        this.isOverloaded = isOverloaded;
    }

    @UsedByFreemarker({
            "$$RestControllerConsumerMethodBodyTemplate.javaftl",
            "$$RestControllerProcessorMethodBodyTemplate.javaftl",
            "$$RestControllerProducerMethodBodyTemplate.javaftl",
            "$$RestControllerSimplestMethodBodyTemplate.javaftl"
    })
    public String getSimpleName() {
        return simpleName;
    }

    @UsedByFreemarker({
            "$$RestControllerProcessorMethodBodyTemplate.javaftl",
            "$$RestControllerProducerMethodBodyTemplate.javaftl"
    })
    public String getUniqueJavaName() {
        if (isOverloaded && parameterTypes.size() > 0) {
            return format("??", simpleName, parameterTypes.stream().map(t ->
                    t.getKind().isPrimitive() ? t.toString() : Names.getSimpleName(t)).collect(joining("")));
        } else {
            return simpleName;
        }
    }

    @UsedByFreemarker("$$RestControllerTemplate.javaftl")
    public String getMethodSignature() {
        return format("?(?)", simpleName, parameterTypes.stream()
                .map(TypeMirror::toString)
                .collect(joining(","))
        );
    }
}
