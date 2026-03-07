package com.pm.billingservice.grpc;

import billing.BillingServiceGrpc.BillingServiceImplBase;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

  private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

  @Override
  public void createBillingAccount(
      billing.BillingRequest billingRequest,
      io.grpc.stub.StreamObserver<billing.BillingResponse> responseObserver) {
    // Implement the logic to create a billing account based on the billingRequest
    // For demonstration, we will return a simple response

    log.info("Received billing account creation request : {}", billingRequest.toString());
    String accountId = "account-" + System.currentTimeMillis(); // Generate a unique account ID
    String status = "ACTIVE";

    billing.BillingResponse response =
        billing.BillingResponse.newBuilder().setAccountId(accountId).setStatus(status).build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
