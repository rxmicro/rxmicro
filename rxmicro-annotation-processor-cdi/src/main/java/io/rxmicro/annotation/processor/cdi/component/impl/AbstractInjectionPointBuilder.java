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

package io.rxmicro.annotation.processor.cdi.component.impl;

import com.google.inject.Inject;
import io.rxmicro.annotation.processor.cdi.component.BeanInjectionPointQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.DefaultNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.InjectionPointTypeBuilder;
import io.rxmicro.annotation.processor.cdi.component.InjectionResourceBuilder;
import io.rxmicro.annotation.processor.cdi.component.UserDefinedNameBuilder;
import io.rxmicro.annotation.processor.cdi.model.InjectionModelField;
import io.rxmicro.annotation.processor.cdi.model.InjectionPoint;
import io.rxmicro.annotation.processor.cdi.model.InjectionPointType;
import io.rxmicro.annotation.processor.common.model.AnnotatedModelElement;
import io.rxmicro.annotation.processor.common.model.SupportedAnnotations;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.cdi.Autowired;
import io.rxmicro.cdi.Resource;

import java.lang.annotation.Annotation;
import java.util.Optional;
import javax.lang.model.element.Element;
import javax.lang.model.element.VariableElement;

import static io.rxmicro.annotation.processor.common.util.Errors.createInternalErrorSupplier;
import static io.rxmicro.annotation.processor.common.util.validators.AnnotationValidators.validateOnlyOneAnnotationPerElement;
import static io.rxmicro.cdi.local.Annotations.INJECT_ANNOTATIONS;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractInjectionPointBuilder {

    @Inject
    private UserDefinedNameBuilder userDefinedNameBuilder;

    @Inject
    private DefaultNameBuilder defaultNameBuilder;

    @Inject
    private InjectionPointTypeBuilder injectionPointTypeBuilder;

    @Inject
    private BeanInjectionPointQualifierRuleBuilder beanInjectionPointQualifierRuleBuilder;

    @Inject
    private InjectionResourceBuilder injectionResourceBuilder;

    final InjectionPoint build(final VariableElement field) {
        validateOnlyOneAnnotationPerElement(field, field.getAnnotationMirrors(), new SupportedAnnotations(INJECT_ANNOTATIONS));
        final String modelName = userDefinedNameBuilder.getName(field).orElseGet(() -> defaultNameBuilder.getName(field));
        final InjectionPointType type = injectionPointTypeBuilder.build(field, modelName);
        final boolean required = isRequired(field);
        final InjectionPoint.Builder injectionPointBuilder = new InjectionPoint.Builder()
                .setType(type)
                .setModelField(new InjectionModelField(buildAnnotatedModelElement(field), modelName))
                .setRequired(required);
        if (type == InjectionPointType.BEAN) {
            injectionPointBuilder.setQualifierRules(beanInjectionPointQualifierRuleBuilder.build(field));
        } else if (type == InjectionPointType.RESOURCE) {
            injectionPointBuilder.setInjectionResource(injectionResourceBuilder.build(field));
        } else if (!required) {
            throw new InterruptProcessingException(field, "Optional injection not supported. Remove annotation parameter!");
        }
        return build(field, injectionPointBuilder);
    }

    protected abstract InjectionPoint build(VariableElement field,
                                            InjectionPoint.Builder injectionPointBuilder);

    protected boolean isRequired(final Element element) {
        return Optional.ofNullable(element.getAnnotation(io.rxmicro.cdi.Inject.class))
                .map(i -> !i.optional())
                .orElseGet(() -> Optional.ofNullable(element.getAnnotation(Autowired.class))
                        .map(Autowired::required)
                        .orElseGet(() -> Optional.ofNullable(element.getAnnotation(Resource.class))
                                .map(i -> !i.optional())
                                .orElseThrow(createInternalErrorSupplier(
                                        "@? or @? or @? annotation must be present!",
                                        io.rxmicro.cdi.Inject.class.getSimpleName(),
                                        Autowired.class.getSimpleName(),
                                        Resource.class.getSimpleName()
                                )))

                );
    }

    protected final boolean isInjectionAnnotationPresent(final Element element) {
        return Optional.ofNullable(element.getAnnotation(io.rxmicro.cdi.Inject.class))
                .map(i -> (Annotation) i)
                .or(() -> Optional.ofNullable(element.getAnnotation(Autowired.class)))
                .or(() -> Optional.ofNullable(element.getAnnotation(Resource.class)))
                .isPresent();
    }

    private AnnotatedModelElement buildAnnotatedModelElement(final VariableElement variableElement) {
        return new AnnotatedModelElement(variableElement);
    }
}
