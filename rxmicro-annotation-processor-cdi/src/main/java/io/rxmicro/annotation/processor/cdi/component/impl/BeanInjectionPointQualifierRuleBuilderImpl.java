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
import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.BeanInjectionPointQualifierRuleBuilder;
import io.rxmicro.annotation.processor.cdi.component.DefaultNameBuilder;
import io.rxmicro.annotation.processor.cdi.component.UserDefinedNameBuilder;
import io.rxmicro.annotation.processor.cdi.model.QualifierRule;
import io.rxmicro.annotation.processor.cdi.model.qualifier.ByTypeAndNameQualifierRule;
import io.rxmicro.annotation.processor.cdi.model.qualifier.ByTypeQualifierRule;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.lang.model.element.VariableElement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.rxmicro.annotation.processor.common.util.Elements.asTypeElement;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class BeanInjectionPointQualifierRuleBuilderImpl implements BeanInjectionPointQualifierRuleBuilder {

    @Inject
    private UserDefinedNameBuilder userDefinedNameBuilder;

    @Inject
    private DefaultNameBuilder defaultNameBuilder;

    @Override
    public List<QualifierRule> build(final VariableElement field) {
        final List<QualifierRule> result = new ArrayList<>(2);
        asTypeElement(field.asType()).ifPresent(typeElement -> userDefinedNameBuilder.getName(field)
                .ifPresentOrElse(
                        modelName -> result.add(new ByTypeAndNameQualifierRule(typeElement, modelName)),
                        () -> result.addAll(Arrays.asList(
                                new ByTypeAndNameQualifierRule(typeElement, defaultNameBuilder.getName(field)),
                                new ByTypeQualifierRule(typeElement)
                        ))
                ));
        if (result.isEmpty()) {
            throw new InterruptProcessingException(field, "Only classes can be injectable!");
        }
        return result;
    }
}
