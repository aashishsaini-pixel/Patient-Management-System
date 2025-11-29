package com.pm.patient_service.grpc;

import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BillingServiceGrpcClient {


//     This is the object used to call GRPC methods.

//     Since it’s a blocking stub:

//     ✔ The call waits until the server replies

//     ✔ Good for synchronous workflows
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;


// Constructor (where channel + stub are created)

    public BillingServiceGrpcClient(
            @Value("${billing.service.address:localhost}") String serverAddress,
            @Value("${billing.service.grpc.port:9001}") int serverPort) {

        log.info("Connecting to Billing Service GRPC service at {}:{}",
                serverAddress, serverPort);

//           This creates a TCP connection to the Billing GRPC service.
//
//          .forAddress(address, port) → set remote service location
//
//          .usePlaintext() → disables HTTPS (common in internal microservices)
//
//           .build() → finalize and create channel

        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress,
                serverPort).usePlaintext().build();


//        This creates the client that can call:
//
//        createBillingAccount()
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);
    }


//  This method is used by your Patient Service when a patient is created.

    public BillingResponse createBillingAccount(String patientId, String name, String email) {

        BillingRequest request = BillingRequest.newBuilder()
                .setPatientId(patientId)
                .setName(name).setEmail(email).build();

//        Call the Billing Service GRPC server
//        This calls the method defined in your proto
        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received response from billing service via GRPC: {}", response);
        return response;
    }

}
