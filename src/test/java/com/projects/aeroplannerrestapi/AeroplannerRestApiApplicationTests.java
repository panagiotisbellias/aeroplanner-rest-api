package com.projects.aeroplannerrestapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Disabled("SMTP info must be specified in the testing application.yml")
@SpringBootTest(classes = AeroplannerRestApiApplication.class)
class AeroplannerRestApiApplicationTests {

	@Test
	void contextLoads() {
		Assertions.assertTrue(true);
	}

}
