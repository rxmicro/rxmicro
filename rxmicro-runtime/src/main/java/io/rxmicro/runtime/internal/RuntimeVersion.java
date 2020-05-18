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

package io.rxmicro.runtime.internal;

import io.rxmicro.common.local.StartTimeStampHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author nedis
 * @since 0.1
 */
public final class RuntimeVersion {

    private static final String RX_MICRO_VERSION;

    private static final String RX_MICRO_VERSION_PROPERTY = "rxmicro.version";

    static {
        StartTimeStampHelper.init();
        RX_MICRO_VERSION = resolveVersion();
        System.setProperty(RX_MICRO_VERSION_PROPERTY, RX_MICRO_VERSION);
    }

    @SuppressWarnings("EmptyMethod")
    public static void setRxMicroVersion() {
        //do nothing. All required logic done at the static section
    }

    public static String getRxMicroVersion() {
        return RX_MICRO_VERSION;
    }

    private static String resolveVersion() {
        final Package pkg = RuntimeVersion.class.getPackage();
        final String implVersion = pkg.getImplementationVersion();
        if (implVersion != null) {
            return implVersion;
        } else {
            return tryReadPomProperties();
        }
    }

    private static String tryReadPomProperties() {
        final String unresolved = "unresolved";
        try {
            final Properties properties = new Properties();
            try (InputStream in = RuntimeVersion.class.getClassLoader()
                    .getResourceAsStream("META-INF/maven/io.rxmicro/rxmicro-runtime/pom.properties")) {
                if (in != null) {
                    properties.load(in);
                    return properties.getProperty("version", unresolved);
                } else {
                    return unresolved;
                }
            }
        } catch (final IOException ignore) {
            return unresolved;
        }
    }

    private RuntimeVersion() {
    }
}
