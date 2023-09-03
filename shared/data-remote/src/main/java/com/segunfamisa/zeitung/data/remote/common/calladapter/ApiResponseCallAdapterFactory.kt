package com.segunfamisa.zeitung.data.remote.common.calladapter

import com.segunfamisa.zeitung.data.remote.common.ApiResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Inspired by https://github.com/skydoves/retrofit-adapters/blob/main/retrofit-adapters-result/src/main/kotlin/com/skydoves/retrofit/adapters/result/
 */
internal class ApiResponseCallAdapterFactory(
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val errorParser: ApiErrorParser,
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                val rawType = getRawType(callType)

                if (rawType != ApiResponse::class.java) {
                    return null
                }

                val responseParamType = getParameterUpperBound(0, callType as ParameterizedType)
                val responseType = getRawType(responseParamType)

                ApiResponseCallAdapter(
                    apiResponseType = responseType,
                    errorParser = errorParser,
                    scope = scope
                )
            }

            else -> null
        }
    }

    companion object {

        @JvmStatic
        fun create(
            scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
            apiErrorParser: ApiErrorParser,
        ): ApiResponseCallAdapterFactory {
            return ApiResponseCallAdapterFactory(scope = scope, errorParser = apiErrorParser)
        }
    }

    private class ApiResponseCallAdapter(
        private val apiResponseType: Type,
        private val scope: CoroutineScope,
        private val errorParser: ApiErrorParser,
    ) : CallAdapter<Type, Call<ApiResponse<Type?>>> {
        override fun responseType(): Type {
            return apiResponseType
        }

        override fun adapt(call: Call<Type>): Call<ApiResponse<Type?>> {
            return ApiResponseCall(
                callDelegate = call,
                paramType = apiResponseType,
                errorParser = errorParser,
                scope = scope
            )
        }
    }

    private class ApiResponseCall<T : Any>(
        private val callDelegate: Call<T>,
        private val paramType: Type,
        private val scope: CoroutineScope,
        private val errorParser: ApiErrorParser,
    ) : Call<ApiResponse<T?>> {
        override fun clone(): Call<ApiResponse<T?>> =
            ApiResponseCall(callDelegate.clone(), paramType, scope, errorParser)

        override fun execute(): Response<ApiResponse<T?>> {
            return runBlocking(scope.coroutineContext) {
                val result = callDelegate.execute()
                Response.success(result.toApiResponse(paramType, errorParser))
            }
        }

        override fun isExecuted(): Boolean = callDelegate.isExecuted

        override fun cancel() = callDelegate.cancel()

        override fun isCanceled(): Boolean = callDelegate.isCanceled

        override fun request(): Request = callDelegate.request()

        override fun enqueue(callback: Callback<ApiResponse<T?>>) {
            scope.launch {
                try {
                    val response = callDelegate.awaitResponse()
                    val result = response.toApiResponse(paramType, errorParser)

                    callback.onResponse(this@ApiResponseCall, Response.success(result))
                } catch (exception: Exception) {
                    val apiError = exception.toApiCallError()

                    callback.onResponse(
                        this@ApiResponseCall,
                        Response.success(ApiResponse.Error(apiError))
                    )
                }
            }
        }

    }
}