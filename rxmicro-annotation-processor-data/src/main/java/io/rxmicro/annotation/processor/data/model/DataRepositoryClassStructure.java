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

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.common.model.ModelTransformer;
import io.rxmicro.annotation.processor.common.util.Names;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.data.local.EntityFromDBConverter;
import io.rxmicro.data.local.EntityToDBConverter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.ExCollectors.toTreeSet;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class DataRepositoryClassStructure extends ClassStructure {

    protected final ClassHeader.Builder classHeaderBuilder;

    protected final TypeElement repositoryInterface;

    protected final TypeElement abstractClass;

    protected final String configNameSpace;

    protected final List<? extends DataRepositoryMethod> methods;

    protected final Set<ModelTransformer> modelTransformers;

    private final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues;

    private final List<Map.Entry<TypeElement, String>> enumMapping;

    protected DataRepositoryClassStructure(final ClassHeader.Builder classHeaderBuilder,
                                           final TypeElement repositoryInterface,
                                           final TypeElement abstractClass,
                                           final String configNameSpace,
                                           final List<? extends DataRepositoryMethod> methods,
                                           final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues,
                                           final List<Map.Entry<TypeElement, String>> enumMapping) {
        this.classHeaderBuilder = require(classHeaderBuilder);
        this.repositoryInterface = require(repositoryInterface);
        this.abstractClass = require(abstractClass);
        this.configNameSpace = require(configNameSpace);
        this.methods = require(methods);
        this.defaultConfigValues = require(defaultConfigValues);
        this.enumMapping = require(enumMapping);
        this.modelTransformers = getModelTransformers();
    }

    public List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigValues() {
        return defaultConfigValues;
    }

    public List<Map.Entry<TypeElement, String>> getEnumMapping() {
        return enumMapping;
    }

    @UsedByFreemarker("$$RepositoryFactoryImplTemplate.javaftl")
    public final String getSimpleInterfaceName() {
        return getSimpleName(getFullInterfaceName());
    }

    @UsedByFreemarker("$$RepositoryFactoryImplTemplate.javaftl")
    public final String getConfigNameSpace() {
        return configNameSpace;
    }

    public final String getFullInterfaceName() {
        return repositoryInterface.asType().toString();
    }

    @Override
    public final String getTargetFullClassName() {
        return format("?.???", getPackageName(), GENERATED_CLASS_NAME_PREFIX, getRepositoryTypePrefix(), getSimpleInterfaceName());
    }

    public List<? extends DataRepositoryMethod> getMethods() {
        return methods;
    }

    protected abstract String getRepositoryTypePrefix();

    protected final String getPackageName() {
        return Names.getPackageName(repositoryInterface);
    }

    private Set<ModelTransformer> getModelTransformers() {
        return Stream.concat(
                        methods.stream()
                                .flatMap(m -> m.getMethodSignature().getParamEntityClasses().stream())
                                .map(cl -> new ModelTransformer(cl, getEntityToDBConverterClass())),
                        methods.stream()
                                .flatMap(m -> m.getMethodSignature().getReturnEntityClasses().stream())
                                .map(cl -> new ModelTransformer(cl, getEntityFromDBConverterClass())))
                .collect(toTreeSet());
    }

    protected abstract Class<? extends EntityToDBConverter> getEntityToDBConverterClass();

    protected abstract Class<? extends EntityFromDBConverter> getEntityFromDBConverterClass();

    protected abstract void customizeClassHeader();

    @Override
    public final ClassHeader getClassHeader() {
        customizeClassHeader();
        return classHeaderBuilder.build();
    }
}
