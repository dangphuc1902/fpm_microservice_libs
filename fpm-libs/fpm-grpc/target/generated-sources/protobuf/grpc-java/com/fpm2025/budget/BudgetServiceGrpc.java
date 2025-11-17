package com.fpm2025.budget;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: budget.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class BudgetServiceGrpc {

  private BudgetServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.fpm2025.budget.BudgetService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fpm2025.budget.ListBudgetsRequest,
      com.fpm2025.budget.ListBudgetsResponse> getListBudgetsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListBudgets",
      requestType = com.fpm2025.budget.ListBudgetsRequest.class,
      responseType = com.fpm2025.budget.ListBudgetsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.budget.ListBudgetsRequest,
      com.fpm2025.budget.ListBudgetsResponse> getListBudgetsMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.budget.ListBudgetsRequest, com.fpm2025.budget.ListBudgetsResponse> getListBudgetsMethod;
    if ((getListBudgetsMethod = BudgetServiceGrpc.getListBudgetsMethod) == null) {
      synchronized (BudgetServiceGrpc.class) {
        if ((getListBudgetsMethod = BudgetServiceGrpc.getListBudgetsMethod) == null) {
          BudgetServiceGrpc.getListBudgetsMethod = getListBudgetsMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.budget.ListBudgetsRequest, com.fpm2025.budget.ListBudgetsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListBudgets"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.budget.ListBudgetsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.budget.ListBudgetsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BudgetServiceMethodDescriptorSupplier("ListBudgets"))
              .build();
        }
      }
    }
    return getListBudgetsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.budget.SetBudgetRequest,
      com.fpm2025.budget.BudgetResponse> getSetBudgetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "SetBudget",
      requestType = com.fpm2025.budget.SetBudgetRequest.class,
      responseType = com.fpm2025.budget.BudgetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.budget.SetBudgetRequest,
      com.fpm2025.budget.BudgetResponse> getSetBudgetMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.budget.SetBudgetRequest, com.fpm2025.budget.BudgetResponse> getSetBudgetMethod;
    if ((getSetBudgetMethod = BudgetServiceGrpc.getSetBudgetMethod) == null) {
      synchronized (BudgetServiceGrpc.class) {
        if ((getSetBudgetMethod = BudgetServiceGrpc.getSetBudgetMethod) == null) {
          BudgetServiceGrpc.getSetBudgetMethod = getSetBudgetMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.budget.SetBudgetRequest, com.fpm2025.budget.BudgetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "SetBudget"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.budget.SetBudgetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.budget.BudgetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BudgetServiceMethodDescriptorSupplier("SetBudget"))
              .build();
        }
      }
    }
    return getSetBudgetMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.budget.CheckBudgetRequest,
      com.fpm2025.budget.CheckBudgetResponse> getCheckBudgetMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CheckBudget",
      requestType = com.fpm2025.budget.CheckBudgetRequest.class,
      responseType = com.fpm2025.budget.CheckBudgetResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.budget.CheckBudgetRequest,
      com.fpm2025.budget.CheckBudgetResponse> getCheckBudgetMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.budget.CheckBudgetRequest, com.fpm2025.budget.CheckBudgetResponse> getCheckBudgetMethod;
    if ((getCheckBudgetMethod = BudgetServiceGrpc.getCheckBudgetMethod) == null) {
      synchronized (BudgetServiceGrpc.class) {
        if ((getCheckBudgetMethod = BudgetServiceGrpc.getCheckBudgetMethod) == null) {
          BudgetServiceGrpc.getCheckBudgetMethod = getCheckBudgetMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.budget.CheckBudgetRequest, com.fpm2025.budget.CheckBudgetResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CheckBudget"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.budget.CheckBudgetRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.budget.CheckBudgetResponse.getDefaultInstance()))
              .setSchemaDescriptor(new BudgetServiceMethodDescriptorSupplier("CheckBudget"))
              .build();
        }
      }
    }
    return getCheckBudgetMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static BudgetServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BudgetServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BudgetServiceStub>() {
        @java.lang.Override
        public BudgetServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BudgetServiceStub(channel, callOptions);
        }
      };
    return BudgetServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static BudgetServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BudgetServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BudgetServiceBlockingStub>() {
        @java.lang.Override
        public BudgetServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BudgetServiceBlockingStub(channel, callOptions);
        }
      };
    return BudgetServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static BudgetServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<BudgetServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<BudgetServiceFutureStub>() {
        @java.lang.Override
        public BudgetServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new BudgetServiceFutureStub(channel, callOptions);
        }
      };
    return BudgetServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listBudgets(com.fpm2025.budget.ListBudgetsRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.budget.ListBudgetsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListBudgetsMethod(), responseObserver);
    }

    /**
     */
    default void setBudget(com.fpm2025.budget.SetBudgetRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.budget.BudgetResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getSetBudgetMethod(), responseObserver);
    }

    /**
     */
    default void checkBudget(com.fpm2025.budget.CheckBudgetRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.budget.CheckBudgetResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCheckBudgetMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service BudgetService.
   */
  public static abstract class BudgetServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return BudgetServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service BudgetService.
   */
  public static final class BudgetServiceStub
      extends io.grpc.stub.AbstractAsyncStub<BudgetServiceStub> {
    private BudgetServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BudgetServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BudgetServiceStub(channel, callOptions);
    }

    /**
     */
    public void listBudgets(com.fpm2025.budget.ListBudgetsRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.budget.ListBudgetsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListBudgetsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void setBudget(com.fpm2025.budget.SetBudgetRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.budget.BudgetResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getSetBudgetMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void checkBudget(com.fpm2025.budget.CheckBudgetRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.budget.CheckBudgetResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCheckBudgetMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service BudgetService.
   */
  public static final class BudgetServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<BudgetServiceBlockingStub> {
    private BudgetServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BudgetServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BudgetServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fpm2025.budget.ListBudgetsResponse listBudgets(com.fpm2025.budget.ListBudgetsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListBudgetsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.budget.BudgetResponse setBudget(com.fpm2025.budget.SetBudgetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getSetBudgetMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.budget.CheckBudgetResponse checkBudget(com.fpm2025.budget.CheckBudgetRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCheckBudgetMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service BudgetService.
   */
  public static final class BudgetServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<BudgetServiceFutureStub> {
    private BudgetServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected BudgetServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new BudgetServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.budget.ListBudgetsResponse> listBudgets(
        com.fpm2025.budget.ListBudgetsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListBudgetsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.budget.BudgetResponse> setBudget(
        com.fpm2025.budget.SetBudgetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getSetBudgetMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.budget.CheckBudgetResponse> checkBudget(
        com.fpm2025.budget.CheckBudgetRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCheckBudgetMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_BUDGETS = 0;
  private static final int METHODID_SET_BUDGET = 1;
  private static final int METHODID_CHECK_BUDGET = 2;

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
        case METHODID_LIST_BUDGETS:
          serviceImpl.listBudgets((com.fpm2025.budget.ListBudgetsRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.budget.ListBudgetsResponse>) responseObserver);
          break;
        case METHODID_SET_BUDGET:
          serviceImpl.setBudget((com.fpm2025.budget.SetBudgetRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.budget.BudgetResponse>) responseObserver);
          break;
        case METHODID_CHECK_BUDGET:
          serviceImpl.checkBudget((com.fpm2025.budget.CheckBudgetRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.budget.CheckBudgetResponse>) responseObserver);
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
          getListBudgetsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.budget.ListBudgetsRequest,
              com.fpm2025.budget.ListBudgetsResponse>(
                service, METHODID_LIST_BUDGETS)))
        .addMethod(
          getSetBudgetMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.budget.SetBudgetRequest,
              com.fpm2025.budget.BudgetResponse>(
                service, METHODID_SET_BUDGET)))
        .addMethod(
          getCheckBudgetMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.budget.CheckBudgetRequest,
              com.fpm2025.budget.CheckBudgetResponse>(
                service, METHODID_CHECK_BUDGET)))
        .build();
  }

  private static abstract class BudgetServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    BudgetServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fpm2025.budget.BudgetProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("BudgetService");
    }
  }

  private static final class BudgetServiceFileDescriptorSupplier
      extends BudgetServiceBaseDescriptorSupplier {
    BudgetServiceFileDescriptorSupplier() {}
  }

  private static final class BudgetServiceMethodDescriptorSupplier
      extends BudgetServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    BudgetServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (BudgetServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new BudgetServiceFileDescriptorSupplier())
              .addMethod(getListBudgetsMethod())
              .addMethod(getSetBudgetMethod())
              .addMethod(getCheckBudgetMethod())
              .build();
        }
      }
    }
    return result;
  }
}
