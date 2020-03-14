/*
 * Copyright 2019 http://rxmicro.io
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

import io.rxmicro.annotation.processor.common.model.type.PrimitiveType;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class DataPrimitiveType implements PrimitiveType {

    private final String convertMethod;

    public static DataPrimitiveType valueOf(final TypeMirror typeMirror) {
        return new DataPrimitiveType("to" + getSimpleName(typeMirror));
    }

    private DataPrimitiveType(final String convertMethod) {
        this.convertMethod = require(convertMethod);
    }

    @Override
    @UsedByFreemarker
    public String getConvertMethod() {
        return convertMethod;
    }
}
