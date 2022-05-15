package com.springboot.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringbootBootstrapApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootBootstrapApplication.class, args);
//		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
//		String password= bCryptPasswordEncoder.encode("password123");//encrypt password
//		System.out.println(password);
	}

}
