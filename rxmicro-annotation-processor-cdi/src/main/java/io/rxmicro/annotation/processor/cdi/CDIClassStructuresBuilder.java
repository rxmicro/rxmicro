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
import io.rxmicro.annotation.processor.cdi.component.BeanWithInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanWithoutInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.FactoryBeanClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.model.BeanFactoryImplClassStructure;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.common.CommonDependenciesModule;
import io.rxmicro.annotation.processor.common.FormatSourceCodeDependenciesModule;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModuleClassStructuresBuilder;
import io.rxmicro.annotation.processor.common.model.CDIUsageCandidateClassStructure;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.cdi.Factory;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Injects.injectDependencies;
import static io.rxmicro.cdi.local.Annotations.INJECT_ANNOTATIONS;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public final class CDIClassStructuresBuilder extends AbstractModuleClassStructuresBuilder {

    private Set<String> beanDefinitionTypes = Set.of();

    @Inject
    private BeanWithInjectionsClassStructureBuilder beanWithInjectionsClassStructureBuilder;

    @Inject
    private BeanWithoutInjectionsClassStructureBuilder beanWithoutInjectionsClassStructureBuilder;

    @Inject
    private FactoryBeanClassStructureBuilder factoryBeanClassStructureBuilder;

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
    public String getBuilderName() {
        return "cdi-annotation-processor-module";
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Stream
                .concat(
                        INJECT_ANNOTATIONS.stream(),
                        Stream.of(Factory.class)
                )
                .map(Class::getName)
                .collect(toSet());
    }

    @SuppressWarnings("CollectionAddAllCanBeReplacedWithConstructor")
    @Override
    public Set<? extends ClassStructure> buildClassStructures(final EnvironmentContext environmentContext,
                                                              final Set<? extends TypeElement> annotations,
                                                              final RoundEnvironment roundEnv) {
        final Set<BeanSupplierClassStructure> beanSupplierClassStructures = new HashSet<>();

        final Set<BeanSupplierClassStructure> beanWithInjectionsClassStructures =
                beanWithInjectionsClassStructureBuilder.build(environmentContext, annotations, roundEnv);
        beanSupplierClassStructures.addAll(beanWithInjectionsClassStructures);

        final Set<BeanSupplierClassStructure> beanWithoutInjectionsClassStructures =
                beanWithoutInjectionsClassStructureBuilder.build(environmentContext, beanWithInjectionsClassStructures);
        beanSupplierClassStructures.addAll(beanWithoutInjectionsClassStructures);

        final Set<BeanSupplierClassStructure> factoryBeanClassStructures =
                factoryBeanClassStructureBuilder.build(environmentContext, beanSupplierClassStructures, roundEnv);
        beanSupplierClassStructures.addAll(factoryBeanClassStructures);

        logAllFoundBeanSuppliers(beanWithInjectionsClassStructures, beanWithoutInjectionsClassStructures, factoryBeanClassStructures);

        beanDefinitionTypes = beanSupplierClassStructures.stream()
                .map(s -> s.getBeanDefinition().getBeanTypeElement().asType().toString())
                .collect(toSet());
        return Stream
                .concat(
                        beanSupplierClassStructures.stream(),
                        Stream.of(new BeanFactoryImplClassStructure(environmentContext, beanSupplierClassStructures))
                )
                .collect(toSet());
    }

    private void logAllFoundBeanSuppliers(final Set<BeanSupplierClassStructure> beanWithInjectionsClassStructures,
                                          final Set<BeanSupplierClassStructure> beanWithoutInjectionsClassStructures,
                                          final Set<BeanSupplierClassStructure> factoryBeanClassStructures) {
        if (isDebugEnabled()) {
            logClassStructureStorageItem("bean with injection(s) supplier(s)", beanWithInjectionsClassStructures);
            logClassStructureStorageItem("bean without injection(s) supplier(s)", beanWithoutInjectionsClassStructures);
            logClassStructureStorageItem("factory bean supplier(s)", factoryBeanClassStructures);
        }
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
