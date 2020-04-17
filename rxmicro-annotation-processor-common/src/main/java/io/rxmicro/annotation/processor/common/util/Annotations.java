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

package io.rxmicro.annotation.processor.common.util;

import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.common.meta.ReadMore;
import io.rxmicro.config.Config;
import io.rxmicro.config.DefaultConfigValue;
import io.rxmicro.config.DefaultConfigValueSupplier;

import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.AnnotationProcessorEnvironment.elements;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Config.getDefaultNameSpace;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class Annotations {

    public static <T extends Annotation> T getPresentOrDefaultAnnotation(final AnnotatedConstruct annotated,
                                                                         final Class<T> annotationClass) {
        return Optional.ofNullable(annotated.getAnnotation(annotationClass))
                .orElseGet(() -> defaultAnnotationInstance(annotationClass));
    }

    public static Optional<ReadMore> getReadMore(final Class<? extends Annotation> annotationClass) {
        return Optional.ofNullable(annotationClass.getAnnotation(ReadMore.class));
    }

    @SuppressWarnings("unchecked")
    public static <T extends Annotation> T defaultAnnotationInstance(final Class<T> annotationClass) {
        final Object mock = new Object();
        return (T) Proxy.newProxyInstance(
                annotationClass.getClassLoader(),
                new Class<?>[]{annotationClass},
                (proxy, method, args) -> {
                    if ("toString".equals(method.getName()) && method.getParameterCount() == 0) {
                        return format("? Proxy", annotationClass.getSimpleName());
                    } else if (method.getDeclaringClass() == Object.class) {
                        return method.invoke(mock, args);
                    } else {
                        return method.getDefaultValue();
                    }
                }
        );
    }

    public static Object getAnnotationValue(final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues,
                                            final String valueName) {
        return elementValues.entrySet()
                .stream()
                .filter(e -> e.getKey().getSimpleName().toString().equals(valueName))
                .findFirst()
                .map(e -> e.getValue().getValue())
                .orElseThrow(createInternalErrorSupplier("Expected required annotation parameter with name: '?'", valueName));
    }

    public static Object getAnnotationValue(final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues) {
        return getAnnotationValue(elementValues, "value");
    }

    public static TypeElement getAnnotationElement(final AnnotationMirror annotation) {
        return asTypeElement(annotation.getAnnotationType()).orElseThrow();
    }

    public static Optional<TypeElement> getAnnotationClassParameter(final Supplier<Class<?>> classSupplier,
                                                                    final Class<?> excludeClass) {
        TypeElement type;
        try {
            type = elements().getTypeElement(classSupplier.get().getName());
        } catch (final MirroredTypeException e) {
            type = asTypeElement(e.getTypeMirror()).orElse(null);
        }
        return Optional.ofNullable(type)
                .filter(t -> !excludeClass.getName().equals(t.getQualifiedName().toString()));
    }

    public static TypeElement getRequiredAnnotationClassParameter(final Supplier<Class<?>> classSupplier) {
        return getAnnotationClassParameter(classSupplier, Void.class).orElseThrow();
    }

    public static Optional<TypeElement> getAnnotationClassParameter(final Supplier<Class<?>> classSupplier) {
        return getAnnotationClassParameter(classSupplier, Void.class);
    }

    public static List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigValues(final String defaultConfigNameSpace,
                                                                                          final Element element) {
        return Stream.concat(
                Arrays.stream(element.getAnnotationsByType(DefaultConfigValue.class)).map(defaultConfigValue -> entry(
                        getDefaultConfigValueName(
                                element, defaultConfigValue.name(), defaultConfigValue::configClass, defaultConfigNameSpace
                        ),
                        new DefaultConfigProxyValue(defaultConfigValue.value())
                )),
                Arrays.stream(element.getAnnotationsByType(DefaultConfigValueSupplier.class)).map(defaultConfigValue -> entry(
                        getDefaultConfigValueName(
                                element, defaultConfigValue.name(), defaultConfigValue::configClass, defaultConfigNameSpace
                        ),
                        new DefaultConfigProxyValue(getRequiredAnnotationClassParameter(defaultConfigValue::supplier))
                ))
        ).collect(Collectors.toList());
    }

    private static String getDefaultConfigValueName(final Element element,
                                                    final String name,
                                                    final Supplier<Class<?>> classSupplier,
                                                    final String defaultConfigNameSpace) {
        if (name.indexOf('.') != -1) {
            if (getAnnotationClassParameter(classSupplier, Config.class).isPresent()) {
                throw new InterruptProcessingException(element, "Redundant config class! Remove it!");
            }
            return name;
        } else {
            return getAnnotationClassParameter(classSupplier, Config.class)
                    .map(te -> getDefaultNameSpace(getSimpleName(te)))
                    .orElse(defaultConfigNameSpace) + "." + name;
        }
    }

    private Annotations() {
    }
}
