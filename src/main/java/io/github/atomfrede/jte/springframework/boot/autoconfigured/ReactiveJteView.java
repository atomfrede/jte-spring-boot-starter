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
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.PooledDataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ReactiveJteView extends AbstractUrlBasedView {

    private final TemplateEngine templateEngine;

    public ReactiveJteView(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean checkResourceExists(Locale locale) {
        return templateEngine.hasTemplate(this.getUrl());
    }

    @Override
    protected Mono<Void> renderInternal(Map<String, Object> renderAttributes, MediaType contentType, ServerWebExchange exchange) {

        return exchange.getResponse().writeWith(Mono
                .fromCallable(() -> {
                    String url = this.getUrl();
                    Utf8ByteOutput output = new Utf8ByteOutput();
                    templateEngine.render(url, renderAttributes, output);

                    DataBuffer dataBuffer = exchange.getResponse().bufferFactory().allocateBuffer();
                    try {
                        output.writeTo(dataBuffer.asOutputStream());
                        return dataBuffer;
                    } catch (IOException ex){
                        DataBufferUtils.release(dataBuffer);
                        String message = "Could not load JTE template for URL [" + getUrl() + "]";
                        throw new IllegalStateException(message, ex);
                    }catch (Throwable ex) {
                        DataBufferUtils.release(dataBuffer);
                        throw ex;
                    }}).doOnDiscard(PooledDataBuffer.class, DataBufferUtils::release));

    }
}
