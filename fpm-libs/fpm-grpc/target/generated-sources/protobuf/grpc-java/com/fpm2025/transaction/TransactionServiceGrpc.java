package com.fpm2025.transaction;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: transaction.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class TransactionServiceGrpc {

  private TransactionServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.fpm2025.transaction.TransactionService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fpm2025.transaction.ListTransactionsRequest,
      com.fpm2025.transaction.ListTransactionsResponse> getListTransactionsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListTransactions",
      requestType = com.fpm2025.transaction.ListTransactionsRequest.class,
      responseType = com.fpm2025.transaction.ListTransactionsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.transaction.ListTransactionsRequest,
      com.fpm2025.transaction.ListTransactionsResponse> getListTransactionsMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.transaction.ListTransactionsRequest, com.fpm2025.transaction.ListTransactionsResponse> getListTransactionsMethod;
    if ((getListTransactionsMethod = TransactionServiceGrpc.getListTransactionsMethod) == null) {
      synchronized (TransactionServiceGrpc.class) {
        if ((getListTransactionsMethod = TransactionServiceGrpc.getListTransactionsMethod) == null) {
          TransactionServiceGrpc.getListTransactionsMethod = getListTransactionsMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.transaction.ListTransactionsRequest, com.fpm2025.transaction.ListTransactionsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListTransactions"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.ListTransactionsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.ListTransactionsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TransactionServiceMethodDescriptorSupplier("ListTransactions"))
              .build();
        }
      }
    }
    return getListTransactionsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.transaction.GetTransactionRequest,
      com.fpm2025.transaction.Transaction> getGetTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetTransaction",
      requestType = com.fpm2025.transaction.GetTransactionRequest.class,
      responseType = com.fpm2025.transaction.Transaction.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.transaction.GetTransactionRequest,
      com.fpm2025.transaction.Transaction> getGetTransactionMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.transaction.GetTransactionRequest, com.fpm2025.transaction.Transaction> getGetTransactionMethod;
    if ((getGetTransactionMethod = TransactionServiceGrpc.getGetTransactionMethod) == null) {
      synchronized (TransactionServiceGrpc.class) {
        if ((getGetTransactionMethod = TransactionServiceGrpc.getGetTransactionMethod) == null) {
          TransactionServiceGrpc.getGetTransactionMethod = getGetTransactionMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.transaction.GetTransactionRequest, com.fpm2025.transaction.Transaction>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.GetTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.Transaction.getDefaultInstance()))
              .setSchemaDescriptor(new TransactionServiceMethodDescriptorSupplier("GetTransaction"))
              .build();
        }
      }
    }
    return getGetTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.transaction.CreateTransactionRequest,
      com.fpm2025.transaction.TransactionResponse> getCreateTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateTransaction",
      requestType = com.fpm2025.transaction.CreateTransactionRequest.class,
      responseType = com.fpm2025.transaction.TransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.transaction.CreateTransactionRequest,
      com.fpm2025.transaction.TransactionResponse> getCreateTransactionMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.transaction.CreateTransactionRequest, com.fpm2025.transaction.TransactionResponse> getCreateTransactionMethod;
    if ((getCreateTransactionMethod = TransactionServiceGrpc.getCreateTransactionMethod) == null) {
      synchronized (TransactionServiceGrpc.class) {
        if ((getCreateTransactionMethod = TransactionServiceGrpc.getCreateTransactionMethod) == null) {
          TransactionServiceGrpc.getCreateTransactionMethod = getCreateTransactionMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.transaction.CreateTransactionRequest, com.fpm2025.transaction.TransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.CreateTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.TransactionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TransactionServiceMethodDescriptorSupplier("CreateTransaction"))
              .build();
        }
      }
    }
    return getCreateTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.transaction.UpdateTransactionRequest,
      com.fpm2025.transaction.TransactionResponse> getUpdateTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateTransaction",
      requestType = com.fpm2025.transaction.UpdateTransactionRequest.class,
      responseType = com.fpm2025.transaction.TransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.transaction.UpdateTransactionRequest,
      com.fpm2025.transaction.TransactionResponse> getUpdateTransactionMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.transaction.UpdateTransactionRequest, com.fpm2025.transaction.TransactionResponse> getUpdateTransactionMethod;
    if ((getUpdateTransactionMethod = TransactionServiceGrpc.getUpdateTransactionMethod) == null) {
      synchronized (TransactionServiceGrpc.class) {
        if ((getUpdateTransactionMethod = TransactionServiceGrpc.getUpdateTransactionMethod) == null) {
          TransactionServiceGrpc.getUpdateTransactionMethod = getUpdateTransactionMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.transaction.UpdateTransactionRequest, com.fpm2025.transaction.TransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.UpdateTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.TransactionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TransactionServiceMethodDescriptorSupplier("UpdateTransaction"))
              .build();
        }
      }
    }
    return getUpdateTransactionMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.transaction.DeleteTransactionRequest,
      com.fpm2025.transaction.TransactionResponse> getDeleteTransactionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteTransaction",
      requestType = com.fpm2025.transaction.DeleteTransactionRequest.class,
      responseType = com.fpm2025.transaction.TransactionResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.transaction.DeleteTransactionRequest,
      com.fpm2025.transaction.TransactionResponse> getDeleteTransactionMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.transaction.DeleteTransactionRequest, com.fpm2025.transaction.TransactionResponse> getDeleteTransactionMethod;
    if ((getDeleteTransactionMethod = TransactionServiceGrpc.getDeleteTransactionMethod) == null) {
      synchronized (TransactionServiceGrpc.class) {
        if ((getDeleteTransactionMethod = TransactionServiceGrpc.getDeleteTransactionMethod) == null) {
          TransactionServiceGrpc.getDeleteTransactionMethod = getDeleteTransactionMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.transaction.DeleteTransactionRequest, com.fpm2025.transaction.TransactionResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteTransaction"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.DeleteTransactionRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.transaction.TransactionResponse.getDefaultInstance()))
              .setSchemaDescriptor(new TransactionServiceMethodDescriptorSupplier("DeleteTransaction"))
              .build();
        }
      }
    }
    return getDeleteTransactionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static TransactionServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TransactionServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TransactionServiceStub>() {
        @java.lang.Override
        public TransactionServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TransactionServiceStub(channel, callOptions);
        }
      };
    return TransactionServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static TransactionServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TransactionServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TransactionServiceBlockingStub>() {
        @java.lang.Override
        public TransactionServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TransactionServiceBlockingStub(channel, callOptions);
        }
      };
    return TransactionServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static TransactionServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<TransactionServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<TransactionServiceFutureStub>() {
        @java.lang.Override
        public TransactionServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new TransactionServiceFutureStub(channel, callOptions);
        }
      };
    return TransactionServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listTransactions(com.fpm2025.transaction.ListTransactionsRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.ListTransactionsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListTransactionsMethod(), responseObserver);
    }

    /**
     */
    default void getTransaction(com.fpm2025.transaction.GetTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.Transaction> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetTransactionMethod(), responseObserver);
    }

    /**
     */
    default void createTransaction(com.fpm2025.transaction.CreateTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateTransactionMethod(), responseObserver);
    }

    /**
     */
    default void updateTransaction(com.fpm2025.transaction.UpdateTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateTransactionMethod(), responseObserver);
    }

    /**
     */
    default void deleteTransaction(com.fpm2025.transaction.DeleteTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteTransactionMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service TransactionService.
   */
  public static abstract class TransactionServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return TransactionServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service TransactionService.
   */
  public static final class TransactionServiceStub
      extends io.grpc.stub.AbstractAsyncStub<TransactionServiceStub> {
    private TransactionServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TransactionServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TransactionServiceStub(channel, callOptions);
    }

    /**
     */
    public void listTransactions(com.fpm2025.transaction.ListTransactionsRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.ListTransactionsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListTransactionsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getTransaction(com.fpm2025.transaction.GetTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.Transaction> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createTransaction(com.fpm2025.transaction.CreateTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateTransaction(com.fpm2025.transaction.UpdateTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateTransactionMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteTransaction(com.fpm2025.transaction.DeleteTransactionRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteTransactionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service TransactionService.
   */
  public static final class TransactionServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<TransactionServiceBlockingStub> {
    private TransactionServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TransactionServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TransactionServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fpm2025.transaction.ListTransactionsResponse listTransactions(com.fpm2025.transaction.ListTransactionsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListTransactionsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.transaction.Transaction getTransaction(com.fpm2025.transaction.GetTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.transaction.TransactionResponse createTransaction(com.fpm2025.transaction.CreateTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.transaction.TransactionResponse updateTransaction(com.fpm2025.transaction.UpdateTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateTransactionMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.transaction.TransactionResponse deleteTransaction(com.fpm2025.transaction.DeleteTransactionRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteTransactionMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service TransactionService.
   */
  public static final class TransactionServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<TransactionServiceFutureStub> {
    private TransactionServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected TransactionServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new TransactionServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.transaction.ListTransactionsResponse> listTransactions(
        com.fpm2025.transaction.ListTransactionsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListTransactionsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.transaction.Transaction> getTransaction(
        com.fpm2025.transaction.GetTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.transaction.TransactionResponse> createTransaction(
        com.fpm2025.transaction.CreateTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.transaction.TransactionResponse> updateTransaction(
        com.fpm2025.transaction.UpdateTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateTransactionMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.transaction.TransactionResponse> deleteTransaction(
        com.fpm2025.transaction.DeleteTransactionRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteTransactionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_TRANSACTIONS = 0;
  private static final int METHODID_GET_TRANSACTION = 1;
  private static final int METHODID_CREATE_TRANSACTION = 2;
  private static final int METHODID_UPDATE_TRANSACTION = 3;
  private static final int METHODID_DELETE_TRANSACTION = 4;

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
        case METHODID_LIST_TRANSACTIONS:
          serviceImpl.listTransactions((com.fpm2025.transaction.ListTransactionsRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.transaction.ListTransactionsResponse>) responseObserver);
          break;
        case METHODID_GET_TRANSACTION:
          serviceImpl.getTransaction((com.fpm2025.transaction.GetTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.transaction.Transaction>) responseObserver);
          break;
        case METHODID_CREATE_TRANSACTION:
          serviceImpl.createTransaction((com.fpm2025.transaction.CreateTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse>) responseObserver);
          break;
        case METHODID_UPDATE_TRANSACTION:
          serviceImpl.updateTransaction((com.fpm2025.transaction.UpdateTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse>) responseObserver);
          break;
        case METHODID_DELETE_TRANSACTION:
          serviceImpl.deleteTransaction((com.fpm2025.transaction.DeleteTransactionRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.transaction.TransactionResponse>) responseObserver);
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
          getListTransactionsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.transaction.ListTransactionsRequest,
              com.fpm2025.transaction.ListTransactionsResponse>(
                service, METHODID_LIST_TRANSACTIONS)))
        .addMethod(
          getGetTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.transaction.GetTransactionRequest,
              com.fpm2025.transaction.Transaction>(
                service, METHODID_GET_TRANSACTION)))
        .addMethod(
          getCreateTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.transaction.CreateTransactionRequest,
              com.fpm2025.transaction.TransactionResponse>(
                service, METHODID_CREATE_TRANSACTION)))
        .addMethod(
          getUpdateTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.transaction.UpdateTransactionRequest,
              com.fpm2025.transaction.TransactionResponse>(
                service, METHODID_UPDATE_TRANSACTION)))
        .addMethod(
          getDeleteTransactionMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.transaction.DeleteTransactionRequest,
              com.fpm2025.transaction.TransactionResponse>(
                service, METHODID_DELETE_TRANSACTION)))
        .build();
  }

  private static abstract class TransactionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    TransactionServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fpm2025.transaction.TransactionProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("TransactionService");
    }
  }

  private static final class TransactionServiceFileDescriptorSupplier
      extends TransactionServiceBaseDescriptorSupplier {
    TransactionServiceFileDescriptorSupplier() {}
  }

  private static final class TransactionServiceMethodDescriptorSupplier
      extends TransactionServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    TransactionServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (TransactionServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new TransactionServiceFileDescriptorSupplier())
              .addMethod(getListTransactionsMethod())
              .addMethod(getGetTransactionMethod())
              .addMethod(getCreateTransactionMethod())
              .addMethod(getUpdateTransactionMethod())
              .addMethod(getDeleteTransactionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
