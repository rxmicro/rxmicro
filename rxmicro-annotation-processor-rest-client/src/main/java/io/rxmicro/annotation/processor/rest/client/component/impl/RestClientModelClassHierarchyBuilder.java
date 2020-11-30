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

package io.rxmicro.annotation.processor.rest.client.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.impl.AbstractModelClassHierarchyBuilder;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.common.model.type.ModelClass;
import io.rxmicro.annotation.processor.rest.client.model.RestClientObjectModelClass;
import io.rxmicro.annotation.processor.rest.model.RestModelField;
import io.rxmicro.annotation.processor.rest.model.RestObjectModelClass;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * @author nedis
 * @since 0.7.2
 */
@Singleton
public final class RestClientModelClassHierarchyBuilder extends AbstractModelClassHierarchyBuilder<RestModelField, RestObjectModelClass> {

    @Override
    public Optional<List<RestObjectModelClass>> build(final RestObjectModelClass returnedByRestMethodModelClass,
                                                      final Set<String> returnedByRestMethodModelClassNames) {
        // An inheritance for REST model for REST client will be implemented later!
        return Optional.empty();
    }

    @Override
    protected RestObjectModelClass createObjectModelClass(final TypeMirror modelTypeMirror,
                                                          final TypeElement modelTypeElement,
                                                          final Map<RestModelField, ModelClass> params,
                                                          final RestObjectModelClass parent,
                                                          final boolean modelClassReturnedByRestMethod) {
        return new RestClientObjectModelClass(modelTypeMirror, modelTypeElement, params, parent, modelClassReturnedByRestMethod);
    }

    @Override
    protected void validateParentModelClass(final RestObjectModelClass parentModelClass) {
        if (parentModelClass.isPathVariablesPresent()) {
            throw new InterruptProcessingException(
                    parentModelClass.getPathVariableMap().entrySet().iterator().next().getValue().getFieldElement(),
                    "Current version of the RxMicro Annotation processor does not support path variables that defined at parent class! " +
                            "Path variables can be defined at child class only! Remove unsupported path variables from ? class!",
                    parentModelClass.getJavaFullClassName()
            );
        }
    }
}
