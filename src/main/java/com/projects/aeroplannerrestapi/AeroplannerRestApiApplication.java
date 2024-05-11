package com.projects.aeroplannerrestapi;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class AeroplannerRestApiApplication {

	private static final Log LOG = LogFactory.getLog(AeroplannerRestApiApplication.class);

	public static void main(String[] args) {
		LOG.debug(String.format("main(%s)", Arrays.toString(args)));
		LOG.info("Main process is being started");
		SpringApplication.run(AeroplannerRestApiApplication.class, args);
		LOG.info("Main process is being stopped");
	}

}
