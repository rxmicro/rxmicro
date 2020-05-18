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

package io.rxmicro.annotation.processor.rest.model.validator;

import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class ModelValidatorCreator {

    private final String className;

    private final String instanceName;

    private final String constructorArgs;

    ModelValidatorCreator(final String className,
                          final String instanceName,
                          final String constructorArgs) {
        this.className = require(className);
        this.instanceName = require(instanceName);
        this.constructorArgs = require(constructorArgs);
    }

    ModelValidatorCreator(final String className,
                          final String instanceName) {
        this.className = require(className);
        this.instanceName = require(instanceName);
        this.constructorArgs = null;
    }

    @UsedByFreemarker
    public String getClassName() {
        return className;
    }

    @UsedByFreemarker
    public String getInstanceName() {
        return instanceName;
    }

    @UsedByFreemarker
    public String getConstructorArgs() {
        return constructorArgs;
    }

    @UsedByFreemarker
    public boolean isStateless() {
        return constructorArgs == null;
    }
}
