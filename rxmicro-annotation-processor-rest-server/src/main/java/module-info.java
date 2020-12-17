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

import io.rxmicro.annotation.processor.rest.server.component.AbstractDocumentationModuleClassStructuresBuilder;

/**
 * The {@code RxMicro Annotation Processor} internal module that generates REST server components.
 *
 * @author nedis
 * @since 0.1
 */
module rxmicro.annotation.processor.rest.server {
    requires transitive rxmicro.rest.server;
    requires transitive rxmicro.monitoring;
    requires rxmicro.rest.client;
    requires transitive rxmicro.cdi;

    requires transitive rxmicro.annotation.processor.rest;

    exports io.rxmicro.annotation.processor.rest.server to
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.documentation.asciidoctor;
    exports io.rxmicro.annotation.processor.rest.server.model to
            freemarker,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor;
    exports io.rxmicro.annotation.processor.rest.server.component to
            rxmicro.annotation.processor.common,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor;

    opens io.rxmicro.annotation.processor.rest.server to
            com.google.guice;
    opens io.rxmicro.annotation.processor.rest.server.model to
            com.google.guice;
    opens io.rxmicro.annotation.processor.rest.server.component.impl to
            com.google.guice;
    opens io.rxmicro.annotation.processor.rest.server.component.impl.method to
            com.google.guice;

    opens ftl.rest.server;
    opens ftl.rest.server.method;

    uses AbstractDocumentationModuleClassStructuresBuilder;
}
