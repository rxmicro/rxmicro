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

package io.rxmicro.annotation.processor.cdi.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.cdi.component.BeanRegistrationQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.ConstructorInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.component.FactoryMethodFinder;
import io.rxmicro.annotation.processor.cdi.component.FieldOrMethodInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.component.PostConstructMethodFinder;
import io.rxmicro.annotation.processor.cdi.model.BeanDefinition;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.cdi.Factory;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static io.rxmicro.annotation.processor.common.util.Elements.allMethods;
import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;
import static io.rxmicro.annotation.processor.common.util.Elements.findSuperType;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateAccessibleDefaultConstructor;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateGenericType;
import static io.rxmicro.annotation.processor.common.util.validators.TypeValidators.validateThatElementIsPublic;
import static java.util.Map.entry;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
abstract class AbstractBeanDefinitionClassStructureBuilder {

    @Inject
    private ConstructorInjectionPointBuilder constructorInjectionPointBuilder;

    @Inject
    private FieldOrMethodInjectionPointBuilder fieldOrMethodInjectionPointBuilder;

    @Inject
    private PostConstructMethodFinder postConstructMethodFinder;

    @Inject
    private FactoryMethodFinder factoryMethodFinder;

    @Inject
    private BeanRegistrationQualifierRuleBuilder beanRegistrationQualifierRuleBuilder;

    final BeanSupplierClassStructure buildCDIBeanDefinitionClassStructure(final TypeElement beanTypeElement) {
        final BeanDefinition.Builder builder = new BeanDefinition.Builder()
                .setBeanTypeElement(beanTypeElement);
        if (constructorInjectionPointBuilder.isConstructorInjection(beanTypeElement)) {
            builder
                    .setConstructorInjection(true)
                    .setInjectionPoints(constructorInjectionPointBuilder.build(beanTypeElement));
        } else {
            builder.setInjectionPoints(fieldOrMethodInjectionPointBuilder.build(beanTypeElement));
        }
        postConstructMethodFinder.findMethod(beanTypeElement)
                .ifPresent(builder::setPostConstructMethod);
        factoryMethodFinder.findMethod(beanTypeElement)
                .ifPresent(builder::setFactoryMethod);
        // If current bean is factory class:
        getFactoryClassEntry(beanTypeElement)
                .ifPresent(e -> builder
                        .setFactoryTypeProviderMethod(e.getKey())
                        .setFactoryTypeProviderType(e.getValue())
                );

        final BeanDefinition beanDefinition = builder.build();
        validate(beanTypeElement, beanDefinition);
        return new BeanSupplierClassStructure(
                beanDefinition,
                beanRegistrationQualifierRuleBuilder.build(beanDefinition.getBeanTypeElement()),
                beanDefinition.getFactoryTypeProviderMethod()
                        .map(e -> beanRegistrationQualifierRuleBuilder.build(e))
                        .orElse(List.of())
        );
    }

    protected final Optional<Map.Entry<ExecutableElement, TypeElement>> getFactoryClassEntry(final TypeElement beanTypeElement) {
        final boolean result = beanTypeElement.getAnnotation(Factory.class) != null;
        if (result) {
            final TypeMirror supplierType = findSuperType(beanTypeElement, Supplier.class).orElseThrow(() -> {
                throw new InterruptProcessingException(
                        beanTypeElement,
                        "Factory bean must implement '?' interface",
                        Supplier.class.getName()
                );
            });
            validateGenericType(beanTypeElement, supplierType);
            final TypeMirror factoryTypeMirror = ((DeclaredType) supplierType).getTypeArguments().get(0);
            final TypeElement typeElement = asTypeElement(factoryTypeMirror).orElseThrow(() -> {
                throw new InterruptProcessingException(
                        beanTypeElement,
                        "? is not class",
                        getTypes().erasure(factoryTypeMirror).toString()
                );
            });
            return allMethods(beanTypeElement, e -> "get".equals(e.getSimpleName().toString()) && e.getParameters().isEmpty()).stream()
                    .findFirst()
                    .map(e -> entry(e, typeElement));
        } else {
            return Optional.empty();
        }
    }

    private void validate(final TypeElement beanTypeElement,
                          final BeanDefinition beanDefinition) {
        validateThatElementIsPublic(beanTypeElement, "Class must be a public. Add 'public' modifier!");
        if (!beanDefinition.isFactoryMethodPresent() && !beanDefinition.isConstructorInjection()) {
            validateAccessibleDefaultConstructor(beanTypeElement);
        }
    }
}
