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

package io.rxmicro.annotation.processor.common.model.type;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.json.JsonTypes;

import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class EnumModelClass extends ModelClass {

    private final TypeMirror typeMirror;

    public EnumModelClass(final TypeMirror typeMirror) {
        this.typeMirror = require(typeMirror);
    }

    @Override
    public String getJavaSimpleClassName() {
        return getSimpleName(typeMirror);
    }

    @UsedByFreemarker
    @Override
    public String getJavaFullClassName() {
        return typeMirror.toString();
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }

    @UsedByFreemarker("$$MongoEntityFromDBConverterTemplate.javaftl")
    public PrimitiveType getPrimitiveType() {
        return new PrimitiveType() {
            @Override
            public String getConvertMethod() {
                return "toEnum";
            }

            @Override
            public String toJsonType() {
                return JsonTypes.STRING;
            }
        };
    }
}
