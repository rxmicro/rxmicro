package io.rxmicro.examples.rest.controller.extendable.model.response.any.internal_header_body.child;

import io.rxmicro.examples.rest.controller.extendable.model.response.any.internal_header_body.parent.$$ParentConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.validation.ConstraintValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildConstraintValidator implements ConstraintValidator<Child> {

    private final $$ParentConstraintValidator parentValidator =
            new $$ParentConstraintValidator();

    @Override
    public void validateNonNull(final Child model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
    }
}
