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

package io.rxmicro.annotation.processor.rest.server.model;

import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.error.InternalErrorException;
import io.rxmicro.annotation.processor.rest.model.AbstractModelJsonConverterClassStructure;
import io.rxmicro.annotation.processor.rest.model.converter.ModelFromJsonConverterClassStructure;
import io.rxmicro.annotation.processor.rest.model.converter.ModelToJsonConverterClassStructure;
import io.rxmicro.annotation.processor.rest.model.validator.ModelValidatorClassStructure;
import io.rxmicro.common.meta.BuilderMethod;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Requires.require;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestControllerClassStructureStorage {

    private final Map<String, ModelValidatorClassStructure> requestValidatorMap;

    private final Map<String, ModelValidatorClassStructure> responseValidatorMap;

    private final Map<String, ServerModelReaderClassStructure> modelReaderMap;

    private final Map<String, ServerModelWriterClassStructure> modelWriterMap;

    private final Map<String, ModelFromJsonConverterClassStructure> modelFromJsonConverterMap;

    private final Map<String, ModelToJsonConverterClassStructure> modelToJsonConverterMap;

    private final Set<CustomExceptionServerWriterClassStructure> customExceptionModelWriters;

    private RestControllerClassStructureStorage(final Map<String, ModelValidatorClassStructure> requestValidatorMap,
                                                final Map<String, ModelValidatorClassStructure> responseValidatorMap,
                                                final Map<String, ServerModelReaderClassStructure> modelReaderMap,
                                                final Map<String, ServerModelWriterClassStructure> modelWriterMap,
                                                final Map<String, ModelFromJsonConverterClassStructure> modelFromJsonConverterMap,
                                                final Map<String, ModelToJsonConverterClassStructure> modelToJsonConverterMap,
                                                final Set<CustomExceptionServerWriterClassStructure> customExceptionModelWriters) {
        this.requestValidatorMap = require(requestValidatorMap);
        this.responseValidatorMap = require(responseValidatorMap);
        this.modelReaderMap = require(modelReaderMap);
        this.modelWriterMap = require(modelWriterMap);
        this.modelFromJsonConverterMap = require(modelFromJsonConverterMap);
        this.modelToJsonConverterMap = require(modelToJsonConverterMap);
        this.customExceptionModelWriters = require(customExceptionModelWriters);
    }

    public boolean isRequestValidatorPresent(final String fullClassName) {
        return requestValidatorMap.containsKey(fullClassName);
    }

    public boolean isRequestValidatorsPresent() {
        return !requestValidatorMap.isEmpty();
    }

    public boolean isResponseValidatorPresent(final String fullClassName) {
        return responseValidatorMap.containsKey(fullClassName);
    }

    public boolean isResponseValidatorsPresent() {
        return !responseValidatorMap.isEmpty();
    }

    public Optional<ServerModelReaderClassStructure> getModelReaderClassStructure(final String fullClassName) {
        return Optional.ofNullable(modelReaderMap.get(fullClassName));
    }

    public Optional<ServerModelWriterClassStructure> getModelWriterClassStructure(final String fullClassName) {
        return Optional.ofNullable(modelWriterMap.get(fullClassName));
    }

    public Set<CustomExceptionServerWriterClassStructure> getCustomExceptionModelWriters() {
        return customExceptionModelWriters;
    }

    public Set<ClassStructure> getAll() {
        return Stream.of(
                requestValidatorMap.values().stream().map(s -> (ClassStructure) s),
                responseValidatorMap.values().stream().map(s -> (ClassStructure) s),
                modelReaderMap.values().stream().map(s -> (ClassStructure) s),
                modelWriterMap.values().stream().map(s -> (ClassStructure) s),
                modelFromJsonConverterMap.values().stream().map(s -> (ClassStructure) s),
                modelToJsonConverterMap.values().stream().map(s -> (ClassStructure) s),
                customExceptionModelWriters.stream().map(s -> (ClassStructure) s)
        ).flatMap(identity()).collect(Collectors.toSet());
    }

    /**
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final Set<ModelValidatorClassStructure> requestValidators = new TreeSet<>();

        private final Set<ModelValidatorClassStructure> responseValidators = new TreeSet<>();

        private final Set<ModelValidatorClassStructure> customExceptionModelValidators = new TreeSet<>();

        private final Set<ServerModelReaderClassStructure> modelReaders = new TreeSet<>();

        private final Set<ServerModelWriterClassStructure> modelWriters = new TreeSet<>();

        private final Set<CustomExceptionServerWriterClassStructure> customExceptionModelWriters = new TreeSet<>();

        private final Set<ModelFromJsonConverterClassStructure> modelFromJsonConverters = new TreeSet<>();

        private final Set<ModelToJsonConverterClassStructure> modelToJsonConverters = new TreeSet<>();

        public void addRequestValidators(final Set<ModelValidatorClassStructure> requestValidators) {
            this.requestValidators.addAll(requestValidators);
        }

        public void addResponseValidators(final Set<ModelValidatorClassStructure> responseValidators) {
            this.responseValidators.addAll(responseValidators);
        }

        @BuilderMethod
        public Builder addModelReaders(final Set<ServerModelReaderClassStructure> modelReaders) {
            this.modelReaders.addAll(modelReaders);
            return this;
        }

        @BuilderMethod
        public Builder addModelWriters(final Set<ServerModelWriterClassStructure> modelWriters) {
            this.modelWriters.addAll(modelWriters);
            return this;
        }

        @BuilderMethod
        public Builder addCustomExceptionModelWriters(final Set<ServerModelWriterClassStructure> modelWriters) {
            this.modelWriters.addAll(modelWriters);
            for (final ServerModelWriterClassStructure modelWriter : modelWriters) {
                final boolean isValidatorGenerated = this.customExceptionModelValidators.stream().anyMatch(cs ->
                        cs.getModelClass().getJavaFullClassName().equals(modelWriter.getModelClass().getJavaFullClassName()));
                this.customExceptionModelWriters.add(
                        new CustomExceptionServerWriterClassStructure(modelWriter.getModelClass(), isValidatorGenerated)
                );
            }
            return this;
        }

        public void addCustomExceptionModelValidators(final Set<ModelValidatorClassStructure> responseValidators) {
            if (!this.customExceptionModelWriters.isEmpty()) {
                throw new InternalErrorException(
                        "'addCustomExceptionModelValidators' method must be invoked before 'addCustomExceptionModelWriters' one!"
                );
            }
            this.customExceptionModelValidators.addAll(responseValidators);
        }

        @BuilderMethod
        public Builder addModelFromJsonConverters(final Set<ModelFromJsonConverterClassStructure> modelFromJsonConverters) {
            this.modelFromJsonConverters.addAll(modelFromJsonConverters);
            return this;
        }

        @BuilderMethod
        public Builder addModelToJsonConverters(final Set<ModelToJsonConverterClassStructure> modelToJsonConverters) {
            this.modelToJsonConverters.addAll(modelToJsonConverters);
            return this;
        }

        public Set<ModelValidatorClassStructure> getRequestValidators() {
            return requestValidators;
        }

        public Set<ModelValidatorClassStructure> getResponseValidators() {
            return responseValidators;
        }

        public Set<ServerModelReaderClassStructure> getModelReaders() {
            return modelReaders;
        }

        public Set<ServerModelWriterClassStructure> getModelWriters() {
            return modelWriters;
        }

        public Set<ModelFromJsonConverterClassStructure> getModelFromJsonConverters() {
            return modelFromJsonConverters;
        }

        public Set<ModelToJsonConverterClassStructure> getModelToJsonConverters() {
            return modelToJsonConverters;
        }

        public RestControllerClassStructureStorage build() {
            return new RestControllerClassStructureStorage(
                    requestValidators.stream().collect(toMap(
                            ModelValidatorClassStructure::getModelFullClassName,
                            identity()
                    )),
                    Stream.concat(
                            responseValidators.stream(),
                            customExceptionModelValidators.stream()
                    ).collect(toMap(
                            ModelValidatorClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelReaders.stream().collect(toMap(
                            AbstractRestControllerModelClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelWriters.stream().collect(toMap(
                            AbstractRestControllerModelClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelFromJsonConverters.stream().collect(toMap(
                            AbstractModelJsonConverterClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelToJsonConverters.stream().collect(toMap(
                            AbstractModelJsonConverterClassStructure::getModelFullClassName,
                            identity()
                    )),
                    customExceptionModelWriters
            );
        }
    }
}
