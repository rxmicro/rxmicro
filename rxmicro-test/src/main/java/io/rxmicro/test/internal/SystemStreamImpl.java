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

package io.rxmicro.test.internal;

import io.rxmicro.test.SystemErr;
import io.rxmicro.test.SystemOut;

import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author nedis
 * @since 0.1
 */
public final class SystemStreamImpl implements SystemOut, SystemErr {

    private final SpyOutputStream spyOutputStream;

    public SystemStreamImpl(final PrintStream originalStdout) {
        this.spyOutputStream = new SpyOutputStream(originalStdout);
    }

    public PrintStream getPrintStream() {
        return new PrintStream(spyOutputStream, false, UTF_8);
    }

    @Override
    public byte[] asBytes() {
        return spyOutputStream.byteArrayOutputStream.toByteArray();
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    private static final class SpyOutputStream extends FilterOutputStream {

        private final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        private SpyOutputStream(final PrintStream originalStdout) {
            super(originalStdout);
        }

        @Override
        public void write(final int byteValue) throws IOException {
            super.write(byteValue);
            byteArrayOutputStream.write(byteValue);
        }
    }
}
