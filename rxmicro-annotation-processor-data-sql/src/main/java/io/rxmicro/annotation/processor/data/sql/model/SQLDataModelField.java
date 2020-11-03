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

package io.rxmicro.annotation.processor.data.sql.model;

import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.data.model.DataModelField;
import io.rxmicro.data.Column;
import io.rxmicro.data.sql.NotInsertable;
import io.rxmicro.data.sql.PrimaryKey;
import io.rxmicro.data.sql.SequenceGenerator;

import java.util.Optional;

import static io.rxmicro.common.util.Formats.FORMAT_PLACEHOLDER_TOKEN;
import static io.rxmicro.common.util.Strings.startsWith;

/**
 * @author nedis
 * @since 0.1
 */
public class SQLDataModelField extends DataModelField {

    private String insertValue;

    private boolean initInsertValue;

    private final int length;

    private final boolean nullable;

    private Boolean lengthPresent;

    public SQLDataModelField(final AnnotatedModelElement annotatedModelElement,
                             final String modelName,
                             final int length,
                             final boolean nullable) {
        super(annotatedModelElement, modelName);
        this.length = length;
        this.nullable = nullable;
        initInsertValue = false;
    }

    public String getColumnName() {
        return getModelName();
    }

    public Optional<String> getInsertValue(final VariableContext variableContext) {
        if (!initInsertValue) {
            initInsertValue = true;
            final PrimaryKey primaryKey = getAnnotation(PrimaryKey.class);
            if (primaryKey != null) {
                validateNotInsertable();
                if (primaryKey.autoGenerated()) {
                    final SequenceGenerator sequenceGenerator = getAnnotation(SequenceGenerator.class);
                    if (sequenceGenerator != null) {
                        validateSequenceGenerator(sequenceGenerator);
                        final String sequenceName = getSequenceName(variableContext, sequenceGenerator);
                        try {
                            insertValue = variableContext.getNextSequenceValue(sequenceName);
                        } catch (final UnsupportedOperationException ignore) {
                            throw new InterruptProcessingException(
                                    getElementAnnotatedBy(SequenceGenerator.class),
                                    "Current database does not support sequences"
                            );
                        }
                    } else {
                        insertValue = null;
                    }
                } else {
                    insertValue = FORMAT_PLACEHOLDER_TOKEN;
                }
            } else {
                insertValue = FORMAT_PLACEHOLDER_TOKEN;
            }
        }
        return Optional.ofNullable(insertValue);
    }

    private String getSequenceName(final VariableContext variableContext,
                                   final SequenceGenerator sequenceGenerator) {
        final String schema = Optional.of(sequenceGenerator.schema()).filter(v -> !v.isEmpty())
                .or(() -> variableContext.getCurrentTableName().getSchema())
                .orElse("");
        final String table = variableContext.getCurrentTableName().getSimpleName();
        final String sequenceName = sequenceGenerator.value()
                .replace("${schema}", schema)
                .replace("${table}", table)
                .trim();
        return startsWith(sequenceName, '.') ? sequenceName.substring(1) : sequenceName;
    }

    private void validateSequenceGenerator(final SequenceGenerator sequenceGenerator) {
        if (!sequenceGenerator.schema().isEmpty() &&
                !sequenceGenerator.value().contains("${schema}")) {
            throw new InterruptProcessingException(
                    getFieldElement(),
                    "If schema is provided for sequence generator, sequence name must contain required ${schema} variable." +
                            " Add the required variable or remove the sequence schema"
            );
        }
    }

    private void validateNotInsertable() {
        if (getAnnotation(NotInsertable.class) != null) {
            throw new InterruptProcessingException(
                    getFieldElement(),
                    "'@?' couldn't be applied to primary key. Remove redundant annotation",
                    NotInsertable.class.getName()
            );
        }
    }

    @UsedByFreemarker("$$SQLEntityToSQLDBConverterTemplate.javaftl")
    public boolean isInsertValuePlaceholder() {
        return FORMAT_PLACEHOLDER_TOKEN.equals(insertValue);
    }

    @UsedByFreemarker("$$SQLEntityToSQLDBConverterTemplate.javaftl")
    public boolean isLengthPresent() {
        if (lengthPresent == null) {
            if (String.class.getName().equals(getFieldClass().toString())) {
                if (length == Column.NOT_SPECIFIED_LENGTH) {
                    throw new InterruptProcessingException(
                            getFieldElement(),
                            "Missing a required column length value for '?' column type. " +
                                    "If it is necessary to set unlimited value use '?.UNLIMITED_LENGTH' constant!",
                            String.class.getName(), Column.class.getName()
                    );
                }
                lengthPresent = length > Column.UNLIMITED_LENGTH;
            } else {
                if (length != Column.NOT_SPECIFIED_LENGTH) {
                    throw new InterruptProcessingException(
                            getFieldElement(),
                            "Column length supported for '?' column type only. " +
                                    "Remove redundant column declaration!",
                            String.class.getName()
                    );
                }
                lengthPresent = false;
            }
        }
        return lengthPresent;
    }

    @UsedByFreemarker("$$SQLEntityToSQLDBConverterTemplate.javaftl")
    public int getLength() {
        return length;
    }

    @UsedByFreemarker("$$SQLEntityToSQLDBConverterTemplate.javaftl")
    public String getTrimMethodName() {
        return nullable ? "trimNullable" : "trimNotNull";
    }
}
