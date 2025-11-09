package com.fpm2025.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GrpcErrorInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                    @Override
                    public void close(Status status, Metadata trailers) {
                        if (!status.isOk()) {
                            log.error("gRPC call failed: {} - {}", status.getCode(), status.getDescription());
                        }
                        super.close(status, trailers);
                    }
                }, headers)) {

            @Override
            public void onMessage(ReqT message) {
                try {
                    super.onMessage(message);
                } catch (Exception e) {
                    log.error("Error processing gRPC message", e);
                    call.close(
                            Status.INTERNAL
                                    .withDescription("Internal server error: " + e.getMessage())
                                    .withCause(e),
                            new Metadata()
                    );
                }
            }

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception e) {
                    log.error("Error in gRPC half close", e);
                    call.close(
                            Status.INTERNAL
                                    .withDescription("Internal server error")
                                    .withCause(e),
                            new Metadata()
                    );
                }
            }
        };
    }
}
