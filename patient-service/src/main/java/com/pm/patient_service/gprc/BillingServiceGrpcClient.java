package com.pm.patient_service.gprc;

import billing.BillingServiceGrpc;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Service;

@Service
public class BillingServiceGrpcClient {


    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;

    // localhost:9001/BillingService/CreatePatientAccount
    public BillingServiceGrpcClient(
        @Value("${billing.service.address:localhost}") String serverAddress,
        @Value("${billing.service.grpc.port:9001}") int serverPort;
    ){

    }

}
