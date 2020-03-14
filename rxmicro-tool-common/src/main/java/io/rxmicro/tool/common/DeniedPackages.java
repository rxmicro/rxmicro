/*
 * Copyright (c) 2020. http://rxmicro.io
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

package io.rxmicro.tool.common;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableSet;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.1
 */
public final class DeniedPackages {

    private static final Set<String> RX_MICRO_PACKAGES = Set.of(
            "io.rxmicro.internal.",
            "io.rxmicro.annotation.",
            "io.rxmicro.cdi.",
            "io.rxmicro.common.",
            "io.rxmicro.config.",
            "io.rxmicro.data.",
            "io.rxmicro.dev.tools.",
            "io.rxmicro.documentation.",
            "io.rxmicro.exchange.",
            "io.rxmicro.files.",
            "io.rxmicro.http.",
            "io.rxmicro.json.",
            "io.rxmicro.logger.",
            "io.rxmicro.model.",
            "io.rxmicro.monitoring.",
            "io.rxmicro.netty.",
            "io.rxmicro.rest.",
            "io.rxmicro.runtime.",
            "io.rxmicro.test",
            "io.rxmicro.validation.",
            "rxmicro."
    );

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

    private static final Set<String> DENIED_PACKAGES =
            Stream.of(
                    RX_MICRO_PACKAGES.stream(),
                    JDK_PACKAGES.stream(),
                    EXTERNAL_LIBS_PACKAGES.stream()
            ).flatMap(identity()).collect(toUnmodifiableSet());

    public static boolean isRxMicroPackage(final String packageName) {
        return RX_MICRO_PACKAGES.stream().anyMatch(prefix ->
                packageName.equals(prefix.substring(0, prefix.length() - 1)) ||
                        packageName.startsWith(prefix));
    }

    public static boolean isDeniedPackage(final String packageName) {
        return DENIED_PACKAGES.stream().anyMatch(prefix ->
                packageName.equals(prefix.substring(0, prefix.length() - 1)) ||
                        packageName.startsWith(prefix));
    }

    private DeniedPackages() {
    }
}
