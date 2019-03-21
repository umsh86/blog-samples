package com.eomdev.configclient.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigController {

  @Autowired
  private ConfigProperties configProperties;

  @GetMapping("/config")
  public ResponseEntity getConfig() {
    return ResponseEntity.ok(configProperties.getMessage());
  }


}
