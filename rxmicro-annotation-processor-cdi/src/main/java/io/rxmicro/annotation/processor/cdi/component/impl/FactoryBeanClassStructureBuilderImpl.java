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

import io.rxmicro.annotation.processor.cdi.component.FactoryBeanClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.cdi.Factory;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.Modifier.ABSTRACT;

/**
 * @author nedis
 * @since 0.10
 */
public final class FactoryBeanClassStructureBuilderImpl
        extends AbstractBeanDefinitionClassStructureBuilder
        implements FactoryBeanClassStructureBuilder {

    private static final List<Class<? extends Annotation>> ANNOTATIONS = List.of(Factory.class);

    @Override
    public Set<BeanSupplierClassStructure> build(final EnvironmentContext environmentContext,
                                                 final Set<BeanSupplierClassStructure> alreadyProcessedClassStructures,
                                                 final RoundEnvironment roundEnv) {
        final Set<BeanSupplierClassStructure> result = new HashSet<>();
        final Set<String> processedTypes = alreadyProcessedClassStructures.stream()
                .map(s -> s.getBeanDefinition().getBeanTypeElement().asType().toString())
                .collect(Collectors.toSet());
        for (final Element element : roundEnv.getElementsAnnotatedWith(Factory.class)) {
            final TypeElement ownerClass = getOwnerType(element, ANNOTATIONS);
            if (!ownerClass.getModifiers().contains(ABSTRACT) &&
                    environmentContext.isRxMicroClassShouldBeProcessed(ownerClass) &&
                    processedTypes.add(ownerClass.asType().toString())) {
                result.add(buildCDIBeanDefinitionClassStructure(environmentContext.getCurrentModule(), ownerClass));
            }
        }
        return result;
    }
}
