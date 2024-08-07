package io.rxmicro.examples.rest.controller.routing;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRoutingUsingHttpBodyRequest2ConstraintValidator implements ConstraintValidator<$$VirtualRoutingUsingHttpBodyRequest2> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final $$VirtualRoutingUsingHttpBodyRequest2 model,
                                final ModelType httpModelType,
                                final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.parameter, HttpModelType.PARAMETER, "parameter");
    }
}
