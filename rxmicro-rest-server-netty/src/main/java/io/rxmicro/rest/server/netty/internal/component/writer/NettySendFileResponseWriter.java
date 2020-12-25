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

package io.rxmicro.rest.server.netty.internal.component.writer;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.HttpChunkedInput;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.handler.stream.ChunkedFile;
import io.rxmicro.common.CheckedWrapperException;
import io.rxmicro.common.ImpossibleException;
import io.rxmicro.config.Secrets;
import io.rxmicro.logger.Logger;
import io.rxmicro.rest.server.HttpServerConfig;
import io.rxmicro.rest.server.netty.NettyRestServerConfig;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpRequest;
import io.rxmicro.rest.server.netty.internal.model.NettyHttpResponse;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Map;

import static io.rxmicro.files.PropertiesResources.loadProperties;
import static io.rxmicro.http.HttpStandardHeaderNames.CACHE_CONTROL;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_LENGTH;
import static io.rxmicro.http.HttpStandardHeaderNames.CONTENT_TYPE;
import static io.rxmicro.http.HttpStandardHeaderNames.EXPIRES;
import static io.rxmicro.http.HttpStandardHeaderNames.LAST_MODIFIED;
import static io.rxmicro.rest.server.netty.internal.util.IOUtils.closeQuietly;
import static java.nio.file.Files.getLastModifiedTime;
import static java.time.Instant.now;
import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME;

/**
 * @author nedis
 * @since 0.8
 */
public final class NettySendFileResponseWriter extends BaseNettyResponseWriter {

    private static final int DEFAULT_CHUNK_SIZE = 8192;

    private static final Map<String, String> MIME_TYPES = loadProperties("mime-types.properties")
            .orElseThrow(() -> {
                throw new ImpossibleException("'mime-types.properties' classpath resource must be present!");
            });

    /**
     * RFC 2046 states in section 4.5.1:
     *
     * The "octet-stream" subtype is used to indicate that a body contains arbitrary binary data.
     */
    private static final String UNKNOWN_MIME_TYPE = "application/octet-stream";

    private final boolean isSslHandlerPresent;

    private final Duration fileContentCacheDuration;

    public NettySendFileResponseWriter(final boolean isSslHandlerPresent,
                                       final Logger logger,
                                       final Secrets secrets,
                                       final HttpServerConfig httpServerConfig,
                                       final NettyRestServerConfig nettyRestServerConfig,
                                       final boolean returnGeneratedRequestId,
                                       final boolean disableLoggerMessagesForHttpHealthChecks) {
        super(logger, secrets, nettyRestServerConfig, disableLoggerMessagesForHttpHealthChecks, returnGeneratedRequestId);
        this.isSslHandlerPresent = isSslHandlerPresent;
        this.fileContentCacheDuration = httpServerConfig.getFileContentCacheDuration();
    }

    public void writeResponse(final ChannelHandlerContext ctx,
                              final NettyHttpRequest request,
                              final NettyHttpResponse response) {
        final boolean keepAlive = isKeepAlive(request);
        setCommonHeaders(request, response, keepAlive);
        final Path sendFilePath = response.getSendFilePath();
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(sendFilePath.toFile(), "r");
            final long fileLength = randomAccessFile.length();
            response.setHeader(CONTENT_LENGTH, fileLength);
            response.setHeader(CONTENT_TYPE, MIME_TYPES.getOrDefault(getPathExtension(sendFilePath), UNKNOWN_MIME_TYPE));
            setCacheHeaders(response, sendFilePath);
            ctx.write(response.toHttpResponseWithoutBody());

            writeHttpResponseBody(ctx, request, response, keepAlive, randomAccessFile, fileLength);
        } catch (final IOException exception) {
            closeQuietly(randomAccessFile);
            // Delegates exception handling to SharableNettyRequestHandler
            throw new CheckedWrapperException(exception);
        }
    }

    private String getPathExtension(final Path sendFilePath) {
        final String name = String.valueOf(sendFilePath.getFileName());
        final int index = name.lastIndexOf('.');
        return index != -1 ? name.substring(index) : name;
    }

    private void setCacheHeaders(final NettyHttpResponse response,
                                 final Path sendFilePath) throws IOException {
        response.setHeader(EXPIRES, RFC_1123_DATE_TIME.format(now().plus(fileContentCacheDuration).atOffset(UTC)));
        response.setHeader(CACHE_CONTROL, "private, max-age=" + fileContentCacheDuration.toSeconds());
        response.setHeader(LAST_MODIFIED, RFC_1123_DATE_TIME.format(getLastModifiedTime(sendFilePath).toInstant().atOffset(UTC)));
    }

    private void writeHttpResponseBody(final ChannelHandlerContext ctx,
                                       final NettyHttpRequest request,
                                       final NettyHttpResponse response,
                                       final boolean keepAlive,
                                       final RandomAccessFile randomAccessFile,
                                       final long fileLength) throws IOException {
        if (isSslHandlerPresent) {
            ctx.writeAndFlush(new HttpChunkedInput(new ChunkedFile(randomAccessFile, 0, fileLength, DEFAULT_CHUNK_SIZE)))
                    .addListener((ChannelFutureListener) future -> {
                        closeQuietly(randomAccessFile);
                        logResponse(ctx, request, response);
                        if (!keepAlive) {
                            future.channel().close();
                        }
                    });
        } else {
            ctx.write(new DefaultFileRegion(randomAccessFile.getChannel(), 0, fileLength), ctx.voidPromise());
            ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT)
                    .addListener((ChannelFutureListener) future -> {
                        closeQuietly(randomAccessFile);
                        logResponse(ctx, request, response);
                        if (!keepAlive) {
                            future.channel().close();
                        }
                    });
        }
    }
}
