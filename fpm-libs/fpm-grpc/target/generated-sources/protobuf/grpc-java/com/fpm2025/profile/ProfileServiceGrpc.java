package com.fpm2025.profile;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: profile.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ProfileServiceGrpc {

  private ProfileServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.fpm2025.profile.ProfileService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fpm2025.profile.GetProfileRequest,
      com.fpm2025.profile.ProfileResponse> getGetProfileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetProfile",
      requestType = com.fpm2025.profile.GetProfileRequest.class,
      responseType = com.fpm2025.profile.ProfileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.profile.GetProfileRequest,
      com.fpm2025.profile.ProfileResponse> getGetProfileMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.profile.GetProfileRequest, com.fpm2025.profile.ProfileResponse> getGetProfileMethod;
    if ((getGetProfileMethod = ProfileServiceGrpc.getGetProfileMethod) == null) {
      synchronized (ProfileServiceGrpc.class) {
        if ((getGetProfileMethod = ProfileServiceGrpc.getGetProfileMethod) == null) {
          ProfileServiceGrpc.getGetProfileMethod = getGetProfileMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.profile.GetProfileRequest, com.fpm2025.profile.ProfileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetProfile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.profile.GetProfileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.profile.ProfileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProfileServiceMethodDescriptorSupplier("GetProfile"))
              .build();
        }
      }
    }
    return getGetProfileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.profile.UpdateProfileRequest,
      com.fpm2025.profile.ProfileResponse> getUpdateProfileMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateProfile",
      requestType = com.fpm2025.profile.UpdateProfileRequest.class,
      responseType = com.fpm2025.profile.ProfileResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.profile.UpdateProfileRequest,
      com.fpm2025.profile.ProfileResponse> getUpdateProfileMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.profile.UpdateProfileRequest, com.fpm2025.profile.ProfileResponse> getUpdateProfileMethod;
    if ((getUpdateProfileMethod = ProfileServiceGrpc.getUpdateProfileMethod) == null) {
      synchronized (ProfileServiceGrpc.class) {
        if ((getUpdateProfileMethod = ProfileServiceGrpc.getUpdateProfileMethod) == null) {
          ProfileServiceGrpc.getUpdateProfileMethod = getUpdateProfileMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.profile.UpdateProfileRequest, com.fpm2025.profile.ProfileResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateProfile"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.profile.UpdateProfileRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.profile.ProfileResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProfileServiceMethodDescriptorSupplier("UpdateProfile"))
              .build();
        }
      }
    }
    return getUpdateProfileMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.profile.ChangeThemeRequest,
      com.fpm2025.profile.ThemeResponse> getChangeThemeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ChangeTheme",
      requestType = com.fpm2025.profile.ChangeThemeRequest.class,
      responseType = com.fpm2025.profile.ThemeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.profile.ChangeThemeRequest,
      com.fpm2025.profile.ThemeResponse> getChangeThemeMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.profile.ChangeThemeRequest, com.fpm2025.profile.ThemeResponse> getChangeThemeMethod;
    if ((getChangeThemeMethod = ProfileServiceGrpc.getChangeThemeMethod) == null) {
      synchronized (ProfileServiceGrpc.class) {
        if ((getChangeThemeMethod = ProfileServiceGrpc.getChangeThemeMethod) == null) {
          ProfileServiceGrpc.getChangeThemeMethod = getChangeThemeMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.profile.ChangeThemeRequest, com.fpm2025.profile.ThemeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ChangeTheme"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.profile.ChangeThemeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.profile.ThemeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ProfileServiceMethodDescriptorSupplier("ChangeTheme"))
              .build();
        }
      }
    }
    return getChangeThemeMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ProfileServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProfileServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProfileServiceStub>() {
        @java.lang.Override
        public ProfileServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProfileServiceStub(channel, callOptions);
        }
      };
    return ProfileServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ProfileServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProfileServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProfileServiceBlockingStub>() {
        @java.lang.Override
        public ProfileServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProfileServiceBlockingStub(channel, callOptions);
        }
      };
    return ProfileServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ProfileServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ProfileServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ProfileServiceFutureStub>() {
        @java.lang.Override
        public ProfileServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ProfileServiceFutureStub(channel, callOptions);
        }
      };
    return ProfileServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void getProfile(com.fpm2025.profile.GetProfileRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.profile.ProfileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetProfileMethod(), responseObserver);
    }

    /**
     */
    default void updateProfile(com.fpm2025.profile.UpdateProfileRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.profile.ProfileResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateProfileMethod(), responseObserver);
    }

    /**
     */
    default void changeTheme(com.fpm2025.profile.ChangeThemeRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.profile.ThemeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getChangeThemeMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ProfileService.
   */
  public static abstract class ProfileServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ProfileServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ProfileService.
   */
  public static final class ProfileServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ProfileServiceStub> {
    private ProfileServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProfileServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProfileServiceStub(channel, callOptions);
    }

    /**
     */
    public void getProfile(com.fpm2025.profile.GetProfileRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.profile.ProfileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetProfileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateProfile(com.fpm2025.profile.UpdateProfileRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.profile.ProfileResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateProfileMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void changeTheme(com.fpm2025.profile.ChangeThemeRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.profile.ThemeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getChangeThemeMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ProfileService.
   */
  public static final class ProfileServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ProfileServiceBlockingStub> {
    private ProfileServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProfileServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProfileServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fpm2025.profile.ProfileResponse getProfile(com.fpm2025.profile.GetProfileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetProfileMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.profile.ProfileResponse updateProfile(com.fpm2025.profile.UpdateProfileRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateProfileMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.profile.ThemeResponse changeTheme(com.fpm2025.profile.ChangeThemeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getChangeThemeMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ProfileService.
   */
  public static final class ProfileServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ProfileServiceFutureStub> {
    private ProfileServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ProfileServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ProfileServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.profile.ProfileResponse> getProfile(
        com.fpm2025.profile.GetProfileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetProfileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.profile.ProfileResponse> updateProfile(
        com.fpm2025.profile.UpdateProfileRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateProfileMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.profile.ThemeResponse> changeTheme(
        com.fpm2025.profile.ChangeThemeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getChangeThemeMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PROFILE = 0;
  private static final int METHODID_UPDATE_PROFILE = 1;
  private static final int METHODID_CHANGE_THEME = 2;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PROFILE:
          serviceImpl.getProfile((com.fpm2025.profile.GetProfileRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.profile.ProfileResponse>) responseObserver);
          break;
        case METHODID_UPDATE_PROFILE:
          serviceImpl.updateProfile((com.fpm2025.profile.UpdateProfileRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.profile.ProfileResponse>) responseObserver);
          break;
        case METHODID_CHANGE_THEME:
          serviceImpl.changeTheme((com.fpm2025.profile.ChangeThemeRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.profile.ThemeResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getGetProfileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.profile.GetProfileRequest,
              com.fpm2025.profile.ProfileResponse>(
                service, METHODID_GET_PROFILE)))
        .addMethod(
          getUpdateProfileMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.profile.UpdateProfileRequest,
              com.fpm2025.profile.ProfileResponse>(
                service, METHODID_UPDATE_PROFILE)))
        .addMethod(
          getChangeThemeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.profile.ChangeThemeRequest,
              com.fpm2025.profile.ThemeResponse>(
                service, METHODID_CHANGE_THEME)))
        .build();
  }

  private static abstract class ProfileServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ProfileServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fpm2025.profile.ProfileProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ProfileService");
    }
  }

  private static final class ProfileServiceFileDescriptorSupplier
      extends ProfileServiceBaseDescriptorSupplier {
    ProfileServiceFileDescriptorSupplier() {}
  }

  private static final class ProfileServiceMethodDescriptorSupplier
      extends ProfileServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ProfileServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (ProfileServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ProfileServiceFileDescriptorSupplier())
              .addMethod(getGetProfileMethod())
              .addMethod(getUpdateProfileMethod())
              .addMethod(getChangeThemeMethod())
              .build();
        }
      }
    }
    return result;
  }
}
