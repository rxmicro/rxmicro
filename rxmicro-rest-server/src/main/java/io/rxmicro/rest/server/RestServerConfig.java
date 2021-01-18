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
import io.rxmicro.config.ConfigException;
import io.rxmicro.config.SingletonConfigClass;
import io.rxmicro.rest.server.feature.RequestIdGenerator;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static io.rxmicro.common.util.ExCollections.unmodifiableOrderedSet;
import static io.rxmicro.common.util.Requires.require;
import static io.rxmicro.rest.server.PredefinedRequestIdGeneratorProvider.DEFAULT_96_BIT;

/**
 * Allows configuring a REST server options.
 *
 * @author nedis
 * @see RequestIdGeneratorProvider
 * @since 0.1
 */
@SuppressWarnings("UnusedReturnValue")
@SingletonConfigClass
public class RestServerConfig extends Config {

    /**
     * Default "Handler not found" error status code.
     */
    public static final int DEFAULT_HANDLER_NOT_FOUND_ERROR_STATUS_CODE = 400;

    /**
     * Default "CORS not allowed" error status code.
     *
     * @see <a href="https://fetch.spec.whatwg.org/#cors-protocol">https://fetch.spec.whatwg.org/#cors-protocol</a>
     */
    public static final int DEFAULT_CORS_NOT_ALLOWED_ERROR_STATUS_CODE = 200;

    /**
     * Default timeout used to verify that the next request id generation method is not blocked by operation system.
     *
     * @see RequestIdGeneratorProvider#getRequestIdGenerator(RestServerConfig)
     * @see io.rxmicro.rest.server.RequestIdGeneratorProvider.CurrentRequestIdGeneratorCantBeUsedException
     */
    public static final Duration DEFAULT_REQUEST_ID_GENERATOR_INIT_TIMEOUT = Duration.ofMillis(3_000);

    private int handlerNotFoundErrorStatusCode = DEFAULT_HANDLER_NOT_FOUND_ERROR_STATUS_CODE;

    private String handlerNotFoundErrorMessage = "Handler not found";

    private int corsNotAllowedErrorStatusCode = DEFAULT_CORS_NOT_ALLOWED_ERROR_STATUS_CODE;

    private String corsNotAllowedErrorMessage = "CORS not allowed";

    private boolean humanReadableOutput;

    private boolean hideInternalErrorMessage = true;

    private boolean logHttpErrorExceptions = true;

    private Set<StaticResponseHeader> staticResponseHeaders = new LinkedHashSet<>(
            List.of(
                    PredefinedStaticResponseHeader.SERVER,
                    PredefinedStaticResponseHeader.DATE
            )
    );

    private RequestIdGeneratorProvider requestIdGeneratorProvider = DEFAULT_96_BIT;

    private boolean returnGeneratedRequestId = true;

    private boolean disableLoggerMessagesForHttpHealthChecks = true;

    private Set<String> healthCheckToolAddresses = Set.of();

    private boolean showRuntimeEnv;

    private boolean useFullClassNamesForRouterMappingLogMessages;

    private boolean enableAdditionalValidations;

    private Duration requestIdGeneratorInitTimeout = DEFAULT_REQUEST_ID_GENERATOR_INIT_TIMEOUT;

    /**
     * Configures REST server for development environment.
     *
     * @param devModeEnable developer mode enable or not
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setDevelopmentMode(final boolean devModeEnable) {
        if (devModeEnable) {
            return this
                    .setHumanReadableOutput(true)
                    .setHideInternalErrorMessage(false)
                    .setEnableAdditionalValidations(true);
        } else {
            return this
                    .setHumanReadableOutput(false)
                    .setHideInternalErrorMessage(true)
                    .setEnableAdditionalValidations(false);
        }
    }

    /**
     * Returns the error status code if the RxMicro framework can't find handler for current HTTP request.
     *
     * @return the error status code if the RxMicro framework can't find handler for current HTTP request
     */
    public int getHandlerNotFoundErrorStatusCode() {
        return handlerNotFoundErrorStatusCode;
    }

    /**
     * Sets the error status code if the RxMicro framework can't find handler for current HTTP request.
     *
     * @param handlerNotFoundErrorStatusCode the custom error status code
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setHandlerNotFoundErrorStatusCode(final int handlerNotFoundErrorStatusCode) {
        this.handlerNotFoundErrorStatusCode = handlerNotFoundErrorStatusCode;
        return this;
    }

    /**
     * Returns the error message if the RxMicro framework can't find handler for current HTTP request.
     *
     * @return the error message if the RxMicro framework can't find handler for current HTTP request
     */
    public String getHandlerNotFoundErrorMessage() {
        return handlerNotFoundErrorMessage;
    }

    /**
     * Sets the error message if the RxMicro framework can't find handler for current HTTP request.
     *
     * @param handlerNotFoundErrorMessage the custom error message
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setHandlerNotFoundErrorMessage(final String handlerNotFoundErrorMessage) {
        this.handlerNotFoundErrorMessage = require(handlerNotFoundErrorMessage);
        return this;
    }

    /**
     * Returns the error status code if the RxMicro framework does not support CORS feature for the current HTTP request.
     *
     * @return the error status code if the RxMicro framework does not support CORS feature for the current HTTP request
     */
    public int getCorsNotAllowedErrorStatusCode() {
        return corsNotAllowedErrorStatusCode;
    }

    /**
     * Sets the error status code if the RxMicro framework does not support CORS feature for the current HTTP request.
     *
     * @param corsNotAllowedErrorStatusCode the custom error status code
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setCorsNotAllowedErrorStatusCode(final int corsNotAllowedErrorStatusCode) {
        this.corsNotAllowedErrorStatusCode = corsNotAllowedErrorStatusCode;
        return this;
    }

    /**
     * Returns the error message if the RxMicro framework does not support CORS feature for the current HTTP request.
     *
     * @return the error message if the RxMicro framework does not support CORS feature for the current HTTP request
     */
    public String getCorsNotAllowedErrorMessage() {
        return corsNotAllowedErrorMessage;
    }

    /**
     * Sets the error message if the RxMicro framework does not support CORS feature for the current HTTP request.
     *
     * @param corsNotAllowedErrorMessage the custom error message
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setCorsNotAllowedErrorMessage(final String corsNotAllowedErrorMessage) {
        this.corsNotAllowedErrorMessage = require(corsNotAllowedErrorMessage);
        return this;
    }

    /**
     * Returns {@code true} if the RxMicro framework returns HTTP response body in human readable format.
     *
     * @return {@code true} if the RxMicro framework returns HTTP response body in human readable format
     */
    public boolean isHumanReadableOutput() {
        return humanReadableOutput;
    }

    /**
     * Activates or disables the human readable of response.
     *
     * <p>
     * <i>It is recommended to activate this option on development environment only!</i>
     *
     * @param humanReadableOutput human readable or not
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setHumanReadableOutput(final boolean humanReadableOutput) {
        this.humanReadableOutput = humanReadableOutput;
        return this;
    }

    /**
     * Returns {@code true} if the RxMicro framework hides a cause of internal error from client.
     *
     * @return {@code true} if the RxMicro framework hides a cause of internal error from client
     */
    public boolean isHideInternalErrorMessage() {
        return hideInternalErrorMessage;
    }

    /**
     * Activates or disables the displaying of cause of internal error if it occur.
     *
     * <p>
     * <i>It is recommended to activate this option on production environment only!</i>
     *
     * @param hideInternalErrorMessage hide internal error cause or not
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setHideInternalErrorMessage(final boolean hideInternalErrorMessage) {
        this.hideInternalErrorMessage = hideInternalErrorMessage;
        return this;
    }

    /**
     * Returns {@code true} if the RxMicro framework must log all not errors (i.e. status codes from 300 to 599).
     *
     * @return {@code true} if the RxMicro framework must log all not errors (i.e. status codes from 300 to 599)
     */
    public boolean isLogHttpErrorExceptions() {
        return logHttpErrorExceptions;
    }

    /**
     * Activates or disables the logging of client and server errors.
     *
     * @param logHttpErrorExceptions log not server errors or not
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setLogHttpErrorExceptions(final boolean logHttpErrorExceptions) {
        this.logHttpErrorExceptions = logHttpErrorExceptions;
        return this;
    }

    /**
     * Returns all defined static HTTP response headers for all HTTP request handlers.
     *
     * @return all defined static HTTP response headers for all HTTP request handlers
     */
    public Set<StaticResponseHeader> getStaticResponseHeaders() {
        return staticResponseHeaders;
    }

    /**
     * Allows customizing static HTTP response headers for all HTTP request handlers.
     *
     * @param staticResponseHeaders static response header set
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setStaticResponseHeaders(final Set<StaticResponseHeader> staticResponseHeaders) {
        this.staticResponseHeaders = require(staticResponseHeaders);
        return this;
    }

    /**
     * Allows customizing static HTTP response headers for all HTTP request handlers.
     *
     * @param staticResponseHeader a new static response header
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig addStaticResponseHeader(final StaticResponseHeader staticResponseHeader) {
        this.staticResponseHeaders.add(require(staticResponseHeader));
        return this;
    }

    /**
     * Returns the configured {@link RequestIdGenerator} instance.
     *
     * @return the configured {@link RequestIdGenerator} instance.
     */
    public RequestIdGenerator getRequestIdGenerator() {
        return requestIdGeneratorProvider.getRequestIdGenerator(this);
    }

    /**
     * Allows changing a {@link RequestIdGeneratorProvider}.
     *
     * @param requestIdGeneratorProvider generator type
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setRequestIdGeneratorProvider(final RequestIdGeneratorProvider requestIdGeneratorProvider) {
        this.requestIdGeneratorProvider = require(requestIdGeneratorProvider);
        return this;
    }

    /**
     * Returns the request id generator initialization timeout.
     *
     * <p>
     * This timeout is used to verify that the next request id generation method is not blocked by operation system.
     * For example, if the entropy source is {@code /dev/random} on various Unix-like operating systems.
     *
     * @return the request id generator initialization timeout.
     * @see RequestIdGeneratorProvider#getRequestIdGenerator(RestServerConfig)
     * @see io.rxmicro.rest.server.RequestIdGeneratorProvider.CurrentRequestIdGeneratorCantBeUsedException
     */
    public Duration getRequestIdGeneratorInitTimeout() {
        return requestIdGeneratorInitTimeout;
    }

    /**
     * Sets the request id generator initialization timeout.
     *
     * <p>
     * This timeout is used to verify that the next request id generation method is not blocked by operation system.
     * For example, if the entropy source is {@code /dev/random} on various Unix-like operating systems.
     *
     * @param requestIdGeneratorInitTimeout new timeout
     * @return the reference to this  {@link RestServerConfig} instance
     * @see RequestIdGeneratorProvider#getRequestIdGenerator(RestServerConfig)
     * @see io.rxmicro.rest.server.RequestIdGeneratorProvider.CurrentRequestIdGeneratorCantBeUsedException
     */
    @BuilderMethod
    public RestServerConfig setRequestIdGeneratorInitTimeout(final Duration requestIdGeneratorInitTimeout) {
        this.requestIdGeneratorInitTimeout = requestIdGeneratorInitTimeout;
        return this;
    }

    /**
     * Returns {@code true} if the RxMicro framework must returns generated request id for each HTTP request.
     *
     * @return {@code true} if the RxMicro framework must returns generated request id for each HTTP request
     */
    public boolean isReturnGeneratedRequestId() {
        return returnGeneratedRequestId;
    }

    /**
     * Activates or disables the returning of generated request id for each HTTP request.
     *
     * @param returnGeneratedRequestId return generated Request-Id o not
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setReturnGeneratedRequestId(final boolean returnGeneratedRequestId) {
        this.returnGeneratedRequestId = returnGeneratedRequestId;
        return this;
    }

    /**
     * Returns {@code true} if the RxMicro framework must disable logger for http health checks.
     *
     * @return {@code true} if the RxMicro framework must disable logger for http health checks
     */
    public boolean isDisableLoggerMessagesForHttpHealthChecks() {
        return disableLoggerMessagesForHttpHealthChecks;
    }

    /**
     * Activates or disables logger messages for http health checks.
     *
     * @param disableLoggerMessagesForHttpHealthChecks disable logger messages for http health checks or not
     * @return the reference to this  {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setDisableLoggerMessagesForHttpHealthChecks(
            final boolean disableLoggerMessagesForHttpHealthChecks) {
        this.disableLoggerMessagesForHttpHealthChecks = disableLoggerMessagesForHttpHealthChecks;
        return this;
    }

    /**
     * Returns {@code true} if the RxMicro framework must print current runtime env after HTTP server is started successful.
     *
     * @return {@code true} if the RxMicro framework must print current runtime env after HTTP server is started successful.
     */
    public boolean isShowRuntimeEnv() {
        return showRuntimeEnv;
    }

    /**
     * If this variable is set and HTTP server is started,
     * the RxMicro framework will print the short info about the current runtime: available processor cores and memory usage.
     *
     * @param showRuntimeEnv print runtime environment or not
     * @return the reference to this {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setShowRuntimeEnv(final boolean showRuntimeEnv) {
        this.showRuntimeEnv = showRuntimeEnv;
        return this;
    }

    /**
     * Returns {@code true} if rest server must use full class names for router mapping log messages.
     *
     * <p>
     * If {@code useFullClassNamesForRouterMappingLogMessages} is {@code true}, then log message will be:
     * <pre><code>
     * [DEBUG] Router : Mapped "POST '/send' onto examples.controller.PublicController.send(examples.model.SendRequest)
     * </code></pre>
     * Otherwise the log message will be:
     * <pre><code>
     * [DEBUG] Router : Mapped "POST '/send' onto PublicController.send(SendRequest)
     * </code></pre>
     *
     * @return {@code true} if rest server must use full class names for router mapping log messages.
     */
    public boolean isUseFullClassNamesForRouterMappingLogMessages() {
        return useFullClassNamesForRouterMappingLogMessages;
    }

    /**
     * Sets {@code true} if rest server must use full class names for router mapping log messages.
     *
     * <p>
     * If {@code useFullClassNamesForRouterMappingLogMessages} is {@code true}, then log message will be:
     * <pre><code>
     * [DEBUG] Router : Mapped "POST '/send' onto examples.controller.PublicController.send(examples.model.SendRequest)
     * </code></pre>
     * Otherwise the log message will be:
     * <pre><code>
     * [DEBUG] Router : Mapped "POST '/send' onto PublicController.send(SendRequest)
     * </code></pre>
     *
     * @param useFullClassNamesForRouterMappingLogMessages enable full class name for router mapping log messages.
     * @return the reference to this {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setUseFullClassNamesForRouterMappingLogMessages(final boolean useFullClassNamesForRouterMappingLogMessages) {
        this.useFullClassNamesForRouterMappingLogMessages = useFullClassNamesForRouterMappingLogMessages;
        return this;
    }

    /**
     * Returns {@code true} if additional validations are enabled.
     *
     * @return {@code true} if additional validations are enabled
     */
    public boolean isEnableAdditionalValidations() {
        return enableAdditionalValidations;
    }

    /**
     * Sets {@code true} if additional validations must be activated.
     *
     * @param enableAdditionalValidations enable additional validations or not
     * @return the reference to this {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setEnableAdditionalValidations(final boolean enableAdditionalValidations) {
        this.enableAdditionalValidations = enableAdditionalValidations;
        return this;
    }

    /**
     * Returns ip addresses for http health check.
     *
     * @return ip addresses for http health check
     */
    public Set<String> getHealthCheckToolAddresses() {
        return healthCheckToolAddresses;
    }

    /**
     * Sets ip addresses for http health check.
     *
     * @param healthCheckToolAddresses ip addresses for http health check.
     * @return the reference to this {@link RestServerConfig} instance
     */
    @BuilderMethod
    public RestServerConfig setHealthCheckToolAddresses(final Set<String> healthCheckToolAddresses) {
        this.healthCheckToolAddresses = unmodifiableOrderedSet(healthCheckToolAddresses);
        return this;
    }

    @Override
    protected void validate(final String namespace) {
        if (!disableLoggerMessagesForHttpHealthChecks && !healthCheckToolAddresses.isEmpty()) {
            throw new ConfigException(
                    "'healthCheckToolAddresses' parameter for ? namespace must be empty, " +
                            "because 'disableLoggerMessagesForHttpHealthChecks' is 'false'! Remove redundant parameter!"
            );
        }
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
                ", logNotServerErrors=" + logHttpErrorExceptions +
                ", staticResponseHeaders=" + staticResponseHeaders +
                ", generatorType=" + requestIdGeneratorProvider +
                ", returnGeneratedRequestId=" + returnGeneratedRequestId +
                ", disableLoggerMessagesForHttpHealthChecks=" + disableLoggerMessagesForHttpHealthChecks +
                ", showRuntimeEnv=" + showRuntimeEnv +
                ", useFullClassNamesForRouterMappingLogMessages=" + useFullClassNamesForRouterMappingLogMessages +
                ", enableAdditionalValidations=" + enableAdditionalValidations +
                ", waitingForRequestIdGeneratorInitTimeoutInMillis=" + requestIdGeneratorInitTimeout +
                ", healthCheckToolAddresses=" + healthCheckToolAddresses +
                '}';
    }
}
