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

import io.rxmicro.common.model.UnNamedModuleFixer;
import io.rxmicro.data.local.test.DataUnNamedModuleFixer;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("JavaRequiresAutoModule")
module rxmicro.data {
    requires transitive rxmicro.config;
    requires transitive rxmicro.model;
    requires transitive org.reactivestreams;

    exports io.rxmicro.data;

    exports io.rxmicro.data.detail;
    exports io.rxmicro.data.detail.adapter;

    exports io.rxmicro.data.local to
            rxmicro.data.sql,
            rxmicro.data.mongo,
            rxmicro.data.sql.r2dbc,
            rxmicro.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.data,
            rxmicro.annotation.processor.data.sql.r2dbc.postgresql,
            rxmicro.annotation.processor.data.mongo;
    exports io.rxmicro.data.local.test to
            rxmicro.test;

    provides UnNamedModuleFixer with DataUnNamedModuleFixer;
}
