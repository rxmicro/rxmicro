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

package io.rxmicro.rest.server.detail.component;

import io.rxmicro.config.ConfigException;
import io.rxmicro.logger.Logger;
import io.rxmicro.logger.LoggerFactory;
import io.rxmicro.rest.model.HttpMethod;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.detail.model.HttpRequest;
import io.rxmicro.rest.server.detail.model.HttpResponse;
import io.rxmicro.rest.server.detail.model.Registration;
import io.rxmicro.rest.server.detail.model.mapping.AnyPathFragmentRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.ExactUrlRequestMappingRule;
import io.rxmicro.rest.server.detail.model.mapping.resource.UrlPathMatchTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

import static io.rxmicro.common.local.RxMicroEnvironment.isRuntimeStrictModeEnabled;
import static io.rxmicro.common.util.ExCollections.unmodifiableList;
import static io.rxmicro.common.util.Formats.format;
import static io.rxmicro.config.Configs.getConfig;
import static io.rxmicro.http.HttpStandardHeaderNames.IF_MODIFIED_SINCE;
import static io.rxmicro.reflection.Reflections.getValidatedMethodName;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;
import static java.time.temporal.ChronoUnit.SECONDS;
import static java.util.concurrent.CompletableFuture.completedStage;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

/**
 * @author nedis
 * @since 0.8
 */
public final class StaticResourceRestController extends AbstractRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticResourceRestController.class);

    // 'getValidatedMethodName' method ensures that HttpServerConfig class contains getRootDirectory method!
    private static final String ROOT_DIRECTORY_PREFIX = format(
            "?.?() + ",
            HttpServerConfig.class.getSimpleName(),
            getValidatedMethodName(HttpServerConfig.class, "getRootDirectory")
    );

    private static final int NOT_MODIFIED = 304;

    private static final int FORBIDDEN = 403;

    private static final int NOT_FOUND = 404;

    private final HttpServerConfig config;

    private final Map<UrlPathMatchTemplate, String> customTemplateResourceMapping;

    private final Map<String, String> customExactResourceMapping;

    private List<UrlPathMatchTemplate> resourcePathTemplates;

    private List<String> exactResourcePaths;

    public StaticResourceRestController(final List<UrlPathMatchTemplate> resourcePathTemplates,
                                        final Map<UrlPathMatchTemplate, String> customTemplateResourceMapping,
                                        final List<String> exactResourcePaths,
                                        final Map<String, String> customExactResourceMapping) {
        this.config = getConfig(HttpServerConfig.class);
        if (isRuntimeStrictModeEnabled()) {
            validatePaths(customTemplateResourceMapping, exactResourcePaths, customExactResourceMapping);
        }
        this.resourcePathTemplates = unmodifiableList(resourcePathTemplates);
        this.customTemplateResourceMapping = Map.copyOf(customTemplateResourceMapping);
        this.exactResourcePaths = unmodifiableList(exactResourcePaths);
        this.customExactResourceMapping = Map.copyOf(customExactResourceMapping);
    }

    private void validatePaths(final Map<UrlPathMatchTemplate, String> customTemplateResourceMapping,
                               final List<String> exactResourcePaths,
                               final Map<String, String> customExactResourceMapping) {
        Stream
                .of(
                        customTemplateResourceMapping.values().stream(),
                        exactResourcePaths.stream(),
                        customExactResourceMapping.values().stream()
                )
                .flatMap(identity())
                .forEach(path -> {
                    try {
                        final Path absolutePath = config.getRootDirectory().resolve(path).toAbsolutePath();
                        if (!Files.exists(absolutePath)) {
                            throw new ConfigException("'?' path not found!", absolutePath);
                        } else if (!Files.isRegularFile(absolutePath)) {
                            throw new ConfigException("'?' path is not regular file!", absolutePath);
                        }
                    } catch (final InvalidPathException exception) {
                        throw new ConfigException("'?' path is invalid: ?", path, exception.getMessage());
                    }
                });
    }

    @Override
    public void register(final RestControllerRegistrar registrar) {
        final List<Class<?>> paramTypes = List.of(PathVariableMapping.class, HttpRequest.class);
        final List<Registration> registrations = new ArrayList<>(2);
        if (!exactResourcePaths.isEmpty()) {
            registrations.add(new Registration(
                    "",
                    "handleExact",
                    paramTypes,
                    this::handleExact,
                    false,
                    exactResourcePaths.stream()
                            .map(path -> new ExactUrlRequestMappingRule(HttpMethod.GET.name(), path, false))
                            .collect(toList())
            ));
            exactResourcePaths = null;
        }
        if (!resourcePathTemplates.isEmpty()) {
            registrations.add(new Registration(
                    "",
                    "handleTemplate",
                    paramTypes,
                    this::handleTemplate,
                    false,
                    resourcePathTemplates.stream()
                            .map(path -> new AnyPathFragmentRequestMappingRule(HttpMethod.GET.name(), path, false))
                            .collect(toList())
            ));
            resourcePathTemplates = null;
        }
        registrar.register(this, registrations.toArray(new Registration[0]));
    }

    @Override
    public Class<?> getRestControllerClass() {
        return StaticResourceRestController.class;
    }

    @SuppressWarnings({"unused", "ReplaceNullCheck"})
    private CompletionStage<HttpResponse> handleExact(final PathVariableMapping pathVariableMapping,
                                                      final HttpRequest request) {
        final String filePath = customExactResourceMapping.get(request.getUri());
        if (filePath != null) {
            return sendFile(request, filePath);
        } else {
            // Relative path must not be started with '/'
            return sendFile(request, request.getUri().substring(1));
        }
    }

    @SuppressWarnings("unused")
    private CompletionStage<HttpResponse> handleTemplate(final PathVariableMapping pathVariableMapping,
                                                         final HttpRequest request) {
        for (final Map.Entry<UrlPathMatchTemplate, String> entry : customTemplateResourceMapping.entrySet()) {
            if (entry.getKey().match(request)) {
                return sendFile(request, entry.getValue());
            }
        }
        // Relative path must not be started with '/'
        return sendFile(request, request.getUri().substring(1));
    }

    private CompletionStage<HttpResponse> sendFile(final HttpRequest request,
                                                   final String relativeFilePath) {
        try {
            final Path filePath = config.getRootDirectory().resolve(relativeFilePath);
            return sendFile(request, filePath);
        } catch (final InvalidPathException exception) {
            LOGGER.error(request, exception, "Resource path '?' is invalid: ?", relativeFilePath, exception.getMessage());
            final String message = format("Resource path '?' is invalid: ?!", request.getUri(), exception.getMessage());
            return completedStage(httpErrorResponseBodyBuilder.build(httpResponseBuilder, NOT_FOUND, message));
        }
    }

    private CompletionStage<HttpResponse> sendFile(final HttpRequest request,
                                                   final Path filePath) {
        final Path absolutePath = filePath.toAbsolutePath();
        if (!Files.exists(absolutePath)) {
            LOGGER.error(request, "File not found: '?'", absolutePath);
            final String message = format("Resource '??' not found!", ROOT_DIRECTORY_PREFIX, request.getUri());
            return completedStage(httpErrorResponseBodyBuilder.build(httpResponseBuilder, NOT_FOUND, message));
        } else if (Files.isDirectory(absolutePath)) {
            LOGGER.error(request, "'?' resource is directory, but must be a file!", absolutePath);
            final String message = format("Resource '??' is directory!", ROOT_DIRECTORY_PREFIX, request.getUri());
            return completedStage(httpErrorResponseBodyBuilder.build(httpResponseBuilder, NOT_FOUND, message));
        } else if (!Files.isRegularFile(absolutePath)) {
            LOGGER.error(request, "'?' resource is not file, but must be a file!", absolutePath);
            final String message = format("Resource '??' is not file!", ROOT_DIRECTORY_PREFIX, request.getUri());
            return completedStage(httpErrorResponseBodyBuilder.build(httpResponseBuilder, FORBIDDEN, message));
        } else if (isNotModified(request, absolutePath)) {
            LOGGER.debug(request, "Sending '?' status instead of file content for '?' url", NOT_MODIFIED, request.getUri());
            return completedStage(
                    httpResponseBuilder.build().setStatus(NOT_MODIFIED)
            );
        } else {
            LOGGER.debug(request, "Sending '?' file for '?' url", absolutePath, request.getUri());
            return completedStage(
                    httpResponseBuilder.build().sendFile(absolutePath)
            );
        }
    }

    private boolean isNotModified(final HttpRequest request,
                                  final Path filePath) {
        final String ifModifiedSince = request.getHeaders().getValue(IF_MODIFIED_SINCE);
        if (ifModifiedSince != null) {
            try {
                final Instant ifModifiedSinceInstant = RFC_1123_DATE_TIME.parse(ifModifiedSince, Instant::from);
                final Instant lastModifiedInstant = Files.getLastModifiedTime(filePath).toInstant().truncatedTo(SECONDS);
                final boolean result = ifModifiedSinceInstant.compareTo(lastModifiedInstant) == 0;
                if (!result) {
                    LOGGER.debug(
                            request,
                            "isNotModified returns false, because expected value (?) doe not equal to actual one (?)",
                            ifModifiedSinceInstant, lastModifiedInstant
                    );
                }
                return result;
            } catch (final IOException exception) {
                LOGGER.warn(
                        request, exception,
                        "isNotModified returns false, because can't read lastModifiedTime for the file: '?': ?",
                        filePath.toAbsolutePath(), exception.getMessage()
                );
                return false;
            } catch (final DateTimeParseException exception) {
                LOGGER.warn(
                        request, exception,
                        "isNotModified returns false, because can't parse '?' HTTP header: ?",
                        IF_MODIFIED_SINCE, exception.getMessage()
                );
                return false;
            }
        } else {
            LOGGER.debug(request, "isNotModified returns false, because '?' HTTP header not found!", IF_MODIFIED_SINCE);
            return false;
        }
    }
}
