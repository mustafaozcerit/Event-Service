package com.opthema.hcm.java_microservice.config;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import org.springframework.stereotype.Component;

@Component
public class GrpcServerInterceptor implements ServerInterceptor {

    // Authorization başlığını almak için doğru Key tanımını yapıyoruz
    private static final Metadata.Key<String> AUTHORIZATION_KEY = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER);

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        // Authorization başlığını alıyoruz
        String authHeader = headers.get(AUTHORIZATION_KEY);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            // Token doğrulama işlemi yapılabilir, örneğin:
            // if (jwtService.validateToken(token)) {
            //     // Doğru token, işlemi devam ettir
            // }
        } else {
            // Authorization başlığı yoksa veya yanlışsa hata dönebiliriz
        }

        // Devam etmek için gRPC çağrısını devam ettir
        return next.startCall(call, headers);
    }
}

