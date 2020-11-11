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

package io.rxmicro.test.internal.validator.impl;

import io.rxmicro.config.Config;
import io.rxmicro.test.Alternative;
import io.rxmicro.test.BlockingHttpClient;
import io.rxmicro.test.BlockingHttpClientSettings;
import io.rxmicro.test.WithConfig;
import io.rxmicro.test.internal.validator.FieldValidator;
import io.rxmicro.test.local.InvalidTestConfigException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;

/**
 * @author nedis
 * @since 0.1
 */
public final class UserCreatedComponentFieldValidator extends FieldValidator {

    @Override
    public void validate(final Field field) {
        validateThatFieldIsNotStatic(field);
        for (final Annotation annotation : field.getAnnotations()) {
            final Class<? extends Annotation> annotationClass = annotation.annotationType();
            if (annotationClass != Alternative.class) {
                if (annotationClass == WithConfig.class) {
                    throw new InvalidTestConfigException(
                            "? is annotated by invalid annotation, " +
                                    "because an annotated by '@?' annotation field must have '? extends ?' type. " +
                                    "Remove the '@?' annotation!",
                            fieldNamePrefix(field),
                            WithConfig.class.getName(),
                            "?",
                            Config.class.getName(),
                            WithConfig.class.getName()
                    );
                } else if (annotationClass == BlockingHttpClientSettings.class) {
                    throw new InvalidTestConfigException(
                            "? is annotated by invalid annotation. " +
                                    "'@?' annotation is applied to field with '?' type only! " +
                                    "Remove the '@?' annotation!",
                            fieldNamePrefix(field),
                            BlockingHttpClientSettings.class.getName(),
                            BlockingHttpClient.class.getName(),
                            BlockingHttpClientSettings.class.getName()
                    );
                }
            }
        }
        validateThatFieldIsAnnotatedOnlyBySupportedOnes(field, Alternative.class, WithConfig.class, BlockingHttpClientSettings.class);
        if (isDeniedPackage(field.getType().getPackageName())) {
            throw new InvalidTestConfigException(
                    "? is an alternative with unsupported type: '?'. Remove this field!",
                    fieldNamePrefix(field), field.getType()
            );
        }
    }
}
