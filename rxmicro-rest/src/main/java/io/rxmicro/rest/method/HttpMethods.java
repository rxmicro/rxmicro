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

package io.rxmicro.rest.method;

import java.lang.annotation.Annotation;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static java.util.Arrays.asList;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class HttpMethods {

    public static final Set<Class<? extends Annotation>> HTTP_METHOD_ANNOTATIONS = unmodifiableOrderedSet(asList(
            io.rxmicro.rest.method.GET.class,
            io.rxmicro.rest.method.POST.class,
            io.rxmicro.rest.method.PUT.class,
            io.rxmicro.rest.method.DELETE.class,
            io.rxmicro.rest.method.PATCH.class,
            io.rxmicro.rest.method.OPTIONS.class,
            io.rxmicro.rest.method.HEAD.class
    ));

    private HttpMethods() {
    }
}
