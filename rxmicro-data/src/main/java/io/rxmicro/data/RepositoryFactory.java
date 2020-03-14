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

package io.rxmicro.data;

import io.rxmicro.runtime.detail.ByTypeInstanceQualifier;
import io.rxmicro.runtime.local.AbstractFactory;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class RepositoryFactory extends AbstractFactory {

    public static final String REPOSITORY_FACTORY_IMPL_CLASS_NAME =
            format("??Impl", GENERATED_CLASS_NAME_PREFIX, RepositoryFactory.class.getSimpleName());

    public static <T> T getRepository(final Class<T> repositoryClass) {
        return get(REPOSITORY_FACTORY_IMPL_CLASS_NAME).getImpl(new ByTypeInstanceQualifier<>(repositoryClass))
                .orElseThrow(implNotFoundError(repositoryClass));
    }
}
