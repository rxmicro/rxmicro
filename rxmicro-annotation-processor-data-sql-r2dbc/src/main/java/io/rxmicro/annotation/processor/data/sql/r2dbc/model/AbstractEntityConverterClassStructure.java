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

package io.rxmicro.annotation.processor.data.sql.r2dbc.model;

import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataModelField;
import io.rxmicro.annotation.processor.data.sql.model.SQLDataObjectModelClass;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.1
 */
abstract class AbstractEntityConverterClassStructure<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>> extends ClassStructure {

    final DMC modelClass;

    final List<Map.Entry<String, List<Map.Entry<DMF, ModelClass>>>> setEntityFieldsConverterMethods;

    AbstractEntityConverterClassStructure(final DMC modelClass) {
        this.modelClass = modelClass;
        this.setEntityFieldsConverterMethods = modelClass.getEntitySetFieldsConverterMethods().stream()
                .map(m -> entry(m.getName(), toModelParams(m.getSelectedColumns())))
                .collect(toList());
    }

    final List<Map.Entry<DMF, ModelClass>> toModelParams(final List<String> selectedColumns) {
        final Set<Map.Entry<DMF, ModelClass>> params = modelClass.getParamEntries();
        return selectedColumns.stream()
                .map(c -> params.stream().filter(e -> e.getKey().getSelectedColumnNameOrCastExpression().equals(c))
                        .findFirst()
                        .orElseThrow(createInternalErrorSupplier(
                                "Column '?' not defined at '?' class",
                                c, modelClass.getModelTypeMirror()
                        )))
                .collect(toList());
    }
}
