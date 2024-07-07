package io.rxmicro.examples.rest.client.model.types.model.request.http_body;

import io.rxmicro.examples.rest.client.model.types.model.nested.$$NestedConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;
import io.rxmicro.validation.validator.RequiredListConstraintValidator;
import io.rxmicro.validation.validator.RequiredMapConstraintValidator;
import io.rxmicro.validation.validator.RequiredSetConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$HttpBodyWithPathVarRequestConstraintValidator implements ConstraintValidator<HttpBodyWithPathVarRequest> {

    private final $$NestedConstraintValidator nestedConstraintValidator =
            new $$NestedConstraintValidator();

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final RequiredListConstraintValidator requiredListConstraintValidator =
            getStatelessValidator(RequiredListConstraintValidator.class);

    private final RequiredSetConstraintValidator requiredSetConstraintValidator =
            getStatelessValidator(RequiredSetConstraintValidator.class);

    private final RequiredMapConstraintValidator requiredMapConstraintValidator =
            getStatelessValidator(RequiredMapConstraintValidator.class);

    @Override
    public void validateNonNull(final HttpBodyWithPathVarRequest model,
                                final ModelType httpModelType,
                                final String name) {
        notEmptyStringConstraintValidator.validate(model.stringPathVariable, HttpModelType.PATH, "k");

        requiredConstraintValidator.validate(model.booleanParameter, HttpModelType.PARAMETER, "booleanParameter");

        requiredConstraintValidator.validate(model.byteParameter, HttpModelType.PARAMETER, "byteParameter");

        requiredConstraintValidator.validate(model.shortParameter, HttpModelType.PARAMETER, "shortParameter");

        requiredConstraintValidator.validate(model.intParameter, HttpModelType.PARAMETER, "intParameter");

        requiredConstraintValidator.validate(model.longParameter, HttpModelType.PARAMETER, "longParameter");

        requiredConstraintValidator.validate(model.bigIntParameter, HttpModelType.PARAMETER, "bigIntParameter");

        requiredConstraintValidator.validate(model.floatParameter, HttpModelType.PARAMETER, "floatParameter");

        requiredConstraintValidator.validate(model.doubleParameter, HttpModelType.PARAMETER, "doubleParameter");

        requiredConstraintValidator.validate(model.decimalParameter, HttpModelType.PARAMETER, "decimalParameter");

        requiredConstraintValidator.validate(model.charParameter, HttpModelType.PARAMETER, "charParameter");

        requiredAndNotEmptyStringConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "stringParameter");

        requiredConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instantParameter");

        requiredConstraintValidator.validate(model.enumParameter, HttpModelType.PARAMETER, "enumParameter");

        requiredListConstraintValidator.validate(model.booleanParameterList, HttpModelType.PARAMETER, "booleanParameterList");
        requiredConstraintValidator.validateIterable(model.booleanParameterList, HttpModelType.PARAMETER, "booleanParameterList");

        requiredListConstraintValidator.validate(model.byteParameterList, HttpModelType.PARAMETER, "byteParameterList");
        requiredConstraintValidator.validateIterable(model.byteParameterList, HttpModelType.PARAMETER, "byteParameterList");

        requiredListConstraintValidator.validate(model.shortParameterList, HttpModelType.PARAMETER, "shortParameterList");
        requiredConstraintValidator.validateIterable(model.shortParameterList, HttpModelType.PARAMETER, "shortParameterList");

        requiredListConstraintValidator.validate(model.intParameterList, HttpModelType.PARAMETER, "intParameterList");
        requiredConstraintValidator.validateIterable(model.intParameterList, HttpModelType.PARAMETER, "intParameterList");

        requiredListConstraintValidator.validate(model.longParameterList, HttpModelType.PARAMETER, "longParameterList");
        requiredConstraintValidator.validateIterable(model.longParameterList, HttpModelType.PARAMETER, "longParameterList");

        requiredListConstraintValidator.validate(model.bigIntParameterList, HttpModelType.PARAMETER, "bigIntParameterList");
        requiredConstraintValidator.validateIterable(model.bigIntParameterList, HttpModelType.PARAMETER, "bigIntParameterList");

        requiredListConstraintValidator.validate(model.floatParameterList, HttpModelType.PARAMETER, "floatParameterList");
        requiredConstraintValidator.validateIterable(model.floatParameterList, HttpModelType.PARAMETER, "floatParameterList");

        requiredListConstraintValidator.validate(model.doubleParameterList, HttpModelType.PARAMETER, "doubleParameterList");
        requiredConstraintValidator.validateIterable(model.doubleParameterList, HttpModelType.PARAMETER, "doubleParameterList");

        requiredListConstraintValidator.validate(model.decimalParameterList, HttpModelType.PARAMETER, "decimalParameterList");
        requiredConstraintValidator.validateIterable(model.decimalParameterList, HttpModelType.PARAMETER, "decimalParameterList");

        requiredListConstraintValidator.validate(model.charParameterList, HttpModelType.PARAMETER, "charParameterList");
        requiredConstraintValidator.validateIterable(model.charParameterList, HttpModelType.PARAMETER, "charParameterList");

        requiredListConstraintValidator.validate(model.stringParameterList, HttpModelType.PARAMETER, "stringParameterList");
        requiredAndNotEmptyStringConstraintValidator.validateIterable(model.stringParameterList, HttpModelType.PARAMETER, "stringParameterList");

        requiredListConstraintValidator.validate(model.instantParameterList, HttpModelType.PARAMETER, "instantParameterList");
        requiredConstraintValidator.validateIterable(model.instantParameterList, HttpModelType.PARAMETER, "instantParameterList");

        requiredListConstraintValidator.validate(model.enumParameterList, HttpModelType.PARAMETER, "enumParameterList");
        requiredConstraintValidator.validateIterable(model.enumParameterList, HttpModelType.PARAMETER, "enumParameterList");

        requiredSetConstraintValidator.validate(model.booleanParameterSet, HttpModelType.PARAMETER, "booleanParameterSet");
        requiredConstraintValidator.validateIterable(model.booleanParameterSet, HttpModelType.PARAMETER, "booleanParameterSet");

        requiredSetConstraintValidator.validate(model.byteParameterSet, HttpModelType.PARAMETER, "byteParameterSet");
        requiredConstraintValidator.validateIterable(model.byteParameterSet, HttpModelType.PARAMETER, "byteParameterSet");

        requiredSetConstraintValidator.validate(model.shortParameterSet, HttpModelType.PARAMETER, "shortParameterSet");
        requiredConstraintValidator.validateIterable(model.shortParameterSet, HttpModelType.PARAMETER, "shortParameterSet");

        requiredSetConstraintValidator.validate(model.intParameterSet, HttpModelType.PARAMETER, "intParameterSet");
        requiredConstraintValidator.validateIterable(model.intParameterSet, HttpModelType.PARAMETER, "intParameterSet");

        requiredSetConstraintValidator.validate(model.longParameterSet, HttpModelType.PARAMETER, "longParameterSet");
        requiredConstraintValidator.validateIterable(model.longParameterSet, HttpModelType.PARAMETER, "longParameterSet");

        requiredSetConstraintValidator.validate(model.bigIntParameterSet, HttpModelType.PARAMETER, "bigIntParameterSet");
        requiredConstraintValidator.validateIterable(model.bigIntParameterSet, HttpModelType.PARAMETER, "bigIntParameterSet");

        requiredSetConstraintValidator.validate(model.floatParameterSet, HttpModelType.PARAMETER, "floatParameterSet");
        requiredConstraintValidator.validateIterable(model.floatParameterSet, HttpModelType.PARAMETER, "floatParameterSet");

        requiredSetConstraintValidator.validate(model.doubleParameterSet, HttpModelType.PARAMETER, "doubleParameterSet");
        requiredConstraintValidator.validateIterable(model.doubleParameterSet, HttpModelType.PARAMETER, "doubleParameterSet");

        requiredSetConstraintValidator.validate(model.decimalParameterSet, HttpModelType.PARAMETER, "decimalParameterSet");
        requiredConstraintValidator.validateIterable(model.decimalParameterSet, HttpModelType.PARAMETER, "decimalParameterSet");

        requiredSetConstraintValidator.validate(model.charParameterSet, HttpModelType.PARAMETER, "charParameterSet");
        requiredConstraintValidator.validateIterable(model.charParameterSet, HttpModelType.PARAMETER, "charParameterSet");

        requiredSetConstraintValidator.validate(model.stringParameterSet, HttpModelType.PARAMETER, "stringParameterSet");
        requiredAndNotEmptyStringConstraintValidator.validateIterable(model.stringParameterSet, HttpModelType.PARAMETER, "stringParameterSet");

        requiredSetConstraintValidator.validate(model.instantParameterSet, HttpModelType.PARAMETER, "instantParameterSet");
        requiredConstraintValidator.validateIterable(model.instantParameterSet, HttpModelType.PARAMETER, "instantParameterSet");

        requiredSetConstraintValidator.validate(model.enumParameterSet, HttpModelType.PARAMETER, "enumParameterSet");
        requiredConstraintValidator.validateIterable(model.enumParameterSet, HttpModelType.PARAMETER, "enumParameterSet");

        requiredMapConstraintValidator.validate(model.booleanParameterMap, HttpModelType.PARAMETER, "booleanParameterMap");
        requiredConstraintValidator.validateMapValues(model.booleanParameterMap, HttpModelType.PARAMETER, "booleanParameterMap");

        requiredMapConstraintValidator.validate(model.byteParameterMap, HttpModelType.PARAMETER, "byteParameterMap");
        requiredConstraintValidator.validateMapValues(model.byteParameterMap, HttpModelType.PARAMETER, "byteParameterMap");

        requiredMapConstraintValidator.validate(model.shortParameterMap, HttpModelType.PARAMETER, "shortParameterMap");
        requiredConstraintValidator.validateMapValues(model.shortParameterMap, HttpModelType.PARAMETER, "shortParameterMap");

        requiredMapConstraintValidator.validate(model.integerParameterMap, HttpModelType.PARAMETER, "integerParameterMap");
        requiredConstraintValidator.validateMapValues(model.integerParameterMap, HttpModelType.PARAMETER, "integerParameterMap");

        requiredMapConstraintValidator.validate(model.longParameterMap, HttpModelType.PARAMETER, "longParameterMap");
        requiredConstraintValidator.validateMapValues(model.longParameterMap, HttpModelType.PARAMETER, "longParameterMap");

        requiredMapConstraintValidator.validate(model.bigIntegerParameterMap, HttpModelType.PARAMETER, "bigIntegerParameterMap");
        requiredConstraintValidator.validateMapValues(model.bigIntegerParameterMap, HttpModelType.PARAMETER, "bigIntegerParameterMap");

        requiredMapConstraintValidator.validate(model.floatParameterMap, HttpModelType.PARAMETER, "floatParameterMap");
        requiredConstraintValidator.validateMapValues(model.floatParameterMap, HttpModelType.PARAMETER, "floatParameterMap");

        requiredMapConstraintValidator.validate(model.doubleParameterMap, HttpModelType.PARAMETER, "doubleParameterMap");
        requiredConstraintValidator.validateMapValues(model.doubleParameterMap, HttpModelType.PARAMETER, "doubleParameterMap");

        requiredMapConstraintValidator.validate(model.bigDecimalParameterMap, HttpModelType.PARAMETER, "bigDecimalParameterMap");
        requiredConstraintValidator.validateMapValues(model.bigDecimalParameterMap, HttpModelType.PARAMETER, "bigDecimalParameterMap");

        requiredMapConstraintValidator.validate(model.characterParameterMap, HttpModelType.PARAMETER, "characterParameterMap");
        requiredConstraintValidator.validateMapValues(model.characterParameterMap, HttpModelType.PARAMETER, "characterParameterMap");

        requiredMapConstraintValidator.validate(model.stringParameterMap, HttpModelType.PARAMETER, "stringParameterMap");
        requiredAndNotEmptyStringConstraintValidator.validateMapValues(model.stringParameterMap, HttpModelType.PARAMETER, "stringParameterMap");

        requiredMapConstraintValidator.validate(model.instantParameterMap, HttpModelType.PARAMETER, "instantParameterMap");
        requiredConstraintValidator.validateMapValues(model.instantParameterMap, HttpModelType.PARAMETER, "instantParameterMap");

        requiredMapConstraintValidator.validate(model.enumParameterMap, HttpModelType.PARAMETER, "enumParameterMap");
        requiredConstraintValidator.validateMapValues(model.enumParameterMap, HttpModelType.PARAMETER, "enumParameterMap");

        requiredConstraintValidator.validate(model.nested, HttpModelType.PARAMETER, "nested");
        nestedConstraintValidator.validate(model.nested, HttpModelType.PARAMETER, "nested");

        requiredListConstraintValidator.validate(model.nestedList, HttpModelType.PARAMETER, "nestedList");
        requiredConstraintValidator.validateIterable(model.nestedList, HttpModelType.PARAMETER, "nestedList");
        nestedConstraintValidator.validateIterable(model.nestedList, HttpModelType.PARAMETER, "nestedList");

        requiredSetConstraintValidator.validate(model.nestedSet, HttpModelType.PARAMETER, "nestedSet");
        requiredConstraintValidator.validateIterable(model.nestedSet, HttpModelType.PARAMETER, "nestedSet");
        nestedConstraintValidator.validateIterable(model.nestedSet, HttpModelType.PARAMETER, "nestedSet");

        requiredMapConstraintValidator.validate(model.nestedParameterMap, HttpModelType.PARAMETER, "nestedParameterMap");
        requiredConstraintValidator.validateMapValues(model.nestedParameterMap, HttpModelType.PARAMETER, "nestedParameterMap");
        nestedConstraintValidator.validateMapValues(model.nestedParameterMap, HttpModelType.PARAMETER, "nestedParameterMap");
    }
}
