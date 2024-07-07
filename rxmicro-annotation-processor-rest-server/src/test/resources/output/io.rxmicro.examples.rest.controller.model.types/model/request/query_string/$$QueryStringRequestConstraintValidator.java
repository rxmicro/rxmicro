package io.rxmicro.examples.rest.controller.model.types.model.request.query_string;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;
import io.rxmicro.validation.validator.RequiredListConstraintValidator;
import io.rxmicro.validation.validator.RequiredSetConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$QueryStringRequestConstraintValidator implements ConstraintValidator<QueryStringRequest> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final RequiredListConstraintValidator requiredListConstraintValidator =
            getStatelessValidator(RequiredListConstraintValidator.class);

    private final RequiredSetConstraintValidator requiredSetConstraintValidator =
            getStatelessValidator(RequiredSetConstraintValidator.class);

    @Override
    public void validateNonNull(final QueryStringRequest model,
                                final ModelType httpModelType,
                                final String name) {
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
    }
}
