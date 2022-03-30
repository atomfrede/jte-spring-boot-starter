package io.github.atomfrede.jtebootstarter;

import gg.jte.TemplateEngine;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

public class JteViewResolver extends AbstractTemplateViewResolver {

    private final TemplateEngine templateEngine;

    public JteViewResolver(TemplateEngine templateEngine) {
        this.setSuffix(".jte");
        this.setViewClass(JteView.class);
        this.templateEngine = templateEngine;
    }

    @Override
    protected AbstractUrlBasedView instantiateView() {
        return new JteView(templateEngine);
    }

    @Override
    protected Class<?> requiredViewClass() {
        return JteView.class;
    }
}
