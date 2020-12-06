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

package io.rxmicro.annotation.processor.data.model;

import io.rxmicro.annotation.processor.common.model.TypeSignature;

import java.util.List;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public final class DataRepositoryInterfaceSignature extends TypeSignature {

    private final TypeElement repositoryInterface;

    private final TypeElement repositoryAbstractClass;

    private final List<DataRepositoryMethodSignature> methods;

    public DataRepositoryInterfaceSignature(final TypeElement repositoryInterface,
                                            final TypeElement repositoryAbstractClass,
                                            final List<DataRepositoryMethodSignature> methods) {
        this.repositoryInterface = require(repositoryInterface);
        this.repositoryAbstractClass = require(repositoryAbstractClass);
        this.methods = require(methods);
        for (final DataRepositoryMethodSignature method : methods) {
            method.setDataRepositoryInterfaceSignature(this);
        }
    }

    public TypeElement getRepositoryInterface() {
        return repositoryInterface;
    }

    public TypeElement getRepositoryAbstractClass() {
        return repositoryAbstractClass;
    }

    public List<DataRepositoryMethodSignature> getMethods() {
        return methods;
    }

    @Override
    protected String getTypeFullName() {
        return repositoryInterface.getQualifiedName().toString();
    }
}
