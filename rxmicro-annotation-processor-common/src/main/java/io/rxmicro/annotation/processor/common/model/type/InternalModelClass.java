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

/**
 * @author nedis
 * @since 0.1
 */
public final class InternalModelClass extends ModelClass {

    private static final InternalModelClass INSTANCE = new InternalModelClass();

    public static InternalModelClass getInstance() {
        return INSTANCE;
    }

    private InternalModelClass() {

    }

    @Override
    public String getJavaSimpleClassName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getJavaFullClassName() {
        throw new UnsupportedOperationException();
    }
}
