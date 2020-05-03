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

package io.rxmicro.annotation.processor.documentation.component.impl;

import com.google.inject.Singleton;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;
import io.rxmicro.annotation.processor.documentation.component.ExternalResourceReader;
import io.rxmicro.common.RxMicroException;

import javax.lang.model.element.Element;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@Singleton
public final class ExternalResourceReaderImpl implements ExternalResourceReader {

    @Override
    public String read(final Element element,
                       final String resourcePath) {
        if (resourcePath.startsWith("http://") || resourcePath.startsWith("https://")) {
            return readFromHttpResource(resourcePath);
        }
        final Path filePath = Paths.get(resourcePath);
        if (Files.exists(filePath)) {
            return readFromFile(filePath);
        }
        throw new InterruptProcessingException(element, "File not found: ?", filePath.toAbsolutePath());
    }

    private String readFromFile(final Path filePath) {
        try {
            return Files.readString(filePath, UTF_8);
        } catch (IOException e) {
            throw new RxMicroException(e, "Can't read data from file: ?", filePath.toAbsolutePath());
        }
    }

    private String readFromHttpResource(final String resourcePath) {
        try (Scanner scanner = new Scanner(new URL(resourcePath).openStream(), UTF_8)) {
            scanner.useDelimiter("\\A");
            if (scanner.hasNext()) {
                return scanner.next();
            }
            throw new RxMicroException("Can't read data from http resource: ?: Empty content", resourcePath);
        } catch (IOException e) {
            throw new RxMicroException(e, "Can't read data from http resource: ?", resourcePath);
        }
    }
}
