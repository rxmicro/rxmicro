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

import io.rxmicro.annotation.processor.common.model.method.MethodResult;
import io.rxmicro.annotation.processor.data.model.Variable;
import io.rxmicro.common.InvalidStateException;

import javax.lang.model.element.ModuleElement;
import java.util.List;
import java.util.Optional;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class SQLMethodDescriptor<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>> {

    private final ModuleElement currentModule;

    private final List<Variable> params;

    private final MethodResult result;

    private final DMC entityParam;

    private final DMC entityResult;

    private SQLMethodDescriptor(final ModuleElement currentModule,
                                final List<Variable> params,
                                final MethodResult result,
                                final DMC entityParam,
                                final DMC entityResult) {
        this.currentModule = currentModule;
        this.params = params;
        this.result = result;
        this.entityParam = entityParam;
        this.entityResult = entityResult;
    }

    public ModuleElement getCurrentModule() {
        return currentModule;
    }

    public List<Variable> getParams() {
        return params;
    }

    public MethodResult getResult() {
        return result;
    }

    public Optional<DMC> getEntityParam() {
        return Optional.ofNullable(entityParam);
    }

    public Optional<DMC> getEntityResult() {
        return Optional.ofNullable(entityResult);
    }

    /**
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder<DMF extends SQLDataModelField, DMC extends SQLDataObjectModelClass<DMF>> {

        private final ModuleElement currentModule;

        private final List<Variable> params;

        private final MethodResult result;

        private DMC entityParam;

        private DMC entityResult;

        public Builder(final ModuleElement currentModule,
                       final List<Variable> params,
                       final MethodResult result) {
            this.currentModule = require(currentModule);
            this.params = require(params);
            this.result = require(result);
        }

        public boolean isEntityParamSet() {
            return entityParam != null;
        }

        public void setEntityParam(final DMC entityParam) {
            if (this.entityParam != null) {
                throw new InvalidStateException("entityParam already set");
            }
            this.entityParam = require(entityParam);
        }

        public void setEntityResult(final DMC entityResult) {
            if (this.entityResult != null) {
                throw new InvalidStateException("entityResult already set");
            }
            this.entityResult = require(entityResult);
        }

        public SQLMethodDescriptor<DMF, DMC> build() {
            return new SQLMethodDescriptor<>(currentModule, params, result, entityParam, entityResult);
        }
    }
}
