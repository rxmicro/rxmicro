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
 * The {@code RxMicro Annotation Processor} internal module that generates CDI components.
 *
 * @author nedis
 * @since 0.1
 */
module rxmicro.annotation.processor.cdi {
    requires rxmicro.config;
    requires rxmicro.data.mongo;
    requires rxmicro.data.sql.r2dbc.postgresql;
    requires rxmicro.rest.client;
    requires rxmicro.cdi;

    requires com.google.guice;
    requires freemarker;

    requires rxmicro.annotation.processor.common;

    exports io.rxmicro.annotation.processor.cdi to
            rxmicro.annotation.processor;
    exports io.rxmicro.annotation.processor.cdi.model to
            freemarker;
    exports io.rxmicro.annotation.processor.cdi.model.qualifier to
            freemarker;

    opens io.rxmicro.annotation.processor.cdi to
            com.google.guice;
    opens io.rxmicro.annotation.processor.cdi.component.impl to
            com.google.guice;
    opens io.rxmicro.annotation.processor.cdi.component.impl.resolver to
            com.google.guice;

    opens ftl.cdi;
}
