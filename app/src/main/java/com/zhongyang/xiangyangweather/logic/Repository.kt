package com.zhongyang.xiangyangweather.logic

import androidx.lifecycle.liveData
import com.zhongyang.xiangyangweather.logic.model.Place
import com.zhongyang.xiangyangweather.logic.network.RosyCloudsApi
import kotlinx.coroutines.Dispatchers

/**
 * @项目名称 XiangyangWeather
 * @类名 Repository
 * @包名 com.zhongyang.xiangyangweather.logic
 * @创建时间 2021/2/1 11:21
 * @作者 钟阳
 * @描述 数据获取与缓存的仓库层
 */
object Repository {

    fun searchPlaces(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val placeResponse = RosyCloudsApi.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val place = placeResponse.places
                Result.success(place)
            } else {
                Result.failure(RuntimeException("响应状态：${placeResponse.status}"))
            }
        } catch (e: Exception) {
            /**/
            Result.failure<List<Place>>(e)
        }
        emit(result)
    }
}