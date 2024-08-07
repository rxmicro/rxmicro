package io.rxmicro.examples.rest.controller.extendable.model.response.any.body_internal_header.parent;

import io.rxmicro.examples.rest.controller.extendable.model.response.any.body_internal_header.grand.$$GrandParentConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentConstraintValidator implements ConstraintValidator<Parent> {

    private final $$GrandParentConstraintValidator parentValidator =
            new $$GrandParentConstraintValidator();

    @Override
    public void validateNonNull(final Parent model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
    }
}
