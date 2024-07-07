package io.rxmicro.examples.rest.controller.extendable.model.request.all_query.child_model_without_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.all_query.child_model_without_fields.grand.$$GrandParentConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentConstraintValidator implements ConstraintValidator<Parent> {

    private final $$GrandParentConstraintValidator parentValidator =
            new $$GrandParentConstraintValidator();

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final Parent model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
        notEmptyStringConstraintValidator.validate(model.parentVar, HttpModelType.PATH, "parentVar");

        requiredAndNotEmptyStringConstraintValidator.validate(model.parentHeader, HttpModelType.HEADER, "parentHeader");

        requiredAndNotEmptyStringConstraintValidator.validate(model.parentParameter, HttpModelType.PARAMETER, "parentParameter");
    }
}
