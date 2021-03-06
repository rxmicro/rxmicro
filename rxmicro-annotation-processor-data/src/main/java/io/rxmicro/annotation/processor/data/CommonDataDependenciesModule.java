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

package io.rxmicro.annotation.processor.data;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import io.rxmicro.annotation.processor.common.component.IterableContainerElementExtractor;
import io.rxmicro.annotation.processor.common.component.impl.CollectionIterableContainerElementExtractor;
import io.rxmicro.annotation.processor.data.component.DataMethodParamsResolver;
import io.rxmicro.annotation.processor.data.component.impl.DataMethodParamsResolverImpl;

import static com.google.inject.multibindings.Multibinder.newSetBinder;

/**
 * @author nedis
 * @since 0.1
 */
public final class CommonDataDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        configureIterableContainerElementExtractors();

        bind(DataMethodParamsResolver.class)
                .to(DataMethodParamsResolverImpl.class);
        //Add common data dependencies here
    }

    private void configureIterableContainerElementExtractors() {
        final Multibinder<IterableContainerElementExtractor> binder =
                newSetBinder(binder(), IterableContainerElementExtractor.class);
        binder.addBinding().to(CollectionIterableContainerElementExtractor.class);
    }
}
