package com.zhongyang.xiangyangweather.logic.model

/**
 * @项目名称 XiangyangWeather
 * @类名 Weather
 * @包名 com.zhongyang.xiangyangweather.logic.model
 * @创建时间 2021/2/7 10:40
 * @作者 钟阳
 * @描述 天气数据类，封装实时天气和预测天气
 */
data class Weather(val realtime: RealtimeResponse.Realtime, val daily: DailyResponse.Daily)