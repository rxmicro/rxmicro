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

package io.rxmicro.annotation.processor.cdi.model;

import io.rxmicro.annotation.processor.common.model.method.MethodName;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import java.util.List;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public abstract class AbstractCDIMethod {

    private final boolean privateMethod;

    private final MethodName methodName;

    protected AbstractCDIMethod(final ExecutableElement executableElement) {
        this.privateMethod = executableElement.getModifiers().contains(Modifier.PRIVATE);
        this.methodName = new MethodName(executableElement.getSimpleName().toString(), List.of(), false);
    }

    public boolean isPrivateMethod() {
        return privateMethod;
    }

    @UsedByFreemarker
    public MethodName getMethodName() {
        return methodName;
    }
}
