package io.github.atomfrede.jtebootstarter;

import gg.jte.TemplateEngine;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

public class ReactiveJteViewResolver extends UrlBasedViewResolver {

    private TemplateEngine templateEngine;

    public ReactiveJteViewResolver(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.setSuffix(".jte");
        this.setViewClass(JteView.class);
        this.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return ReactiveJteView.class;
    }

    @Override
    protected AbstractUrlBasedView instantiateView() {
        return (getViewClass() == ReactiveJteView.class ? new ReactiveJteView(templateEngine) : super.instantiateView());
    }
}
