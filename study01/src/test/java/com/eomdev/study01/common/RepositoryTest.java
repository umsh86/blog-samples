package com.eomdev.study01.common;

import org.junit.Ignore;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@Ignore
public class RepositoryTest {

}
