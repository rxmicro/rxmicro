package io.rxmicro.examples.rest.controller.model.types.model.response.without_body;

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
public final class $$InternalsAndHeadersResponseConstraintValidator implements ConstraintValidator<InternalsAndHeadersResponse> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final RequiredListConstraintValidator requiredListConstraintValidator =
            getStatelessValidator(RequiredListConstraintValidator.class);

    private final RequiredSetConstraintValidator requiredSetConstraintValidator =
            getStatelessValidator(RequiredSetConstraintValidator.class);

    @Override
    public void validateNonNull(final InternalsAndHeadersResponse model,
                                final ModelType httpModelType,
                                final String name) {
        requiredConstraintValidator.validate(model.booleanHeader, HttpModelType.HEADER, "booleanHeader");

        requiredConstraintValidator.validate(model.byteHeader, HttpModelType.HEADER, "byteHeader");

        requiredConstraintValidator.validate(model.shortHeader, HttpModelType.HEADER, "shortHeader");

        requiredConstraintValidator.validate(model.intHeader, HttpModelType.HEADER, "intHeader");

        requiredConstraintValidator.validate(model.longHeader, HttpModelType.HEADER, "longHeader");

        requiredConstraintValidator.validate(model.bigIntHeader, HttpModelType.HEADER, "bigIntHeader");

        requiredConstraintValidator.validate(model.floatHeader, HttpModelType.HEADER, "floatHeader");

        requiredConstraintValidator.validate(model.doubleHeader, HttpModelType.HEADER, "doubleHeader");

        requiredConstraintValidator.validate(model.decimalHeader, HttpModelType.HEADER, "decimalHeader");

        requiredConstraintValidator.validate(model.charHeader, HttpModelType.HEADER, "charHeader");

        requiredAndNotEmptyStringConstraintValidator.validate(model.stringHeader, HttpModelType.HEADER, "stringHeader");

        requiredConstraintValidator.validate(model.instantHeader, HttpModelType.HEADER, "instantHeader");

        requiredConstraintValidator.validate(model.enumHeader, HttpModelType.HEADER, "enumHeader");

        requiredListConstraintValidator.validate(model.booleanHeaderList, HttpModelType.HEADER, "booleanHeaderList");
        requiredConstraintValidator.validateIterable(model.booleanHeaderList, HttpModelType.HEADER, "booleanHeaderList");

        requiredListConstraintValidator.validate(model.byteHeaderList, HttpModelType.HEADER, "byteHeaderList");
        requiredConstraintValidator.validateIterable(model.byteHeaderList, HttpModelType.HEADER, "byteHeaderList");

        requiredListConstraintValidator.validate(model.shortHeaderList, HttpModelType.HEADER, "shortHeaderList");
        requiredConstraintValidator.validateIterable(model.shortHeaderList, HttpModelType.HEADER, "shortHeaderList");

        requiredListConstraintValidator.validate(model.intHeaderList, HttpModelType.HEADER, "intHeaderList");
        requiredConstraintValidator.validateIterable(model.intHeaderList, HttpModelType.HEADER, "intHeaderList");

        requiredListConstraintValidator.validate(model.longHeaderList, HttpModelType.HEADER, "longHeaderList");
        requiredConstraintValidator.validateIterable(model.longHeaderList, HttpModelType.HEADER, "longHeaderList");

        requiredListConstraintValidator.validate(model.bigIntHeaderList, HttpModelType.HEADER, "bigIntHeaderList");
        requiredConstraintValidator.validateIterable(model.bigIntHeaderList, HttpModelType.HEADER, "bigIntHeaderList");

        requiredListConstraintValidator.validate(model.floatHeaderList, HttpModelType.HEADER, "floatHeaderList");
        requiredConstraintValidator.validateIterable(model.floatHeaderList, HttpModelType.HEADER, "floatHeaderList");

        requiredListConstraintValidator.validate(model.doubleHeaderList, HttpModelType.HEADER, "doubleHeaderList");
        requiredConstraintValidator.validateIterable(model.doubleHeaderList, HttpModelType.HEADER, "doubleHeaderList");

        requiredListConstraintValidator.validate(model.decimalHeaderList, HttpModelType.HEADER, "decimalHeaderList");
        requiredConstraintValidator.validateIterable(model.decimalHeaderList, HttpModelType.HEADER, "decimalHeaderList");

        requiredListConstraintValidator.validate(model.charHeaderList, HttpModelType.HEADER, "charHeaderList");
        requiredConstraintValidator.validateIterable(model.charHeaderList, HttpModelType.HEADER, "charHeaderList");

        requiredListConstraintValidator.validate(model.stringHeaderList, HttpModelType.HEADER, "stringHeaderList");
        requiredAndNotEmptyStringConstraintValidator.validateIterable(model.stringHeaderList, HttpModelType.HEADER, "stringHeaderList");

        requiredListConstraintValidator.validate(model.instantHeaderList, HttpModelType.HEADER, "instantHeaderList");
        requiredConstraintValidator.validateIterable(model.instantHeaderList, HttpModelType.HEADER, "instantHeaderList");

        requiredListConstraintValidator.validate(model.enumHeaderList, HttpModelType.HEADER, "enumHeaderList");
        requiredConstraintValidator.validateIterable(model.enumHeaderList, HttpModelType.HEADER, "enumHeaderList");

        requiredSetConstraintValidator.validate(model.booleanHeaderSet, HttpModelType.HEADER, "booleanHeaderSet");
        requiredConstraintValidator.validateIterable(model.booleanHeaderSet, HttpModelType.HEADER, "booleanHeaderSet");

        requiredSetConstraintValidator.validate(model.byteHeaderSet, HttpModelType.HEADER, "byteHeaderSet");
        requiredConstraintValidator.validateIterable(model.byteHeaderSet, HttpModelType.HEADER, "byteHeaderSet");

        requiredSetConstraintValidator.validate(model.shortHeaderSet, HttpModelType.HEADER, "shortHeaderSet");
        requiredConstraintValidator.validateIterable(model.shortHeaderSet, HttpModelType.HEADER, "shortHeaderSet");

        requiredSetConstraintValidator.validate(model.intHeaderSet, HttpModelType.HEADER, "intHeaderSet");
        requiredConstraintValidator.validateIterable(model.intHeaderSet, HttpModelType.HEADER, "intHeaderSet");

        requiredSetConstraintValidator.validate(model.longHeaderSet, HttpModelType.HEADER, "longHeaderSet");
        requiredConstraintValidator.validateIterable(model.longHeaderSet, HttpModelType.HEADER, "longHeaderSet");

        requiredSetConstraintValidator.validate(model.bigIntHeaderSet, HttpModelType.HEADER, "bigIntHeaderSet");
        requiredConstraintValidator.validateIterable(model.bigIntHeaderSet, HttpModelType.HEADER, "bigIntHeaderSet");

        requiredSetConstraintValidator.validate(model.floatHeaderSet, HttpModelType.HEADER, "floatHeaderSet");
        requiredConstraintValidator.validateIterable(model.floatHeaderSet, HttpModelType.HEADER, "floatHeaderSet");

        requiredSetConstraintValidator.validate(model.doubleHeaderSet, HttpModelType.HEADER, "doubleHeaderSet");
        requiredConstraintValidator.validateIterable(model.doubleHeaderSet, HttpModelType.HEADER, "doubleHeaderSet");

        requiredSetConstraintValidator.validate(model.decimalHeaderSet, HttpModelType.HEADER, "decimalHeaderSet");
        requiredConstraintValidator.validateIterable(model.decimalHeaderSet, HttpModelType.HEADER, "decimalHeaderSet");

        requiredSetConstraintValidator.validate(model.charHeaderSet, HttpModelType.HEADER, "charHeaderSet");
        requiredConstraintValidator.validateIterable(model.charHeaderSet, HttpModelType.HEADER, "charHeaderSet");

        requiredSetConstraintValidator.validate(model.stringHeaderSet, HttpModelType.HEADER, "stringHeaderSet");
        requiredAndNotEmptyStringConstraintValidator.validateIterable(model.stringHeaderSet, HttpModelType.HEADER, "stringHeaderSet");

        requiredSetConstraintValidator.validate(model.instantHeaderSet, HttpModelType.HEADER, "instantHeaderSet");
        requiredConstraintValidator.validateIterable(model.instantHeaderSet, HttpModelType.HEADER, "instantHeaderSet");

        requiredSetConstraintValidator.validate(model.enumHeaderSet, HttpModelType.HEADER, "enumHeaderSet");
        requiredConstraintValidator.validateIterable(model.enumHeaderSet, HttpModelType.HEADER, "enumHeaderSet");

        requiredListConstraintValidator.validate(model.repeatingEnumHeaderList, HttpModelType.HEADER, "repeatingEnumHeaderList");
        requiredConstraintValidator.validateIterable(model.repeatingEnumHeaderList, HttpModelType.HEADER, "repeatingEnumHeaderList");

        requiredSetConstraintValidator.validate(model.repeatingEnumHeaderSet, HttpModelType.HEADER, "repeatingEnumHeaderSet");
        requiredConstraintValidator.validateIterable(model.repeatingEnumHeaderSet, HttpModelType.HEADER, "repeatingEnumHeaderSet");
    }
}
