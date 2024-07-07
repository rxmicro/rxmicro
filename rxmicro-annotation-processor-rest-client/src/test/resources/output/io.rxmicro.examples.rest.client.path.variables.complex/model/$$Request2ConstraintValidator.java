package io.rxmicro.examples.rest.client.path.variables.complex.model;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$Request2ConstraintValidator implements ConstraintValidator<Request2> {

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final Request2 model,
                                final ModelType httpModelType,
                                final String name) {
        notEmptyStringConstraintValidator.validate(model.a, HttpModelType.PATH, "a");

        notEmptyStringConstraintValidator.validate(model.b, HttpModelType.PATH, "b");
    }
}
