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

package io.rxmicro.rest.server;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.config.SingletonConfigClass;
import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;
import io.rxmicro.resource.model.ResourceException;
import io.rxmicro.validation.constraint.ExistingDirectory;
import io.rxmicro.validation.constraint.Min;

import java.nio.file.Path;
import java.time.Duration;

import static io.rxmicro.http.ProtocolSchema.HTTP;
import static io.rxmicro.resource.Paths.CURRENT_DIRECTORY;
import static io.rxmicro.resource.Paths.createPath;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Allows configuring HTTP server options.
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
@SingletonConfigClass
public final class HttpServerConfig extends HttpConfig {

    /**
     * Default file content cache duration.
     */
    public static final Duration DEFAULT_FILE_CONTENT_CACHE_DURATION = Duration.of(365, DAYS);

    /**
     * Default server port.
     */
    public static final int DEFAULT_SERVER_PORT = 8080;

    private boolean startTimeTrackerEnabled = true;

    @ExistingDirectory
    private Path rootDirectory = createPath(CURRENT_DIRECTORY);

    @Min("PT0S")
    private Duration fileContentCacheDuration = DEFAULT_FILE_CONTENT_CACHE_DURATION;

    /**
     * Creates a HTTP server config instance with default settings.
     */
    public HttpServerConfig() {
        super(Config.getDefaultNameSpace(HttpServerConfig.class), HTTP, "0.0.0.0", DEFAULT_SERVER_PORT);
    }

    /**
     * Returns {@code true} if start time tracking is enabled.
     *
     * @return {@code true} if start time tracking is enabled
     */
    public boolean isStartTimeTrackerEnabled() {
        return startTimeTrackerEnabled;
    }

    /**
     * Allows enabling/disabling a start time tracking.
     *
     * <p>
     * If enable, the RxMicro framework displays a time which HTTP server spent during start.
     *
     * @param startTimeTrackerEnabled enabling/disabling flag
     * @return the reference to this {@link HttpServerConfig} instance
     */
    @BuilderMethod
    public HttpServerConfig setStartTimeTrackerEnabled(final boolean startTimeTrackerEnabled) {
        this.startTimeTrackerEnabled = startTimeTrackerEnabled;
        return this;
    }

    /**
     * Returns the root directory for all relative static resources.
     *
     * <p>
     * By default the current directory is used.
     *
     * @return the root directory for all relative static resources.
     */
    public Path getRootDirectory() {
        return rootDirectory;
    }

    /**
     * Sets the custom root directory.
     *
     * @param rootDirectoryPath the provided root directory path
     * @return the reference to this {@link HttpServerConfig} instance
     * @throws ResourceException if the provided {@link Path} instance not found or is not directory.
     */
    @BuilderMethod
    public HttpServerConfig setRootDirectory(final String rootDirectoryPath) {
        final Path path = createPath(rootDirectoryPath);
        this.rootDirectory = ensureValid(path);
        return this;
    }

    /**
     * Sets the custom root directory.
     *
     * @param rootDirectoryPath the provided root directory path
     * @return the reference to this {@link HttpServerConfig} instance
     * @throws ResourceException if the provided {@link Path} instance not found or is not directory.
     */
    @BuilderMethod
    public HttpServerConfig setRootDirectory(final Path rootDirectoryPath) {
        this.rootDirectory = ensureValid(rootDirectoryPath);
        return this;
    }

    /**
     * Returns the file content cache duration.
     *
     * @return the file content cache duration.
     */
    public Duration getFileContentCacheDuration() {
        return fileContentCacheDuration;
    }

    /**
     * Sets the custom file content cache duration.
     *
     * @param fileContentCacheDuration the custom file content cache duration.
     * @return the reference to this {@link HttpServerConfig} instance
     */
    @BuilderMethod
    public HttpServerConfig setFileContentCacheDuration(final Duration fileContentCacheDuration) {
        this.fileContentCacheDuration = ensureValid(fileContentCacheDuration);
        return this;
    }

    @Override
    public HttpServerConfig setSchema(final ProtocolSchema schema) {
        return (HttpServerConfig) super.setSchema(schema);
    }

    @Override
    public HttpServerConfig setHost(final String host) {
        return (HttpServerConfig) super.setHost(host);
    }

    @Override
    public HttpServerConfig setPort(final Integer port) {
        return (HttpServerConfig) super.setPort(port);
    }

    @Override
    public HttpServerConfig setConnectionString(final String connectionString) {
        return (HttpServerConfig) super.setConnectionString(connectionString);
    }

    @Override
    public String toString() {
        return "HttpServerConfig{" +
                "connectionString='" + getConnectionString() +
                ", startTimeTrackerEnabled=" + startTimeTrackerEnabled +
                ", rootDirectory=" + rootDirectory +
                ", fileContentCacheDuration=" + fileContentCacheDuration +
                '}';
    }
}
