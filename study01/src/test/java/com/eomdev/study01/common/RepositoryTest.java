package com.eomdev.study01.common;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Disabled
public class RepositoryTest {

}
