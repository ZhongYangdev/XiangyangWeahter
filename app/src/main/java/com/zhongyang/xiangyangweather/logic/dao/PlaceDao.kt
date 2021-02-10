package com.zhongyang.xiangyangweather.logic.dao

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.zhongyang.xiangyangweather.base.BaseApplication
import com.zhongyang.xiangyangweather.logic.model.Place

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceDao
 * @包名 com.zhongyang.xiangyangweather.logic.dao
 * @创建时间 2021/2/10 21:45
 * @作者 钟阳
 * @描述
 */
object PlaceDao {

    private const val SP_WEATHER_NAME = "xiangyang_weather"//天气文件名称
    private const val SP_PLACE_NAME = "place"//保存地址的sp名称

    /**
     * 保存地址方法
     */
    fun savePlace(place: Place) {
        /*获取编辑器*/
        sharedPreferences().edit {
            /*封装数据*/
            putString(SP_PLACE_NAME, Gson().toJson(place))//通过GSON将Place对象转换成一个JSON字符串
        }
    }

    /**
     * 获取保存地址的方法
     */
    fun getSavedPlace(): Place {
        /*获取保存的数据*/
        val placeJson = sharedPreferences().getString(SP_PLACE_NAME, "")//先读取sp保存的JSON字符串
        return Gson().fromJson(placeJson, Place::class.java)//通过GSON将JSON字符串解析成Place对象并返回
    }

    /**
     * 判断地址是否已保存方法
     */
    fun isPlaceSaved() = sharedPreferences().contains(SP_PLACE_NAME)

    /**
     * 获取sp操作对象方法
     */
    private fun sharedPreferences() =
        BaseApplication.context.getSharedPreferences(SP_WEATHER_NAME, Context.MODE_PRIVATE)
}