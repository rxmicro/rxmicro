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

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.cdi.component.BeanDefinitionWithInjectionsClassStructureBuilder;
import io.rxmicro.annotation.processor.cdi.model.BeanSupplierClassStructure;
import io.rxmicro.annotation.processor.common.model.EnvironmentContext;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.HashSet;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class BeanDefinitionWithInjectionsClassStructureBuilderImpl
        extends AbstractBeanDefinitionClassStructureBuilder
        implements BeanDefinitionWithInjectionsClassStructureBuilder {

    @Override
    public Set<BeanSupplierClassStructure> build(final EnvironmentContext environmentContext,
                                                 final Set<? extends TypeElement> annotations,
                                                 final RoundEnvironment roundEnv) {
        final Set<BeanSupplierClassStructure> result = new HashSet<>();
        final Set<String> processedTypes = new HashSet<>();
        for (final TypeElement annotation : annotations) {
            for (final Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                final TypeElement ownerClass = getOwnerType(element);
                if (environmentContext.isRxMicroClassShouldBeProcessed(ownerClass) &&
                        processedTypes.add(ownerClass.asType().toString())) {
                    result.add(buildCDIBeanDefinitionClassStructure(ownerClass));
                }
            }
        }
        return result;
    }

    private TypeElement getOwnerType(final Element element) {
        Element ownerType = element;
        do {
            ownerType = ownerType.getEnclosingElement();
            if (ownerType instanceof TypeElement) {
                return (TypeElement) ownerType;
            }
        } while (ownerType != null);
        throw new InterruptProcessingException(element, "Can't find class element for @Inject or @Autowired annotation!");
    }
}
