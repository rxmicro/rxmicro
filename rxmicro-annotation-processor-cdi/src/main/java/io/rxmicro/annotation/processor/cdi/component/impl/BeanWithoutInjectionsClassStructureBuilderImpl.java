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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.BeanWithoutInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.cdi.model.InjectionPoint;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.BEAN;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.MULTI_BINDER;
import static io.rxmicro.annotation.processor.common.util.Elements.allSuperTypesAndInterfaces;
import static io.rxmicro.annotation.processor.common.util.Elements.getTypeElementsAtAllNotStandardModules;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class BeanWithoutInjectionsClassStructureBuilderImpl
        extends AbstractBeanDefinitionClassStructureBuilder
        implements BeanWithoutInjectionsClassStructureBuilder {

    @Override
    public Set<BeanSupplierClassStructure> build(final EnvironmentContext environmentContext,
                                                 final Set<BeanSupplierClassStructure> beanWithInjectionsClassStructures) {
        final Map<String, Set<TypeElement>> implMap = buildImplMap(environmentContext);
        final Set<String> alreadyProcessedClasses = beanWithInjectionsClassStructures.stream()
                .map(s -> s.getBeanDefinition().getBeanTypeElement().asType().toString())
                .collect(toSet());
        final List<InjectionPoint> injectionPoints = beanWithInjectionsClassStructures.stream()
                .flatMap(s -> s.getBeanDefinition().getInjectionPoints().stream())
                .collect(toList());

        final Set<BeanSupplierClassStructure> result = new HashSet<>();
        for (int i = 0; i < injectionPoints.size(); i++) {
            getInjectionPointBeanFullClassName(injectionPoints.get(i))
                    .flatMap(type -> Optional.ofNullable(implMap.get(type)))
                    .ifPresent(typeElements -> typeElements.forEach(typeElement -> {
                        if (alreadyProcessedClasses.add(typeElement.asType().toString())) {
                            final BeanSupplierClassStructure beanSupplierClassStructure = buildCDIBeanDefinitionClassStructure(typeElement);
                            result.add(beanSupplierClassStructure);
                            injectionPoints.addAll(beanSupplierClassStructure.getBeanDefinition().getInjectionPoints());
                        }
                    }));
        }
        return result;
    }

    private Optional<String> getInjectionPointBeanFullClassName(final InjectionPoint injectionPoint) {
        if (injectionPoint.getType() == BEAN) {
            return Optional.of(injectionPoint.getModelField().getFieldClass().toString());
        } else if (injectionPoint.getType() == MULTI_BINDER) {
            return Optional.of(((DeclaredType) injectionPoint.getModelField().getFieldClass()).getTypeArguments().get(0).toString());
        } else {
            return Optional.empty();
        }
    }

    private Map<String, Set<TypeElement>> buildImplMap(final EnvironmentContext environmentContext) {
        final Set<TypeElement> allClassesAtAllNotStandardModules = getAllClassesAtAllNotStandardModules(environmentContext);
        final Map<String, Set<TypeElement>> map = new HashMap<>();
        for (final TypeElement typeElement : allClassesAtAllNotStandardModules) {
            allSuperTypesAndInterfaces(typeElement, true, true).forEach(superType ->
                    addSubType(map, typeElement, superType)
            );
            getFactoryClassEntry(typeElement).ifPresent(entry ->
                    addSubType(map, typeElement, entry.getValue())
            );
        }
        return map;
    }

    private void addSubType(final Map<String, Set<TypeElement>> map,
                            final TypeElement typeElement,
                            final TypeElement superType) {
        final Set<TypeElement> typeElements = map.computeIfAbsent(
                superType.asType().toString(),
                k -> new HashSet<>()
        );
        if (typeElements.stream().noneMatch(te ->
                te.asType().toString().equals(typeElement.asType().toString()))) {
            typeElements.add(typeElement);
        }
    }

    private Set<TypeElement> getAllClassesAtAllNotStandardModules(final EnvironmentContext environmentContext) {
        return getTypeElementsAtAllNotStandardModules(environmentContext, te -> te.getKind() == ElementKind.CLASS);
    }
}
