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

package io.rxmicro.annotation.processor.cdi.component.impl.resolver;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.ConverterClassResolver;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.virtual.ClassWrapperTypeElement;
import io.rxmicro.cdi.resource.ClasspathJsonArrayResourceConverter;
import io.rxmicro.cdi.resource.ClasspathJsonObjectResourceConverter;
import io.rxmicro.cdi.resource.FileJsonArrayResourceConverter;
import io.rxmicro.cdi.resource.FileJsonObjectResourceConverter;

import java.util.List;
import java.util.Map;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.InputStreamResources.CLASSPATH_SCHEME;

/**
 * @author nedis
 * @since 0.6
 */
@Singleton
public final class JsonResourceConverterClassResolver implements ConverterClassResolver {

    private static final String JSON_OBJECT_TYPE =
            format("?<?,?>", Map.class.getName(), String.class.getName(), Object.class.getName());

    private static final String JSON_ARRAY_TYPE =
            format("?<?>", List.class.getName(), Object.class.getName());

    @Override
    public boolean isSupported(final String resourcePath) {
        return resourcePath.endsWith(".json");
    }

    @Override
    public TypeElement resolve(final String resourcePath,
                               final VariableElement field) {
        final String type = field.asType().toString();
        if (JSON_OBJECT_TYPE.equals(type)) {
            if (resourcePath.startsWith(CLASSPATH_SCHEME)) {
                return new ClassWrapperTypeElement(ClasspathJsonObjectResourceConverter.class);
            } else {
                return new ClassWrapperTypeElement(FileJsonObjectResourceConverter.class);
            }
        } else if (JSON_ARRAY_TYPE.equals(type)) {
            if (resourcePath.startsWith(CLASSPATH_SCHEME)) {
                return new ClassWrapperTypeElement(ClasspathJsonArrayResourceConverter.class);
            } else {
                return new ClassWrapperTypeElement(FileJsonArrayResourceConverter.class);
            }
        } else {
            throw new InterruptProcessingException(
                    field,
                    "Unsupported resource type. Only '?' and '?' supported",
                    JSON_OBJECT_TYPE, JSON_ARRAY_TYPE
            );
        }
    }
}
