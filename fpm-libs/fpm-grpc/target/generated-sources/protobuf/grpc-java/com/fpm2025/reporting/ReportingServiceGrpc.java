package com.fpm2025.reporting;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: reporting.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class ReportingServiceGrpc {

  private ReportingServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.fpm2025.reporting.ReportingService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fpm2025.reporting.GenerateReportRequest,
      com.fpm2025.reporting.ReportResponse> getGenerateReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GenerateReport",
      requestType = com.fpm2025.reporting.GenerateReportRequest.class,
      responseType = com.fpm2025.reporting.ReportResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.reporting.GenerateReportRequest,
      com.fpm2025.reporting.ReportResponse> getGenerateReportMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.reporting.GenerateReportRequest, com.fpm2025.reporting.ReportResponse> getGenerateReportMethod;
    if ((getGenerateReportMethod = ReportingServiceGrpc.getGenerateReportMethod) == null) {
      synchronized (ReportingServiceGrpc.class) {
        if ((getGenerateReportMethod = ReportingServiceGrpc.getGenerateReportMethod) == null) {
          ReportingServiceGrpc.getGenerateReportMethod = getGenerateReportMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.reporting.GenerateReportRequest, com.fpm2025.reporting.ReportResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GenerateReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.reporting.GenerateReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.reporting.ReportResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ReportingServiceMethodDescriptorSupplier("GenerateReport"))
              .build();
        }
      }
    }
    return getGenerateReportMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.reporting.ListReportsRequest,
      com.fpm2025.reporting.ListReportsResponse> getListReportsMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListReports",
      requestType = com.fpm2025.reporting.ListReportsRequest.class,
      responseType = com.fpm2025.reporting.ListReportsResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.reporting.ListReportsRequest,
      com.fpm2025.reporting.ListReportsResponse> getListReportsMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.reporting.ListReportsRequest, com.fpm2025.reporting.ListReportsResponse> getListReportsMethod;
    if ((getListReportsMethod = ReportingServiceGrpc.getListReportsMethod) == null) {
      synchronized (ReportingServiceGrpc.class) {
        if ((getListReportsMethod = ReportingServiceGrpc.getListReportsMethod) == null) {
          ReportingServiceGrpc.getListReportsMethod = getListReportsMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.reporting.ListReportsRequest, com.fpm2025.reporting.ListReportsResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListReports"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.reporting.ListReportsRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.reporting.ListReportsResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ReportingServiceMethodDescriptorSupplier("ListReports"))
              .build();
        }
      }
    }
    return getListReportsMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.reporting.DownloadReportRequest,
      com.fpm2025.reporting.DownloadReportResponse> getDownloadReportMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DownloadReport",
      requestType = com.fpm2025.reporting.DownloadReportRequest.class,
      responseType = com.fpm2025.reporting.DownloadReportResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.reporting.DownloadReportRequest,
      com.fpm2025.reporting.DownloadReportResponse> getDownloadReportMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.reporting.DownloadReportRequest, com.fpm2025.reporting.DownloadReportResponse> getDownloadReportMethod;
    if ((getDownloadReportMethod = ReportingServiceGrpc.getDownloadReportMethod) == null) {
      synchronized (ReportingServiceGrpc.class) {
        if ((getDownloadReportMethod = ReportingServiceGrpc.getDownloadReportMethod) == null) {
          ReportingServiceGrpc.getDownloadReportMethod = getDownloadReportMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.reporting.DownloadReportRequest, com.fpm2025.reporting.DownloadReportResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DownloadReport"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.reporting.DownloadReportRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.reporting.DownloadReportResponse.getDefaultInstance()))
              .setSchemaDescriptor(new ReportingServiceMethodDescriptorSupplier("DownloadReport"))
              .build();
        }
      }
    }
    return getDownloadReportMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static ReportingServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReportingServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReportingServiceStub>() {
        @java.lang.Override
        public ReportingServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReportingServiceStub(channel, callOptions);
        }
      };
    return ReportingServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static ReportingServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReportingServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReportingServiceBlockingStub>() {
        @java.lang.Override
        public ReportingServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReportingServiceBlockingStub(channel, callOptions);
        }
      };
    return ReportingServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static ReportingServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<ReportingServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<ReportingServiceFutureStub>() {
        @java.lang.Override
        public ReportingServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new ReportingServiceFutureStub(channel, callOptions);
        }
      };
    return ReportingServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void generateReport(com.fpm2025.reporting.GenerateReportRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.reporting.ReportResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGenerateReportMethod(), responseObserver);
    }

    /**
     */
    default void listReports(com.fpm2025.reporting.ListReportsRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.reporting.ListReportsResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListReportsMethod(), responseObserver);
    }

    /**
     */
    default void downloadReport(com.fpm2025.reporting.DownloadReportRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.reporting.DownloadReportResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDownloadReportMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service ReportingService.
   */
  public static abstract class ReportingServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return ReportingServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service ReportingService.
   */
  public static final class ReportingServiceStub
      extends io.grpc.stub.AbstractAsyncStub<ReportingServiceStub> {
    private ReportingServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReportingServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReportingServiceStub(channel, callOptions);
    }

    /**
     */
    public void generateReport(com.fpm2025.reporting.GenerateReportRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.reporting.ReportResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGenerateReportMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void listReports(com.fpm2025.reporting.ListReportsRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.reporting.ListReportsResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListReportsMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void downloadReport(com.fpm2025.reporting.DownloadReportRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.reporting.DownloadReportResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDownloadReportMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service ReportingService.
   */
  public static final class ReportingServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<ReportingServiceBlockingStub> {
    private ReportingServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReportingServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReportingServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fpm2025.reporting.ReportResponse generateReport(com.fpm2025.reporting.GenerateReportRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGenerateReportMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.reporting.ListReportsResponse listReports(com.fpm2025.reporting.ListReportsRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListReportsMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.reporting.DownloadReportResponse downloadReport(com.fpm2025.reporting.DownloadReportRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDownloadReportMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service ReportingService.
   */
  public static final class ReportingServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<ReportingServiceFutureStub> {
    private ReportingServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected ReportingServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new ReportingServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.reporting.ReportResponse> generateReport(
        com.fpm2025.reporting.GenerateReportRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGenerateReportMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.reporting.ListReportsResponse> listReports(
        com.fpm2025.reporting.ListReportsRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListReportsMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.reporting.DownloadReportResponse> downloadReport(
        com.fpm2025.reporting.DownloadReportRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDownloadReportMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GENERATE_REPORT = 0;
  private static final int METHODID_LIST_REPORTS = 1;
  private static final int METHODID_DOWNLOAD_REPORT = 2;

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
        case METHODID_GENERATE_REPORT:
          serviceImpl.generateReport((com.fpm2025.reporting.GenerateReportRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.reporting.ReportResponse>) responseObserver);
          break;
        case METHODID_LIST_REPORTS:
          serviceImpl.listReports((com.fpm2025.reporting.ListReportsRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.reporting.ListReportsResponse>) responseObserver);
          break;
        case METHODID_DOWNLOAD_REPORT:
          serviceImpl.downloadReport((com.fpm2025.reporting.DownloadReportRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.reporting.DownloadReportResponse>) responseObserver);
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
          getGenerateReportMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.reporting.GenerateReportRequest,
              com.fpm2025.reporting.ReportResponse>(
                service, METHODID_GENERATE_REPORT)))
        .addMethod(
          getListReportsMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.reporting.ListReportsRequest,
              com.fpm2025.reporting.ListReportsResponse>(
                service, METHODID_LIST_REPORTS)))
        .addMethod(
          getDownloadReportMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.reporting.DownloadReportRequest,
              com.fpm2025.reporting.DownloadReportResponse>(
                service, METHODID_DOWNLOAD_REPORT)))
        .build();
  }

  private static abstract class ReportingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    ReportingServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fpm2025.reporting.ReportingProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("ReportingService");
    }
  }

  private static final class ReportingServiceFileDescriptorSupplier
      extends ReportingServiceBaseDescriptorSupplier {
    ReportingServiceFileDescriptorSupplier() {}
  }

  private static final class ReportingServiceMethodDescriptorSupplier
      extends ReportingServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    ReportingServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (ReportingServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new ReportingServiceFileDescriptorSupplier())
              .addMethod(getGenerateReportMethod())
              .addMethod(getListReportsMethod())
              .addMethod(getDownloadReportMethod())
              .build();
        }
      }
    }
    return result;
  }
}
