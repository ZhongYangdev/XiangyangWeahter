package com.zhongyang.xiangyangweather.logic.network

import com.zhongyang.xiangyangweather.base.BaseApplication.Companion.TOKEN
import com.zhongyang.xiangyangweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceService
 * @包名 com.zhongyang.xiangyangweather.logic.network
 * @创建时间 2021/2/1 10:57
 * @作者 钟阳
 * @描述
 */
interface PlaceService {

    @GET("v2/place?token=${TOKEN}&lang=zh_CN")
    fun searchPlaces(@Query("query") query: String): Call<PlaceResponse>
}