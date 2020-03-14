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

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.annotation.processor.common {
    requires transitive java.compiler;
    requires transitive rxmicro.json;
    requires transitive rxmicro.config;
    requires transitive rxmicro.model;
    requires transitive rxmicro.tool.common;
    requires transitive rxmicro.documentation;
    requires transitive rxmicro.validation;
    requires transitive com.google.guice;
    requires transitive freemarker;
    requires transitive reactor.core;
    requires transitive org.reactivestreams;
    requires transitive io.reactivex.rxjava3;

    exports io.rxmicro.annotation.processor.common to
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;
    exports io.rxmicro.annotation.processor.common.util to
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;
    exports io.rxmicro.annotation.processor.common.model to
            freemarker,
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;
    exports io.rxmicro.annotation.processor.common.model.error to
            freemarker,
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;
    exports io.rxmicro.annotation.processor.common.model.virtual to
            freemarker,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server;
    exports io.rxmicro.annotation.processor.common.model.method to
            freemarker,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;
    exports io.rxmicro.annotation.processor.common.model.definition to
            freemarker,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi, rxmicro.annotation.processor.documentation;
    exports io.rxmicro.annotation.processor.common.model.definition.impl to
            freemarker,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.common.model.type to
            freemarker,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.documentation.asciidoctor,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;
    exports io.rxmicro.annotation.processor.common.component to
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.common.component.impl to
            rxmicro.annotation.processor,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.aggregator,
            rxmicro.annotation.processor.rest,
            rxmicro.annotation.processor.rest.client,
            rxmicro.annotation.processor.rest.server,
            rxmicro.annotation.processor.documentation,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.cdi;

    opens io.rxmicro.annotation.processor.common to
            com.google.guice;
    opens io.rxmicro.annotation.processor.common.model to
            com.google.guice;
    opens io.rxmicro.annotation.processor.common.component to
            com.google.guice;
    opens io.rxmicro.annotation.processor.common.component.impl to
            com.google.guice;
    opens io.rxmicro.annotation.processor.common.component.impl.reactive to
            com.google.guice;

    opens ftl;
}
