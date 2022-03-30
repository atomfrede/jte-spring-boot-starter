/*
 * Copyright 2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.atomfrede.jte.springframework.boot.autoconfigured;

import gg.jte.TemplateEngine;
import gg.jte.output.Utf8ByteOutput;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.view.AbstractTemplateView;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

public class JteView extends AbstractTemplateView {

    private final TemplateEngine templateEngine;

    public JteView(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean checkResource(Locale locale) {
        return templateEngine.hasTemplate(this.getUrl());
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {

        String url = this.getUrl();

        Utf8ByteOutput output = new Utf8ByteOutput();
        templateEngine.render(url, model, output);

        response.setContentType(MediaType.TEXT_HTML_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentLength(output.getContentLength());

        output.writeTo(response.getOutputStream());
    }
}
