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
import io.rxmicro.test.local.AlternativeEntryPoint;
import io.rxmicro.test.local.InvalidTestConfigException;

import java.util.List;

import static io.rxmicro.cdi.BeanFactory.BEAN_FACTORY_IMPL_CLASS_NAME;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.runtime.detail.Runtimes.ENTRY_POINT_PACKAGE;
import static io.rxmicro.runtime.local.Instances.instantiate;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class BeanFactoryInjector extends AbstractFactory {

    private final List<AlternativeEntryPointBean> beanComponents;

    BeanFactoryInjector(final List<AlternativeEntryPoint> beanComponents) {
        this.beanComponents = beanComponents.stream().map(AlternativeEntryPointBean::new).collect(toList());
    }

    public boolean isBeansFound() {
        return !beanComponents.isEmpty();
    }

    public void validateThatAllAlternativesAreUsed() {
        for (final AlternativeEntryPointBean bean : beanComponents) {
            if (!bean.used) {
                throw new InvalidTestConfigException(
                        "'?' alternative is redundant. " +
                                "Remove redundant alternative!",
                        bean.alternativeEntryPoint.getHumanReadableErrorName());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void injectIfFound(final List<Object> testInstances) {
        if (isBeansFound()) {
            registerFactory(BEAN_FACTORY_IMPL_CLASS_NAME, this);
            instantiate(format("?.?", ENTRY_POINT_PACKAGE, BEAN_FACTORY_IMPL_CLASS_NAME));
            for (final AlternativeEntryPointBean bean : beanComponents) {
                bean.alternativeEntryPoint.readValue(testInstances);
                override(
                        bean.alternativeEntryPoint.getValue(),
                        o -> bean.used = true,
                        new ByTypeInstanceQualifier<>((Class<Object>) bean.alternativeEntryPoint.getFieldType())
                );
            }
        }
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class AlternativeEntryPointBean {

        private final AlternativeEntryPoint alternativeEntryPoint;

        private boolean used;

        private AlternativeEntryPointBean(final AlternativeEntryPoint alternativeEntryPoint) {
            this.alternativeEntryPoint = alternativeEntryPoint;
        }
    }
}
