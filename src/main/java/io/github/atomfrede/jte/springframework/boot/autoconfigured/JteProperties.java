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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@ConfigurationProperties(prefix = "gg.jte")
public class JteProperties {

    private String productionProfileName = "prod";
    private String templateLocation = "src/main/jte";

    public String getProductionProfileName() {
        return productionProfileName;
    }

    public void setProductionProfileName(String productionProfileName) {
        this.productionProfileName = productionProfileName;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

    public void setTemplateLocation(String templateLocation) {
        this.templateLocation = templateLocation;
    }

    public boolean isProductionEnabled(@NonNull Environment environment) {
        return Arrays.stream(environment.getActiveProfiles()).anyMatch(it -> it.equals(this.getProductionProfileName()));
    }
}
