package com.zhongyang.xiangyangweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceResponse
 * @包名 com.zhongyang.xiangyangweather.logic.model
 * @创建时间 2021/2/1 8:45
 * @作者 钟阳
 * @描述 数据模型
 */

/**
 * 获取相关地区结果
 * @param status 请求结果是否成功
 * @param places 请求到的地区结果
 */
data class PlaceResponse(
    val status: String,
    val places: List<Place>
)

/**
 * 与关键词相关的地区
 * @param name 地区名称
 * @param location 地理位置
 * @param address 地址
 */
data class Place(
    val name: String,
    val location: Location,
    @SerializedName("formatted_address") val address: String
)

/**
 * 地理位置
 * @param lng 经度
 * @param lat 纬度
 */
data class Location(
    val lng: Double,
    val lat: Double
)
