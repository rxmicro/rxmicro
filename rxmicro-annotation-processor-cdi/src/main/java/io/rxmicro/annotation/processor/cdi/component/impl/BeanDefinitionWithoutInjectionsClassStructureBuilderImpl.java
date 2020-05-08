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
import io.rxmicro.annotation.processor.cdi.component.BeanDefinitionWithoutInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.cdi.model.InjectionPoint;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.BEAN;
import static io.rxmicro.annotation.processor.cdi.model.InjectionPointType.MULTI_BINDER;
import static io.rxmicro.annotation.processor.common.util.Elements.allSuperTypesAndInterfaces;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static io.rxmicro.tool.common.DeniedPackages.isDeniedPackage;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class BeanDefinitionWithoutInjectionsClassStructureBuilderImpl
        extends AbstractBeanDefinitionClassStructureBuilder
        implements BeanDefinitionWithoutInjectionsClassStructureBuilder {

    @Override
    public Set<BeanSupplierClassStructure> build(final EnvironmentContext environmentContext,
                                                 final List<InjectionPoint> injectionPoints,
                                                 final Set<String> alreadyProcessedClasses) {
        final Set<BeanSupplierClassStructure> result = new HashSet<>();
        final Map<String, Set<TypeElement>> implMap = buildImplMap(environmentContext);
        for (final InjectionPoint injectionPoint : injectionPoints) {
            final String type;
            if (injectionPoint.getType() == BEAN) {
                type = injectionPoint.getModelField().getFieldClass().toString();
            } else if (injectionPoint.getType() == MULTI_BINDER) {
                type = ((DeclaredType) injectionPoint.getModelField().getFieldClass()).getTypeArguments().get(0).toString();
            } else {
                type = null;
            }
            if (type != null) {
                Optional.ofNullable(implMap.get(type))
                        .ifPresent(typeElements -> typeElements.forEach(typeElement -> {
                            if (!alreadyProcessedClasses.contains(typeElement.asType().toString())) {
                                result.add(buildCDIBeanDefinitionClassStructure(typeElement));
                            }
                        }));
            }
        }
        return result;
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
        final Set<PackageElement> packageElements = getElements().getAllModuleElements().stream()
                .flatMap(me -> me.getEnclosedElements().stream().map(e -> (PackageElement) e))
                .filter(pe -> !isDeniedPackage(pe.getQualifiedName().toString()))
                .collect(toSet());
        return packageElements.stream()
                .flatMap(pe -> pe.getEnclosedElements().stream().map(e -> (TypeElement) e))
                .filter(environmentContext::isRxMicroClassShouldBeProcessed)
                .filter(te -> te.getKind() == ElementKind.CLASS)
                .collect(toTreeSet(Comparator.comparing(o -> o.getQualifiedName().toString())));
    }
}
