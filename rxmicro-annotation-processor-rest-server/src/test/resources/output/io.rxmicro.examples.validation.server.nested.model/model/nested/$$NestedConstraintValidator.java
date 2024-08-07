package io.rxmicro.examples.validation.server.nested.model.model.nested;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.PhoneConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NestedConstraintValidator implements ConstraintValidator<Nested> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final PhoneConstraintValidator phonePhonePhoneConstraintValidator =
            new PhoneConstraintValidator(true, false);

    @Override
    public void validateNonNull(final Nested model,
                                final ModelType httpModelType,
                                final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.phone, HttpModelType.PARAMETER, "phone");
        phonePhonePhoneConstraintValidator.validate(model.phone, HttpModelType.PARAMETER, "phone");
    }
}
