package com.fpm2025.security.grpc;

import com.fpm2025.security.jwt.JwtTokenProvider;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@GrpcGlobalServerInterceptor
@RequiredArgsConstructor
public class GrpcAuthInterceptor implements ServerInterceptor {

    private static final String AUTHORIZATION_HEADER = "authorization";
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT, RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        String token = headers.get(Metadata.Key.of(AUTHORIZATION_HEADER, Metadata.ASCII_STRING_MARSHALLER));

        if (token != null) {
            token = jwtTokenProvider.extractTokenFromHeader(token);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Long userId = jwtTokenProvider.extractUserId(token);
                String email = jwtTokenProvider.extractEmail(token);
                
                Context context = Context.current()
                        .withValue(GrpcSecurityContext.USER_ID_KEY, userId)
                        .withValue(GrpcSecurityContext.EMAIL_KEY, email);
                
                return Contexts.interceptCall(context, call, headers, next);
            }
        }

        return next.startCall(call, headers);
    }
}