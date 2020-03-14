/*
 * Copyright (c) 2020. http://rxmicro.io
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
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.detail.InternalBeanFactory;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class BeanDefinition {

    private final boolean constructorInjection;

    private final TypeElement beanTypeElement;

    private final ExecutableElement factoryTypeProviderMethod;

    private final TypeElement factoryTypeProviderType;

    private final List<InjectionPoint> injectionPoints;

    private final PostConstructMethod postConstructMethod;

    private final FactoryMethod factoryMethod;

    private BeanDefinition(final boolean constructorInjection,
                           final TypeElement beanTypeElement,
                           final ExecutableElement factoryTypeProviderMethod,
                           final TypeElement factoryTypeProviderType,
                           final List<InjectionPoint> injectionPoints,
                           final PostConstructMethod postConstructMethod,
                           final FactoryMethod factoryMethod) {
        this.constructorInjection = constructorInjection;
        this.factoryTypeProviderMethod = factoryTypeProviderMethod;
        this.factoryTypeProviderType = factoryTypeProviderType;
        this.beanTypeElement = require(beanTypeElement);
        this.injectionPoints = require(injectionPoints);
        this.postConstructMethod = postConstructMethod;
        this.factoryMethod = factoryMethod;
    }

    public boolean isConstructorInjection() {
        return constructorInjection;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public boolean isFactoryClass() {
        return factoryTypeProviderType != null && factoryTypeProviderMethod != null;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getFactoryTypeSimpleClassName() {
        // check isFactory() before invoke this method
        return factoryTypeProviderType.getSimpleName().toString();
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getBeanFactoryTypeSupplierImplSimpleClassName() {
        // check isFactory() before invoke this method
        return getModelTransformerSimpleClassName(factoryTypeProviderType, BeanSupplier.class);
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public List<InjectionPoint> getInjectionPoints() {
        return injectionPoints;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public boolean isPostConstructMethodPresent() {
        return postConstructMethod != null;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public PostConstructMethod getPostConstructMethod() {
        // check isPostConstructMethodPresent() before invoke this method
        return require(postConstructMethod);
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public boolean isFactoryMethodPresent() {
        return factoryMethod != null;
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public FactoryMethod getFactoryMethod() {
        // check isFactoryMethodPresent() before invoke this method
        return require(factoryMethod);
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getJavaSimpleClassName() {
        return beanTypeElement.getSimpleName().toString();
    }

    @UsedByFreemarker("$$BeanSupplierTemplate.javaftl")
    public String getBeanSupplierImplSimpleClassName() {
        return getModelTransformerSimpleClassName(beanTypeElement, BeanSupplier.class);
    }

    public TypeElement getBeanTypeElement() {
        return beanTypeElement;
    }

    public Optional<TypeElement> getFactoryTypeProviderType() {
        return Optional.ofNullable(factoryTypeProviderType);
    }

    public Optional<ExecutableElement> getFactoryTypeProviderMethod() {
        return Optional.ofNullable(factoryTypeProviderMethod);
    }

    public void populateClassHeaderBuilder(final ClassHeader.Builder classHeaderBuilder) {
        injectionPoints.forEach(injectionPoint -> injectionPoint.populateClassHeaderBuilder(classHeaderBuilder));
        if (isFactoryClass()) {
            classHeaderBuilder
                    .addImports(factoryTypeProviderType)
                    .addStaticImport(InternalBeanFactory.class, "getBean");
        }
    }

    /**
     * @author nedis
     * @link http://rxmicro.io
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private boolean constructorInjection;

        private TypeElement beanTypeElement;

        private ExecutableElement factoryTypeProviderMethod;

        private TypeElement factoryTypeProviderType;

        private List<InjectionPoint> injectionPoints;

        private PostConstructMethod postConstructMethod;

        private FactoryMethod factoryMethod;

        public Builder setBeanTypeElement(final TypeElement beanTypeElement) {
            this.beanTypeElement = require(beanTypeElement);
            return this;
        }

        public Builder setFactoryTypeProviderMethod(final ExecutableElement factoryTypeProviderMethod) {
            this.factoryTypeProviderMethod = require(factoryTypeProviderMethod);
            return this;
        }

        public Builder setFactoryTypeProviderType(final TypeElement factoryTypeProviderType) {
            this.factoryTypeProviderType = require(factoryTypeProviderType);
            return this;
        }

        public Builder setConstructorInjection(final boolean constructorInjection) {
            this.constructorInjection = constructorInjection;
            return this;
        }

        public Builder setInjectionPoints(final List<InjectionPoint> injectionPoints) {
            this.injectionPoints = require(injectionPoints);
            return this;
        }

        public Builder setPostConstructMethod(final PostConstructMethod postConstructMethod) {
            this.postConstructMethod = require(postConstructMethod);
            return this;
        }

        public Builder setFactoryMethod(final FactoryMethod factoryMethod) {
            this.factoryMethod = require(factoryMethod);
            return this;
        }

        public BeanDefinition build() {
            return new BeanDefinition(
                    constructorInjection,
                    beanTypeElement,
                    factoryTypeProviderMethod,
                    factoryTypeProviderType,
                    injectionPoints,
                    postConstructMethod,
                    factoryMethod
            );
        }

    }
}
