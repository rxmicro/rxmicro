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
module rxmicro.annotation.processor.data.sql.r2dbc.postgresql {
    requires transitive rxmicro.annotation.processor.data.sql.r2dbc;
    requires transitive rxmicro.data.sql.r2dbc.postgresql;

    exports io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql to
            rxmicro.annotation.processor.data.aggregator;
    exports io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.model to
            freemarker,
            rxmicro.annotation.processor.data.aggregator;

    opens io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.builder to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.method to
            com.google.guice;

    opens ftl.data.sql.r2dbc.postgresql;
    opens ftl.data.sql.r2dbc.postgresql.method;
}
