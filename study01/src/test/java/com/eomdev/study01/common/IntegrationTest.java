package com.eomdev.study01.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
//@WebMvcTest
//@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Ignore
public class IntegrationTest {

  @Autowired
  protected MockMvc mockMvc;

  @Autowired
  protected ObjectMapper objectMapper;



}
