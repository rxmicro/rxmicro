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

package io.rxmicro.test.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author nedis
 * @link http://rxmicro.io
 * @since 0.3
 */
public final class TestedProcessProxy extends Process {

    private final Process process;

    private final Thread outputCatcher;

    public TestedProcessProxy(final Process process) {
        this.process = process;
        this.outputCatcher = new Thread(() -> {
            try {
                process.getInputStream().transferTo(System.out);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }, "Process output catcher");
        this.outputCatcher.start();
    }

    @Override
    public OutputStream getOutputStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getInputStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public InputStream getErrorStream() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int waitFor() throws InterruptedException {
        return process.waitFor();
    }

    @Override
    public int exitValue() {
        return process.exitValue();
    }

    @Override
    public void destroy() {
        process.destroy();
        outputCatcher.interrupt();
    }
}
