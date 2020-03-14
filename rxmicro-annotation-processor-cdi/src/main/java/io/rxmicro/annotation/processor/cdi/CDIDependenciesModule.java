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

package io.rxmicro.annotation.processor.cdi;

import com.google.inject.AbstractModule;
import io.rxmicro.annotation.processor.cdi.component.BeanDefinitionWithInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanDefinitionWithoutInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanInjectionPointQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.BeanRegistrationQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.ConstructorInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.component.DefaultNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.FactoryMethodFinder;
import io.rxmicro.annotation.processor.cdi.component.FieldOrMethodInjectionPointBuilder;
import io.rxmicro.annotation.processor.cdi.component.InjectionPointTypeBuilder;
import io.rxmicro.annotation.processor.cdi.component.PostConstructMethodFinder;
import io.rxmicro.annotation.processor.cdi.component.UserDefinedNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanDefinitionWithInjectionsClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanDefinitionWithoutInjectionsClassStructureBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanInjectionPointQualifierRuleBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.BeanRegistrationQualifierRuleBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.ConstructorInjectionPointBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.DefaultNameBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.FactoryMethodFinderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.FieldOrMethodInjectionPointBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.InjectionPointTypeBuilderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.PostConstructMethodFinderImpl;
import io.rxmicro.annotation.processor.cdi.component.impl.UserDefinedNameBuilderImpl;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class CDIDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(BeanDefinitionWithInjectionsClassStructureBuilder.class)
                .to(BeanDefinitionWithInjectionsClassStructureBuilderImpl.class);
        bind(ConstructorInjectionPointBuilder.class)
                .to(ConstructorInjectionPointBuilderImpl.class);
        bind(FieldOrMethodInjectionPointBuilder.class)
                .to(FieldOrMethodInjectionPointBuilderImpl.class);
        bind(InjectionPointTypeBuilder.class)
                .to(InjectionPointTypeBuilderImpl.class);
        bind(PostConstructMethodFinder.class)
                .to(PostConstructMethodFinderImpl.class);
        bind(BeanDefinitionWithoutInjectionsClassStructureBuilder.class)
                .to(BeanDefinitionWithoutInjectionsClassStructureBuilderImpl.class);
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
    }
}
