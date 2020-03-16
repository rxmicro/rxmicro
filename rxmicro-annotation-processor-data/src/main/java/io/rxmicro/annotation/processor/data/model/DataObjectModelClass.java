/*
 * Copyright 2019 https://rxmicro.io
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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.data.local.EntityFromDBConverter;
import io.rxmicro.data.local.EntityToDBConverter;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerInstanceName;
import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerSimpleClassName;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public abstract class DataObjectModelClass<F extends DataModelField> extends ObjectModelClass<F> {

    public DataObjectModelClass(final TypeMirror modelTypeMirror,
                                final TypeElement modelTypeElement,
                                final Map<F, ModelClass> params) {
        super(modelTypeMirror, modelTypeElement, params);
    }

    @UsedByFreemarker({
            "$$MongoEntityFromDBConverterTemplate.javaftl",
            "$$SQLEntityFromSQLDBConverterTemplate.javaftl"
    })
    public String getFromDBConverterInstanceName() {
        return getModelTransformerInstanceName(getJavaSimpleClassName(), getEntityFromDBConverterClass());
    }

    @UsedByFreemarker({
            "$$MongoEntityFromDBConverterTemplate.javaftl",
            "$$SQLEntityFromSQLDBConverterTemplate.javaftl"
    })
    public String getEntityFromDBConverterImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), getEntityFromDBConverterClass());
    }

    @UsedByFreemarker({
            "$$MongoEntityToDBConverterTemplate.javaftl",
            "$$SQLEntityToSQLDBConverterTemplate"
    })
    public String getToDBConverterInstanceName() {
        return getModelTransformerInstanceName(getJavaSimpleClassName(), getEntityToDBConverterClass());
    }

    @UsedByFreemarker({
            "$$MongoEntityToDBConverterTemplate.javaftl",
            "$$SQLEntityToSQLDBConverterTemplate"
    })
    public String getEntityToDBConverterImplSimpleClassName() {
        return getModelTransformerSimpleClassName(getModelTypeElement(), getEntityToDBConverterClass());
    }

    protected abstract Class<? extends EntityToDBConverter> getEntityToDBConverterClass();

    protected abstract Class<? extends EntityFromDBConverter> getEntityFromDBConverterClass();

    @UsedByFreemarker({
            "$$MongoEntityFromDBConverterTemplate.javaftl",
            "$$SQLEntityFromSQLDBConverterTemplate.javaftl"
    })
    public String getEntityFromDBConverterParentSimpleClassName() {
        return getEntityFromDBConverterClass().getSimpleName();
    }

    @UsedByFreemarker({
            "$$MongoEntityToDBConverterTemplate.javaftl",
            "$$SQLEntityToSQLDBConverterTemplate"
    })
    public String getEntityToDBConverterParentSimpleClassName() {
        return getEntityToDBConverterClass().getSimpleName();
    }
}
