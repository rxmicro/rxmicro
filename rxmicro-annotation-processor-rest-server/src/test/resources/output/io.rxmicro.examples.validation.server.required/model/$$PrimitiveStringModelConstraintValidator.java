package io.rxmicro.examples.validation.server.required.model;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$PrimitiveStringModelConstraintValidator implements ConstraintValidator<PrimitiveStringModel> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validate(final PrimitiveStringModel model,
                         final HttpModelType httpModelType,
                         final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.requiredNotEmptyString, HttpModelType.PARAMETER, "requiredNotEmptyString");

        notEmptyStringConstraintValidator.validate(model.nullableString, HttpModelType.PARAMETER, "nullableString");

        requiredConstraintValidator.validate(model.allowEmptyString, HttpModelType.PARAMETER, "allowEmptyString");
    }
}
