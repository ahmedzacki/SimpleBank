package com.ahmed.simpleBank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

@SpringBootApplication
public class SimpleBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleBankApplication.class, args);
	}

	/**
	 * This method creates a Logger that can be autowired in other classes:{@code
	 *        @Autowired
	 *        private Logger logger
	 *        }
	 * @paramip
	 * @return
	 */
	@Bean
	@Scope("prototype")
	@Profile({"dev", "prod"}) // The active profile is set in application.properties
	public Logger logger(InjectionPoint ip){
		Class<?> classThatWantsALogger = ip.getField().getDeclaringClass();
		return LoggerFactory.getLogger(classThatWantsALogger);
	}

	/**
	 * This method creates a singleton mock Logger that can be autowired in test classes:{@code
	 * 		@MockitoBean
	 * 		private Logger logger
	 * 		}
	 * @paramip
	 * @return
	 */
	@Bean
	@Scope("singleton")  // @MockitoBean in the web layer test requires a singleton
	@Profile("test")	 // The active profile is set in application.properties
	Logger mockLogger() {
		return LoggerFactory.getLogger(getClass().getPackageName());
	}

}
