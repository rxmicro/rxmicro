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

import io.rxmicro.config.Config;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.Requires.require;

/**
 * Allows to configure REST options
 *
 * @author nedis
 * @link https://rxmicro.io
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
public class RestServerConfig extends Config {

    private int handlerNotFoundErrorStatusCode = 400;

    private String handlerNotFoundErrorMessage = "Handler not found";

    // https://fetch.spec.whatwg.org/#cors-protocol
    private int corsNotAllowedErrorStatusCode = 200;

    private String corsNotAllowedErrorMessage = "CORS not allowed";

    private boolean humanReadableOutput = false;

    private boolean hideInternalErrorMessage = true;

    private boolean logNotServerErrors = true;

    private Set<StaticResponseHeader> staticResponseHeaders = new LinkedHashSet<>(
            List.of(
                    StandardStaticResponseHeader.SERVER,
                    StandardStaticResponseHeader.DATE
            )
    );

    private RequestIdGeneratorType generatorType = RequestIdGeneratorType.FASTER_BUT_UNSAFE;

    private boolean returnGeneratedRequestId = true;

    private boolean disableLoggerMessagesForHttpHealthChecks = true;

    private boolean showRuntimeEnv = false;

    /**
     * Configures REST server for development environment
     *
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setDevelopmentMode() {
        return this
                .setHumanReadableOutput(true)
                .setHideInternalErrorMessage(false);
    }

    public int getHandlerNotFoundErrorStatusCode() {
        return handlerNotFoundErrorStatusCode;
    }

    public RestServerConfig setHandlerNotFoundErrorStatusCode(final int handlerNotFoundErrorStatusCode) {
        this.handlerNotFoundErrorStatusCode = handlerNotFoundErrorStatusCode;
        return this;
    }

    public String getHandlerNotFoundErrorMessage() {
        return handlerNotFoundErrorMessage;
    }

    public RestServerConfig setHandlerNotFoundErrorMessage(final String handlerNotFoundErrorMessage) {
        this.handlerNotFoundErrorMessage = require(handlerNotFoundErrorMessage);
        return this;
    }

    public int getCorsNotAllowedErrorStatusCode() {
        return corsNotAllowedErrorStatusCode;
    }

    public RestServerConfig setCorsNotAllowedErrorStatusCode(final int corsNotAllowedErrorStatusCode) {
        this.corsNotAllowedErrorStatusCode = corsNotAllowedErrorStatusCode;
        return this;
    }

    public String getCorsNotAllowedErrorMessage() {
        return corsNotAllowedErrorMessage;
    }

    public RestServerConfig setCorsNotAllowedErrorMessage(final String corsNotAllowedErrorMessage) {
        this.corsNotAllowedErrorMessage = require(corsNotAllowedErrorMessage);
        return this;
    }

    public boolean isHumanReadableOutput() {
        return humanReadableOutput;
    }

    /**
     * Activates or disables the human readable of response.
     * It is recommended to activate this option on development environment only
     *
     * @param humanReadableOutput human readable or not
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setHumanReadableOutput(final boolean humanReadableOutput) {
        this.humanReadableOutput = humanReadableOutput;
        return this;
    }

    public boolean isHideInternalErrorMessage() {
        return hideInternalErrorMessage;
    }

    /**
     * Activates or disables the displaying of cause of internal error if it occur.
     * It is recommended to activate this option on production environment only
     *
     * @param hideInternalErrorMessage hide internal error cause or not
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setHideInternalErrorMessage(final boolean hideInternalErrorMessage) {
        this.hideInternalErrorMessage = hideInternalErrorMessage;
        return this;
    }

    public boolean isLogNotServerErrors() {
        return logNotServerErrors;
    }

    public RestServerConfig setLogNotServerErrors(final boolean logNotServerErrors) {
        this.logNotServerErrors = logNotServerErrors;
        return this;
    }

    public Set<StaticResponseHeader> getStaticResponseHeaders() {
        return staticResponseHeaders;
    }

    /**
     *
     * @param staticResponseHeaders static response header set
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setStaticResponseHeaders(final Set<StaticResponseHeader> staticResponseHeaders) {
        this.staticResponseHeaders = require(staticResponseHeaders);
        return this;
    }

    /**
     *
     * @param staticResponseHeader a new static response header
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig addStaticResponseHeader(final StaticResponseHeader staticResponseHeader) {
        this.staticResponseHeaders.add(require(staticResponseHeader));
        return this;
    }

    public RequestIdGeneratorType getGeneratorType() {
        return generatorType;
    }

    /**
     *
     * @param generatorType generator type
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setGeneratorType(final RequestIdGeneratorType generatorType) {
        this.generatorType = require(generatorType);
        return this;
    }

    public boolean isReturnGeneratedRequestId() {
        return returnGeneratedRequestId;
    }

    /**
     *
     * @param returnGeneratedRequestId return generated Request-Id o not
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setReturnGeneratedRequestId(final boolean returnGeneratedRequestId) {
        this.returnGeneratedRequestId = returnGeneratedRequestId;
        return this;
    }

    public boolean isDisableLoggerMessagesForHttpHealthChecks() {
        return disableLoggerMessagesForHttpHealthChecks;
    }

    /**
     *
     * @param disableLoggerMessagesForHttpHealthChecks disable logger messages or not
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setDisableLoggerMessagesForHttpHealthChecks(
            final boolean disableLoggerMessagesForHttpHealthChecks) {
        this.disableLoggerMessagesForHttpHealthChecks = disableLoggerMessagesForHttpHealthChecks;
        return this;
    }

    public boolean isShowRuntimeEnv() {
        return showRuntimeEnv;
    }

    /**
     * If this variable is set and rest server is started,
     * the RxMicro framework will print the short info about the current runtime: available processor cores and memory usage.
     *
     * @param showRuntimeEnv print runtime environment or not
     * @return A reference to this {@code RestConfig}
     */
    public RestServerConfig setShowRuntimeEnv(final boolean showRuntimeEnv) {
        this.showRuntimeEnv = showRuntimeEnv;
        return this;
    }

    @Override
    public String toString() {
        return "RestServerConfig{" +
                "handlerNotFoundErrorStatusCode=" + handlerNotFoundErrorStatusCode +
                ", handlerNotFoundErrorMessage='" + handlerNotFoundErrorMessage + '\'' +
                ", corsNotAllowedErrorStatusCode=" + corsNotAllowedErrorStatusCode +
                ", corsNotAllowedErrorMessage='" + corsNotAllowedErrorMessage + '\'' +
                ", humanReadableOutput=" + humanReadableOutput +
                ", hideInternalErrorMessage=" + hideInternalErrorMessage +
                ", logNotServerErrors=" + logNotServerErrors +
                ", standardResponseHeaders=" + staticResponseHeaders +
                ", generatorType=" + generatorType +
                ", returnGeneratedRequestId=" + returnGeneratedRequestId +
                ", disableTraceLoggerMessagesForHttpHealthChecks=" + disableLoggerMessagesForHttpHealthChecks +
                '}';
    }
}
