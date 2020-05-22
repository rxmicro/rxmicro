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

package io.rxmicro.test;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.test.internal.TestedProcessProxy;

import java.io.File;
import java.io.IOException;

import static io.rxmicro.common.util.Requires.require;

/**
 * Creates a {@link Process} with redirection of {@code stdout} and {@code stderr} to the {@link System#out}.
 *
 * <p>
 * This class can be useful for integration tests.
 *
 * @author nedis
 * @see ProcessBuilder
 * @see System#out
 * @since 0.3
 */
public final class TestedProcessBuilder {

    private String[] commandWithArgs;

    private File workingDir;

    private boolean redirectStdOutAndStdErrToSysOut = true;

    /**
     * Sets the command with arguments to run.
     *
     * @param commandWithArgs the command with arguments to run
     * @return the reference to this {@link TestedProcessBuilder} instance
     * @throws NullPointerException if any arguments is {@code null}
     */
    @BuilderMethod
    public TestedProcessBuilder setCommandWithArgs(final String... commandWithArgs) {
        this.commandWithArgs = require(commandWithArgs);
        return this;
    }

    /**
     * Sets the process working directory.
     *
     * @param workingDir the process working directory
     * @return the reference to this {@link TestedProcessBuilder} instance
     * @throws NullPointerException if the process working directory is {@code null}
     */
    @BuilderMethod
    public TestedProcessBuilder setWorkingDir(final File workingDir) {
        this.workingDir = require(workingDir);
        return this;
    }

    /**
     * Sets the stream redirection.
     *
     * @param redirectStdOutAndStdErrToSysOut redirect stream or not
     * @return the reference to this {@link TestedProcessBuilder} instance
     */
    @BuilderMethod
    public TestedProcessBuilder setRedirectStdOutAndStdErrToSysOut(final boolean redirectStdOutAndStdErrToSysOut) {
        this.redirectStdOutAndStdErrToSysOut = redirectStdOutAndStdErrToSysOut;
        return this;
    }

    /**
     * Starts the {@link Process} using provided arguments.
     *
     * @return the reference to the started {@link Process}
     * @see ProcessBuilder
     * @throws IOException if any error occurs during starting the process
     */
    public Process start() throws IOException {
        if (redirectStdOutAndStdErrToSysOut) {
            return new TestedProcessProxy(new ProcessBuilder(commandWithArgs)
                    .directory(workingDir)
                    .redirectErrorStream(true)
                    .start());
        } else {
            return new ProcessBuilder(commandWithArgs)
                    .directory(workingDir)
                    .start();
        }
    }
}
