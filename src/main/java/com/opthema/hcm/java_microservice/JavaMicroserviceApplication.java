package com.opthema.hcm.java_microservice;

import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(exclude = GrpcServerSecurityAutoConfiguration.class)
public class JavaMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaMicroserviceApplication.class, args);
	}
}
