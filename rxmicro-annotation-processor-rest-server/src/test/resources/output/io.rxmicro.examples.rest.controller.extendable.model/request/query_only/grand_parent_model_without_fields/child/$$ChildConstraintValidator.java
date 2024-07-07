package io.rxmicro.examples.rest.controller.extendable.model.request.query_only.grand_parent_model_without_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.query_only.grand_parent_model_without_fields.parent.$$ParentConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildConstraintValidator implements ConstraintValidator<Child> {

    private final $$ParentConstraintValidator parentValidator =
            new $$ParentConstraintValidator();

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final Child model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
        requiredAndNotEmptyStringConstraintValidator.validate(model.childParameter, HttpModelType.PARAMETER, "childParameter");
    }
}
