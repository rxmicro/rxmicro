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

package io.rxmicro.annotation.processor.common.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.component.UnnamedPackageValidator;

import javax.lang.model.element.Element;
import javax.lang.model.element.ModuleElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getElements;
import static io.rxmicro.common.Constants.VirtualModuleInfoConstants.RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME;

/**
 * @author nedis
 * @since 0.2
 */
@Singleton
public final class UnnamedPackageValidatorImpl extends AbstractProcessorComponent implements UnnamedPackageValidator {

    @Override
    public void validate(final ModuleElement realModuleElement) {
        if (realModuleElement.isUnnamed()) {
            validate(getElements().getPackageElement(""), true);
        } else {
            validate(getElements().getPackageElement(realModuleElement, ""), false);
        }
    }

    private void validate(final PackageElement packageElement,
                          final boolean isUnNamedModule) {
        if (packageElement == null) {
            return;
        }
        for (final Element element : packageElement.getEnclosedElements()) {
            if (element instanceof TypeElement) {
                if (isUnNamedModule) {
                    if (!RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME.equals(element.getSimpleName().toString())) {
                        error(element,
                                "Each class (interface, enum or annotation) must be declared at a package! " +
                                        "Unnamed (default) package is not supported! " +
                                        "Move this element to the named package! " +
                                        "FYI: If you want to add the virtual module " +
                                        "you should use the predefined by RxMicro framework name: '?' instead of '?'!",
                                RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME,
                                element.getSimpleName()
                        );
                    }
                } else {
                    if (RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME.equals(element.getSimpleName().toString())) {
                        error(element,
                                "? is used as alternative of `module-info.java` only! " +
                                        "To configure project use `module-info.java` instead of `?.java`",
                                RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME, RX_MICRO_VIRTUAL_MODULE_INFO_ANNOTATION_NAME
                        );
                    } else {
                        error(element,
                                "Each class (interface, enum or annotation) must be declared at a package! " +
                                        "Unnamed (default) package is not supported! " +
                                        "Move this element to the named package!"
                        );
                    }
                }
            }
        }
    }
}
