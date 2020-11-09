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

import java.util.List;

import javax.lang.model.type.TypeMirror;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getTypes;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class IterableModelClass extends ModelClass {

    private final ModelClass elementModelClass;

    private final TypeMirror containerType;

    private final String containerTypeName;

    public IterableModelClass(final ModelClass elementModelClass,
                              final TypeMirror containerType) {
        this.elementModelClass = require(elementModelClass);
        this.containerType = containerType;
        this.containerTypeName = getSimpleName(getTypes().erasure(containerType));
    }

    @UsedByFreemarker
    public ModelClass getElementModelClass() {
        return elementModelClass;
    }

    @UsedByFreemarker
    public boolean isPrimitiveIterable() {
        return elementModelClass instanceof PrimitiveModelClass;
    }

    @UsedByFreemarker
    public boolean isObjectIterable() {
        return elementModelClass instanceof ObjectModelClass;
    }

    @UsedByFreemarker
    public boolean isEnumIterable() {
        return elementModelClass instanceof EnumModelClass;
    }

    @UsedByFreemarker
    public String getContainerType(){
        return containerTypeName;
    }

    @Override
    @UsedByFreemarker
    public String getJavaSimpleClassName() {
        return getSimpleName(containerType);
    }

    @Override
    public String getJavaFullClassName() {
        return containerType.toString();
    }
}
