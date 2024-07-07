package io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_simple_fields.child;

import io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_simple_fields.parent.$$ParentConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ChildConstraintValidator implements ConstraintValidator<Child> {

    private final $$ParentConstraintValidator parentValidator =
            new $$ParentConstraintValidator();

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final Child model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
        notEmptyStringConstraintValidator.validate(model.childVar, HttpModelType.PATH, "childVar");

        requiredAndNotEmptyStringConstraintValidator.validate(model.childHeader, HttpModelType.HEADER, "childHeader");

        requiredAndNotEmptyStringConstraintValidator.validate(model.childParameter, HttpModelType.PARAMETER, "childParameter");
    }
}
