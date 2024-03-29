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

package io.rxmicro.test.local.component.injector;

import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;
import io.rxmicro.test.internal.AlternativeEntryPoint;

import java.util.List;

import static io.rxmicro.cdi.BeanFactory.BEAN_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.reflection.Reflections.instantiate;
import static io.rxmicro.runtime.detail.RxMicroRuntime.getEntryPointFullClassName;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
public final class BeanFactoryInjector extends AbstractFactory {

    private final Module module;

    private final List<AlternativeEntryPointBean> beanComponents;

    BeanFactoryInjector(final Module module,
                        final List<AlternativeEntryPoint> beanComponents) {
        this.module = require(module);
        this.beanComponents = beanComponents.stream().map(AlternativeEntryPointBean::new).collect(toList());
    }

    public boolean isBeansFound() {
        return !beanComponents.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public void injectIfFound(final List<Object> testInstances) {
        if (isBeansFound()) {
            registerFactory(module, BEAN_FACTORY_IMPL_CLASS_NAME, this);
            instantiate(getEntryPointFullClassName(module, BEAN_FACTORY_IMPL_CLASS_NAME));
            for (final AlternativeEntryPointBean bean : beanComponents) {
                bean.alternativeEntryPoint.readValue(testInstances);
                override(
                        bean.alternativeEntryPoint.getValue(),
                        new ByTypeInstanceQualifier<>((Class<Object>) bean.alternativeEntryPoint.getFieldType())
                );
            }
        }
    }

    /**
     * @author nedis
     * @since 0.1
     */
    private static final class AlternativeEntryPointBean {

        private final AlternativeEntryPoint alternativeEntryPoint;

        private AlternativeEntryPointBean(final AlternativeEntryPoint alternativeEntryPoint) {
            this.alternativeEntryPoint = alternativeEntryPoint;
        }
    }
}
