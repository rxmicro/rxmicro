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
import io.rxmicro.annotation.processor.common.component.DocumentationGenerator;
import io.rxmicro.annotation.processor.common.component.PathVariablesResolver;
import io.rxmicro.annotation.processor.common.model.DocumentStructure;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static io.rxmicro.annotation.processor.common.SupportedOptions.RX_MICRO_DOC_DESTINATION_DIR;
import static java.nio.file.Files.writeString;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class DocumentationGeneratorImpl extends AbstractGenerator implements DocumentationGenerator {

    @Inject
    private PathVariablesResolver pathVariablesResolver;

    @Override
    public void generate(final DocumentStructure documentStructure) {
        final Map<String, Object> variables = documentStructure.getTemplateVariables();
        try {
            final String destinationFile = getDestinationFile(documentStructure);
            final StringWriter stringWriter = new StringWriter();
            try (Writer writer = stringWriter) {
                final Template template = getTemplate(documentStructure.getTemplateName());
                template.process(variables, writer);
            }
            writeString(Paths.get(destinationFile), stringWriter.toString());
            info("Document generated successfully: ?", destinationFile);
        } catch (final TemplateException | IOException ex) {
            catchThrowable(ex, () -> cantGenerateDocument(documentStructure.getName(), ex));
        }
    }

    private String getDestinationFile(final DocumentStructure documentStructure) throws IOException {
        final String dir = getDirectory(documentStructure);
        final Path path = Paths.get(dir).toAbsolutePath();
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (final IOException ex) {
                throw new IOException("Can't created directory: " + path, ex);
            }
        }
        if (dir.endsWith("/")) {
            return dir + documentStructure.getName();
        } else {
            return dir + "/" + documentStructure.getName();
        }
    }

    private String getDirectory(final DocumentStructure documentStructure) {
        return pathVariablesResolver.resolvePathVariables(
                documentStructure.getCurrentModule(),
                documentStructure.getProjectDirectory(),
                documentStructure.getCustomDestinationDirectory()
                        .orElseGet(() ->
                                getStringOption(
                                        RX_MICRO_DOC_DESTINATION_DIR,
                                        documentStructure.getDocumentationType().getDestinationDirectory()
                                )
                        )
        );
    }
}
