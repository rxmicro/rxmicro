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

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.cdi.component.BeanInjectionPointQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanRegistrationQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanWithInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanWithoutInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.ConstructorInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.component.ConverterClassResolver;
import io.rxmicro.annotation.processor.cdi.component.DefaultNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.FactoryBeanClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.FactoryMethodFinder;
import io.rxmicro.annotation.processor.cdi.component.FieldOrMethodInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.component.InjectionPointTypeBuilder;
import io.rxmicro.annotation.processor.cdi.component.InjectionResourceBuilder;
import io.rxmicro.annotation.processor.cdi.component.PostConstructMethodFinder;
import io.rxmicro.annotation.processor.cdi.component.UserDefinedNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanInjectionPointQualifierRuleBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanRegistrationQualifierRuleBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanWithInjectionsClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanWithoutInjectionsClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.ConstructorInjectionPointBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.DefaultNameBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.FactoryBeanClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.FactoryMethodFinderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.FieldOrMethodInjectionPointBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.InjectionPointTypeBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.InjectionResourceBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.PostConstructMethodFinderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.UserDefinedNameBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.resolver.JsonResourceConverterClassResolver;
import io.rxmicro.annotation.processor.cdi.component.impl.resolver.PropertiesResourceConverterClassResolver;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @since 0.1
 */
public final class CDIDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BeanWithInjectionsClassStructureBuilder.class)
                .to(BeanWithInjectionsClassStructureBuilderImpl.class);
        bind(BeanWithoutInjectionsClassStructureBuilder.class)
                .to(BeanWithoutInjectionsClassStructureBuilderImpl.class);
        bind(FactoryBeanClassStructureBuilder.class)
                .to(FactoryBeanClassStructureBuilderImpl.class);

        bind(ConstructorInjectionPointBuilder.class)
                .to(ConstructorInjectionPointBuilderImpl.class);
        bind(FieldOrMethodInjectionPointBuilder.class)
                .to(FieldOrMethodInjectionPointBuilderImpl.class);
        bind(InjectionPointTypeBuilder.class)
                .to(InjectionPointTypeBuilderImpl.class);
        bind(PostConstructMethodFinder.class)
                .to(PostConstructMethodFinderImpl.class);
        bind(FactoryMethodFinder.class)
                .to(FactoryMethodFinderImpl.class);
        bind(UserDefinedNameBuilder.class)
                .to(UserDefinedNameBuilderImpl.class);
        bind(DefaultNameBuilder.class)
                .to(DefaultNameBuilderImpl.class);
        bind(BeanRegistrationQualifierRuleBuilder.class)
                .to(BeanRegistrationQualifierRuleBuilderImpl.class);
        bind(BeanInjectionPointQualifierRuleBuilder.class)
                .to(BeanInjectionPointQualifierRuleBuilderImpl.class);

        bindResourceComponents();
    }

    private void bindResourceComponents() {
        bind(InjectionResourceBuilder.class)
                .to(InjectionResourceBuilderImpl.class);

        final Multibinder<ConverterClassResolver> converterClassResolverBinder =
                newSetBinder(binder(), ConverterClassResolver.class);
        converterClassResolverBinder.addBinding().to(JsonResourceConverterClassResolver.class);
        converterClassResolverBinder.addBinding().to(PropertiesResourceConverterClassResolver.class);
    }

}
