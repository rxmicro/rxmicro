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
import io.rxmicro.cdi.resource.ClasspathPropertiesResourceConverter;
import io.rxmicro.cdi.resource.FilePropertiesResourceConverter;

import java.util.Map;
import java.util.Properties;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.InputStreamResources.CLASSPATH_SCHEME;

/**
 * @author nedis
 * @since 0.6
 */
@Singleton
public final class PropertiesResourceConverterClassResolver implements ConverterClassResolver {

    private static final String PROPERTIES_TYPE =
            format("?<?,?>", Map.class.getName(), String.class.getName(), String.class.getName());

    @Override
    public boolean isSupported(final String resourcePath) {
        return resourcePath.endsWith(".properties");
    }

    @Override
    public TypeElement resolve(final String resourcePath,
                               final VariableElement field) {
        final String type = field.asType().toString();
        if (PROPERTIES_TYPE.equals(type)) {
            if (resourcePath.startsWith(CLASSPATH_SCHEME)) {
                return new ClassWrapperTypeElement(ClasspathPropertiesResourceConverter.class);
            } else {
                return new ClassWrapperTypeElement(FilePropertiesResourceConverter.class);
            }
        } else if (Properties.class.getName().equals(type)) {
            throw new InterruptProcessingException(
                    field,
                    "Use '?' instead of '?' type!",
                    PROPERTIES_TYPE, Properties.class.getSimpleName()
            );
        } else {
            throw new InterruptProcessingException(
                    field,
                    "Unsupported resource type. Only '?' supported",
                    PROPERTIES_TYPE
            );
        }
    }
}
