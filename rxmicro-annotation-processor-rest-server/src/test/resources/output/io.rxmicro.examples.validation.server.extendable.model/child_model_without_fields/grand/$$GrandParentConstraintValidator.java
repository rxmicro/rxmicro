package io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.grand;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.UppercaseConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentConstraintValidator implements ConstraintValidator<GrandParent> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final UppercaseConstraintValidator uppercaseConstraintValidator =
            getStatelessValidator(UppercaseConstraintValidator.class);

    @Override
    public void validate(final GrandParent model,
                         final HttpModelType httpModelType,
                         final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.grandParameter, HttpModelType.PARAMETER, "grandParameter");
        uppercaseConstraintValidator.validate(model.grandParameter, HttpModelType.PARAMETER, "grandParameter");
    }
}
