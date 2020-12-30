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

package io.rxmicro.test.dbunit.internal;

import io.rxmicro.test.dbunit.internal.loader.DataSetLoader;
import io.rxmicro.test.dbunit.internal.loader.impl.FlatXmlDataSetLoader;
import io.rxmicro.test.local.InvalidTestConfigException;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.rxmicro.resource.InputStreamResources.getInputStreamResource;
import static io.rxmicro.resource.InputStreamResources.getSupportedInputStreamResources;
import static java.util.Map.entry;
import static java.util.stream.Collectors.toUnmodifiableMap;

/**
 * @author nedis
 * @since 0.7
 */
public final class DataSetLoaders {

    // Add other data set loaders here
    private static final Map<String, DataSetLoader> LOADER_MAP =
            List.of(
                    new FlatXmlDataSetLoader()
            )
                    .stream()
                    .flatMap(l -> l.getSupportedExtensions().stream().map(ex -> entry(ex, l)))
                    .collect(toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue));

    public static IDataSet loadIDataSet(final String... resources) {
        final List<IDataSet> dataSets = new ArrayList<>();
        for (final String resource : resources) {
            dataSets.add(loadIDataSet(resource));
        }
        try {
            return new CompositeDataSet(dataSets.toArray(new IDataSet[0]));
        } catch (final DataSetException ex) {
            throw new InvalidTestConfigException(
                    ex.getCause(),
                    "Can't load dataset from '?' resources: ?",
                    Arrays.toString(resources),
                    ex.getMessage()
            );
        }
    }

    public static IDataSet loadIDataSet(final String resource) {
        final String extension = getExtension(resource);
        return getInputStreamResource(resource)
                .map(r -> loadFromInputStream(resource, extension, r.getBufferedInputStream()))
                .orElseThrow(() -> {
                    throw new InvalidTestConfigException(
                            "Can't load dataset from '?' resource: Unsupported resource! Only following resources supported: ?",
                            resource,
                            getSupportedInputStreamResources()
                    );
                });
    }

    private static String getExtension(final String resource) {
        final int index = resource.lastIndexOf('.');
        if (index == -1) {
            throw new InvalidTestConfigException("Can't load dataset from '?' resource: Undefined resource type!", resource);
        }
        return resource.substring(index + 1);
    }

    private static IDataSet loadFromInputStream(final String resource,
                                                final String extension,
                                                final InputStream in) {
        return Optional.ofNullable(LOADER_MAP.get(extension))
                .map(loader -> loader.load(in))
                .orElseThrow(() -> {
                    throw new InvalidTestConfigException(
                            "Can't load dataset from '?' resource: Unsupported resource type: '?'. Only following types are supported: ?!",
                            resource, extension, LOADER_MAP.keySet()
                    );
                });
    }

    private DataSetLoaders() {
    }
}
