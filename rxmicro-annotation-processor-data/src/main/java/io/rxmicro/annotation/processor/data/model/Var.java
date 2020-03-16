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

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;
import java.util.Objects;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public class Var {

    private final TypeMirror type;

    private final String name;

    private final String getter;

    public Var(final VariableElement element) {
        this(element.asType(), element.getSimpleName().toString());
    }

    public Var(final VariableElement element,
               final String getter) {
        this(element.asType(), element.getSimpleName().toString(), getter);
    }

    public Var(final TypeMirror type,
               final String name) {
        this(type, name, name);
    }

    public Var(final TypeMirror type,
               final String name,
               final String getter) {
        this.type = require(type);
        this.name = require(name);
        this.getter = require(getter);
    }

    public String getGetter() {
        return getter;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return type;
    }

    public boolean is(final Class<?> type) {
        return type.getName().equals(this.type.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(type.toString(), name);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Var var = (Var) o;
        return type.toString().equals(var.type.toString()) &&
                name.equals(var.name);
    }

    @Override
    public String toString() {
        return format("? ?", type, name);
    }
}
