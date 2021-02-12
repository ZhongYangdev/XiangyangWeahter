package com.zhongyang.xiangyangweather.ui.activities

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zhongyang.xiangyangweather.R
import com.zhongyang.xiangyangweather.logic.model.Weather
import com.zhongyang.xiangyangweather.logic.model.getSky
import com.zhongyang.xiangyangweather.ui.fragment.PlaceFragment
import com.zhongyang.xiangyangweather.ui.utils.ToastUtil
import com.zhongyang.xiangyangweather.ui.weahter.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.sub_weather_forecast.*
import kotlinx.android.synthetic.main.sub_weather_info.*
import kotlinx.android.synthetic.main.sub_weather_life_index.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private val weatherViewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    private val tag = "WeatherActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*处理状态栏*/
        val decorView = window.decorView//获取当前Activity的DecorView
        decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE//改变系统UI的显示
        window.statusBarColor = Color.TRANSPARENT//将状态栏设置为透明色
        setContentView(R.layout.activity_weather)//加载布局文件
        /*初始化控件*/
        initView()
        /*初始化事件*/
        initEvent()
        /*获取跳转数据*/
        getIntentData()
        /*初始化观察者*/
        initObserve()
        /*刷新天气信息*/
        refreshWeather()
    }

    private fun initEvent() {
        /*刷新控件刷新事件*/
        srl_refreshWeather.setOnRefreshListener {
            /*刷新天气信息*/
            refreshWeather()
        }
    }

    private fun refreshWeather() {
        /*获取最新天气信息*/
        weatherViewModel.getWeather(weatherViewModel.locationLng, weatherViewModel.locationLat)
        /*设置刷新控件*/
        srl_refreshWeather.isRefreshing = true
        /*提示*/
        ToastUtil.showToast("天气更新完毕")
    }

    private fun initView() {
        /*刷新控件*/
        srl_refreshWeather.setColorSchemeResources(R.color.teal_200)//设置刷新控件颜色
    }

    private fun initObserve() {
        weatherViewModel.weatherLiveData.observe(this, Observer { result ->
            val weather = result.getOrNull()
            if (weather != null) {
                /*展示天气数据*/
                showWeatherData(weather)
//                Log.d(tag, "获取数据成功...")
            } else {
//                Log.d(tag, "获取数据失败...")
                Toast.makeText(this, "无法成功获取天气信息", Toast.LENGTH_SHORT).show()//提示
                result.exceptionOrNull()?.printStackTrace()//输出异常
            }
            /*获取导数据后设置刷新控件*/
            srl_refreshWeather.isRefreshing = false
        })
    }

    private fun showWeatherData(weather: Weather) {
        tv_locationName.text = weatherViewModel.placeName//设置地区名
        /*获取数据对象*/
        val realtime = weather.realtime//实时天气数据
        val daily = weather.daily//预测天气数据
        /*设置sub_weather_info布局中的控件显示内容*/
        val currentTempText = "${realtime.temperature.toInt()}°"//获取当前温度
        val currentPM25Text = "空气指数 ${realtime.air_quality.aqi.chn}"//获取当前空气指数
        tv_currentTemp.text = currentTempText//当前温度
        tv_currentSky.text = getSky(realtime.skycon).info//当前天气情况
        tv_currentAQI.text = currentPM25Text//当前空气指数
        cl_nowLayout.setBackgroundResource(getSky(realtime.skycon).bg)//当前天气背景
        /*设置sub_weather_forecast布局中的控件显示内容*/
        ll_forecastLayout.removeAllViews()//清除所有布局中控件
        val days = daily.skycon.size//获取预测天气数据大小
        /*循环遍历days数据*/
        for (i in 0 until days) {
            val skyCon = daily.skycon[i]//
            val temperature = daily.temperature[i]//
            /*载入条目布局*/
            val view =
                LayoutInflater.from(this).inflate(R.layout.forecast_item, ll_forecastLayout, false)
            /*找控件*/
            val dateInfo = view.findViewById(R.id.tv_dateInfo) as TextView//日期
            val skyIcon = view.findViewById(R.id.iv_skyIcon) as ImageView//天气图标
            val skyInfo = view.findViewById(R.id.tv_skyInfo) as TextView//天气信息
            val temperatureInfo = view.findViewById(R.id.tv_temperatureInfo) as TextView//预测温度信息
            /*格式化日期数据*/
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            dateInfo.text = simpleDateFormat.format(skyCon.date)
            /*获取天气信息*/
            val sky = getSky(skyCon.value)
            skyIcon.setImageResource(sky.icon)//设置天气图标显示
            skyInfo.text = sky.info//设置天气信息文字显示
            /*计算预测天气温度*/
            val tempText = "${temperature.min.toInt()}° ~ ${temperature.max.toInt()}°"//预测温度
            temperatureInfo.text = tempText//设置预测温度信息显示
            /*添加到布局中*/
            ll_forecastLayout.addView(view)
        }
        /*设置sub_weather_life_index布局中的控件显示内容*/
        val lifeIndex = daily.life_index//获取生活指数
        tv_coldRisk.text = lifeIndex.coldRisk[0].desc//感冒指数
        tv_dressing.text = lifeIndex.comfort[0].desc//穿衣指数
        tv_ultraviolet.text = lifeIndex.ultraviolet[0].desc//实时紫外线指数
        tv_carWashing.text = lifeIndex.carWashing[0].desc//洗车指数
        /*设置Activity布局可见*/
        nsv_weatherLayout.visibility = View.VISIBLE
    }

    private fun getIntentData() {
        if (weatherViewModel.locationLng == 0.00) {
            weatherViewModel.locationLng =
                intent.getDoubleExtra(PlaceFragment.KEY_LOCATION_LNG, 0.00)//将经度赋值
        }
        if (weatherViewModel.locationLat == 0.00) {
            weatherViewModel.locationLat =
                intent.getDoubleExtra(PlaceFragment.KEY_LOCATION_LAT, 0.00)//将纬度赋值
        }
        if (weatherViewModel.placeName.isEmpty()) {
            weatherViewModel.placeName =
                intent.getStringExtra(PlaceFragment.KEY_PLACE_NAME) ?: ""//将地区名称赋值
        }
    }
}