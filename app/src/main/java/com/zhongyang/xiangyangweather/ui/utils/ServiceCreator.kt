package com.zhongyang.xiangyangweather.ui.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @项目名称 XiangyangWeather
 * @类名 ServiceCreator
 * @包名 com.zhongyang.xiangyangweather.ui.utils
 * @创建时间 2021/2/1 11:05
 * @作者 钟阳
 * @描述 Retrofit构建器
 */
object ServiceCreator {

    private const val BASE_URL = "https://api.caiyunapp.com/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}