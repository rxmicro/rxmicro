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

package io.rxmicro.annotation.processor.rest.server.component.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.ModelFieldBuilder;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.ModelFieldBuilderOptions;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.rest.model.MappedRestObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;
import io.rxmicro.annotation.processor.rest.server.component.CustomExceptionMappedRestObjectModelClassBuilder;
import io.rxmicro.http.error.HttpErrorException;
import io.rxmicro.rest.ResponseStatusCode;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.model.ModelFieldType.REST_SERVER_RESPONSE;
import static io.rxmicro.annotation.processor.common.util.Elements.findSuperType;
import static io.rxmicro.common.local.DeniedPackages.isDeniedPackage;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author nedis
 * @since 0.9
 */
@Singleton
public final class CustomExceptionMappedRestObjectModelClassBuilderImpl implements CustomExceptionMappedRestObjectModelClassBuilder {

    @Inject
    private ModelFieldBuilder<RestModelField, RestObjectModelClass> modelFieldBuilder;

    @Override
    public List<MappedRestObjectModelClass> build(final EnvironmentContext environmentContext) {
        final Set<PackageElement> packageElements = Stream.of(environmentContext.getCurrentModule())
                .flatMap(me -> me.getEnclosedElements().stream().map(e -> (PackageElement) e))
                .filter(pe -> !isDeniedPackage(pe.getQualifiedName().toString()))
                .collect(toSet());
        return packageElements.stream()
                .flatMap(pe -> pe.getEnclosedElements().stream().filter(e -> e instanceof TypeElement).map(e -> (TypeElement) e))
                .filter(environmentContext::isRxMicroClassShouldBeProcessed)
                .filter(this::isSubClassOfHttpErrorException)
                .map(te -> getModelClass(environmentContext, te))
                .filter(this::isCustomExceptionModelClass)
                .map(mc -> new MappedRestObjectModelClass(mc, List.of()))
                .collect(toList());
    }

    private boolean isSubClassOfHttpErrorException(final TypeElement type) {
        return type.getKind() == ElementKind.CLASS && findSuperType(type, HttpErrorException.class).isPresent();
    }

    private boolean isCustomExceptionModelClass(final RestObjectModelClass restObjectModelClass) {
        return restObjectModelClass.isParamEntriesPresent() ||
                restObjectModelClass.isHeadersPresent() ||
                restObjectModelClass.isInternalsPresent();
    }

    private RestObjectModelClass getModelClass(final EnvironmentContext environmentContext,
                                               final TypeElement exceptionTypeElement) {
        final ModuleElement currentModule = environmentContext.getCurrentModule();
        final ModelFieldBuilderOptions options = new ModelFieldBuilderOptions()
                .setWithFieldsFromParentClasses(false)
                .setAccessViaReflectionMustBeDetected(false);
        final Map<TypeElement, RestObjectModelClass> map =
                modelFieldBuilder.build(REST_SERVER_RESPONSE, currentModule, Set.of(exceptionTypeElement), options);
        return validate(map.get(exceptionTypeElement));
    }

    private RestObjectModelClass validate(final RestObjectModelClass restObjectModelClass) {
        for (final RestModelField internal : restObjectModelClass.getInternals()) {
            if (internal.getAnnotation(ResponseStatusCode.class) != null) {
                throw new InterruptProcessingException(
                        internal.getElementAnnotatedBy(ResponseStatusCode.class),
                        "'@?' annotation can't be used for custom exception type! Remove this annotation!",
                        ResponseStatusCode.class.getName()
                );
            }
        }
        return restObjectModelClass;
    }
}
