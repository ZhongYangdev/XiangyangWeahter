package com.zhongyang.xiangyangweather.logic.network

import com.zhongyang.xiangyangweather.ui.utils.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @项目名称 XiangyangWeather
 * @类名 RosyCloudsApi
 * @包名 com.zhongyang.xiangyangweather.logic.network
 * @创建时间 2021/2/1 11:11
 * @作者 钟阳
 * @描述 彩云API
 */
object RosyCloudsApi {

    private val placeService = ServiceCreator.create<PlaceService>()

    suspend fun searchPlaces(query: String) = placeService.searchPlaces(query).await()

    private suspend fun <T> Call<T>.await(): T {
        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    /**/
                    val body = response.body()
                    /**/
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("响应内容为空"))
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    /*输出异常*/
                    continuation.resumeWithException(t)
                }

            })
        }
    }
}