/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.test.local;

import io.rxmicro.test.Alternative;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.test.local.util.FieldNames.getHumanReadableFieldName;
import static io.rxmicro.tool.common.Reflections.getFieldValue;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class AlternativeEntryPoint {

    private final Alternative alternative;

    private final Field testClassField;

    private Object value;

    public AlternativeEntryPoint(final Alternative alternative,
                                 final Field testClassField) {
        this.alternative = require(alternative);
        this.testClassField = require(testClassField);
    }

    public Class<?> getFieldType() {
        return testClassField.getType();
    }

    public String getHumanReadableErrorName() {
        return getHumanReadableFieldName(testClassField);
    }

    public void readValue(final List<Object> testInstances) {
        value = getFieldValue(testInstances, testClassField);
        if (value == null && !alternative.expectNull()) {
            throw new InvalidTestConfigException(
                    "Alternative couldn't be null: '?.?'",
                    testClassField.getDeclaringClass().getSimpleName(),
                    testClassField.getName()
            );
        }
    }

    public String getInjectionName() {
        return Optional.of(alternative)
                .map(Alternative::name)
                .filter(v -> !v.isEmpty())
                .orElse(testClassField.getName());
    }

    public Object getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(testClassField);
    }

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        final AlternativeEntryPoint that = (AlternativeEntryPoint) other;
        return testClassField.equals(that.testClassField);
    }

    @Override
    public String toString() {
        return "AlternativeEntryPoint{" +
                "alternative=" + alternative +
                ", testClassField=" + testClassField +
                ", value=" + value +
                '}';
    }
}
