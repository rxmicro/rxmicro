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

package io.rxmicro.annotation.processor.data.sql;

import com.google.inject.AbstractModule;
import io.rxmicro.annotation.processor.data.sql.component.SQLFieldsOrderExtractor;
import io.rxmicro.annotation.processor.data.sql.component.impl.DbObjectNameBuilder;
import io.rxmicro.annotation.processor.data.sql.component.impl.SQLFieldsOrderExtractorImpl;
import io.rxmicro.annotation.processor.data.sql.component.impl.SQLFieldsOrderValidator;

/**
 * @author nedis
 * @since 0.1
 */
public final class SQLDependenciesModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(SQLFieldsOrderExtractor.class)
                .to(SQLFieldsOrderExtractorImpl.class);

        binClasses();
    }

    private void binClasses() {
        bind(DbObjectNameBuilder.class);
        bind(SQLFieldsOrderValidator.class);
    }
}
