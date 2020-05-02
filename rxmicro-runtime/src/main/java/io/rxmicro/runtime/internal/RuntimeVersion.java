/*
 * Copyright 2019 https://rxmicro.io
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
 * @link https://rxmicro.io
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
        final Package p = RuntimeVersion.class.getPackage();
        final String implVersion = p.getImplementationVersion();
        if (implVersion != null) {
            return implVersion;
        } else {
            return tryReadPomProperties();
        }
    }

    private static String tryReadPomProperties() {
        final String unresolved = "unresolved";
        try {
            final Properties p = new Properties();
            try (final InputStream in = RuntimeVersion.class.getClassLoader()
                    .getResourceAsStream("META-INF/maven/io.rxmicro/rxmicro-runtime/pom.properties")) {
                if (in != null) {
                    p.load(in);
                    return p.getProperty("version", unresolved);
                } else {
                    return unresolved;
                }
            }
        } catch (final IOException e) {
            return unresolved;
        }
    }

    private RuntimeVersion() {
    }
}
