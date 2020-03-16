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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions;

import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive.BooleanPrimitiveConstraintReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive.DateTimePrimitiveConstraintReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive.EnumPrimitiveConstraintReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive.NumberPrimitiveConstraintReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.primitive.StringPrimitiveConstraintReader;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.meta.ReadMore;

import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class PrimitiveRestrictionReader extends AbstractRestrictionReader {

    private final List<ConstraintReader> readers = List.of(
            new BooleanPrimitiveConstraintReader(),
            new EnumPrimitiveConstraintReader(),
            new StringPrimitiveConstraintReader(),
            new NumberPrimitiveConstraintReader(),
            new DateTimePrimitiveConstraintReader()
    );

    public void readPrimitive(final EnvironmentContext environmentContext,
                              final List<String> restrictions,
                              final List<ReadMore> readMores,
                              final Map.Entry<RestModelField, ModelClass> entry,
                              final StringBuilder descriptionBuilder) {
        readRequired(environmentContext, restrictions, readMores, entry);
        for (final ConstraintReader reader : readers) {
            reader.readIfConstraintEnabled(restrictions, readMores, entry, descriptionBuilder);
        }
    }
}
