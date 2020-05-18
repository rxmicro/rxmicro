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

package io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.common.model.type.ListModelClass;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.RestrictionReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.ArrayRestrictionReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.ObjectRestrictionReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.component.impl.restrictions.PrimitiveRestrictionReader;
import io.rxmicro.annotation.processor.documentation.asciidoctor.model.Restrictions;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.common.meta.ReadMore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class RestrictionReaderImpl implements RestrictionReader {

    private final PrimitiveRestrictionReader primitiveRestrictionReader = new PrimitiveRestrictionReader();

    private final ObjectRestrictionReader objectRestrictionReader = new ObjectRestrictionReader();

    private final ArrayRestrictionReader arrayRestrictionReader = new ArrayRestrictionReader();

    @Override
    public Restrictions read(final EnvironmentContext environmentContext,
                             final Map.Entry<RestModelField, ModelClass> entry) {
        final List<String> restrictions = new ArrayList<>();
        final List<ReadMore> readMores = new ArrayList<>();
        final StringBuilder descriptionBuilder = new StringBuilder();
        if (entry.getValue().isObject()) {
            objectRestrictionReader.read(environmentContext, entry, restrictions, readMores);
        } else if (entry.getValue().isList()) {
            final ListModelClass listModelClass = entry.getValue().asList();
            if (listModelClass.isObjectList()) {
                objectRestrictionReader.read(environmentContext, entry, restrictions, readMores);
                arrayRestrictionReader.read(entry, restrictions, readMores);
            } else if (listModelClass.isPrimitiveList() || listModelClass.isEnumList()) {
                primitiveRestrictionReader.readPrimitive(environmentContext, entry, restrictions, readMores, descriptionBuilder);
                arrayRestrictionReader.read(entry, restrictions, readMores);
            } else {
                throw new InternalErrorException(
                        "?: Unsupported array model type: ?",
                        getClass().getSimpleName(),
                        listModelClass.getElementModelClass().getClass());
            }
        } else if (entry.getValue().isPrimitive() || entry.getValue().isEnum()) {
            primitiveRestrictionReader.readPrimitive(environmentContext, entry, restrictions, readMores, descriptionBuilder);
        } else {
            throw new InternalErrorException(
                    "?: Unsupported model type: ?",
                    getClass().getSimpleName(),
                    entry.getValue());
        }
        return new Restrictions(
                restrictions,
                readMores,
                descriptionBuilder.length() == 0 ? null : descriptionBuilder.toString()
        );
    }
}
