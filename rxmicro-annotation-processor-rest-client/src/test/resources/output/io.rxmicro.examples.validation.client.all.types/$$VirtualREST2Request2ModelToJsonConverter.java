package io.rxmicro.examples.validation.client.all.types;

import io.rxmicro.exchange.json.detail.ModelToJsonConverter;
import io.rxmicro.json.JsonObjectBuilder;

import java.util.Map;

/**
 * Generated by rxmicro annotation processor
 *
 * @link https://rxmicro.io
 */
public final class $$VirtualREST2Request2ModelToJsonConverter extends ModelToJsonConverter<$$VirtualREST2Request2> {

    @Override
    public Map<String, Object> toJsonObject(final $$VirtualREST2Request2 model) {
        return new JsonObjectBuilder()
                .put("email", model.email)
                .build();
    }
}
