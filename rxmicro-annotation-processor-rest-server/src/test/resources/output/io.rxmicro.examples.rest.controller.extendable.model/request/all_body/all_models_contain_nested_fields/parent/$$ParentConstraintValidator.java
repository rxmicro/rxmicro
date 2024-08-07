package io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.grand.$$GrandParentConstraintValidator;
import io.rxmicro.examples.rest.controller.extendable.model.request.all_body.all_models_contain_nested_fields.parent.nested.$$NestedConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentConstraintValidator implements ConstraintValidator<Parent> {

    private final $$GrandParentConstraintValidator parentValidator =
            new $$GrandParentConstraintValidator();

    private final $$NestedConstraintValidator nestedConstraintValidator =
            new $$NestedConstraintValidator();

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validateNonNull(final Parent model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
        notEmptyStringConstraintValidator.validate(model.parentVar, HttpModelType.PATH, "parentVar");

        requiredAndNotEmptyStringConstraintValidator.validate(model.parentHeader, HttpModelType.HEADER, "parentHeader");

        requiredConstraintValidator.validate(model.parentNestedParameter, HttpModelType.PARAMETER, "parentNestedParameter");
        nestedConstraintValidator.validate(model.parentNestedParameter, HttpModelType.PARAMETER, "parentNestedParameter");

        requiredAndNotEmptyStringConstraintValidator.validate(model.parentParameter, HttpModelType.PARAMETER, "parentParameter");
    }
}
