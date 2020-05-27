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
 * The {@code RxMicro Annotation Processor} internal module that generates common SQL components.
 *
 * @author nedis
 * @since 0.1
 */
module rxmicro.annotation.processor.data.sql {
    requires transitive rxmicro.annotation.processor.data;
    requires transitive rxmicro.data.sql;

    exports io.rxmicro.annotation.processor.data.sql to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.model to
            freemarker,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.model.inject to
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.component to
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.util to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.component.impl to
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.component.impl.resolver to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.component.impl.builder to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.component.impl.builder.select to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;
    exports io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.operator to
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql;

    opens io.rxmicro.annotation.processor.data.sql.model.inject to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.component.impl to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.component.impl.builder to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.component.impl.builder.select to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.component.impl.builder.select.operator to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.sql.component.impl.resolver to
            com.google.guice;
}
