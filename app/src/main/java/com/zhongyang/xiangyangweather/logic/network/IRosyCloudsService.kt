package com.zhongyang.xiangyangweather.logic.network

import com.zhongyang.xiangyangweather.base.BaseApplication.Companion.TOKEN
import com.zhongyang.xiangyangweather.logic.model.DailyResponse
import com.zhongyang.xiangyangweather.logic.model.PlaceResponse
import com.zhongyang.xiangyangweather.logic.model.RealtimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceService
 * @包名 com.zhongyang.xiangyangweather.logic.network
 * @创建时间 2021/2/1 10:57
 * @作者 钟阳
 * @描述
 */
interface IRosyCloudsService {

    /**
     * 请求搜索地区方法
     */
    @GET("v2/place?token=${TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>

    /**
     * 请求实时天气数据方法
     */
    @GET("v2.5/${TOKEN}/{lng},{lat}/realtime.json")
    fun getRealtimeData(@Path("lng") lng: Double, @Path("lat") lat: Double): Call<RealtimeResponse>

    /**
     * 获取预测天气数据方法
     */
    @GET("v2.5/${TOKEN}/{lng},{lat}/daily.json")
    fun getDailyData(@Path("lng") lng: Double, @Path("lat") lat: Double): Call<DailyResponse>
}