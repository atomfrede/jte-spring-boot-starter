package io.github.atomfrede.jtebootstarter;

import org.springframework.boot.context.properties.ConfigurationProperties;

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
}
