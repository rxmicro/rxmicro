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

import com.google.inject.Inject;
import com.google.inject.Singleton;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.rxmicro.annotation.processor.common.component.SourceCodeFormatter;
import io.rxmicro.annotation.processor.common.component.SourceCodeGenerator;
import io.rxmicro.annotation.processor.common.model.ClassStructure;
import io.rxmicro.annotation.processor.common.model.SourceCode;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class SourceCodeGeneratorImpl extends AbstractGenerator implements SourceCodeGenerator {

    @Inject
    private SourceCodeFormatter sourceCodeFormatter;

    @Override
    public SourceCode generate(final ClassStructure classStructure) {
        final Map<String, Object> variables = classStructure.getTemplateVariables();
        final StringWriter stringWriter = new StringWriter();
        try {
            final Template template = getTemplate(classStructure.getTemplateName());
            template.process(variables, stringWriter);
        } catch (final TemplateException | IOException ex) {
            catchThrowable(ex, () -> cantGenerateClass(classStructure.getTargetFullClassName(), ex));
        }
        return new SourceCode(
                classStructure.getTargetFullClassName(),
                sourceCodeFormatter.format(
                        classStructure.getClassHeader(),
                        stringWriter.toString()
                )
        );
    }
}
