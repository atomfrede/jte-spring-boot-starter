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

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.result.view.UrlBasedViewResolver;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ConditionalOnClass({TemplateEngine.class, UrlBasedViewResolver.class})
@EnableConfigurationProperties(JteProperties.class)
public class ReactiveJteAutoConfiguration {

    @Autowired
    private Environment environment;

    @Autowired
    private JteProperties jteProperties;

    @Bean
    @ConditionalOnMissingBean(ReactiveJteViewResolver.class)
    public ReactiveJteViewResolver reactiveJteViewResolver(TemplateEngine templateEngine) {

        return new ReactiveJteViewResolver(templateEngine);
    }

    @Bean
    @ConditionalOnMissingBean(TemplateEngine.class)
    public TemplateEngine jteTemplateEngine() {

        if (jteProperties.isProductionEnabled(environment)) {
            // Templates will be compiled by the maven build task
            return TemplateEngine.createPrecompiled(ContentType.Html);
        } else {
            // Here, a JTE file watcher will recompile the JTE templates upon file save (the web browser will auto-refresh)
            // If using IntelliJ, use Ctrl-F9 to trigger an auto-refresh when editing non-JTE files.
            String[] split = jteProperties.getTemplateLocation().split("/");
            CodeResolver codeResolver = new DirectoryCodeResolver(Path.of("", split));
            TemplateEngine templateEngine = TemplateEngine.create(codeResolver, Paths.get("jte-classes"), ContentType.Html, getClass().getClassLoader());
            templateEngine.setBinaryStaticContent(true);
            return templateEngine;
        }
    }
}
