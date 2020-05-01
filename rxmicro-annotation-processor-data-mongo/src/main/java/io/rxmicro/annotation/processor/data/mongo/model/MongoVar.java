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

package io.rxmicro.annotation.processor.data.mongo.model;

import io.rxmicro.annotation.processor.data.model.Var;
import io.rxmicro.data.Pageable;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;

import java.util.Objects;

import static io.rxmicro.data.Pageable.LIMIT_NAMES;
import static io.rxmicro.data.Pageable.OFFSET_NAMES;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class MongoVar extends Var {

    private final boolean limit;

    private final boolean skip;

    public MongoVar(final VariableElement element,
                    final String name) {
        super(element, name);
        if (Pageable.class.getName().equals(element.asType().toString())) {
            this.limit = name.contains("getLimit");
            this.skip = name.contains("getSkip");
        } else {
            this.limit = LIMIT_NAMES.contains(element.getSimpleName().toString()) &&
                    element.asType().getKind() == TypeKind.INT;
            this.skip = OFFSET_NAMES.contains(element.getSimpleName().toString()) &&
                    element.asType().getKind() == TypeKind.INT;
        }
    }

    public boolean isLimit() {
        return limit;
    }

    public boolean isSkip() {
        return skip;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final MongoVar mongoVar = (MongoVar) o;
        return limit == mongoVar.limit &&
                skip == mongoVar.skip;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), limit, skip);
    }
}
