package com.zhongyang.xiangyangweather.logic.model

/**
 * @项目名称 XiangyangWeather
 * @类名 RealtimeResponse
 * @包名 com.zhongyang.xiangyangweather.logic.model
 * @创建时间 2021/2/7 10:38
 * @作者 钟阳
 * @描述 实时天气数据类
 */
data class RealtimeResponse(
    val api_status: String,
    val api_version: String,
    val lang: String,
    val location: List<Double>,
    val result: Result,
    val server_time: Int,
    val status: String,
    val timezone: String,
    val tzshift: Int,
    val unit: String
) {
    data class Result(
        val primary: Int,
        val realtime: Realtime
    )

    data class Realtime(
        val air_quality: AirQuality,
        val apparent_temperature: Double,
        val cloudrate: Double,
        val dswrf: Double,
        val humidity: Double,
        val life_index: LifeIndex,
        val precipitation: Precipitation,
        val pressure: Double,
        val skycon: String,
        val status: String,
        val temperature: Double,
        val visibility: Double,
        val wind: Wind
    )

    data class AirQuality(
        val aqi: Aqi,
        val co: Double,
        val description: Description,
        val no2: Int,
        val o3: Int,
        val pm10: Int,
        val pm25: Int,
        val so2: Int
    )

    data class LifeIndex(
        val comfort: Comfort,
        val ultraviolet: Ultraviolet
    )

    data class Precipitation(
        val local: Local,
        val nearest: Nearest
    )

    data class Wind(
        val direction: Double,
        val speed: Double
    )

    data class Aqi(
        val chn: Int,
        val usa: Int
    )

    data class Description(
        val chn: String,
        val usa: String
    )

    data class Comfort(
        val desc: String,
        val index: Int
    )

    data class Ultraviolet(
        val desc: String,
        val index: Double
    )

    data class Local(
        val datasource: String,
        val intensity: Double,
        val status: String
    )

    data class Nearest(
        val distance: Double,
        val intensity: Double,
        val status: String
    )
}