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

import io.rxmicro.resource.model.ResourceException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static io.rxmicro.common.util.UrlPaths.normalizeUrlPath;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getResource(folder).openStream(), UTF_8))) {
            while (true) {
                final String line = br.readLine();
                if (line == null) {
                    break;
                }
                if (resourcePredicate.test(line)) {
                    resources.add(line);
                }
            }
        } catch (final IOException ex) {
            throw new ResourceException(ex, "Can't load resources from folder: ?", folder);
        }
        return Collections.unmodifiableSet(resources);
    }

    public static String getResourceContent(final String resource) {
        try (InputStream in = getResource(resource).openStream()) {
            return new Scanner(in, UTF_8).useDelimiter("\\A").next();
        } catch (final IOException ex) {
            throw new ResourceException(ex, "Can't read resource: ?", resource);
        }
    }

    private static void readAll(final Set<String> resources,
                                final String folder,
                                final Predicate<String> resourcePredicate) {
        final URL resUrl = getResource(folder);
        if (resUrl != null) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(resUrl.openStream(), UTF_8))) {
                while (true) {
                    final String line = br.readLine();
                    if (line == null) {
                        break;
                    }
                    final String resource = normalize(folder + "/" + line);
                    //If current resource is directory (i.e. resource with name without extension)
                    if (line.indexOf('.') == -1) {
                        readAll(resources, resource, resourcePredicate);
                    } else if (resourcePredicate.test(resource)) {
                        resources.add(resource);
                    }
                }
            } catch (final IOException ex) {
                throw new ResourceException(ex, "Can't read resources from folder: ?", folder);
            }
        }
    }

    private static URL getResource(final String resource) {
        final List<Supplier<URL>> resourceSuppliers = List.of(
                () -> Thread.currentThread().getContextClassLoader().getResource(resource),
                () -> ClasspathResources.class.getClassLoader().getResource(resource),
                () -> ClasspathResources.class.getResource(resource)
        );
        for (final Supplier<URL> supplier : resourceSuppliers) {
            final URL url = supplier.get();
            if (url != null) {
                return url;
            }
        }
        return null;
    }

    private static String normalize(final String path) {
        return normalizeUrlPath(path).substring(1);
    }

    private ClasspathResources() {
    }
}
