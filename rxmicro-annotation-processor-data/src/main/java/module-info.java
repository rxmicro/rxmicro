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
 * The {@code RxMicro Annotation Processor} internal module that generates common data components.
 *
 * @author nedis
 * @since 0.1
 */
module rxmicro.annotation.processor.data {
    requires transitive rxmicro.data;

    requires transitive rxmicro.annotation.processor.common;

    exports io.rxmicro.annotation.processor.data to
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.data.aggregator;
    exports io.rxmicro.annotation.processor.data.model to
            freemarker,
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.data.aggregator;
    exports io.rxmicro.annotation.processor.data.component to
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.data.aggregator;
    exports io.rxmicro.annotation.processor.data.component.impl to
            rxmicro.annotation.processor.data.mongo,
            rxmicro.annotation.processor.data.sql,
            rxmicro.annotation.processor.data.sql.r2dbc,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.data.aggregator;

    opens io.rxmicro.annotation.processor.data to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.component to
            com.google.guice;
    opens io.rxmicro.annotation.processor.data.component.impl to
            com.google.guice;
}
