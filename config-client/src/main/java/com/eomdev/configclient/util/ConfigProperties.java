package com.eomdev.configclient.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "custom")
@RefreshScope
@Getter @Setter
public class ConfigProperties {

  private String message;

}
