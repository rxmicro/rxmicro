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

import io.rxmicro.annotation.processor.common.model.CDIUsageCandidateClassStructure;
import io.rxmicro.annotation.processor.common.model.ClassHeader;
import io.rxmicro.annotation.processor.rest.model.AbstractSimpleObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.ParentUrl;
import io.rxmicro.cdi.BeanFactory;
import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.model.UrlSegments;
import io.rxmicro.rest.server.detail.component.AbstractRestController;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.component.ModelWriter;
import io.rxmicro.rest.server.detail.component.RestControllerRegistrar;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.UrlTemplateRequestMappingRule;
import io.rxmicro.rest.server.feature.EnableCrossOriginResourceSharing;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.detail.ResponseValidators;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import static io.rxmicro.annotation.processor.common.util.GeneratedClassNames.getModelTransformerFullClassName;
import static io.rxmicro.annotation.processor.common.util.Names.getPackageName;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.GeneratedClassRules.GENERATED_CLASS_NAME_PREFIX;
import static io.rxmicro.common.util.Requires.require;
import static java.util.Map.entry;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class RestControllerClassStructure extends CDIUsageCandidateClassStructure {

    private final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig;

    private final ParentUrl parentUrl;

    private final ClassHeader.Builder classHeaderBuilder;

    private final TypeElement ownerClass;

    private final List<RestControllerMethod> methods;

    private final RestControllerClassStructureStorage classStructureStorage;

    public RestControllerClassStructure(final RestServerModuleGeneratorConfig restServerModuleGeneratorConfig,
                                        final ParentUrl parentUrl,
                                        final ClassHeader.Builder classHeaderBuilder,
                                        final TypeElement ownerClass,
                                        final List<RestControllerMethod> methods,
                                        final RestControllerClassStructureStorage classStructureStorage) {
        this.restServerModuleGeneratorConfig = require(restServerModuleGeneratorConfig);
        this.parentUrl = require(parentUrl);
        this.classHeaderBuilder = require(classHeaderBuilder);
        this.ownerClass = require(ownerClass);
        this.methods = require(methods);
        this.classStructureStorage = require(classStructureStorage);
    }

    public ParentUrl getParentUrl() {
        return parentUrl;
    }

    public TypeElement getOwnerClass() {
        return ownerClass;
    }

    public List<RestControllerMethod> getMethods() {
        return methods;
    }

    @Override
    public String getTargetFullClassName() {
        return getPackageName(ownerClass) + "." +
                GENERATED_CLASS_NAME_PREFIX + ownerClass.getSimpleName().toString();
    }

    @Override
    public String getTemplateName() {
        return "rest/server/$$RestControllerTemplate.javaftl";
    }

    @Override
    public Map<String, Object> getTemplateVariables() {
        final Map<String, Object> map = new HashMap<>();
        map.put("PREFIX", GENERATED_CLASS_NAME_PREFIX);
        map.put("USE_CDI", isUseCDI());
        map.put("JAVA_CLASS_NAME", ownerClass.getSimpleName().toString());
        map.put("JAVA_REQUEST_CLASSES", methods.stream()
                .flatMap(m -> m.getFromHttpDataType().stream())
                .map(RestServerSimpleObjectModelClass::new)
                .collect(toSet()));
        map.put("JAVA_RESPONSE_CLASSES", methods.stream()
                .flatMap(m -> m.getToHttpDataType().stream())
                .map(RestServerSimpleObjectModelClass::new)
                .collect(toSet()));
        map.put("NOT_FOUND_RESPONSES", methods.stream()
                .filter(RestControllerMethod::isNotFoundPossible)
                .map(m -> entry(format("?NotFoundResponse", m.getName().getUniqueJavaName()), m.getNotFoundMessage()))
                .collect(toList()));
        map.put("VOID_RESPONSE_POSSIBLE", methods.stream().anyMatch(RestControllerMethod::isVoidReturn));
        final Map<String, AbstractSimpleObjectModelClass> requestValidators = getRequestValidators();
        final Map<String, AbstractSimpleObjectModelClass> responseValidators = getResponseValidators();
        map.put("VALIDATORS_MAP", Stream.concat(
                requestValidators.entrySet().stream(),
                responseValidators.entrySet().stream()
        ).collect(toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (o1, o2) -> o1)
        ));
        map.put("PARENT_CLASS_NAME", AbstractRestController.class.getSimpleName());
        map.put("METHODS", methods);
        map.put("PARENT_URL", parentUrl.toString());
        map.put("CORS_ENABLED", ownerClass.getAnnotation(EnableCrossOriginResourceSharing.class) != null);
        map.put("GENERATE_RESPONSE_VALIDATORS", restServerModuleGeneratorConfig.isGenerateResponseValidators());
        return map;
    }

    @Override
    public ClassHeader getClassHeader() {
        classHeaderBuilder.addImports(methods.stream()
                .flatMap(m -> m.getFromHttpDataType().stream())
                .flatMap(t -> allImports(
                        t,
                        ModelReader.class,
                        classStructureStorage.isRequestValidatorPresent(t.getQualifiedName().toString()))
                )
                .toArray(String[]::new));
        classHeaderBuilder.addImports(methods.stream()
                .flatMap(m -> m.getToHttpDataType().stream())
                .flatMap(t -> allImports(
                        t,
                        ModelWriter.class,
                        classStructureStorage.isResponseValidatorPresent(t.getQualifiedName().toString()))
                )
                .toArray(String[]::new));
        classHeaderBuilder
                .addImports(
                        AbstractRestController.class,
                        RestControllerRegistrar.class,
                        Registration.class,
                        HttpRequest.class,
                        HttpResponse.class,
                        PathVariableMapping.class,
                        CompletionStage.class,
                        HttpHeaders.class,
                        UrlTemplateRequestMappingRule.class,
                        UrlSegments.class,
                        List.class,
                        ExactUrlRequestMappingRule.class
                )
                .addStaticImport(ResponseValidators.class, "validateResponse");
        if (isUseCDI()) {
            classHeaderBuilder.addStaticImport(BeanFactory.class, "getBean");
        }
        return classHeaderBuilder.build();
    }

    @Override
    public String getBeanFullClassName() {
        return ownerClass.getQualifiedName().toString();
    }

    private Map<String, AbstractSimpleObjectModelClass> getRequestValidators() {
        final Map<String, AbstractSimpleObjectModelClass> map = new HashMap<>();
        if (classStructureStorage.isRequestValidatorsPresent()) {
            map.putAll(methods.stream()
                    .flatMap(m -> m.getFromHttpDataType().stream())
                    .filter(t -> classStructureStorage.isRequestValidatorPresent(t.getQualifiedName().toString()))
                    .map(RestServerSimpleObjectModelClass::new)
                    .collect(toMap(
                            AbstractSimpleObjectModelClass::getFullClassName,
                            identity(),
                            (o1, o2) -> o1
                    )));
        }
        return map;
    }

    private Map<String, AbstractSimpleObjectModelClass> getResponseValidators() {
        final Map<String, AbstractSimpleObjectModelClass> map = new HashMap<>();
        if (classStructureStorage.isResponseValidatorsPresent()) {
            map.putAll(methods.stream()
                    .flatMap(m -> m.getToHttpDataType().stream())
                    .filter(t -> classStructureStorage.isResponseValidatorPresent(t.getQualifiedName().toString()))
                    .map(RestServerSimpleObjectModelClass::new)
                    .collect(toMap(
                            AbstractSimpleObjectModelClass::getFullClassName,
                            identity(),
                            (o1, o2) -> o1
                    )));
        }
        return map;
    }

    private Stream<String> allImports(final TypeElement typeElement,
                                      final Class<?> baseTransformerClass,
                                      final boolean shouldValidatorsBeGenerated) {
        if (shouldValidatorsBeGenerated) {
            return Stream.of(
                    typeElement.getQualifiedName().toString(),
                    getModelTransformerFullClassName(typeElement, baseTransformerClass),
                    getModelTransformerFullClassName(typeElement, ConstraintValidator.class)
            );
        } else {
            return Stream.of(
                    typeElement.getQualifiedName().toString(),
                    getModelTransformerFullClassName(typeElement, baseTransformerClass)
            );
        }
    }
}
