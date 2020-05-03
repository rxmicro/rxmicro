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
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.cdi.detail.BeanSupplier;

import java.util.List;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.model.ClassHeader.newClassHeaderBuilder;
import static io.rxmicro.annotation.processor.common.model.ModelAccessorType.REFLECTION;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.REFLECTIONS_FULL_CLASS_NAME;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class BeanSupplierClassStructure extends ClassStructure {

    private final BeanDefinition beanDefinition;

    private final List<QualifierRule> beanRegistrationQualifierRules;

    private final List<QualifierRule> factoryRegistrationQualifierRules;

    public BeanSupplierClassStructure(final BeanDefinition beanDefinition,
                                      final List<QualifierRule> beanRegistrationQualifierRules,
                                      final List<QualifierRule> factoryRegistrationQualifierRules) {
        this.beanDefinition = require(beanDefinition);
        this.beanRegistrationQualifierRules = require(beanRegistrationQualifierRules);
        this.factoryRegistrationQualifierRules = require(factoryRegistrationQualifierRules);
    }

    @Override
    public String getTargetFullClassName() {
        return format("?.?",
                getPackageName(beanDefinition.getBeanTypeElement()),
                getBeanSupplierImplSimpleClassName()
        );
    }

    @Override
    public String getTemplateName() {
        return "cdi/$$BeanSupplierTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        return Map.of("JAVA_MODEL_CLASS", beanDefinition);
    }

    @Override
    public ClassHeader getClassHeader() {
        final ClassHeader.Builder classHeaderBuilder = newClassHeaderBuilder(beanDefinition.getBeanTypeElement())
                .addImports(BeanSupplier.class)
                .addImports(beanDefinition.getBeanTypeElement().asType());
        beanDefinition.populateClassHeaderBuilder(classHeaderBuilder);
        if (isRequiredReflectionSetter()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "setFieldValue");
        }
        if (isRequiredReflectionInvoke()) {
            classHeaderBuilder.addStaticImport(REFLECTIONS_FULL_CLASS_NAME, "invoke");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public boolean isRequiredReflectionSetter() {
        return beanDefinition.getInjectionPoints().stream()
                .anyMatch(injectionPoint -> injectionPoint.getModelField().getModelWriteAccessorType() == REFLECTION);
    }

    @Override
    public boolean isRequiredReflectionInvoke() {
        return beanDefinition.isPostConstructMethodPresent() && beanDefinition.getPostConstructMethod().isPrivateMethod() ||
                        beanDefinition.isFactoryMethodPresent() && beanDefinition.getFactoryMethod().isPrivateMethod();
    }

    @UsedByFreemarker("$$BeanFactoryImplTemplate.javaftl")
    public String getBeanSupplierImplSimpleClassName() {
        return beanDefinition.getBeanSupplierImplSimpleClassName();
    }

    @UsedByFreemarker("$$BeanFactoryImplTemplate.javaftl")
    public String getBeanSimpleClassName() {
        return beanDefinition.getBeanTypeElement().getSimpleName().toString();
    }

    @UsedByFreemarker("$$BeanFactoryImplTemplate.javaftl")
    public List<QualifierRule> getBeanRegistrationQualifierRules() {
        return beanRegistrationQualifierRules;
    }

    @UsedByFreemarker("$$BeanFactoryImplTemplate.javaftl")
    public List<QualifierRule> getFactoryRegistrationQualifierRules() {
        return factoryRegistrationQualifierRules;
    }

    @UsedByFreemarker("$$BeanFactoryImplTemplate.javaftl")
    public BeanDefinition getBeanDefinition() {
        return beanDefinition;
    }
}
