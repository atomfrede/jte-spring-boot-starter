package io.github.atomfrede.jtebootstarter;

import gg.jte.TemplateEngine;
import org.springframework.web.servlet.view.AbstractTemplateView;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class JteViewResolver extends AbstractTemplateViewResolver {

    private TemplateEngine templateEngine;

    public JteViewResolver(TemplateEngine templateEngine) {
        this.setSuffix(".jte");
        this.setViewClass(JteView.class);
        this.templateEngine = templateEngine;
    }

    @Override
    protected AbstractUrlBasedView instantiateView() {
        return (getViewClass() == JteView.class ? new JteView(templateEngine) : super.instantiateView());
    }

    @Override
    protected Class<?> requiredViewClass() {
        return JteView.class;
    }
}
