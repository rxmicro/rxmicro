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

package io.rxmicro.annotation.processor.integration.test.internal;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.testing.compile.JavaFileObjects;

import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;

import static javax.tools.JavaFileObject.Kind.SOURCE;

/**
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnstableApiUsage")
public final class JavaSources {

    public static JavaFileObject forResource(final String resourceName) {
        if(resourceName.endsWith("moduleinfo.java")) {
            return new ModuleInfoSimpleJavaFileObject(Resources.getResource(resourceName));
        } else{
            return JavaFileObjects.forResource(resourceName);
        }
    }

    private JavaSources(){
    }

    /**
     * @author nedis
     * @link https://rxmicro.io
     * @since 0.1
     */
    private static final class ModuleInfoSimpleJavaFileObject extends SimpleJavaFileObject {

        private final ByteSource resourceByteSource;

        private ModuleInfoSimpleJavaFileObject(final URL resourceUrl) {
            super(URI.create(resourceUrl.toString().replace("moduleinfo.java", "module-info.java")), SOURCE);
            this.resourceByteSource = Resources.asByteSource(resourceUrl);
        }

        public CharSequence getCharContent(final boolean ignoreEncodingErrors) throws IOException {
            return this.resourceByteSource.asCharSource(Charset.defaultCharset()).read();
        }

        public InputStream openInputStream() throws IOException {
            return this.resourceByteSource.openStream();
        }

        public Reader openReader(final boolean ignoreEncodingErrors) throws IOException {
            return this.resourceByteSource.asCharSource(Charset.defaultCharset()).openStream();
        }
    }
}
