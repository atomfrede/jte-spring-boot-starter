package io.github.atomfrede.jtebootstarter;

import gg.jte.TemplateEngine;
import org.springframework.web.reactive.result.view.AbstractUrlBasedView;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

public class ReactiveJteViewResolver extends UrlBasedViewResolver {

    private TemplateEngine templateEngine;

    public ReactiveJteViewResolver(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        this.setViewClass(ReactiveJteView.class);
        this.setSuffix(".jte");
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
