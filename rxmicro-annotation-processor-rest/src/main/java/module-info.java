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

/**
 * The {@code RxMicro Annotation Processor} internal module.
 *
 * @author nedis
 * @since 0.1
 */
module rxmicro.annotation.processor.rest {
    requires transitive rxmicro.annotation.processor.common;
    requires transitive rxmicro.rest;
    requires transitive rxmicro.exchange.json;

    exports io.rxmicro.annotation.processor.rest to
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.rest.client;
    exports io.rxmicro.annotation.processor.rest.model to
            freemarker,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor;
    exports io.rxmicro.annotation.processor.rest.model.converter to
            freemarker,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.rest.client;
    exports io.rxmicro.annotation.processor.rest.model.validator to
            freemarker,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.rest.client;
    exports io.rxmicro.annotation.processor.rest.component to
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.rest.client, rxmicro.annotation.processor.documentation;
    exports io.rxmicro.annotation.processor.rest.component.impl to
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.rest.client;

    opens io.rxmicro.annotation.processor.rest.component to
            com.google.guice;
    opens io.rxmicro.annotation.processor.rest.component.impl to
            com.google.guice;

    opens ftl.rest;
}
