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

package io.rxmicro.common.internal;

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
            "com.sun.",
            "java.",
            "javax.",
            "jdk.",
            "netscape.javascript.",
            "org.graalvm.",
            "org.ietf.jgss.",
            "org.jcp.",
            "org.openjdk.",
            "org.xml.sax.",
            "org.w3c.dom.",
            "sun."
    );

    private static final Set<String> EXTERNAL_LIBS_PACKAGES = Set.of(
            "autovalue.shaded.",

            "com.google.",
            "com.graphbuilder.",
            "com.microsoft.",
            "com.mongodb.",
            "com.ongres.",

            "difflib.",

            "freemarker.",

            "io.netty.",
            "io.projectreactor.",
            "io.r2dbc.",
            "io.reactivex.",

            "joptsimple.",
            "junit.",

            "net.bytebuddy.",

            "org.aopalliance.",
            "org.apache.",
            "org.apiguardian.",
            "org.bson.",
            "org.checkerframework.",
            "org.codehaus.",
            "org.dbunit.",
            "org.freemarker.",
            "org.hamcrest.",
            "org.junit.",
            "org.mockito.",
            "org.objectweb.",
            "org.objenesis.",
            "org.opentest4j.",
            "org.openxmlformats.",
            "org.reactivestreams.",
            "org.slf4j.",

            "reactor."
    );

    public static final Set<String> DENIED_PACKAGES =
            Stream.of(
                    RX_MICRO_PACKAGES.stream(),
                    JDK_PACKAGES.stream(),
                    EXTERNAL_LIBS_PACKAGES.stream()
            ).flatMap(identity()).collect(toUnmodifiableSet());

    private DeniedPackageConstants() {
    }
}
