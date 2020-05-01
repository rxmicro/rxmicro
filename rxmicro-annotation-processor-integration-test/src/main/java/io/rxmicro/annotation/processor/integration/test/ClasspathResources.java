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

import io.rxmicro.common.util.UrlPaths;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class ClasspathResources {

    public static Set<String> getResourcesAtTheFolderWithAllNestedOnes(final String folder,
                                                                       final Predicate<String> resourcePredicate) {
        final Set<String> resources = new HashSet<>();
        readAll(resources, folder, resourcePredicate);
        return Collections.unmodifiableSet(resources);
    }

    public static Set<String> getOnlyChildrenAtTheFolder(final String folder,
                                                         final Predicate<String> resourcePredicate) {
        final Set<String> resources = new TreeSet<>();
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(folder), UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (resourcePredicate.test(line)) {
                    resources.add(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Collections.unmodifiableSet(resources);
    }

    public static String getResourceContent(final String resource) {
        return new Scanner(require(
                getResourceAsStream(resource),
                "Resource '" + resource + "' not found"
        ), UTF_8).useDelimiter("\\A").next();
    }

    private static void readAll(final Set<String> resources,
                                final String folder,
                                final Predicate<String> resourcePredicate) {
        try (final BufferedReader br = new BufferedReader(new InputStreamReader(getResourceAsStream(folder), UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                final String resource = normalize(folder + "/" + line);
                //FIXME It is necessary to use more correct condition to define that current resource is directory
                if (line.indexOf('.') == -1) {
                    readAll(resources, resource, resourcePredicate);
                } else if (resourcePredicate.test(resource)) {
                    resources.add(resource);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream getResourceAsStream(final String resource) {
        final List<Supplier<InputStream>> inputStreamSuppliers = List.of(
                () -> Thread.currentThread().getContextClassLoader().getResourceAsStream(resource),
                () -> ClasspathResources.class.getClassLoader().getResourceAsStream(resource),
                () -> ClasspathResources.class.getResourceAsStream(resource)
        );
        for (final Supplier<InputStream> supplier : inputStreamSuppliers) {
            final InputStream in = supplier.get();
            if (in != null) {
                return in;
            }
        }
        throw new IllegalArgumentException(format("Classpath resource not found: '?'", resource));
    }

    private static String normalize(final String path) {
        return UrlPaths.normalizeUrlPath(path).substring(1);
    }

    private ClasspathResources() {
    }
}
