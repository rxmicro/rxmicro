package io.rxmicro.examples.cdi.resource.injection.optional;

import io.rxmicro.cdi.detail.BeanSupplier;
import io.rxmicro.cdi.resource.ClasspathJsonArrayResourceConverter;
import io.rxmicro.cdi.resource.ClasspathJsonObjectResourceConverter;
import io.rxmicro.cdi.resource.ClasspathPropertiesResourceConverter;

import static io.rxmicro.cdi.detail.ResourceLoaderFactory.loadOptionalResource;
import static io.rxmicro.cdi.detail.ResourceLoaderFactory.loadResource;
import static rxmicro.cdi.resource.injection.$$Reflections.setFieldValue;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$OptionalResourceInjectionBeanSupplier extends BeanSupplier<OptionalResourceInjection> {

    @Override
    public OptionalResourceInjection get() {
        final OptionalResourceInjection bean = new OptionalResourceInjection();
        bean.requiredJsonObject = loadResource("classpath:object.json", ClasspathJsonObjectResourceConverter.class);
        setFieldValue(bean, "requiredJsonArray", loadResource("classpath:array.json", ClasspathJsonArrayResourceConverter.class));
        loadOptionalResource("classpath:not_found.json", ClasspathJsonObjectResourceConverter.class)
                .ifPresent(r -> bean.optionalJsonObject = r);
        loadOptionalResource("classpath:not_found.json", ClasspathJsonArrayResourceConverter.class)
                .ifPresent(r -> setFieldValue(bean, "optionalJsonArray", r));
        bean.setRequiredProperties(loadResource("classpath:value.properties", ClasspathPropertiesResourceConverter.class));
        loadOptionalResource("classpath:not_found.properties", ClasspathPropertiesResourceConverter.class)
                .ifPresent(bean::setOptionalProperties);
        return bean;
    }
}
