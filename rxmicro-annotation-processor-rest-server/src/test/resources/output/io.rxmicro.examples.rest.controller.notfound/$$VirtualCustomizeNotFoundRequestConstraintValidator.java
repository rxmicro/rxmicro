package io.rxmicro.examples.rest.controller.notfound;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualCustomizeNotFoundRequestConstraintValidator implements ConstraintValidator<$$VirtualCustomizeNotFoundRequest> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validateNonNull(final $$VirtualCustomizeNotFoundRequest model,
                                final ModelType httpModelType,
                                final String name) {
        requiredConstraintValidator.validate(model.found, HttpModelType.PARAMETER, "found");
    }
}