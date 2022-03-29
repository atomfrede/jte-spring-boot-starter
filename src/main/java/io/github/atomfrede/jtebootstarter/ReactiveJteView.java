package io.github.atomfrede.jtebootstarter;

import gg.jte.TemplateEngine;
import gg.jte.output.Utf8ByteOutput;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.PooledDataBuffer;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.servlet.view.AbstractTemplateView;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Map;

public class ReactiveJteView extends AbstractUrlBasedView {

    private final TemplateEngine templateEngine;

    public ReactiveJteView(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public boolean checkResourceExists(Locale locale) throws Exception {
        return false;
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
