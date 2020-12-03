package io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.child;

import io.rxmicro.examples.validation.server.extendable.model.child_model_without_fields.parent.$$ParentConstraintValidator;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildConstraintValidator implements ConstraintValidator<Child> {

    private final $$ParentConstraintValidator parentValidator =
            new $$ParentConstraintValidator();

    @Override
    public void validate(final Child model,
                         final HttpModelType httpModelType,
                         final String name) {
        parentValidator.validate(model, httpModelType, name);
    }
}
