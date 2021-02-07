package com.zhongyang.xiangyangweather.logic

import androidx.lifecycle.liveData
import com.zhongyang.xiangyangweather.logic.model.Weather
import com.zhongyang.xiangyangweather.logic.network.WeatherNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.CoroutineContext

/**
 * @项目名称 XiangyangWeather
 * @类名 Repository
 * @包名 com.zhongyang.xiangyangweather.logic
 * @创建时间 2021/2/1 11:21
 * @作者 钟阳
 * @描述 数据获取与缓存的仓库层
 */
object Repository {

    /**
     * 封装获取天气方法
     */
    fun getWeather(lng: Double, lat: Double) = fire(Dispatchers.IO) {
        /*开启协程*/
        coroutineScope {
            val realtimeWeather = async {
                /*在子协程中去获取实时天气*/
                WeatherNetwork.realtimeWeather(lng, lat)
            }
            val dailyWeather = async {
                /*在子协程中去获取预测天气*/
                WeatherNetwork.dailyWeather(lng, lat)
            }
            /*获取结果*/
            val realtimeResponse = realtimeWeather.await()//实时天气
            val dailyResponse = dailyWeather.await()//预测天气
            if (realtimeResponse.status == "ok" && dailyResponse.status == "ok") {
                /*处理请求数据成功的结果*/
                val weather =
                    Weather(realtimeResponse.result.realtime, dailyResponse.result.daily)//封装数据
                /*打包成功的数据结果*/
                Result.success(weather)
            } else {
                /*处理请求数据失败的结果*/
                Result.failure(
                    RuntimeException(
                        "请求实时天气状态错误：${realtimeResponse.status}" +
                                "请求预测天气状态错误：${dailyResponse.status}"
                    )
                )
            }
        }
    }

    /**
     * 封装搜索地区方法
     */
    fun searchPlaces(query: String) = fire(Dispatchers.IO) {
        /*获取搜索地区*/
        val placeResponse = WeatherNetwork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            /*请求数据成功时*/
            val place = placeResponse.places
            /*打包成功数据*/
            Result.success(place)
        } else {
            /*打包异常数据*/
            Result.failure(RuntimeException("响应状态：${placeResponse.status}"))
        }
    }

    /**
     * 简化处理异常方法
     */
    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>> {
            val result = try {
                /**/
                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            /*发送数据*/
            emit(result)
        }
}