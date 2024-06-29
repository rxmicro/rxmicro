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

package io.rxmicro.cdi;

import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;

/**
 * Utility class that must be used to get the instance of the requested bean.
 *
 * @author nedis
 * @since 0.1
 */
public abstract class BeanFactory extends AbstractFactory {

    /**
     * Default name of the bean factory implementation class.
     */
    public static final String BEAN_FACTORY_IMPL_CLASS_NAME =
            format("??Impl", GENERATED_CLASS_NAME_PREFIX, BeanFactory.class.getSimpleName());

    /**
     * Returns the instance of the requested bean.
     *
     * @param <T>       the bean type
     * @param beanClass the bean interface or class
     * @return the instance of the requested bean
     */
    public static <T> T getBean(final Class<T> beanClass) {
        return getRequiredFactory(beanClass.getModule(), BEAN_FACTORY_IMPL_CLASS_NAME)
                .getImpl(new ByTypeInstanceQualifier<>(beanClass))
                .orElseThrow(implNotFoundError(beanClass));
    }
}
