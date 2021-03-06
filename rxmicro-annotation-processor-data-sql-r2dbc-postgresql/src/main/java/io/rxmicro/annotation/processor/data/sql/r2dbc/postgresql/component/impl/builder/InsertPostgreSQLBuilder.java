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

package io.rxmicro.annotation.processor.data.sql.r2dbc.postgresql.component.impl.builder;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedInsertParamsVariables;
import io.rxmicro.annotation.processor.data.sql.model.inject.SupportedInsertResultsVariables;
import io.rxmicro.data.sql.operation.Insert;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;

import static io.rxmicro.annotation.processor.data.sql.model.SQLKeywords.INSERT;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class InsertPostgreSQLBuilder extends AbstractModificationPostgreSQLBuilder<Insert> {

    @Inject
    @SupportedInsertParamsVariables
    private Set<String> supportedParamsVariables;

    @Inject
    @SupportedInsertResultsVariables
    private Set<String> supportedResultsVariables;

    @Override
    protected Set<String> getSupportedParamsVariables() {
        return supportedParamsVariables;
    }

    @Override
    protected Set<String> getSupportedResultsVariables() {
        return supportedResultsVariables;
    }

    @Override
    protected void validateStatement(final ExecutableElement method, final List<String> sqlTokens) {
        if (sqlTokens.isEmpty() || !INSERT.equalsIgnoreCase(sqlTokens.get(0))) {
            throw new InterruptProcessingException(method, "Insert SQL query must start with '?' keyword", INSERT);
        }
    }
}
