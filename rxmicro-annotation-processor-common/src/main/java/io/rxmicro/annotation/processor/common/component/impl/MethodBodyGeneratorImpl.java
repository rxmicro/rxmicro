/*
 * Copyright 2019 http://rxmicro.io
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
import freemarker.template.TemplateException;
import io.rxmicro.annotation.processor.common.component.MethodBodyGenerator;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class MethodBodyGeneratorImpl extends AbstractGenerator
        implements MethodBodyGenerator {

    @Override
    public List<String> generate(final String templateName,
                                 final Map<String, Object> parameters) {
        final StringWriter stringWriter = new StringWriter();
        try (final Writer writer = stringWriter) {
            getTemplate(templateName).process(parameters, writer);
        } catch (final TemplateException | IOException e) {
            cantGenerateMethodBody(templateName, e);
        }
        return stringWriter.toString().lines().collect(Collectors.toList());
    }
}
