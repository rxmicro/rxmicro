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

package io.rxmicro.annotation.processor.integration.test;

import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.TreeSet;

import static io.rxmicro.annotation.processor.integration.test.ClasspathResources.getResourcesAtTheFolderWithAllNestedOnes;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author nedis
 * @link https://rxmicro.io
 */
final class ClasspathResources_Test {

    @Test
    void getAllClasspathResourcesAtTheFolder_should_read_all_files_and_directories() {
        final Set<String> resources = getResourcesAtTheFolderWithAllNestedOnes("source-code", r -> r.endsWith(".txt"));

        assertEquals(
                new TreeSet<>(Set.of(
                        "source-code/file1.txt",
                        "source-code/file2.txt",
                        "source-code/file3.txt",
                        "source-code/directory1/file1.txt",
                        "source-code/directory1/file2.txt",
                        "source-code/directory1/file3.txt",
                        "source-code/directory1/directory2/file1.txt",
                        "source-code/directory1/directory2/file2.txt",
                        "source-code/directory1/directory2/file3.txt"
                )),
                new TreeSet<>(resources)
        );
    }
}