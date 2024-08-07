package io.rxmicro.examples.rest.controller.redirect.model;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$RedirectResponseConstraintValidator implements ConstraintValidator<RedirectResponse> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final RedirectResponse model,
                                final ModelType httpModelType,
                                final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.location, HttpModelType.HEADER, "Location");
    }
}
