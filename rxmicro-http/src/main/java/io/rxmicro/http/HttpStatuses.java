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

package io.rxmicro.http;

import java.util.Map;

import static java.util.Map.entry;

/**
 * Wide used HTTP statuses and its short descriptions.
 *
 * <p>
 * Read more:
 * <a href="https://www.restapitutorial.com/httpstatuscodes.html">https://www.restapitutorial.com/httpstatuscodes.html</a>
 *
 * @author nedis
 * @see <a href="https://www.restapitutorial.com/httpstatuscodes.html">https://www.restapitutorial.com/httpstatuscodes.html</a>
 * @since 0.9
 */
public final class HttpStatuses {

    // ----------------------------------------------------- Informational -----------------------------------------------------------------

    /**
     * Continue.
     */
    public static final int CONTINUE_100 = 100;

    /**
     * Switching Protocols.
     */
    public static final int SWITCHING_PROTOCOLS_101 = 101;

    /**
     * Processing (WebDAV).
     */
    public static final int PROCESSING_102 = 102;

    /**
     * Early Hints.
     */
    public static final int EARLY_HINTS_103 = 103;

    // ----------------------------------------------------- Success -----------------------------------------------------------------------

    /**
     * OK.
     */
    public static final int OK_200 = 200;

    /**
     * Created.
     */
    public static final int CREATED_201 = 201;

    /**
     * Accepted.
     */
    public static final int ACCEPTED_202 = 202;

    /**
     * Non-Authoritative Information.
     */
    public static final int NON_AUTHORITATIVE_INFORMATION_203 = 203;

    /**
     * No Content.
     */
    public static final int NO_CONTENT_204 = 204;

    /**
     * Reset Content.
     */
    public static final int RESET_CONTENT_205 = 205;

    /**
     * Partial Content.
     */
    public static final int PARTIAL_CONTENT_206 = 206;

    /**
     * Multi-Status (WebDAV).
     */
    public static final int MULTI_STATUS_207 = 207;

    /**
     * Already Reported (WebDAV).
     */
    public static final int ALREADY_REPORTED_208 = 208;

    /**
     * IM Used.
     */
    public static final int IM_USED_226 = 226;

    // ----------------------------------------------------- Redirection -------------------------------------------------------------------

    /**
     * Multiple Choices.
     */
    public static final int MULTIPLE_CHOICES_300 = 300;

    /**
     * Moved Permanently.
     */
    public static final int MOVED_PERMANENTLY_301 = 301;

    /**
     * Moved Temporarily.
     */
    public static final int MOVED_TEMPORARY_302 = 302;

    /**
     * Found.
     */
    public static final int FOUND_302 = 302;

    /**
     * See Other.
     */
    public static final int SEE_OTHER_303 = 303;

    /**
     * Not Modified.
     */
    public static final int NOT_MODIFIED_304 = 304;

    /**
     * Use Proxy.
     */
    public static final int USE_PROXY_305 = 305;

    /**
     * Switch Proxy.
     */
    public static final int SWITCH_PROXY_306 = 306;

    /**
     * Temporary Redirect.
     */
    public static final int TEMPORARY_REDIRECT_307 = 307;

    /**
     * Permanent Redirect.
     */
    public static final int PERMANENT_REDIRECT_308 = 308;

    // ----------------------------------------------------- Client errors -----------------------------------------------------------------

    /**
     * Bad Request.
     */
    public static final int BAD_REQUEST_400 = 400;

    /**
     * Unauthorized.
     */
    public static final int UNAUTHORIZED_401 = 401;

    /**
     * Payment Required.
     */
    public static final int PAYMENT_REQUIRED_402 = 402;

    /**
     * Forbidden.
     */
    public static final int FORBIDDEN_403 = 403;

    /**
     * Not Found.
     */
    public static final int NOT_FOUND_404 = 404;

    /**
     * Method Not Allowed.
     */
    public static final int METHOD_NOT_ALLOWED_405 = 405;

    /**
     * Not Acceptable.
     */
    public static final int NOT_ACCEPTABLE_406 = 406;

    /**
     * Proxy Authentication Required.
     */
    public static final int PROXY_AUTHENTICATION_REQUIRED_407 = 407;

    /**
     * Request Timeout.
     */
    public static final int REQUEST_TIMEOUT_408 = 408;

    /**
     * Conflict.
     */
    public static final int CONFLICT_409 = 409;

    /**
     * Gone.
     */
    public static final int GONE_410 = 410;

    /**
     * Length Required.
     */
    public static final int LENGTH_REQUIRED_411 = 411;

    /**
     * Precondition Failed.
     */
    public static final int PRECONDITION_FAILED_412 = 412;

    /**
     * Payload Too Large.
     */
    public static final int PAYLOAD_TOO_LARGE_413 = 413;

    /**
     * URI Too Long.
     */
    public static final int URI_TOO_LONG_414 = 414;

    /**
     * Unsupported Media Type.
     */
    public static final int UNSUPPORTED_MEDIA_TYPE_415 = 415;

    /**
     * Range Not Satisfiable.
     */
    public static final int RANGE_NOT_SATISFIABLE_416 = 416;

    /**
     * Expectation Failed.
     */
    public static final int EXPECTATION_FAILED_417 = 417;

    /**
     * I'm a teapot (RFC 2324).
     */
    public static final int I_M_A_TEAPOT_418 = 418;

    /**
     * Authentication Timeout (not in RFC 2616).
     */
    public static final int AUTHENTICATION_TIMEOUT_419 = 419;

    /**
     * Enhance Your Calm (Twitter).
     */
    public static final int ENHANCE_YOUR_CALM_420 = 420;

    /**
     * Misdirected Request.
     */
    public static final int MISDIRECTED_REQUEST_421 = 421;

    /**
     * Unprocessable Entity (WebDAV).
     */
    public static final int UNPROCESSABLE_ENTITY_422 = 422;

    /**
     * Locked (WebDAV).
     */
    public static final int LOCKED_423 = 423;

    /**
     * Failed Dependency (WebDAV).
     */
    public static final int FAILED_DEPENDENCY_424 = 424;

    /**
     * Too Early (WebDAV).
     */
    public static final int TOO_EARLY_425 = 425;

    /**
     * Upgrade Required.
     */
    public static final int UPGRADE_REQUIRED_426 = 426;

    /**
     * Precondition Required.
     */
    public static final int PRECONDITION_REQUIRED_428 = 428;

    /**
     * Too Many Requests.
     */
    public static final int TOO_MANY_REQUESTS_429 = 429;

    /**
     * Request Header Fields Too Large.
     */
    public static final int REQUEST_HEADER_FIELDS_TOO_LARGE_431 = 431;

    /**
     * No Response (Nginx).
     */
    public static final int NO_RESPONSE_444 = 444;

    /**
     * Retry With (Microsoft).
     */
    public static final int RETRY_WITH_449 = 449;

    /**
     * Blocked by Windows Parental Controls (Microsoft).
     */
    public static final int BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS_450 = 450;

    /**
     * Unavailable For Legal Reasons.
     */
    public static final int UNAVAILABLE_FOR_LEGAL_REASONS_451 = 451;

    /**
     * Client Closed Request (Nginx).
     */
    public static final int CLIENT_CLOSED_REQUEST_499 = 499;

    // ----------------------------------------------------- Server errors -----------------------------------------------------------------

    /**
     * Internal Server Error.
     */
    public static final int INTERNAL_SERVER_ERROR_500 = 500;

    /**
     * Not Implemented.
     */
    public static final int NOT_IMPLEMENTED_501 = 501;

    /**
     * Bad Gateway.
     */
    public static final int BAD_GATEWAY_502 = 502;

    /**
     * Service Unavailable.
     */
    public static final int SERVICE_UNAVAILABLE_503 = 503;

    /**
     * Gateway Timeout.
     */
    public static final int GATEWAY_TIMEOUT_504 = 504;

    /**
     * HTTP Version Not Supported.
     */
    public static final int HTTP_VERSION_NOT_SUPPORTED_505 = 505;

    /**
     * Variant Also Negotiates.
     */
    public static final int VARIANT_ALSO_NEGOTIATES_506 = 506;

    /**
     * Insufficient Storage.
     */
    public static final int INSUFFICIENT_STORAGE_507 = 507;

    /**
     * Loop Detected.
     */
    public static final int LOOP_DETECTED_508 = 508;

    /**
     * Bandwidth Limit Exceeded.
     */
    public static final int BANDWIDTH_LIMIT_EXCEEDED_509 = 509;

    /**
     * Not Extended.
     */
    public static final int NOT_EXTENDED_510 = 510;

    /**
     * Network Authentication Required.
     */
    public static final int NETWORK_AUTHENTICATION_REQUIRED_511 = 511;

    /**
     * Unknown Error.
     */
    public static final int UNKNOWN_ERROR_520 = 520;

    /**
     * Web Server Is Down.
     */
    public static final int WEB_SERVER_IS_DOWN_521 = 521;

    /**
     * Connection Timed Out.
     */
    public static final int CONNECTION_TIMED_OUT_522 = 522;

    /**
     * Origin Is Unreachable.
     */
    public static final int ORIGIN_IS_UNREACHABLE_523 = 523;

    /**
     * A Timeout Occurred.
     */
    public static final int A_TIMEOUT_OCCURRED_524 = 524;

    /**
     * SSL Handshake Failed.
     */
    public static final int SSL_HANDSHAKE_FAILED_525 = 525;

    /**
     * Invalid SSL Certificate.
     */
    public static final int INVALID_SSL_CERTIFICATE_526 = 526;

    /**
     * Network read timeout error (Proxy).
     */
    public static final int NETWORK_READ_TIMEOUT_ERROR_598 = 598;

    /**
     * Network connect timeout error (Proxy).
     */
    public static final int NETWORK_CONNECT_TIMEOUT_ERROR_599 = 599;

    /**
     * Min allowed status value.
     */
    public static final int MIN_SUPPORTED_HTTP_STATUS = 100;

    /**
     * Max allowed status value.
     */
    public static final int MAX_SUPPORTED_HTTP_STATUS = 599;

    @SuppressWarnings("JavacQuirks")
    private static final Map<Integer, String> STATUES = Map.ofEntries(
            // ------------- Informational -------------
            entry(CONTINUE_100, "Continue"),
            entry(SWITCHING_PROTOCOLS_101, "Switching Protocols"),
            entry(PROCESSING_102, "Processing"),
            entry(EARLY_HINTS_103, "Early Hints"),
            // ---------------- Success ----------------
            entry(OK_200, "OK"),
            entry(CREATED_201, "Created"),
            entry(ACCEPTED_202, "Accepted"),
            entry(NON_AUTHORITATIVE_INFORMATION_203, "Non-Authoritative Information"),
            entry(NO_CONTENT_204, "No Content"),
            entry(RESET_CONTENT_205, "Reset Content"),
            entry(PARTIAL_CONTENT_206, "Partial Content"),
            entry(MULTI_STATUS_207, "Multi-Status"),
            entry(ALREADY_REPORTED_208, "Already Reported"),
            entry(IM_USED_226, "IM Used"),
            // -------------- Redirection --------------
            entry(MULTIPLE_CHOICES_300, "Multiple Choices"),
            entry(MOVED_PERMANENTLY_301, "Moved Permanently"),
            entry(FOUND_302, "Found"),
            entry(SEE_OTHER_303, "See Other"),
            entry(NOT_MODIFIED_304, "Not Modified"),
            entry(USE_PROXY_305, "Use Proxy"),
            entry(SWITCH_PROXY_306, "Switch Proxy"),
            entry(TEMPORARY_REDIRECT_307, "Temporary Redirect"),
            entry(PERMANENT_REDIRECT_308, "Permanent Redirect"),
            // ------------- Client errors -------------
            entry(BAD_REQUEST_400, "Bad Request"),
            entry(UNAUTHORIZED_401, "Unauthorized"),
            entry(PAYMENT_REQUIRED_402, "Payment Required"),
            entry(FORBIDDEN_403, "Forbidden"),
            entry(NOT_FOUND_404, "Not Found"),
            entry(METHOD_NOT_ALLOWED_405, "Method Not Allowed"),
            entry(NOT_ACCEPTABLE_406, "Not Acceptable"),
            entry(PROXY_AUTHENTICATION_REQUIRED_407, "Proxy Authentication Required"),
            entry(REQUEST_TIMEOUT_408, "Request Timeout"),
            entry(CONFLICT_409, "Conflict"),
            entry(GONE_410, "Gone"),
            entry(LENGTH_REQUIRED_411, "Length Required"),
            entry(PRECONDITION_FAILED_412, "Precondition Failed"),
            entry(PAYLOAD_TOO_LARGE_413, "Payload Too Large"),
            entry(URI_TOO_LONG_414, "URI Too Long"),
            entry(UNSUPPORTED_MEDIA_TYPE_415, "Unsupported Media Type"),
            entry(RANGE_NOT_SATISFIABLE_416, "Range Not Satisfiable"),
            entry(EXPECTATION_FAILED_417, "Expectation Failed"),
            entry(I_M_A_TEAPOT_418, "I'm a teapot"),
            entry(AUTHENTICATION_TIMEOUT_419, "Authentication Timeout"),
            entry(ENHANCE_YOUR_CALM_420, "Enhance Your Calm"),
            entry(MISDIRECTED_REQUEST_421, "Misdirected Request"),
            entry(UNPROCESSABLE_ENTITY_422, "Unprocessable Entity"),
            entry(LOCKED_423, "Locked"),
            entry(FAILED_DEPENDENCY_424, "Failed Dependency"),
            entry(TOO_EARLY_425, "Too Early"),
            entry(UPGRADE_REQUIRED_426, "Upgrade Required"),
            entry(PRECONDITION_REQUIRED_428, "Precondition Required"),
            entry(TOO_MANY_REQUESTS_429, "Too Many Requests"),
            entry(REQUEST_HEADER_FIELDS_TOO_LARGE_431, "Request Header Fields Too Large"),
            entry(NO_RESPONSE_444, "No Response"),
            entry(RETRY_WITH_449, "Retry With"),
            entry(BLOCKED_BY_WINDOWS_PARENTAL_CONTROLS_450, "Blocked by Windows Parental Controls"),
            entry(UNAVAILABLE_FOR_LEGAL_REASONS_451, "Unavailable For Legal Reasons"),
            entry(CLIENT_CLOSED_REQUEST_499, "Client Closed Request"),
            // ------------- Server errors -------------
            entry(INTERNAL_SERVER_ERROR_500, "Internal Server Error"),
            entry(NOT_IMPLEMENTED_501, "Not Implemented"),
            entry(BAD_GATEWAY_502, "Bad Gateway"),
            entry(SERVICE_UNAVAILABLE_503, "Service Unavailable"),
            entry(GATEWAY_TIMEOUT_504, "Gateway Timeout"),
            entry(HTTP_VERSION_NOT_SUPPORTED_505, "HTTP Version Not Supported"),
            entry(VARIANT_ALSO_NEGOTIATES_506, "Variant Also Negotiates"),
            entry(INSUFFICIENT_STORAGE_507, "Insufficient Storage"),
            entry(LOOP_DETECTED_508, "Loop Detected"),
            entry(BANDWIDTH_LIMIT_EXCEEDED_509, "Bandwidth Limit Exceeded"),
            entry(NOT_EXTENDED_510, "Not Extended"),
            entry(NETWORK_AUTHENTICATION_REQUIRED_511, "Network Authentication Required"),
            entry(UNKNOWN_ERROR_520, "Unknown Error"),
            entry(WEB_SERVER_IS_DOWN_521, "Web Server Is Down"),
            entry(CONNECTION_TIMED_OUT_522, "Connection Timed Out"),
            entry(ORIGIN_IS_UNREACHABLE_523, "Origin Is Unreachable"),
            entry(A_TIMEOUT_OCCURRED_524, "A Timeout Occurred"),
            entry(SSL_HANDSHAKE_FAILED_525, "SSL Handshake Failed"),
            entry(INVALID_SSL_CERTIFICATE_526, "Invalid SSL Certificate"),
            entry(NETWORK_READ_TIMEOUT_ERROR_598, "Network read timeout error"),
            entry(NETWORK_CONNECT_TIMEOUT_ERROR_599, "Network connect timeout error")
    );

    /**
     * Returns the short description for the specified status code.
     *
     * @param status the specified status code
     * @return the short description for the specified status code or
     * <code>`Unofficial code: ${statusCode}`</code> if the short description not defined
     * @throws IllegalArgumentException if the specified status code is not in range [100-599]
     */
    public static String getErrorMessage(final int status) {
        if (status < MIN_SUPPORTED_HTTP_STATUS || status > MAX_SUPPORTED_HTTP_STATUS) {
            throw new IllegalArgumentException("Invalid status code: " + status);
        } else {
            return STATUES.getOrDefault(status, "Unofficial code: " + status);
        }
    }

    private HttpStatuses() {
    }
}
