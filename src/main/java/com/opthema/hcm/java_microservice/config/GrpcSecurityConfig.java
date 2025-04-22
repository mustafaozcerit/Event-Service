package com.opthema.hcm.java_microservice.config;

import com.opthema.hcm.grpc.EventServiceGrpc;
import com.opthema.hcm.grpc.user.UserServiceGrpc;
import com.opthema.hcm.java_microservice.application.service.JwtService;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import net.devh.boot.grpc.server.autoconfigure.GrpcServerSecurityAutoConfiguration;
import net.devh.boot.grpc.server.security.authentication.GrpcAuthenticationReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Configuration
public class GrpcSecurityConfig {

    @Value("${grpc.client.user-service.address}")
    private String grpcUserServiceAddress;

    @Value("${grpc.client.event-service.address}")
    private String grpcEventServiceAddress;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private JwtService jwtService;

    @Bean
    public GrpcServerSecurityAutoConfiguration grpcServerSecurityAutoConfiguration() {
        return new GrpcServerSecurityAutoConfiguration();
    }

    @Bean
    public UserServiceGrpc.UserServiceBlockingStub userServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(grpcUserServiceAddress)
                .usePlaintext()
                .build();
        return UserServiceGrpc.newBlockingStub(channel);
    }

    @Bean
    public EventServiceGrpc.EventServiceBlockingStub eventServiceBlockingStub() {
        ManagedChannel channel = ManagedChannelBuilder.forTarget(grpcEventServiceAddress)
                .usePlaintext()
                .build();
        return EventServiceGrpc.newBlockingStub(channel);
    }
}


