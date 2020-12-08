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

import io.rxmicro.common.CheckedWrapperException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.common.util.Requires.require;

/**
 * @author nedis
 * @since 0.3
 */
public final class TestedProcessProxy extends Process {

    private final Process process;

    private final Thread outputCatcher;

    private final CompletableFuture<Process> onExit;

    public TestedProcessProxy(final Process processWithRedirectedErrorStream) {
        this.process = require(processWithRedirectedErrorStream);
        this.outputCatcher = new Thread(
                new ProcessOutputCatcher(processWithRedirectedErrorStream),
                "Process output catcher for pid=" + this.process.pid()
        );
        this.outputCatcher.start();
        this.onExit = process.onExit()
                .thenApply(interruptOutputCatcherAfterProcessExit());
    }

    private Function<Process, Process> interruptOutputCatcherAfterProcessExit() {
        return process -> {
            interruptOutputCatcher(true);
            return process;
        };
    }

    @Override
    public OutputStream getOutputStream() {
        return process.getOutputStream();
    }

    @Override
    public InputStream getInputStream() {
        throw new UnsupportedOperationException("Input stream is redirected to System.out, so this method can't be used!");
    }

    @Override
    public InputStream getErrorStream() {
        throw new UnsupportedOperationException("Error stream is redirected to System.out, so this method can't be used!");
    }

    @Override
    public int waitFor() throws InterruptedException {
        final int exitValue = process.waitFor();
        interruptOutputCatcher(true);
        return exitValue;
    }

    @Override
    public boolean waitFor(final long timeout,
                           final TimeUnit unit) throws InterruptedException {
        final boolean isProcessTerminated = process.waitFor(timeout, unit);
        if (isProcessTerminated) {
            interruptOutputCatcher(true);
        }
        return isProcessTerminated;
    }

    @Override
    public int exitValue() {
        return process.exitValue();
    }

    @Override
    public void destroy() {
        interruptOutputCatcher(false);
        process.destroy();
        waitForOutputCatcherTerminated();
    }

    @Override
    public Process destroyForcibly() {
        interruptOutputCatcher(false);
        process.destroyForcibly();
        waitForOutputCatcherTerminated();
        return this;
    }

    @Override
    public boolean supportsNormalTermination() {
        return process.supportsNormalTermination();
    }

    @Override
    public boolean isAlive() {
        return process.isAlive();
    }

    @Override
    public long pid() {
        return process.pid();
    }

    @Override
    public CompletableFuture<Process> onExit() {
        return onExit;
    }

    @Override
    public ProcessHandle toHandle() {
        return process.toHandle();
    }

    @Override
    public ProcessHandle.Info info() {
        return process.info();
    }

    @Override
    public Stream<ProcessHandle> children() {
        return process.children();
    }

    @Override
    public Stream<ProcessHandle> descendants() {
        return process.descendants();
    }

    private void interruptOutputCatcher(final boolean waitForOutputCatcherTerminated) {
        if (outputCatcher.isAlive()) {
            outputCatcher.interrupt();
            if (waitForOutputCatcherTerminated) {
                waitForOutputCatcherTerminated();
            }
        }
    }

    private void waitForOutputCatcherTerminated() {
        try {
            outputCatcher.join();
        } catch (final InterruptedException ex) {
            throw new CheckedWrapperException(ex);
        }
    }

    /**
     * @author nedis
     * @since 0.7.2
     */
    private static final class ProcessOutputCatcher implements Runnable {

        private final Process process;

        private ProcessOutputCatcher(final Process process) {
            this.process = process;
        }

        @Override
        public void run() {
            try (InputStream inputStream = process.getInputStream()) {
                inputStream.transferTo(System.out);
            } catch (final IOException | RuntimeException ex) {
                System.out.println(format(
                        "Can't display process output: process=`?`, message=?",
                        processInfoToString(process), ex.getMessage()
                ));
                ex.printStackTrace(System.out);
            }
        }

        private String processInfoToString(final Process process) {
            final StringBuilder stringBuilder = new StringBuilder()
                    .append(format("[PID=?]", process.pid()));
            final ProcessHandle.Info info = process.info();
            info.user().ifPresent(user -> stringBuilder.append(format(" {USER='?'}", user)));
            info.command().ifPresent(command -> stringBuilder.append(' ').append(command));
            info.arguments().ifPresent(args -> stringBuilder.append(' ').append(String.join(" ", args)));
            return stringBuilder.toString();
        }
    }
}
