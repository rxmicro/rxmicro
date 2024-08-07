package io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.all_models_contain_fields.parent;

import io.rxmicro.examples.rest.controller.extendable.model.request.path_vars_only.all_models_contain_fields.grand.$$GrandParentConstraintValidator;
import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.NotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ParentConstraintValidator implements ConstraintValidator<Parent> {

    private final $$GrandParentConstraintValidator parentValidator =
            new $$GrandParentConstraintValidator();

    private final NotEmptyStringConstraintValidator notEmptyStringConstraintValidator =
            getStatelessValidator(NotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final Parent model,
                                final ModelType httpModelType,
                                final String name) {
        parentValidator.validate(model, httpModelType, name);
        notEmptyStringConstraintValidator.validate(model.parentVar, HttpModelType.PATH, "parentVar");
    }
}
