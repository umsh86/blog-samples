package com.eomdev.eurekaclient.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {

  @Autowired
  private RestTemplate restClient;

  @GetMapping("/user/{userId}")
  public ResponseEntity getUser(@PathVariable String userId) {
    User user = restClient.postForObject("http://user-service/user", userId, User.class);
    return ResponseEntity.ok(user);
  }

}
