package io.rxmicro.examples.rest.client.model.field.mapping.strategy;

import io.rxmicro.model.ModelType;
import io.rxmicro.rest.model.HttpModelType;
import io.rxmicro.validation.ConstraintValidator;
import io.rxmicro.validation.validator.RequiredConstraintValidator;

import static io.rxmicro.validation.detail.StatelessValidators.getStatelessValidator;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$VirtualMappingStrategyRequest2ConstraintValidator implements ConstraintValidator<$$VirtualMappingStrategyRequest2> {

    private final RequiredConstraintValidator requiredConstraintValidator =
            getStatelessValidator(RequiredConstraintValidator.class);

    @Override
    public void validateNonNull(final $$VirtualMappingStrategyRequest2 model,
                                final ModelType httpModelType,
                                final String name) {
        requiredConstraintValidator.validate(model.supportedApiVersionCode, HttpModelType.HEADER, "Supported_Api_Version_Code");

        requiredConstraintValidator.validate(model.maxSupportedDateTime, HttpModelType.PARAMETER, "max_supported_date_time");
    }
}
