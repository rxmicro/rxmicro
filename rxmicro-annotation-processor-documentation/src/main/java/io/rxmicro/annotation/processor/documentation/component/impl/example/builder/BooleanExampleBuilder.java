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

package io.rxmicro.annotation.processor.documentation.component.impl.example.builder;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.documentation.component.impl.example.TypeExampleBuilder;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.validation.constraint.AssertTrue;

import javax.lang.model.type.TypeMirror;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class BooleanExampleBuilder implements TypeExampleBuilder {

    @Override
    public boolean isSupported(final RestModelField restModelField,
                               final TypeMirror typeMirror) {
        return Boolean.class.getName().equals(typeMirror.toString());
    }

    @Override
    public Boolean getExample(final RestModelField restModelField,
                              final TypeMirror typeMirror) {
        final AssertTrue assertTrue = restModelField.getAnnotation(AssertTrue.class);
        return assertTrue != null && !assertTrue.off();
    }
}
