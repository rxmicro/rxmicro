package io.rxmicro.examples.rest.client.params.model.nested;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NestedModelConstraintValidator implements ConstraintValidator<NestedModel> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validateNonNull(final NestedModel model,
                                final ModelType httpModelType,
                                final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.stringParameter, HttpModelType.PARAMETER, "string_parameter");

        requiredConstraintValidator.validate(model.bigDecimalParameter, HttpModelType.PARAMETER, "big_decimal_parameter");

        requiredConstraintValidator.validate(model.instantParameter, HttpModelType.PARAMETER, "instant_parameter");
    }
}
