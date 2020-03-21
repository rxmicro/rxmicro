/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.AbstractModule;
import io.rxmicro.annotation.processor.common.component.AnnotationProcessingInformer;
import io.rxmicro.annotation.processor.common.component.ClassWriter;
import io.rxmicro.annotation.processor.common.component.CurrentModuleDecorator;
import io.rxmicro.annotation.processor.common.component.EnvironmentContextBuilder;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RxMicroAnnotationProcessorDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ClassWriter.class)
                .to(ClassWriterImpl.class);
        bind(EnvironmentContextBuilder.class)
                .to(EnvironmentContextBuilderImpl.class);
        bind(AnnotationProcessingInformer.class)
                .to(AnnotationProcessingInformerImpl.class);
        bind(CurrentModuleDecorator.class)
                .to(CurrentModuleDecoratorImpl.class);
    }
}
