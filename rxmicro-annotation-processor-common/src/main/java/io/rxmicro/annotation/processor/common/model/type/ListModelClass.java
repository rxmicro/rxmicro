/*
 * Copyright (c) 2020. http://rxmicro.io
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

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class ListModelClass extends ModelClass {

    private static final String CONTAINER_SIMPLE_CLASS_NAME = List.class.getSimpleName();

    private final ModelClass elementModelClass;

    public ListModelClass(final ModelClass elementModelClass) {
        this.elementModelClass = require(elementModelClass);
    }

    @UsedByFreemarker
    public ModelClass getElementModelClass() {
        return elementModelClass;
    }

    @UsedByFreemarker
    public boolean isPrimitiveList() {
        return elementModelClass instanceof PrimitiveModelClass;
    }

    @UsedByFreemarker
    public boolean isObjectList() {
        return elementModelClass instanceof ObjectModelClass;
    }

    @UsedByFreemarker
    public boolean isEnumList() {
        return elementModelClass instanceof EnumModelClass;
    }

    @Override
    @UsedByFreemarker
    public String getJavaSimpleClassName() {
        return format("?<?>", CONTAINER_SIMPLE_CLASS_NAME, elementModelClass.getJavaSimpleClassName());
    }

    @Override
    public String getJavaFullClassName() {
        return format("?<?>", List.class.getName(), elementModelClass.getJavaFullClassName());
    }
}
