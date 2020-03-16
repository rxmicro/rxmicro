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

package io.rxmicro.cdi.local;

import io.rxmicro.cdi.Autowired;
import io.rxmicro.cdi.Inject;
import io.rxmicro.cdi.Named;
import io.rxmicro.cdi.Qualifier;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
public final class AnnotationsSupport {

    public static final Set<Class<? extends Annotation>> INJECT_ANNOTATIONS =
            Set.of(Inject.class, Autowired.class);

    public static final Set<Class<? extends Annotation>> QUALIFIER_ANNOTATIONS =
            Set.of(Named.class, Qualifier.class);

    private AnnotationsSupport() {
    }
}
