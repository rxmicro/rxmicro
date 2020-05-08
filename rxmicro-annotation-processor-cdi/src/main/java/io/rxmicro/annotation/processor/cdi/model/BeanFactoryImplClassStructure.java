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

package io.rxmicro.annotation.processor.cdi.model;

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.cdi.detail.InternalBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getEntryPointFullClassName;
import static io.rxmicro.cdi.BeanFactory.BEAN_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.common.util.ExCollections.EMPTY_STRING_ARRAY;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;

/**
 * @author nedis
 * @since 0.1
 */
public final class BeanFactoryImplClassStructure extends ClassStructure {

    private final Set<BeanSupplierClassStructure> beanSupplierClassStructures;

    public BeanFactoryImplClassStructure(final Set<BeanSupplierClassStructure> beanSupplierClassStructures) {
        this.beanSupplierClassStructures = new TreeSet<>(beanSupplierClassStructures);
    }

    @Override
    public String getTargetFullClassName() {
        return getEntryPointFullClassName(BEAN_FACTORY_IMPL_CLASS_NAME);
    }

    @Override
    public String getTemplateName() {
        return "cdi/$$BeanFactoryImplTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("IMPL_CLASS_NAME", BEAN_FACTORY_IMPL_CLASS_NAME);
        map.put("BEAN_DEFINITIONS", beanSupplierClassStructures);
        map.put("ENVIRONMENT_CUSTOMIZER_CLASS", ENVIRONMENT_CUSTOMIZER_SIMPLE_CLASS_NAME);
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder builder = newClassHeaderBuilder(ENTRY_POINT_PACKAGE)
                .addImports(InternalBeanFactory.class);
        for (final BeanSupplierClassStructure beanSupplierClassStructure : beanSupplierClassStructures) {
            builder
                    .addImports(beanSupplierClassStructure.getTargetFullClassName())
                    .addImports(beanSupplierClassStructure.getBeanDefinition().getBeanTypeElement());
            beanSupplierClassStructure.getBeanDefinition().getFactoryTypeProviderType()
                    .ifPresent(builder::addImports);
            beanSupplierClassStructure.getBeanRegistrationQualifierRules()
                    .forEach(q -> builder.addImports(q.getImports().toArray(EMPTY_STRING_ARRAY)));
            beanSupplierClassStructure.getFactoryRegistrationQualifierRules()
                    .forEach(q -> builder.addImports(q.getImports().toArray(EMPTY_STRING_ARRAY)));
        }
        return builder.build();
    }
}
