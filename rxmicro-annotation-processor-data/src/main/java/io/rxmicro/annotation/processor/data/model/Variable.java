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

import io.rxmicro.common.meta.BuilderMethod;

import java.util.Objects;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public class Variable {

    private final VariableElement element;

    private final String name;

    private final String getter;

    private final int repeatCount;

    protected Variable(final VariableElement element,
                       final String name,
                       final String getter,
                       final int repeatCount) {
        this.element = require(element);
        this.name = require(name);
        this.getter = require(getter);
        this.repeatCount = repeatCount;
    }

    public Variable(final VariableElement element) {
        this(element, element.getSimpleName().toString(), element.getSimpleName().toString(), 1);
    }

    public Variable(final VariableElement element,
                    final String getter) {
        this(element, element.getSimpleName().toString(), getter, 1);
    }

    public String getGetter() {
        return getter;
    }

    public String getName() {
        return name;
    }

    public TypeMirror getType() {
        return element.asType();
    }

    public Element getElement(){
        return element;
    }

    public boolean is(final Class<?> type) {
        return type.getName().equals(getType().toString());
    }

    public boolean is(final String fullClassName) {
        return fullClassName.equals(getType().toString());
    }

    public boolean isRepeated(){
        return repeatCount > 1;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final Variable variable = (Variable) other;
        return repeatCount == variable.repeatCount &&
                element.equals(variable.element) &&
                name.equals(variable.name) &&
                getter.equals(variable.getter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, name, getter, repeatCount);
    }

    @Override
    public String toString() {
        return format("? ?", getType(), name);
    }

    /**
     * @author nedis
     * @since 0.7
     */
    public static final class Builder {

        private VariableElement variableElement;

        private String name;

        private String getter;

        private int repeatCount = 1;

        @BuilderMethod
        public Builder setVariableElement(final VariableElement variableElement) {
            this.variableElement = require(variableElement);
            setName(variableElement.getSimpleName().toString());
            setGetter(name);
            return this;
        }

        @BuilderMethod
        public Builder setName(final String name) {
            this.name = require(name);
            setGetter(name);
            return this;
        }

        @BuilderMethod
        public Builder setGetter(final String getter) {
            this.getter = require(getter);
            return this;
        }

        @BuilderMethod
        public Builder setRepeatCount(final int repeatCount) {
            this.repeatCount = repeatCount;
            return this;
        }

        public Variable build() {
            return new Variable(variableElement, name, getter, repeatCount);
        }
    }
}
