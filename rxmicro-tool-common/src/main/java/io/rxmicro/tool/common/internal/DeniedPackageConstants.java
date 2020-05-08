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

package io.rxmicro.tool.common.internal;

import io.rxmicro.common.RxMicroModule;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * @author nedis
 * @since 0.3
 */
public final class DeniedPackageConstants {

    private static final Set<String> RX_MICRO_PACKAGES =
            Stream.concat(
                    Arrays.stream(RxMicroModule.values())
                            .map(m -> m.getRootPackage() + "."),
                    Stream.of("rxmicro.")
            ).collect(Collectors.toSet());

    private static final Set<String> JDK_PACKAGES = Set.of(
            "java.",
            "javax.",
            "sun.",
            "jdk.",
            "com.sun.",
            "org.ietf.jgss.",
            "org.w3c.dom.",
            "org.xml.sax.",
            "org.jcp.",
            "org.graalvm.",
            "netscape.javascript.",
            "org.openjdk."
    );

    private static final Set<String> EXTERNAL_LIBS_PACKAGES = Set.of(
            "io.netty.",
            "org.slf4j.",
            "com.google.",
            "autovalue.shaded.",
            "io.projectreactor.",
            "io.reactivex.",
            "junit.",
            "org.junit.",
            "org.freemarker.",
            "freemarker.",
            "org.bson.",
            "com.mongodb.",
            "io.r2dbc.",
            "reactor.",
            "org.reactivestreams.",
            "org.apiguardian.",
            "org.opentest4j.",
            "org.aopalliance.",
            "difflib.",
            "com.ongres.",
            "net.bytebuddy.",
            "joptsimple.",
            "org.apache.",
            "org.codehaus.",
            "org.hamcrest.",
            "org.mockito.",
            "org.objenesis."
    );

    public static final Set<String> DENIED_PACKAGES =
            Stream.of(
                    RX_MICRO_PACKAGES.stream(),
                    JDK_PACKAGES.stream(),
                    EXTERNAL_LIBS_PACKAGES.stream()
            ).flatMap(identity()).collect(toUnmodifiableSet());
}
