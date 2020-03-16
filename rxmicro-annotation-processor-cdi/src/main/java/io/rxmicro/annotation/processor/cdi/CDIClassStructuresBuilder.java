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

package io.rxmicro.annotation.processor.cdi;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.cdi.component.BeanDefinitionWithInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanDefinitionWithoutInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.model.BeanFactoryImplClassStructure;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.CDIUsageCandidateClassStructure;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.EnvironmentCustomizerClassStructure;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import java.util.Set;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.cdi.local.AnnotationsSupport.INJECT_ANNOTATIONS;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class CDIClassStructuresBuilder extends AbstractModuleClassStructuresBuilder {

    private Set<String> beanDefinitionTypes = Set.of();

    @Inject
    private BeanDefinitionWithInjectionsClassStructureBuilder beanDefinitionWithInjectionsClassStructureBuilder;

    @Inject
    private BeanDefinitionWithoutInjectionsClassStructureBuilder beanDefinitionWithoutInjectionsClassStructureBuilder;

    public static CDIClassStructuresBuilder create() {
        final CDIClassStructuresBuilder builder = new CDIClassStructuresBuilder();
        injectDependencies(
                builder,
                new FormatSourceCodeDependenciesModule(),
                new CommonDependenciesModule(),
                new CDIDependenciesModule()
        );
        return builder;
    }

    private CDIClassStructuresBuilder() {
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return INJECT_ANNOTATIONS.stream().map(Class::getName).collect(toSet());
    }

    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        final Set<BeanSupplierClassStructure> beanSupplierClassStructures =
                beanDefinitionWithInjectionsClassStructureBuilder.build(environmentContext, annotations, roundEnv);
        beanSupplierClassStructures.addAll(
                beanDefinitionWithoutInjectionsClassStructureBuilder.build(
                        environmentContext,
                        beanSupplierClassStructures.stream()
                                .flatMap(s -> s.getBeanDefinition().getInjectionPoints().stream())
                                .collect(toList()),
                        beanSupplierClassStructures.stream()
                                .map(s -> s.getBeanDefinition().getBeanTypeElement().asType().toString())
                                .collect(toSet())
                )
        );
        beanDefinitionTypes = beanSupplierClassStructures.stream()
                .map(s -> s.getBeanDefinition().getBeanTypeElement().asType().toString())
                .collect(toSet());
        return Stream
                .concat(
                        beanSupplierClassStructures.stream(),
                        Stream.of(
                                new BeanFactoryImplClassStructure(beanSupplierClassStructures),
                                new EnvironmentCustomizerClassStructure(environmentContext.getDefaultConfigValues())
                        )
                )
                .collect(toSet());
    }

    @Override
    public void afterAllClassStructuresBuilt(final Set<? extends ClassStructure> classStructures) {
        classStructures.stream()
                .filter(s -> s instanceof CDIUsageCandidateClassStructure)
                .map(s -> (CDIUsageCandidateClassStructure) s)
                .forEach(candidate -> {
                    if (beanDefinitionTypes.contains(candidate.getBeanFullClassName())) {
                        candidate.setUseCDI(true);
                    }
                });
    }
}
