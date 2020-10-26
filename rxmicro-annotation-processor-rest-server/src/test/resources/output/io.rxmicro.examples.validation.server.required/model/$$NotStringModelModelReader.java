package io.rxmicro.examples.validation.server.required.model;

import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NotStringModelModelReader extends ModelReader<NotStringModel> {

    @Override
    public NotStringModel read(final PathVariableMapping pathVariableMapping,
                               final HttpRequest request,
                               final boolean readParametersFromBody) {
        final NotStringModel model = new NotStringModel();
        final QueryParams params = extractParams(request.getQueryString());
        model.requiredPrimitive = toInteger(params.getValue("requiredPrimitive"), HttpModelType.PARAMETER, "requiredPrimitive");
        model.nullablePrimitive = toInteger(params.getValue("nullablePrimitive"), HttpModelType.PARAMETER, "nullablePrimitive");
        model.requiredListWithRequiredItems = toIntegerArray(params.getValues("requiredListWithRequiredItems"), HttpModelType.PARAMETER, "requiredListWithRequiredItems");
        model.requiredListWithNullableItems = toIntegerArray(params.getValues("requiredListWithNullableItems"), HttpModelType.PARAMETER, "requiredListWithNullableItems");
        model.nullableListWithRequiredItems = toIntegerArray(params.getValues("nullableListWithRequiredItems"), HttpModelType.PARAMETER, "nullableListWithRequiredItems");
        model.nullableListWithNullableItems = toIntegerArray(params.getValues("nullableListWithNullableItems"), HttpModelType.PARAMETER, "nullableListWithNullableItems");
        return model;
    }
}