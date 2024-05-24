package com.projects.aeroplannerrestapi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AeroplannerRestApiApplication {

	private static final Log LOG = LogFactory.getLog(AeroplannerRestApiApplication.class);

	public static void main(String[] args) {
		LOG.info("Main process started");
		SpringApplication.run(AeroplannerRestApiApplication.class, args);
		LOG.info("Main process is up");
	}

}
