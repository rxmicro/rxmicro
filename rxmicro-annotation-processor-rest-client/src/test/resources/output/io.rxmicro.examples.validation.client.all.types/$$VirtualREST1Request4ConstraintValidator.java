package io.rxmicro.examples.validation.client.all.types;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.EmailConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualREST1Request4ConstraintValidator implements ConstraintValidator<$$VirtualREST1Request4> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final EmailConstraintValidator emailEmailEmailConstraintValidator =
            new EmailConstraintValidator(false);

    @Override
    public void validate(final $$VirtualREST1Request4 model,
                         final HttpModelType httpModelType,
                         final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.email, HttpModelType.PARAMETER, "email");
        emailEmailEmailConstraintValidator.validate(model.email, HttpModelType.PARAMETER, "email");
    }
}
