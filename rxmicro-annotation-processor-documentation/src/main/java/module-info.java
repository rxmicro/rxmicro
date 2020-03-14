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

import io.rxmicro.annotation.processor.documentation.component.IncludeReferenceSyntaxProvider;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.annotation.processor.documentation {
    requires transitive rxmicro.annotation.processor.rest.server;
    requires transitive rxmicro.documentation;
    requires transitive maven.model;
    requires transitive plexus.utils;
    requires transitive rxmicro.files;
    requires transitive rxmicro.http.client;

    exports io.rxmicro.annotation.processor.documentation to
            rxmicro.annotation.processor.documentation.asciidoctor;
    exports io.rxmicro.annotation.processor.documentation.component to
            rxmicro.annotation.processor.documentation.asciidoctor;
    exports io.rxmicro.annotation.processor.documentation.component.impl to
            rxmicro.annotation.processor.documentation.asciidoctor;
    exports io.rxmicro.annotation.processor.documentation.model to
            freemarker,
            rxmicro.annotation.processor.documentation.asciidoctor;

    opens io.rxmicro.annotation.processor.documentation
            to com.google.guice;
    opens io.rxmicro.annotation.processor.documentation.component.impl
            to com.google.guice;
    opens io.rxmicro.annotation.processor.documentation.component.impl.example.builder
            to com.google.guice;
    opens io.rxmicro.annotation.processor.documentation.component.impl.example.converter
            to com.google.guice;

    uses IncludeReferenceSyntaxProvider;
}
