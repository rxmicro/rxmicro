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

package io.rxmicro.annotation.processor.rest.client.model;

import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.type.ObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.AbstractModelJsonConverterClassStructure;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
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
public final class RestClientClassStructureStorage {

    private final Map<String, RestObjectModelClass> modelClassMap;

    private final Map<String, ModelValidatorClassStructure> requestValidatorMap;

    private final Map<String, ModelValidatorClassStructure> responseValidatorMap;

    private final Map<String, PathBuilderClassStructure> pathBuilderMap;

    private final Map<String, RequestModelExtractorClassStructure> requestModelExtractorMap;

    private final Map<String, ClientModelReaderClassStructure> modelReaderMap;

    private final Map<String, ModelFromJsonConverterClassStructure> modelFromJsonConverterMap;

    private final Map<String, ModelToJsonConverterClassStructure> modelToJsonConverterMap;

    private RestClientClassStructureStorage(
            final Map<String, RestObjectModelClass> modelClassMap,
            final Map<String, ModelValidatorClassStructure> requestValidatorMap,
            final Map<String, ModelValidatorClassStructure> responseValidatorMap,
            final Map<String, PathBuilderClassStructure> pathBuilderMap,
            final Map<String, RequestModelExtractorClassStructure> requestModelExtractorMap,
            final Map<String, ClientModelReaderClassStructure> modelReaderMap,
            final Map<String, ModelFromJsonConverterClassStructure> modelFromJsonConverterMap,
            final Map<String, ModelToJsonConverterClassStructure> modelToJsonConverterMap) {
        this.modelClassMap = require(modelClassMap);
        this.requestValidatorMap = require(requestValidatorMap);
        this.responseValidatorMap = require(responseValidatorMap);
        this.pathBuilderMap = require(pathBuilderMap);
        this.requestModelExtractorMap = require(requestModelExtractorMap);
        this.modelReaderMap = require(modelReaderMap);
        this.modelFromJsonConverterMap = require(modelFromJsonConverterMap);
        this.modelToJsonConverterMap = require(modelToJsonConverterMap);
    }

    public boolean isPathBuilderPresent(final String fullClassName) {
        return pathBuilderMap.containsKey(fullClassName);
    }

    public boolean isRequestModelExtractorPresent(final String fullClassName) {
        return requestModelExtractorMap.containsKey(fullClassName);
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

    public boolean isModelReaderPresent(final String fullClassName) {
        return modelReaderMap.containsKey(fullClassName);
    }

    public boolean isModelToJsonConverterPresent(final String fullClassName) {
        return modelToJsonConverterMap.containsKey(fullClassName);
    }

    public Optional<RestObjectModelClass> getModelClass(final String fullClassName) {
        return Optional.ofNullable(modelClassMap.get(fullClassName));
    }

    public Set<ClassStructure> getAll() {
        return Stream.of(
                requestValidatorMap.values().stream().map(s -> (ClassStructure) s),
                responseValidatorMap.values().stream().map(s -> (ClassStructure) s),
                pathBuilderMap.values().stream().map(s -> (ClassStructure) s),
                requestModelExtractorMap.values().stream().map(s -> (ClassStructure) s),
                modelReaderMap.values().stream().map(s -> (ClassStructure) s),
                modelFromJsonConverterMap.values().stream().map(s -> (ClassStructure) s),
                modelToJsonConverterMap.values().stream().map(s -> (ClassStructure) s)
        ).flatMap(identity()).collect(Collectors.toSet());
    }

    /**
     * @author nedis
     * @since 0.1
     */
    @SuppressWarnings("UnusedReturnValue")
    public static final class Builder {

        private final Set<RestObjectModelClass> restClientObjectModelClasses = new TreeSet<>();

        private final Set<ModelValidatorClassStructure> requestValidators = new TreeSet<>();

        private final Set<ModelValidatorClassStructure> responseValidators = new TreeSet<>();

        private final Set<PathBuilderClassStructure> pathBuilders = new TreeSet<>();

        private final Set<ClientModelReaderClassStructure> modelReaders = new TreeSet<>();

        private final Set<RequestModelExtractorClassStructure> requestModelExtractors = new TreeSet<>();

        private final Set<ModelFromJsonConverterClassStructure> modelFromJsonConverters = new TreeSet<>();

        private final Set<ModelToJsonConverterClassStructure> modelToJsonConverters = new TreeSet<>();

        public void addRestObjectModelClasses(
                final Set<RestObjectModelClass> restObjectModelClasses) {
            this.restClientObjectModelClasses.addAll(restObjectModelClasses);
        }

        public void addRequestValidators(
                final Set<ModelValidatorClassStructure> requestValidators) {
            this.requestValidators.addAll(requestValidators);
        }

        public void addResponseValidators(
                final Set<ModelValidatorClassStructure> responseValidators) {
            this.responseValidators.addAll(responseValidators);
        }

        @BuilderMethod
        public Builder addPathBuilders(
                final Set<PathBuilderClassStructure> pathBuilders) {
            this.pathBuilders.addAll(pathBuilders);
            return this;
        }

        @BuilderMethod
        public Builder addModelReaders(
                final Set<ClientModelReaderClassStructure> modelReaders) {
            this.modelReaders.addAll(modelReaders);
            return this;
        }

        @BuilderMethod
        public Builder addRequestModelExtractors(
                final Set<RequestModelExtractorClassStructure> requestModelExtractors) {
            this.requestModelExtractors.addAll(requestModelExtractors);
            return this;
        }

        @BuilderMethod
        public Builder addModelFromJsonConverters(
                final Set<ModelFromJsonConverterClassStructure> modelFromJsonConverters) {
            this.modelFromJsonConverters.addAll(modelFromJsonConverters);
            return this;
        }

        @BuilderMethod
        public Builder addModelToJsonConverters(
                final Set<ModelToJsonConverterClassStructure> modelToJsonConverters) {
            this.modelToJsonConverters.addAll(modelToJsonConverters);
            return this;
        }

        public Set<RestObjectModelClass> getRestClientObjectModelClasses() {
            return restClientObjectModelClasses;
        }

        public Set<ModelValidatorClassStructure> getRequestValidators() {
            return requestValidators;
        }

        public Set<ModelValidatorClassStructure> getResponseValidators() {
            return responseValidators;
        }

        public Set<PathBuilderClassStructure> getPathBuilders() {
            return pathBuilders;
        }

        public Set<ClientModelReaderClassStructure> getModelReaders() {
            return modelReaders;
        }

        public Set<RequestModelExtractorClassStructure> getRequestModelExtractors() {
            return requestModelExtractors;
        }

        public Set<ModelFromJsonConverterClassStructure> getModelFromJsonConverters() {
            return modelFromJsonConverters;
        }

        public Set<ModelToJsonConverterClassStructure> getModelToJsonConverters() {
            return modelToJsonConverters;
        }

        public RestClientClassStructureStorage build() {
            return new RestClientClassStructureStorage(
                    restClientObjectModelClasses.stream().collect(toMap(
                            ObjectModelClass::getJavaFullClassName,
                            identity()
                    )),
                    requestValidators.stream().collect(toMap(
                            ModelValidatorClassStructure::getModelFullClassName,
                            identity()
                    )),
                    responseValidators.stream().collect(toMap(
                            ModelValidatorClassStructure::getModelFullClassName,
                            identity()
                    )),
                    pathBuilders.stream().collect(toMap(
                            PathBuilderClassStructure::getModelFullClassName,
                            identity()
                    )),
                    requestModelExtractors.stream().collect(toMap(
                            RequestModelExtractorClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelReaders.stream().collect(toMap(
                            ClientModelReaderClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelFromJsonConverters.stream().collect(toMap(
                            AbstractModelJsonConverterClassStructure::getModelFullClassName,
                            identity()
                    )),
                    modelToJsonConverters.stream().collect(toMap(
                            AbstractModelJsonConverterClassStructure::getModelFullClassName,
                            identity()
                    ))
            );
        }
    }
}
