package com.udea.mibanco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.udea.mibanco")
public class MibancoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MibancoApplication.class, args);
	}

}
