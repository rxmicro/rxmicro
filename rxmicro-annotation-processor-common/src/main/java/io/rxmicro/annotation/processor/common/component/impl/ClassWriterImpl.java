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
import io.rxmicro.annotation.processor.common.component.ClassWriter;
import io.rxmicro.annotation.processor.common.model.AnnotationProcessorType;
import io.rxmicro.annotation.processor.common.model.SourceCode;

import java.io.IOException;
import java.io.Writer;
import javax.annotation.processing.FilerException;

import static io.rxmicro.annotation.processor.common.model.AnnotationProcessorType.PROJECT_COMPILE;
import static io.rxmicro.annotation.processor.common.util.ProcessingEnvironmentHelper.getFiler;

/**
 * @author nedis
 * @since 0.1
 */
@Singleton
public final class ClassWriterImpl extends BaseProcessorComponent implements ClassWriter {

    @Override
    public void write(final SourceCode sourceCode,
                      final AnnotationProcessorType annotationProcessorType) {
        try (Writer writer = getFiler().createSourceFile(sourceCode.getName()).openWriter()) {
            writer.write(sourceCode.getContent());
            if (annotationProcessorType == PROJECT_COMPILE) {
                debug("Class generated successfully: ?", sourceCode::getName);
            } else {
                info("Test fixer class generated successfully: ?", sourceCode::getName);
            }
        } catch (final FilerException ex) {
            //If java source file already created skip error
            if (!ex.getMessage().startsWith("Attempt to recreate a file for type ")) {
                cantGenerateClass(sourceCode.getName(), ex);
            }
        } catch (final IOException ex) {
            cantGenerateClass(sourceCode.getName(), ex);
        }
    }
}
