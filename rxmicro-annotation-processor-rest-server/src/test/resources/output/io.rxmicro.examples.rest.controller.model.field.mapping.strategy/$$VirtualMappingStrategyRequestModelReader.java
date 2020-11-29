package io.rxmicro.examples.rest.controller.model.field.mapping.strategy;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualMappingStrategyRequestModelReader extends ModelReader<$$VirtualMappingStrategyRequest> {

    @Override
    public $$VirtualMappingStrategyRequest read(final PathVariableMapping pathVariableMapping,
                                                final HttpRequest request,
                                                final boolean readParametersFromBody) {
        final $$VirtualMappingStrategyRequest model = new $$VirtualMappingStrategyRequest();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model);
        return model;
    }

    protected void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                         final HttpRequest request,
                                         final QueryParams params,
                                         final $$VirtualMappingStrategyRequest model) {
        readPrimitivesToModel(pathVariableMapping, request, model);
        model.maxSupportedDateTime = toInstant(params.getValue("max_supported_date_time"), HttpModelType.PARAMETER, "max_supported_date_time");
    }

    protected void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                         final HttpRequest request,
                                         final $$VirtualMappingStrategyRequest model) {
        final HttpHeaders httpHeaders = request.getHeaders();
        model.supportedApiVersionCode = toBigDecimal(httpHeaders.getValue("Supported-Api-Version-Code"), HttpModelType.HEADER, "Supported-Api-Version-Code");
    }
}