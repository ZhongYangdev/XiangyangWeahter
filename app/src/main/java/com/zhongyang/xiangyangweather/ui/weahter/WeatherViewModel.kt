package com.zhongyang.xiangyangweather.ui.weahter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zhongyang.xiangyangweather.logic.Repository
import com.zhongyang.xiangyangweather.logic.model.Location

/**
 * @项目名称 XiangyangWeather
 * @类名 WatherViewModel
 * @包名 com.zhongyang.xiangyangweather.ui.weahter
 * @创建时间 2021/2/7 11:10
 * @作者 钟阳
 * @描述 天气ViewModel层
 */
class WeatherViewModel : ViewModel() {

    /*相关变量数据*/
    var locationLng = 0.00//地区经度
    var locationLat = 0.00//地区纬度
    var placeName = ""//地区名称

    private val locationLiveData = MutableLiveData<Location>()//

    val weatherLiveData = Transformations.switchMap(locationLiveData) { location ->
        Repository.getWeather(location.lng, location.lat)
    }

    fun getWeather(lng: Double, lat: Double) {
        locationLiveData.value = Location(lng, lat)//赋值
    }
}