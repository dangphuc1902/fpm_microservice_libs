package com.fpm2025.grpc.interceptor;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GrpcLoggingInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String methodName = call.getMethodDescriptor().getFullMethodName();
        log.info("gRPC request received: {}", methodName);

        long startTime = System.currentTimeMillis();

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT>(
                next.startCall(new ForwardingServerCall.SimpleForwardingServerCall<ReqT, RespT>(call) {
                    @Override
                    public void close(Status status, Metadata trailers) {
                        long duration = System.currentTimeMillis() - startTime;
                        log.info("gRPC request completed: {} - Status: {} - Duration: {}ms",
                                methodName, status.getCode(), duration);
                        super.close(status, trailers);
                    }
                }, headers)) {
        };
    }
}
