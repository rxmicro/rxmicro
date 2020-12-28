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

package io.rxmicro.rest.server.netty;

import io.netty.channel.ChannelOption;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.rxmicro.common.meta.BuilderMethod;
import io.rxmicro.config.Config;
import io.rxmicro.config.SingletonConfigClass;

import static io.netty.handler.codec.http.HttpObjectDecoder.DEFAULT_INITIAL_BUFFER_SIZE;
import static io.netty.handler.codec.http.HttpObjectDecoder.DEFAULT_MAX_CHUNK_SIZE;
import static io.netty.handler.codec.http.HttpObjectDecoder.DEFAULT_MAX_HEADER_SIZE;
import static io.netty.handler.codec.http.HttpObjectDecoder.DEFAULT_MAX_INITIAL_LINE_LENGTH;

/**
 * Allows customizing netty REST server options.
 *
 * @author nedis
 * @see ChannelOption
 * @see HttpServerCodec
 * @see HttpObjectAggregator
 * @see io.rxmicro.netty.runtime.NettyRuntimeConfig
 * @see NettyRestServerConfigCustomizer
 * @since 0.8
 */
@SuppressWarnings("UnusedReturnValue")
@SingletonConfigClass
public final class NettyRestServerConfig extends Config {

    /**
     * Default backlog size.
     */
    public static final int DEFAULT_BACKLOG_SIZE = 128;

    /**
     * Default aggregator content length in bytes.
     */
    public static final int DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES = 64 * 1024;

    private int maxHttpRequestInitialLineLength = DEFAULT_MAX_INITIAL_LINE_LENGTH;

    private int maxHttpRequestHeaderSize = DEFAULT_MAX_HEADER_SIZE;

    private int maxHttpRequestChunkSize = DEFAULT_MAX_CHUNK_SIZE;

    private boolean validateHttpRequestHeaders;

    private int initialHttpRequestBufferSize = DEFAULT_INITIAL_BUFFER_SIZE;

    private boolean allowDuplicateHttpRequestContentLengths;

    private int maxHttpRequestContentLength = DEFAULT_AGGREGATOR_CONTENT_LENGTH_IN_BYTES;

    private boolean closeOnHttpRequestContentExpectationFailed = true;

    /**
     * See {@link HttpServerCodec}.
     *
     * @return maxHttpRequestInitialLineLength
     */
    public int getMaxHttpRequestInitialLineLength() {
        return maxHttpRequestInitialLineLength;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @param maxHttpRequestInitialLineLength maxHttpRequestInitialLineLength
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setMaxHttpRequestInitialLineLength(final int maxHttpRequestInitialLineLength) {
        this.maxHttpRequestInitialLineLength = maxHttpRequestInitialLineLength;
        return this;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @return maxHttpRequestHeaderSize
     */
    public int getMaxHttpRequestHeaderSize() {
        return maxHttpRequestHeaderSize;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @param maxHttpRequestHeaderSize maxHttpRequestHeaderSize
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setMaxHttpRequestHeaderSize(final int maxHttpRequestHeaderSize) {
        this.maxHttpRequestHeaderSize = maxHttpRequestHeaderSize;
        return this;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @return maxHttpRequestChunkSize
     */
    public int getMaxHttpRequestChunkSize() {
        return maxHttpRequestChunkSize;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @param maxHttpRequestChunkSize maxHttpRequestChunkSize
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setMaxHttpRequestChunkSize(final int maxHttpRequestChunkSize) {
        this.maxHttpRequestChunkSize = maxHttpRequestChunkSize;
        return this;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @return validateHttpRequestHeaders
     */
    public boolean isValidateHttpRequestHeaders() {
        return validateHttpRequestHeaders;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @param validateHttpRequestHeaders validateHttpRequestHeaders
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setValidateHttpRequestHeaders(final boolean validateHttpRequestHeaders) {
        this.validateHttpRequestHeaders = validateHttpRequestHeaders;
        return this;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @return initialHttpRequestBufferSize
     */
    public int getInitialHttpRequestBufferSize() {
        return initialHttpRequestBufferSize;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @param initialHttpRequestBufferSize initialHttpRequestBufferSize
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setInitialHttpRequestBufferSize(final int initialHttpRequestBufferSize) {
        this.initialHttpRequestBufferSize = initialHttpRequestBufferSize;
        return this;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @return allowDuplicateHttpRequestContentLengths
     */
    public boolean isAllowDuplicateHttpRequestContentLengths() {
        return allowDuplicateHttpRequestContentLengths;
    }

    /**
     * See {@link HttpServerCodec}.
     *
     * @param allowDuplicateHttpRequestContentLengths allowDuplicateHttpRequestContentLengths
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setAllowDuplicateHttpRequestContentLengths(final boolean allowDuplicateHttpRequestContentLengths) {
        this.allowDuplicateHttpRequestContentLengths = allowDuplicateHttpRequestContentLengths;
        return this;
    }

    /**
     * See {@link HttpObjectAggregator}.
     *
     * @return maxHttpRequestContentLength
     */
    public int getMaxHttpRequestContentLength() {
        return maxHttpRequestContentLength;
    }

    /**
     * See {@link HttpObjectAggregator}.
     *
     * @param maxHttpRequestContentLength maxHttpRequestContentLength
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setMaxHttpRequestContentLength(final int maxHttpRequestContentLength) {
        this.maxHttpRequestContentLength = maxHttpRequestContentLength;
        return this;
    }

    /**
     * See {@link HttpObjectAggregator}.
     *
     * @return closeOnHttpRequestContentExpectationFailed
     */
    public boolean isCloseOnHttpRequestContentExpectationFailed() {
        return closeOnHttpRequestContentExpectationFailed;
    }

    /**
     * See {@link HttpObjectAggregator}.
     *
     * @param closeOnHttpRequestContentExpectationFailed closeOnHttpRequestContentExpectationFailed
     * @return the reference to this {@link NettyRestServerConfig} instance
     */
    @BuilderMethod
    public NettyRestServerConfig setCloseOnHttpRequestContentExpectationFailed(final boolean closeOnHttpRequestContentExpectationFailed) {
        this.closeOnHttpRequestContentExpectationFailed = closeOnHttpRequestContentExpectationFailed;
        return this;
    }

    @Override
    public String toString() {
        return "NettyRestServerConfig{" +
                "maxHttpRequestInitialLineLength=" + maxHttpRequestInitialLineLength +
                ", maxHttpRequestHeaderSize=" + maxHttpRequestHeaderSize +
                ", maxHttpRequestChunkSize=" + maxHttpRequestChunkSize +
                ", validateHttpRequestHeaders=" + validateHttpRequestHeaders +
                ", initialHttpRequestBufferSize=" + initialHttpRequestBufferSize +
                ", allowDuplicateHttpRequestContentLengths=" + allowDuplicateHttpRequestContentLengths +
                ", maxHttpRequestContentLength=" + maxHttpRequestContentLength +
                ", closeOnHttpRequestContentExpectationFailed=" + closeOnHttpRequestContentExpectationFailed +
                '}';
    }
}
