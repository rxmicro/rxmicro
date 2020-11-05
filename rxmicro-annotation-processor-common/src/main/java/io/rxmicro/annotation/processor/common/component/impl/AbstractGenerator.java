/*
 * Copyright (c) 2020. https://rxmicro.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.rxmicro.annotation.processor.common.component.impl;

import freemarker.cache.TemplateLookupContext;
import freemarker.cache.TemplateLookupResult;
import freemarker.cache.TemplateLookupStrategy;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import io.rxmicro.annotation.processor.common.model.error.InterruptProcessingException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nedis
 * @since 0.1
 */
public abstract class AbstractGenerator extends AbstractProcessorComponent {

    private Configuration cfg;

    protected final Template getTemplate(final String name) throws IOException {
        if (cfg == null) {
            cfg = new Configuration(Configuration.VERSION_2_3_29);
            cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "ftl");
            cfg.setDefaultEncoding("UTF-8");
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
            cfg.setTemplateLookupStrategy(new TemplateLookupStrategy() {
                @Override
                public TemplateLookupResult lookup(final TemplateLookupContext ctx) throws IOException {
                    return ctx.lookupWithAcquisitionStrategy(ctx.getTemplateName());
                }
            });
            cfg.setLogTemplateExceptions(false);
            cfg.setWrapUncheckedExceptions(true);
            cfg.setFallbackOnNullLoopVariable(false);
        }
        return cfg.getTemplate(name);
    }

    protected final void catchThrowable(final Throwable throwable,
                                        final Runnable cantGenerateMessageConsumer) {
        if (throwable instanceof TemplateException) {
            final TemplateException ex = (TemplateException) throwable;
            final InterruptProcessingException interruptProcessingExceptionCause = getNullableInterruptProcessingExceptionCause(ex);
            if (interruptProcessingExceptionCause != null) {
                throw interruptProcessingExceptionCause;
            } else {
                cantGenerateMessageConsumer.run();
            }
        } else {
            cantGenerateMessageConsumer.run();
        }
    }

    private InterruptProcessingException getNullableInterruptProcessingExceptionCause(final TemplateException exception) {
        final List<Throwable> throwableList = new ArrayList<>(2);
        Throwable cause = exception;
        while (true) {
            cause = cause.getCause();
            if (cause == null || cause.equals(exception) || throwableList.contains(cause)) {
                break;
            } else if (cause instanceof InterruptProcessingException) {
                return (InterruptProcessingException) cause;
            } else {
                throwableList.add(cause);
            }
        }
        return null;
    }
}
