package com.zhongyang.xiangyangweather.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * @项目名称 XiangyangWeather
 * @类名 BaseApplication
 * @包名 com.zhongyang.xiangyangweather.base
 * @创建时间 2021/2/1 8:39
 * @作者 钟阳
 * @描述
 */
class BaseApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

        /*彩云天气令牌*/
        const val TOKEN = "VwigNfNDzqZGcWlN"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}