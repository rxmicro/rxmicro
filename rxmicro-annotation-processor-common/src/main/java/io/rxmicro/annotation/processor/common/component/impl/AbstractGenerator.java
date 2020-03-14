/*
 * Copyright (c) 2020. http://rxmicro.io
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
import freemarker.template.TemplateExceptionHandler;

import java.io.IOException;

/**
 * @author nedis
 * @link http://rxmicro.io
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
}
