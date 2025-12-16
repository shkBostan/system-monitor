package com.shkbostan.system_monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * Created on Dec, 2025
 *
 * @author s Bostan
 */

@EnableScheduling
@SpringBootApplication
public class SystemMonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(SystemMonitorApplication.class, args);
	}

}
