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

package io.rxmicro.annotation.processor.data.sql.model;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.data.model.Variable;

import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.Types.JAVA_PRIMITIVE_REPLACEMENT;

/**
 * @author nedis
 * @since 0.7
 */
public final class BindParameter {

    private final Variable variable;

    private final String name;

    public BindParameter(final Variable variable,
                         final String name) {
        this.variable = variable;
        this.name = name;
    }

    @UsedByFreemarker({
            "sql-lib.javaftl",
            "$$SQLRepositoryInsertMethodBodyTemplate.javaftl",
            "$$SQLRepositoryUpdateMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryInsertWithReturningMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryUpdateWithReturningMethodBodyTemplate.javaftl"
    })
    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return variable.getType();
    }

    @UsedByFreemarker({
            "$$SQLRepositoryInsertMethodBodyTemplate.javaftl",
            "$$SQLRepositoryUpdateMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryInsertWithReturningMethodBodyTemplate.javaftl",
            "$$PostgreSQLRepositoryUpdateWithReturningMethodBodyTemplate.javaftl"
    })
    public String getSimpleTypeName() {
        if (getType().getKind().isPrimitive()) {
            return JAVA_PRIMITIVE_REPLACEMENT.get(getType().getKind()).getSimpleName();
        } else {
            return getSimpleName(getType());
        }
    }
}
