package com.zhongyang.xiangyangweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zhongyang.xiangyangweather.logic.Repository
import com.zhongyang.xiangyangweather.logic.model.Place

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceViewModel
 * @包名 com.zhongyang.xiangyangweather.ui.place
 * @创建时间 2021/2/1 11:30
 * @作者 钟阳
 * @描述
 */
class PlaceViewModel : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    val placeList = ArrayList<Place>()

    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        Repository.searchPlaces(query)
    }

    /**
     * 判断地区是否已经保存方法
     */
    fun isPlaceSaved() = Repository.isPlaceSaved()

    /**
     * 获取保存地址方法
     */
    fun getSavedPlace() = Repository.getSavedPlace()

    /**
     * 保存地址方法
     */
    fun savePlace(place: Place) = Repository.savePlace(place)

    fun searchPlaces(query: String) {
        searchLiveData.value = query//赋值
    }
}