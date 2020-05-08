/*
 * Copyright 2019 https://rxmicro.io
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

import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.DefaultConfigProxyValue;
import io.rxmicro.annotation.processor.common.model.ModelTransformer;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualFieldElement;
import io.rxmicro.annotation.processor.common.model.virtual.VirtualTypeElement;
import io.rxmicro.annotation.processor.common.util.UsedByFreemarker;
import io.rxmicro.annotation.processor.rest.model.AbstractSimpleObjectModelClass;
import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.http.client.ClientHttpResponse;
import io.rxmicro.http.client.HttpClient;
import io.rxmicro.rest.client.detail.ModelReader;
import io.rxmicro.rest.client.detail.PathBuilder;
import io.rxmicro.rest.client.detail.RequestModelExtractor;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.annotation.processor.common.util.Names.getSimpleName;
import static io.rxmicro.common.util.ExCollections.join;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;
import static io.rxmicro.common.util.Requires.require;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.1
 */
public final class RestClientClassStructure extends ClassStructure {

    private final ClassHeader.Builder classHeaderBuilder;

    private final String configNameSpace;

    private final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues;

    private final TypeElement httpClientConfigClass;

    private final TypeElement restClientInterface;

    private final TypeElement restClientAbstractClass;

    private final List<RestClientMethod> methods;

    private final RestClientClassStructureStorage classStructureStorage;

    private final Set<ModelTransformer> modelTransformers;

    private final Set<AbstractSimpleObjectModelClass> allValidators;

    public RestClientClassStructure(final ClassHeader.Builder classHeaderBuilder,
                                    final String configNameSpace,
                                    final List<Map.Entry<String, DefaultConfigProxyValue>> defaultConfigValues,
                                    final TypeElement httpClientConfigClass,
                                    final TypeElement restClientInterface,
                                    final TypeElement restClientAbstractClass,
                                    final List<RestClientMethod> methods,
                                    final RestClientClassStructureStorage classStructureStorage) {
        this.classHeaderBuilder = require(classHeaderBuilder);
        this.configNameSpace = configNameSpace;
        this.defaultConfigValues = defaultConfigValues;
        this.httpClientConfigClass = httpClientConfigClass;
        this.restClientInterface = require(restClientInterface);
        this.restClientAbstractClass = require(restClientAbstractClass);
        this.methods = require(methods);
        this.classStructureStorage = classStructureStorage;
        this.modelTransformers = Stream.of(
                methods.stream()
                        .filter(m -> m.shouldGeneratePathBuilder(classStructureStorage))
                        .flatMap(m -> m.getToHttpDataType().stream())
                        .map(cl -> new ModelTransformer(cl.asType(), PathBuilder.class)),
                methods.stream()
                        .filter(m -> m.shouldGenerateModelExtractor(classStructureStorage))
                        .flatMap(m -> m.getToHttpDataType().stream())
                        .map(cl -> new ModelTransformer(cl.asType(), RequestModelExtractor.class)),
                methods.stream()
                        .filter(m -> m.shouldGenerateModelToHttpBodyConverter(classStructureStorage))
                        .flatMap(m -> m.getToHttpDataType().stream())
                        .map(cl -> new ModelTransformer(cl.asType(), ModelToJsonConverter.class)),
                methods.stream()
                        .filter(m -> m.shouldGenerateModelReader(classStructureStorage))
                        .flatMap(m -> m.getFromHttpDataType().stream())
                        .map(cl -> new ModelTransformer(cl.asType(), ModelReader.class))
        ).flatMap(identity()).collect(toSet());

        this.allValidators = join(getRequestValidators(), getResponseValidators());
    }

    public List<Map.Entry<String, DefaultConfigProxyValue>> getDefaultConfigValues() {
        return defaultConfigValues;
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getSimpleInterfaceName() {
        return getSimpleName(getFullInterfaceName());
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getTargetSimpleClassName() {
        return GENERATED_CLASS_NAME_PREFIX + restClientInterface.getSimpleName().toString();
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getConfigNameSpace() {
        return configNameSpace;
    }

    public TypeElement getHttpClientConfigFullClassName() {
        return httpClientConfigClass;
    }

    @UsedByFreemarker("$$RestClientFactoryImplTemplate.javaftl")
    public String getHttpClientConfigSimpleClassName() {
        return getSimpleName(httpClientConfigClass);
    }

    public String getFullInterfaceName() {
        return restClientInterface.getQualifiedName().toString();
    }

    @Override
    public String getTargetFullClassName() {
        return getPackageName(restClientInterface) + "." + getTargetSimpleClassName();
    }

    @Override
    public String getTemplateName() {
        return "rest/client/$$RestClientTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("PREFIX", GENERATED_CLASS_NAME_PREFIX);
        map.put("JAVA_REST_CLIENT_INTERFACE", restClientInterface.getSimpleName());
        map.put("JAVA_REST_CLIENT_ABSTRACT_CLASS", restClientAbstractClass.getSimpleName());
        map.put("JAVA_REST_CLIENT_METHODS", methods);
        map.put("JAVA_MODEL_TRANSFORMERS", modelTransformers);
        map.put("JAVA_VALIDATED_MODEL_CLASSES", allValidators);
        map.put("CONFIG_CLASS", getHttpClientConfigSimpleClassName());
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        classHeaderBuilder
                .addImports(
                        CompletableFuture.class,
                        HttpClient.class,
                        ClientHttpResponse.class
                )
                .addImports(restClientAbstractClass);
        classHeaderBuilder.addImports(getHttpClientConfigFullClassName());
        allValidators.forEach(v -> classHeaderBuilder.addImports(v.getModelValidatorImplFullClassName()));
        modelTransformers.forEach(c -> classHeaderBuilder.addImports(c.getJavaFullClassName()));
        methods.stream().flatMap(m -> m.getToHttpDataType().stream())
                .filter(te -> te instanceof VirtualTypeElement)
                .forEach(typeElement ->
                        classHeaderBuilder.addImports(((VirtualTypeElement) typeElement).getVirtualFieldElements().stream()
                                .map(VirtualFieldElement::asType)
                                .toArray(TypeMirror[]::new)));
        return classHeaderBuilder.build();
    }

    private Set<AbstractSimpleObjectModelClass> getRequestValidators() {
        final Set<AbstractSimpleObjectModelClass> simpleObjectModelClasses = new HashSet<>();
        if (classStructureStorage.isRequestValidatorsPresent()) {
            simpleObjectModelClasses.addAll(
                    methods.stream()
                            .filter(m -> m.shouldGenerateRequestValidator(classStructureStorage))
                            .flatMap(m -> m.getToHttpDataType().stream())
                            .map(RestClientSimpleObjectModelClass::new)
                            .collect(toSet()));
        }
        return simpleObjectModelClasses;
    }

    private Set<AbstractSimpleObjectModelClass> getResponseValidators() {
        final Set<AbstractSimpleObjectModelClass> simpleObjectModelClasses = new HashSet<>();
        if (classStructureStorage.isResponseValidatorsPresent()) {
            simpleObjectModelClasses.addAll(
                    methods.stream()
                            .filter(m -> m.shouldGenerateResponseValidator(classStructureStorage))
                            .flatMap(m -> m.getFromHttpDataType().stream())
                            .map(RestClientSimpleObjectModelClass::new)
                            .collect(toSet()));
        }
        return simpleObjectModelClasses;
    }
}
