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

package io.rxmicro.http.server;

import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.http.HttpConfig;
import io.rxmicro.http.ProtocolSchema;

import java.nio.file.Path;
import java.time.Duration;

import static io.rxmicro.files.Paths.CURRENT_DIRECTORY;
import static io.rxmicro.files.Paths.createPath;
import static io.rxmicro.files.Paths.validateDirectory;
import static io.rxmicro.http.ProtocolSchema.HTTP;
import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Allows configuring HTTP server options.
 *
 * @author nedis
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class HttpServerConfig extends HttpConfig {

    /**
     * Default HTTP port.
     */
    public static final int DEFAULT_HTTP_PORT = 8080;

    /**
     * Default file content cache duration.
     */
    public static final Duration DEFAULT_FILE_CONTENT_CACHE_DURATION = Duration.of(365, DAYS);

    private boolean startTimeTrackerEnabled;

    private Path rootDirectory;

    private Duration fileContentCacheDuration;

    /**
     * Creates a HTTP server config instance with default settings.
     */
    public HttpServerConfig() {
        setSchema(HTTP);
        setHost("0.0.0.0");
        setPort(DEFAULT_HTTP_PORT);
        this.startTimeTrackerEnabled = true;
        this.rootDirectory = createPath(CURRENT_DIRECTORY);
        this.fileContentCacheDuration = DEFAULT_FILE_CONTENT_CACHE_DURATION;
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
     * @throws io.rxmicro.files.ResourceException if the provided {@link Path} instance not found or is not directory.
     */
    @BuilderMethod
    public HttpServerConfig setRootDirectory(final String rootDirectoryPath) {
        final Path path = createPath(rootDirectoryPath);
        validateDirectory(path);
        this.rootDirectory = path;
        return this;
    }

    /**
     * Sets the custom root directory.
     *
     * @param rootDirectoryPath the provided root directory path
     * @return the reference to this {@link HttpServerConfig} instance
     * @throws io.rxmicro.files.ResourceException if the provided {@link Path} instance not found or is not directory.
     */
    @BuilderMethod
    public HttpServerConfig setRootDirectory(final Path rootDirectoryPath) {
        validateDirectory(rootDirectoryPath);
        this.rootDirectory = rootDirectoryPath;
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
        this.fileContentCacheDuration = fileContentCacheDuration;
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
    public HttpServerConfig setPort(final int port) {
        return (HttpServerConfig) super.setPort(port);
    }

    @Override
    public HttpServerConfig setConnectionString(final String connectionString) {
        return (HttpServerConfig) super.setConnectionString(connectionString);
    }
}
