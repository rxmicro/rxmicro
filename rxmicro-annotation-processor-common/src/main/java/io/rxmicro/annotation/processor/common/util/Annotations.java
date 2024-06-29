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

import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.lang.model.AnnotatedConstruct;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;

import static io.rxmicro.annotation.processor.common.util.Elements.allDeclaredProperties;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.findSetter;
import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.common.CommonConstants.EMPTY_STRING;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Strings.startsWith;
import static io.rxmicro.config.Config.getDefaultNameSpace;
import static java.util.Map.entry;

/**
 * @author nedis
 * @since 0.1
 */
public final class Annotations {

    public static <T extends Annotation> T getRequiredAnnotation(final AnnotatedConstruct annotated,
                                                                 final Class<T> annotationClass) {
        return Optional.ofNullable(annotated.getAnnotation(annotationClass))
                .orElseThrow(createInternalErrorSupplier(
                        "'?' annotated element is not annotated by the required '@?' annotation!",
                        annotated, annotationClass.getName())
                );
    }

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

    private static TypeElement getTypeElement(final Supplier<Class<?>> classSupplier) {
        try {
            return getElements().getTypeElement(classSupplier.get().getName());
        } catch (final MirroredTypeException ex) {
            return asTypeElement(ex.getTypeMirror()).orElse(null);
        }
    }

    public static Optional<TypeElement> getAnnotationClassParameter(final Supplier<Class<?>> classSupplier,
                                                                    final Class<?> excludeClass) {
        return Optional.ofNullable(getTypeElement(classSupplier))
                .filter(t -> !excludeClass.getName().equals(t.getQualifiedName().toString()));
    }

    public static Optional<TypeElement> getAnnotationClassParameter(final Supplier<Class<?>> classSupplier) {
        return getAnnotationClassParameter(classSupplier, Void.class);
    }

    public static TypeElement getRequiredAnnotationClassParameter(final Supplier<Class<?>> classSupplier) {
        return getAnnotationClassParameter(classSupplier, Void.class).orElseThrow();
    }

    public static List<Map.Entry<String, DefaultConfigProxyValue>> getValidatedDefaultConfigValues(final String defaultConfigNameSpace,
                                                                                                   final TypeElement configClass,
                                                                                                   final Element element) {
        final List<Map.Entry<String, DefaultConfigProxyValue>> values = getDefaultConfigValues(defaultConfigNameSpace, element);
        validateDefaultConfigValues(defaultConfigNameSpace, configClass, element, values);
        return values;
    }

    public static List<Map.Entry<String, DefaultConfigProxyValue>> getValidatedDefaultConfigValues(final ModuleElement moduleElement) {
        final List<Map.Entry<String, DefaultConfigProxyValue>> values = getDefaultConfigValues(EMPTY_STRING, moduleElement);
        validateDefaultConfigValues(moduleElement, values);
        return values;
    }

    private static void validateDefaultConfigValues(final String defaultConfigNameSpace,
                                                    final TypeElement configClass,
                                                    final Element element,
                                                    final List<Map.Entry<String, DefaultConfigProxyValue>> values) {
        final Set<String> declaredProperties = allDeclaredProperties(configClass);
        for (final Map.Entry<String, DefaultConfigProxyValue> value : values) {
            final String[] namePair = value.getKey().split("\\.");
            final String nameSpace = namePair[0];
            if (!defaultConfigNameSpace.equals(nameSpace)) {
                throw new InterruptProcessingException(
                        element,
                        "Invalid namespace for property: '?'. Expected '?', but actual is '?'!",
                        value.getKey(), defaultConfigNameSpace, nameSpace
                );
            }
            final String propertyName = namePair[1];
            if (!declaredProperties.contains(propertyName)) {
                throw new InterruptProcessingException(
                        element,
                        "Config class '?' does not contain '?' property. Supported properties are : '?'." +
                                " Fix the name for default config value!",
                        configClass.asType().toString(), propertyName, declaredProperties
                );
            }
        }
    }

    private static void validateDefaultConfigValues(final ModuleElement moduleElement,
                                                    final List<Map.Entry<String, DefaultConfigProxyValue>> values) {
        for (final Map.Entry<String, DefaultConfigProxyValue> defaultConfigValue : values) {
            if (startsWith(defaultConfigValue.getKey(), '.')) {
                throw new InterruptProcessingException(
                        moduleElement,
                        "Missing name space for default config name: ?",
                        defaultConfigValue.getKey().substring(1)
                );
            }
        }
    }

    private static List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigValues(final String defaultConfigNameSpace,
                                                                                           final Element element) {
        return Stream.concat(
                Arrays.stream(element.getAnnotationsByType(DefaultConfigValue.class)).map(defaultConfigValue -> entry(
                        getDefaultConfigValueName(
                                element,
                                DefaultConfigValue.class,
                                defaultConfigValue.name(),
                                defaultConfigValue::configClass,
                                defaultConfigNameSpace
                        ),
                        new DefaultConfigProxyValue(defaultConfigValue.value())
                )),
                Arrays.stream(element.getAnnotationsByType(DefaultConfigValueSupplier.class)).map(defaultConfigValue -> entry(
                        getDefaultConfigValueName(
                                element,
                                DefaultConfigValueSupplier.class,
                                defaultConfigValue.name(),
                                defaultConfigValue::configClass,
                                defaultConfigNameSpace
                        ),
                        new DefaultConfigProxyValue(getRequiredAnnotationClassParameter(defaultConfigValue::supplier))
                ))
        ).collect(Collectors.toList());
    }

    private static String getDefaultConfigValueName(final Element element,
                                                    final Class<? extends Annotation> annotationClass,
                                                    final String name,
                                                    final Supplier<Class<?>> classSupplier,
                                                    final String defaultConfigNameSpace) {
        final Optional<TypeElement> annotationClassParameter = getAnnotationClassParameter(classSupplier, Config.class);
        if (name.indexOf('.') == -1) {
            return annotationClassParameter
                    .map(te -> {
                        validateThatConfigClassContainsProperty(element, annotationClass, te, name);
                        return getDefaultNameSpace(getSimpleName(te));
                    })
                    .orElse(defaultConfigNameSpace) + "." + name;
        } else {
            if (annotationClassParameter.isPresent()) {
                throw new InterruptProcessingException(element, "Redundant config class! Remove it!");
            }
            return name;
        }
    }

    private static void validateThatConfigClassContainsProperty(final Element element,
                                                                final Class<? extends Annotation> annotationClass,
                                                                final TypeElement typeElement,
                                                                final String property) {
        if (findSetter(typeElement, property).isEmpty()) {
            throw new InterruptProcessingException(
                    element,
                    "Invalid 'configClass' or 'name' parameter for @? annotation: " +
                            "'?' class does not contain setter for the '?' property! " +
                            "Change invalid parameter(s)!",
                    annotationClass.getName(),
                    typeElement.getQualifiedName().toString(),
                    property
            );
        }
    }

    private Annotations() {
    }
}
