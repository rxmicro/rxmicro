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

package io.rxmicro.annotation.processor.documentation.asciidoctor;

import com.google.inject.AbstractModule;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.CustomErrorResponsesBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.DocumentedModelFieldBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.GenerationOutputOrganizer;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.RequestBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.ResponsesBuilder;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.CustomErrorResponsesBuilderImpl;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.DocumentedModelFieldBuilderImpl;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.GenerationOutputOrganizerImpl;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.RequestBuilderImpl;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.ResponsesBuilderImpl;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.RestrictionReaderImpl;

/**
 * @author nedis
 * @since 0.1
 */
public final class AsciiDoctorDocumentationDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(DocumentedModelFieldBuilder.class)
                .to(DocumentedModelFieldBuilderImpl.class);
        bind(RestrictionReader.class)
                .to(RestrictionReaderImpl.class);
        bind(RequestBuilder.class)
                .to(RequestBuilderImpl.class);
        bind(ResponsesBuilder.class)
                .to(ResponsesBuilderImpl.class);
        bind(CustomErrorResponsesBuilder.class)
                .to(CustomErrorResponsesBuilderImpl.class);
        bind(GenerationOutputOrganizer.class)
                .to(GenerationOutputOrganizerImpl.class);
    }
}
