package io.rxmicro.examples.validation.server.required.model;

import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;
import io.rxmicro.validation.validator.RequiredListConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$NotStringModelConstraintValidator implements ConstraintValidator<NotStringModel> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    private final RequiredListConstraintValidator requiredListConstraintValidator =
            getStatelessValidator(RequiredListConstraintValidator.class);

    @Override
    public void validate(final NotStringModel model,
                         final HttpModelType httpModelType,
                         final String name) {
        requiredConstraintValidator.validate(model.requiredPrimitive, HttpModelType.PARAMETER, "requiredPrimitive");

        requiredListConstraintValidator.validate(model.requiredListWithRequiredItems, HttpModelType.PARAMETER, "requiredListWithRequiredItems");
        requiredConstraintValidator.validateList(model.requiredListWithRequiredItems, HttpModelType.PARAMETER, "requiredListWithRequiredItems");

        requiredListConstraintValidator.validate(model.requiredListWithNullableItems, HttpModelType.PARAMETER, "requiredListWithNullableItems");

        requiredConstraintValidator.validateList(model.nullableListWithRequiredItems, HttpModelType.PARAMETER, "nullableListWithRequiredItems");
    }
}
