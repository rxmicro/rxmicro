package io.rxmicro.examples.validation.server.required.model;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$StringModelWithRequiredNonNullValidatorsOnlyServerModelReader extends ServerModelReader<StringModelWithRequiredNonNullValidatorsOnly> {

    @Override
    public StringModelWithRequiredNonNullValidatorsOnly read(final PathVariableMapping pathVariableMapping,
                                                             final HttpRequest request,
                                                             final boolean readParametersFromBody) {
        final StringModelWithRequiredNonNullValidatorsOnly model = new StringModelWithRequiredNonNullValidatorsOnly();
        final QueryParams params = extractParams(request.getQueryString());
        readPrimitivesToModel(pathVariableMapping, request, params, model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final StringModelWithRequiredNonNullValidatorsOnly model,
                                      final boolean readParametersFromBody) {
        model.allowEmptyString = toString(params.getValue("allowEmptyString"), HttpModelType.PARAMETER, "allowEmptyString");
        model.minLength = toString(params.getValue("minLength"), HttpModelType.PARAMETER, "minLength");
        model.length = toString(params.getValue("length"), HttpModelType.PARAMETER, "length");
        model.enumeration = toString(params.getValue("enumeration"), HttpModelType.PARAMETER, "enumeration");
    }
}