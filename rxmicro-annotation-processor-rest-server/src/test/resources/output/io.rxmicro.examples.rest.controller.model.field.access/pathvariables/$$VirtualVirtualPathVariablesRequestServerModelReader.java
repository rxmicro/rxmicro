package io.rxmicro.examples.rest.controller.model.field.access.pathvariables;

import io.rxmicro.examples.rest.controller.model.field.access.Status;
import io.rxmicro.http.QueryParams;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.rest.model.PathVariableMapping;
import io.rxmicro.rest.server.detail.component.ServerModelReader;
import io.rxmicro.rest.server.detail.model.HttpRequest;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualVirtualPathVariablesRequestServerModelReader extends ServerModelReader<$$VirtualVirtualPathVariablesRequest> {

    @Override
    public $$VirtualVirtualPathVariablesRequest read(final PathVariableMapping pathVariableMapping,
                                                     final HttpRequest request,
                                                     final boolean readParametersFromBody) {
        final $$VirtualVirtualPathVariablesRequest model = new $$VirtualVirtualPathVariablesRequest();
        readPrimitivesToModel(pathVariableMapping, request, QueryParams.of(), model, readParametersFromBody);
        return model;
    }

    public void readPrimitivesToModel(final PathVariableMapping pathVariableMapping,
                                      final HttpRequest request,
                                      final QueryParams params,
                                      final $$VirtualVirtualPathVariablesRequest model,
                                      final boolean readParametersFromBody) {
        model.booleanPathVariable = toBoolean(pathVariableMapping.getValue("a"), HttpModelType.PATH, "a");
        model.bytePathVariable = toByte(pathVariableMapping.getValue("b"), HttpModelType.PATH, "b");
        model.shortPathVariable = toShort(pathVariableMapping.getValue("c"), HttpModelType.PATH, "c");
        model.intPathVariable = toInteger(pathVariableMapping.getValue("d"), HttpModelType.PATH, "d");
        model.longPathVariable = toLong(pathVariableMapping.getValue("e"), HttpModelType.PATH, "e");
        model.bigIntegerPathVariable = toBigInteger(pathVariableMapping.getValue("f"), HttpModelType.PATH, "f");
        model.floatPathVariable = toFloat(pathVariableMapping.getValue("g"), HttpModelType.PATH, "g");
        model.doublePathVariable = toDouble(pathVariableMapping.getValue("h"), HttpModelType.PATH, "h");
        model.decimalPathVariable = toBigDecimal(pathVariableMapping.getValue("i"), HttpModelType.PATH, "i");
        model.charPathVariable = toCharacter(pathVariableMapping.getValue("j"), HttpModelType.PATH, "j");
        model.stringPathVariable = toString(pathVariableMapping.getValue("k"), HttpModelType.PATH, "k");
        model.instantPathVariable = toInstant(pathVariableMapping.getValue("l"), HttpModelType.PATH, "l");
        model.enumPathVariable = toEnum(Status.class, pathVariableMapping.getValue("m"), HttpModelType.PATH, "m");
    }
}
