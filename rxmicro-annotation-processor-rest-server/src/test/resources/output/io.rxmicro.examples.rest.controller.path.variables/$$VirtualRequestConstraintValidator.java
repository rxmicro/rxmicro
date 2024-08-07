package io.rxmicro.examples.rest.controller.path.variables;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualRequestConstraintValidator implements ConstraintValidator<$$VirtualRequest> {

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final $$VirtualRequest model,
                                final ModelType httpModelType,
                                final String name) {
        notEmptyStringConstraintValidator.validate(model.category, HttpModelType.PATH, "category");

        notEmptyStringConstraintValidator.validate(model.type, HttpModelType.PATH, "class");

        notEmptyStringConstraintValidator.validate(model.subType, HttpModelType.PATH, "subType");
    }
}
