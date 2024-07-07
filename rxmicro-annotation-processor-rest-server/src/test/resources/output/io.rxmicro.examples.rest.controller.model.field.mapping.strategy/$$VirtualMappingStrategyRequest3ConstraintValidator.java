package io.rxmicro.examples.rest.controller.model.field.mapping.strategy;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualMappingStrategyRequest3ConstraintValidator implements ConstraintValidator<$$VirtualMappingStrategyRequest3> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validateNonNull(final $$VirtualMappingStrategyRequest3 model,
                                final ModelType httpModelType,
                                final String name) {
        requiredConstraintValidator.validate(model.supportedApiVersionCode, HttpModelType.HEADER, "Supported-Api-Version-Code");

        requiredConstraintValidator.validate(model.maxSupportedDateTime, HttpModelType.PARAMETER, "max-supported-date-time");
    }
}
