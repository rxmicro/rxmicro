package io.rxmicro.examples.rest.client.headers.model;

import io.rxmicro.http.HttpHeaders;
import io.rxmicro.rest.client.detail.ClientModelReader;
import io.rxmicro.rest.client.detail.HttpResponse;
import io.rxmicro.rest.model.HttpModelType;

/**
 * Generated by {@code RxMicro Annotation Processor}
 */
public final class $$ListHeaderResponseClientModelReader extends ClientModelReader<ListHeaderResponse> {

    @Override
    public ListHeaderResponse readSingle(final HttpResponse response) {
        final ListHeaderResponse model = new ListHeaderResponse();
        readPrimitivesToModel(response, model);
        return model;
    }

    protected void readPrimitivesToModel(final HttpResponse response,
                                         final ListHeaderResponse model) {
        final HttpHeaders httpHeaders = response.getHeaders();
        model.supportedStatuses = toEnumList(Status.class, httpHeaders.getValues("Supported-Statuses"), HttpModelType.HEADER, "Supported-Statuses");
    }
}
