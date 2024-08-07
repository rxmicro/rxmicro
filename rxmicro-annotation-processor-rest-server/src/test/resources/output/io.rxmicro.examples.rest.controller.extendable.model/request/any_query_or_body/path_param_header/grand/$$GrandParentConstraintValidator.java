package io.rxmicro.examples.rest.controller.extendable.model.request.any_query_or_body.path_param_header.grand;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredAndNotEmptyStringConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$GrandParentConstraintValidator implements ConstraintValidator<GrandParent> {

    private final RequiredAndNotEmptyStringConstraintValidator requiredAndNotEmptyStringConstraintValidator =
            getStatelessValidator(RequiredAndNotEmptyStringConstraintValidator.class);

    @Override
    public void validateNonNull(final GrandParent model,
                                final ModelType httpModelType,
                                final String name) {
        requiredAndNotEmptyStringConstraintValidator.validate(model.grandHeader, HttpModelType.HEADER, "grandHeader");
    }
}
