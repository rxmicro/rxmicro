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

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.data.model.DataObjectModelClass;
import io.rxmicro.data.sql.NotInsertable;
import io.rxmicro.data.sql.NotUpdatable;
import io.rxmicro.data.sql.PrimaryKey;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

import static io.rxmicro.common.util.ExCollectors.toOrderedSet;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class SQLDataObjectModelClass<DMF extends SQLDataModelField> extends DataObjectModelClass<DMF> {

    private final Set<EntityFromDBConverterMethod> entityFromDBConverterMethods = new TreeSet<>();

    private final Set<EntitySetFieldsConverterMethod> entitySetFieldsConverterMethods = new TreeSet<>();

    private Set<Map.Entry<DMF, ModelClass>> insertableParams;

    private Set<Map.Entry<DMF, ModelClass>> updatableParams;

    private Set<Map.Entry<DMF, ModelClass>> primaryKeysParams;

    private boolean deletable;

    public SQLDataObjectModelClass(final TypeMirror modelTypeMirror,
                                   final TypeElement modelTypeElement,
                                   final Map<DMF, ModelClass> params) {
        super(modelTypeMirror, modelTypeElement, params);
    }

    public Set<EntityFromDBConverterMethod> getEntityFromDBConverterMethods() {
        return Collections.unmodifiableSet(entityFromDBConverterMethods);
    }

    public void addEntityFromDBConverterMethod(final EntityFromDBConverterMethod method) {
        entityFromDBConverterMethods.add(method);
    }

    public Set<EntitySetFieldsConverterMethod> getEntitySetFieldsConverterMethods() {
        return Collections.unmodifiableSet(entitySetFieldsConverterMethods);
    }

    public void addEntitySetFieldsConverterMethod(final EntitySetFieldsConverterMethod method) {
        entitySetFieldsConverterMethods.add(method);
    }

    public boolean isInsertable() {
        return insertableParams != null;
    }

    public Set<Map.Entry<DMF, ModelClass>> getInsertableParams() {
        if (insertableParams == null) {
            insertableParams = getParamEntries().stream()
                    .filter(e -> e.getKey().getAnnotation(NotInsertable.class) == null)
                    .collect(toOrderedSet());
        }
        return insertableParams;
    }

    public boolean isUpdatable() {
        return updatableParams != null;
    }

    public Set<Map.Entry<DMF, ModelClass>> getUpdatableParams() {
        if (updatableParams == null) {
            updatableParams = getParamEntries().stream()
                    .filter(e -> e.getKey().getAnnotation(NotUpdatable.class) == null &&
                            e.getKey().getAnnotation(PrimaryKey.class) == null)
                    .collect(toOrderedSet());
        }
        return updatableParams;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(final boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isPrimaryKeysPresent() {
        return primaryKeysParams != null;
    }

    public Set<Map.Entry<DMF, ModelClass>> getPrimaryKeysParams() {
        if (primaryKeysParams == null) {
            primaryKeysParams = getParamEntries().stream()
                    .filter(e -> e.getKey().getAnnotation(PrimaryKey.class) != null)
                    .collect(toOrderedSet());
        }
        return primaryKeysParams;
    }
}
