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

package io.rxmicro.annotation.processor.documentation.component.impl.example.converter;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.impl.example.ExampleValueConverter;
import io.rxmicro.annotation.processor.rest.model.RestModelField;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class CharacterExampleValueConverter extends ExampleValueConverter {

    @Override
    public boolean isSupported(final RestModelField restModelField) {
        return Character.class.getName().equals(restModelField.getFieldClass().toString());
    }

    @Override
    public Object convert(final RestModelField restModelField, final String value) {
        if (value.length() != 1) {
            showInvalidExampleTypeError(restModelField, Character.class, value);
            return ERROR_DETECTED;
        } else {
            return value;
        }
    }
}
