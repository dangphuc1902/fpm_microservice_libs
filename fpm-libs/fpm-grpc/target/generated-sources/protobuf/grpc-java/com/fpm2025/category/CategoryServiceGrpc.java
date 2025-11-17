package com.fpm2025.category;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: category.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class CategoryServiceGrpc {

  private CategoryServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "com.fpm2025.category.CategoryService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.fpm2025.category.ListCategoriesRequest,
      com.fpm2025.category.ListCategoriesResponse> getListCategoriesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "ListCategories",
      requestType = com.fpm2025.category.ListCategoriesRequest.class,
      responseType = com.fpm2025.category.ListCategoriesResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.category.ListCategoriesRequest,
      com.fpm2025.category.ListCategoriesResponse> getListCategoriesMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.category.ListCategoriesRequest, com.fpm2025.category.ListCategoriesResponse> getListCategoriesMethod;
    if ((getListCategoriesMethod = CategoryServiceGrpc.getListCategoriesMethod) == null) {
      synchronized (CategoryServiceGrpc.class) {
        if ((getListCategoriesMethod = CategoryServiceGrpc.getListCategoriesMethod) == null) {
          CategoryServiceGrpc.getListCategoriesMethod = getListCategoriesMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.category.ListCategoriesRequest, com.fpm2025.category.ListCategoriesResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "ListCategories"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.ListCategoriesRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.ListCategoriesResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CategoryServiceMethodDescriptorSupplier("ListCategories"))
              .build();
        }
      }
    }
    return getListCategoriesMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.category.GetCategoryRequest,
      com.fpm2025.category.Category> getGetCategoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "GetCategory",
      requestType = com.fpm2025.category.GetCategoryRequest.class,
      responseType = com.fpm2025.category.Category.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.category.GetCategoryRequest,
      com.fpm2025.category.Category> getGetCategoryMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.category.GetCategoryRequest, com.fpm2025.category.Category> getGetCategoryMethod;
    if ((getGetCategoryMethod = CategoryServiceGrpc.getGetCategoryMethod) == null) {
      synchronized (CategoryServiceGrpc.class) {
        if ((getGetCategoryMethod = CategoryServiceGrpc.getGetCategoryMethod) == null) {
          CategoryServiceGrpc.getGetCategoryMethod = getGetCategoryMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.category.GetCategoryRequest, com.fpm2025.category.Category>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "GetCategory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.GetCategoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.Category.getDefaultInstance()))
              .setSchemaDescriptor(new CategoryServiceMethodDescriptorSupplier("GetCategory"))
              .build();
        }
      }
    }
    return getGetCategoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.category.CreateCategoryRequest,
      com.fpm2025.category.CategoryResponse> getCreateCategoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "CreateCategory",
      requestType = com.fpm2025.category.CreateCategoryRequest.class,
      responseType = com.fpm2025.category.CategoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.category.CreateCategoryRequest,
      com.fpm2025.category.CategoryResponse> getCreateCategoryMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.category.CreateCategoryRequest, com.fpm2025.category.CategoryResponse> getCreateCategoryMethod;
    if ((getCreateCategoryMethod = CategoryServiceGrpc.getCreateCategoryMethod) == null) {
      synchronized (CategoryServiceGrpc.class) {
        if ((getCreateCategoryMethod = CategoryServiceGrpc.getCreateCategoryMethod) == null) {
          CategoryServiceGrpc.getCreateCategoryMethod = getCreateCategoryMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.category.CreateCategoryRequest, com.fpm2025.category.CategoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "CreateCategory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.CreateCategoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.CategoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CategoryServiceMethodDescriptorSupplier("CreateCategory"))
              .build();
        }
      }
    }
    return getCreateCategoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.category.UpdateCategoryRequest,
      com.fpm2025.category.CategoryResponse> getUpdateCategoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "UpdateCategory",
      requestType = com.fpm2025.category.UpdateCategoryRequest.class,
      responseType = com.fpm2025.category.CategoryResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.category.UpdateCategoryRequest,
      com.fpm2025.category.CategoryResponse> getUpdateCategoryMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.category.UpdateCategoryRequest, com.fpm2025.category.CategoryResponse> getUpdateCategoryMethod;
    if ((getUpdateCategoryMethod = CategoryServiceGrpc.getUpdateCategoryMethod) == null) {
      synchronized (CategoryServiceGrpc.class) {
        if ((getUpdateCategoryMethod = CategoryServiceGrpc.getUpdateCategoryMethod) == null) {
          CategoryServiceGrpc.getUpdateCategoryMethod = getUpdateCategoryMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.category.UpdateCategoryRequest, com.fpm2025.category.CategoryResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "UpdateCategory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.UpdateCategoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.CategoryResponse.getDefaultInstance()))
              .setSchemaDescriptor(new CategoryServiceMethodDescriptorSupplier("UpdateCategory"))
              .build();
        }
      }
    }
    return getUpdateCategoryMethod;
  }

  private static volatile io.grpc.MethodDescriptor<com.fpm2025.category.DeleteCategoryRequest,
      com.google.protobuf.Empty> getDeleteCategoryMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "DeleteCategory",
      requestType = com.fpm2025.category.DeleteCategoryRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.fpm2025.category.DeleteCategoryRequest,
      com.google.protobuf.Empty> getDeleteCategoryMethod() {
    io.grpc.MethodDescriptor<com.fpm2025.category.DeleteCategoryRequest, com.google.protobuf.Empty> getDeleteCategoryMethod;
    if ((getDeleteCategoryMethod = CategoryServiceGrpc.getDeleteCategoryMethod) == null) {
      synchronized (CategoryServiceGrpc.class) {
        if ((getDeleteCategoryMethod = CategoryServiceGrpc.getDeleteCategoryMethod) == null) {
          CategoryServiceGrpc.getDeleteCategoryMethod = getDeleteCategoryMethod =
              io.grpc.MethodDescriptor.<com.fpm2025.category.DeleteCategoryRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "DeleteCategory"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.fpm2025.category.DeleteCategoryRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new CategoryServiceMethodDescriptorSupplier("DeleteCategory"))
              .build();
        }
      }
    }
    return getDeleteCategoryMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static CategoryServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CategoryServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CategoryServiceStub>() {
        @java.lang.Override
        public CategoryServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CategoryServiceStub(channel, callOptions);
        }
      };
    return CategoryServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static CategoryServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CategoryServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CategoryServiceBlockingStub>() {
        @java.lang.Override
        public CategoryServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CategoryServiceBlockingStub(channel, callOptions);
        }
      };
    return CategoryServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static CategoryServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<CategoryServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<CategoryServiceFutureStub>() {
        @java.lang.Override
        public CategoryServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new CategoryServiceFutureStub(channel, callOptions);
        }
      };
    return CategoryServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void listCategories(com.fpm2025.category.ListCategoriesRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.ListCategoriesResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getListCategoriesMethod(), responseObserver);
    }

    /**
     */
    default void getCategory(com.fpm2025.category.GetCategoryRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.Category> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getGetCategoryMethod(), responseObserver);
    }

    /**
     */
    default void createCategory(com.fpm2025.category.CreateCategoryRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.CategoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getCreateCategoryMethod(), responseObserver);
    }

    /**
     */
    default void updateCategory(com.fpm2025.category.UpdateCategoryRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.CategoryResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getUpdateCategoryMethod(), responseObserver);
    }

    /**
     */
    default void deleteCategory(com.fpm2025.category.DeleteCategoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getDeleteCategoryMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service CategoryService.
   */
  public static abstract class CategoryServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return CategoryServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service CategoryService.
   */
  public static final class CategoryServiceStub
      extends io.grpc.stub.AbstractAsyncStub<CategoryServiceStub> {
    private CategoryServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CategoryServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CategoryServiceStub(channel, callOptions);
    }

    /**
     */
    public void listCategories(com.fpm2025.category.ListCategoriesRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.ListCategoriesResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getListCategoriesMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void getCategory(com.fpm2025.category.GetCategoryRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.Category> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getGetCategoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void createCategory(com.fpm2025.category.CreateCategoryRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.CategoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getCreateCategoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void updateCategory(com.fpm2025.category.UpdateCategoryRequest request,
        io.grpc.stub.StreamObserver<com.fpm2025.category.CategoryResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getUpdateCategoryMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void deleteCategory(com.fpm2025.category.DeleteCategoryRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getDeleteCategoryMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service CategoryService.
   */
  public static final class CategoryServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<CategoryServiceBlockingStub> {
    private CategoryServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CategoryServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CategoryServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.fpm2025.category.ListCategoriesResponse listCategories(com.fpm2025.category.ListCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getListCategoriesMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.category.Category getCategory(com.fpm2025.category.GetCategoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getGetCategoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.category.CategoryResponse createCategory(com.fpm2025.category.CreateCategoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getCreateCategoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.fpm2025.category.CategoryResponse updateCategory(com.fpm2025.category.UpdateCategoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getUpdateCategoryMethod(), getCallOptions(), request);
    }

    /**
     */
    public com.google.protobuf.Empty deleteCategory(com.fpm2025.category.DeleteCategoryRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getDeleteCategoryMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service CategoryService.
   */
  public static final class CategoryServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<CategoryServiceFutureStub> {
    private CategoryServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected CategoryServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new CategoryServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.category.ListCategoriesResponse> listCategories(
        com.fpm2025.category.ListCategoriesRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getListCategoriesMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.category.Category> getCategory(
        com.fpm2025.category.GetCategoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getGetCategoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.category.CategoryResponse> createCategory(
        com.fpm2025.category.CreateCategoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getCreateCategoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.fpm2025.category.CategoryResponse> updateCategory(
        com.fpm2025.category.UpdateCategoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getUpdateCategoryMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> deleteCategory(
        com.fpm2025.category.DeleteCategoryRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getDeleteCategoryMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_LIST_CATEGORIES = 0;
  private static final int METHODID_GET_CATEGORY = 1;
  private static final int METHODID_CREATE_CATEGORY = 2;
  private static final int METHODID_UPDATE_CATEGORY = 3;
  private static final int METHODID_DELETE_CATEGORY = 4;

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
        case METHODID_LIST_CATEGORIES:
          serviceImpl.listCategories((com.fpm2025.category.ListCategoriesRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.category.ListCategoriesResponse>) responseObserver);
          break;
        case METHODID_GET_CATEGORY:
          serviceImpl.getCategory((com.fpm2025.category.GetCategoryRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.category.Category>) responseObserver);
          break;
        case METHODID_CREATE_CATEGORY:
          serviceImpl.createCategory((com.fpm2025.category.CreateCategoryRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.category.CategoryResponse>) responseObserver);
          break;
        case METHODID_UPDATE_CATEGORY:
          serviceImpl.updateCategory((com.fpm2025.category.UpdateCategoryRequest) request,
              (io.grpc.stub.StreamObserver<com.fpm2025.category.CategoryResponse>) responseObserver);
          break;
        case METHODID_DELETE_CATEGORY:
          serviceImpl.deleteCategory((com.fpm2025.category.DeleteCategoryRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
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
          getListCategoriesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.category.ListCategoriesRequest,
              com.fpm2025.category.ListCategoriesResponse>(
                service, METHODID_LIST_CATEGORIES)))
        .addMethod(
          getGetCategoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.category.GetCategoryRequest,
              com.fpm2025.category.Category>(
                service, METHODID_GET_CATEGORY)))
        .addMethod(
          getCreateCategoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.category.CreateCategoryRequest,
              com.fpm2025.category.CategoryResponse>(
                service, METHODID_CREATE_CATEGORY)))
        .addMethod(
          getUpdateCategoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.category.UpdateCategoryRequest,
              com.fpm2025.category.CategoryResponse>(
                service, METHODID_UPDATE_CATEGORY)))
        .addMethod(
          getDeleteCategoryMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              com.fpm2025.category.DeleteCategoryRequest,
              com.google.protobuf.Empty>(
                service, METHODID_DELETE_CATEGORY)))
        .build();
  }

  private static abstract class CategoryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    CategoryServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return com.fpm2025.category.CategoryProto.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("CategoryService");
    }
  }

  private static final class CategoryServiceFileDescriptorSupplier
      extends CategoryServiceBaseDescriptorSupplier {
    CategoryServiceFileDescriptorSupplier() {}
  }

  private static final class CategoryServiceMethodDescriptorSupplier
      extends CategoryServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    CategoryServiceMethodDescriptorSupplier(java.lang.String methodName) {
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
      synchronized (CategoryServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new CategoryServiceFileDescriptorSupplier())
              .addMethod(getListCategoriesMethod())
              .addMethod(getGetCategoryMethod())
              .addMethod(getCreateCategoryMethod())
              .addMethod(getUpdateCategoryMethod())
              .addMethod(getDeleteCategoryMethod())
              .build();
        }
      }
    }
    return result;
  }
}
