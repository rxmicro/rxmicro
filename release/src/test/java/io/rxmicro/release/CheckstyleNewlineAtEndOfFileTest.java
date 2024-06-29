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

package io.rxmicro.release;

import io.rxmicro.common.model.StringIterator;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.release.Utils.getRootDirectory;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author nedis
 * @since 0.8
 */
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
final class CheckstyleNewlineAtEndOfFileTest {

    private static final Set<String> IGNORE_DIRECTORIES = Set.of(
            "target", ".git", ".idea", "src/test/resources"
    );

    private static final Set<String> IGNORE_FILE_EXTENSIONS = Set.of(
            "iml"
    );

    private static final int CAPACITY = 150;

    @Test
    void verify() throws IOException {
        final Set<String> missingNewLineAtTheEndOfFiles = new HashSet<>();
        final Set<String> redundantNewLineAtTheEndOfFiles = new HashSet<>();
        Files.walkFileTree(getRootDirectory().toPath(), new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult preVisitDirectory(final Path dir,
                                                     final BasicFileAttributes attrs) {
                if (IGNORE_DIRECTORIES.stream().anyMatch(dirFragment -> dir.toAbsolutePath().toString().contains(dirFragment))) {
                    return FileVisitResult.SKIP_SUBTREE;
                } else {
                    return FileVisitResult.CONTINUE;
                }
            }

            @Override
            public FileVisitResult visitFile(final Path file,
                                             final BasicFileAttributes attrs) throws IOException {
                if (IGNORE_FILE_EXTENSIONS.stream().noneMatch(ext -> file.getFileName().toString().endsWith("." + ext))) {
                    try {
                        final String content = Files.readString(file);
                        final StringIterator iterator = new StringIterator(content);
                        boolean foundNewLine = false;
                        while (iterator.previous()) {
                            final char ch = iterator.getCurrent();
                            if (ch != '\r') {
                                if (ch == '\n') {
                                    if (foundNewLine) {
                                        redundantNewLineAtTheEndOfFiles.add(file.toAbsolutePath().toString());
                                        break;
                                    }
                                    foundNewLine = true;
                                } else {
                                    if (!foundNewLine) {
                                        missingNewLineAtTheEndOfFiles.add(file.toAbsolutePath().toString());
                                    }
                                    break;
                                }
                            }
                        }
                    } catch (final IOException exception) {
                        throw new IOException(
                                format("Can't read string from '?' file: ?", file.toAbsolutePath(), exception.getMessage()),
                                exception
                        );
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        boolean hasError = false;
        final StringBuilder stringBuilder = new StringBuilder(CAPACITY);
        if (!missingNewLineAtTheEndOfFiles.isEmpty()) {
            hasError = true;
            stringBuilder
                    .append("The following resources do not contain required '\\n' at the end of file:\n\t")
                    .append(String.join("\n\t", missingNewLineAtTheEndOfFiles))
                    .append('\n');
        }
        if (!redundantNewLineAtTheEndOfFiles.isEmpty()) {
            hasError = true;
            stringBuilder
                    .append("The following resources contain redundant '\\n' at the end of file:\n\t")
                    .append(String.join("\n\t", redundantNewLineAtTheEndOfFiles))
                    .append('\n');
        }
        if (hasError) {
            fail(stringBuilder.toString());
        }
    }
}
