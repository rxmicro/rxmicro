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

package io.rxmicro.annotation.processor.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.function.Function;

/**
 * @author nedis
 * @since 0.1
 */
public final class ServiceLoaderImplementations {

    public static <T> List<T> getImplementations(final Class<T> serviceInterface,
                                                 final Function<Class<T>, ServiceLoader<T>> serviceLoader) {
        final ServiceLoader<T> loader = serviceLoader.apply(serviceInterface);
        final Iterator<T> iterator = loader.iterator();
        final List<T> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }
        return result;
    }

    private ServiceLoaderImplementations() {
    }
}
