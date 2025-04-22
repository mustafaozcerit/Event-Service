package com.opthema.hcm.java_microservice.application.service.TimeAndAbsence;

import com.opthema.hcm.grpc.*;
import com.opthema.hcm.grpc.user.UserOuterClass.UserRequest;
import com.opthema.hcm.grpc.user.UserOuterClass.UserResponse;
import com.opthema.hcm.grpc.user.UserServiceGrpc;
import com.opthema.hcm.java_microservice.domain.model.AppUser;
import com.opthema.hcm.java_microservice.domain.model.Events;
import com.opthema.hcm.java_microservice.infrastructure.repository.EventsRepository;
import com.opthema.hcm.java_microservice.mapper.EventMapper;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@GrpcService
public class GrpcEventService extends EventServiceGrpc.EventServiceImplBase {

    @Autowired
    private EventsRepository eventRepository;
    @Autowired
    private EventMapper eventMapper;
    @Autowired
    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    // gRPC client için kanal oluşturma işlemi
    private ManagedChannel channel;

    @PostConstruct
    public void init() {
        String grpcServerAddress = "localhost:6565"; // gRPC sunucu adresinizi buraya ekleyin
        channel = ManagedChannelBuilder.forTarget(grpcServerAddress)
                .usePlaintext()
                .idleTimeout(5, TimeUnit.MINUTES) // Bağlantı zaman aşımını 5 dakikaya uzat
                .build();
    }

    @PreDestroy
    public void shutdown() {
        if (channel != null) {
            channel.shutdownNow();
        }
    }

    @Override
    public void createEvent(EventRequest request, StreamObserver<EventResponse> responseObserver) {
        // Kullanıcı ID'lerini al
        List<Long> userIds = request.getAppUserIdsList();

//         gRPC ile UserService'ten kullanıcıları al
        UserRequest userRequest = UserRequest.newBuilder()
                .addAllIds(userIds)
                .build();

        UserResponse userResponse = userStub.getUsers(userRequest);

        // Events entity'sine dönüştür ve kaydet
        Events event = new Events();
        event.setName(request.getName());
        event.setDate(request.getDate());
        event.setUserOrGroupType(request.getUserOrGroupType());
        event.setUserOrGroupsFilterValue(request.getUserOrGroupsFilterValue());
        event.setAppUserIds(userIds.stream().mapToLong(Long::longValue).toArray());

        eventRepository.save(event);

        // gRPC response için kullanıcıları ekle
        EventResponse.Builder responseBuilder = EventResponse.newBuilder()
                .setMessage("Event '" + request.getName() + "' created successfully");


        UserRequest userRequests = UserRequest.newBuilder()
                .addAllIds(userIds)
                .build();

        // UserResponse'ten kullanıcıları ekleyin
            responseBuilder.addAllUsers(Collections.singleton(userRequests));

        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}


