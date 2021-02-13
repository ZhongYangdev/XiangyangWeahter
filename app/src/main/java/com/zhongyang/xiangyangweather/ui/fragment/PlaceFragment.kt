package com.zhongyang.xiangyangweather.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhongyang.xiangyangweather.R
import com.zhongyang.xiangyangweather.base.BaseApplication
import com.zhongyang.xiangyangweather.ui.activities.MainActivity
import com.zhongyang.xiangyangweather.ui.activities.WeatherActivity
import com.zhongyang.xiangyangweather.ui.adapter.PlaceAdapter
import com.zhongyang.xiangyangweather.ui.place.PlaceViewModel
import com.zhongyang.xiangyangweather.ui.utils.KeyBordUtils
import com.zhongyang.xiangyangweather.ui.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_weather.*
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceFragment
 * @包名 com.zhongyang.xiangyangweather.ui.fragment
 * @创建时间 2021/2/1 14:25
 * @作者 钟阳
 * @描述
 */
class PlaceFragment : Fragment(), PlaceAdapter.OnPlaceItemClickListener {

    private val viewModel by lazy {
        ViewModelProvider(this).get(PlaceViewModel::class.java)
    }

    companion object {
        const val KEY_LOCATION_LNG = "key_location_lng"
        const val KEY_LOCATION_LAT = "key_location_lat"
        const val KEY_PLACE_NAME = "key_place_name"
    }

    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*判断是否有保存的地区数据，若有数据就直接跳赋值转到天气界面*/
        if (activity is MainActivity && viewModel.isPlaceSaved()) {
            val place = viewModel.getSavedPlace()//获取保存的地区数据
            val intent = Intent(context, WeatherActivity::class.java).apply {
                /*封装数据*/
                putExtra(KEY_PLACE_NAME, place.name)//地区名称
                putExtra(KEY_LOCATION_LNG, place.location.lng)//地区经度
                putExtra(KEY_LOCATION_LAT, place.location.lat)//地区纬度
            }
            startActivity(intent)//启动跳转
            activity?.finish()//关闭当前Activity
            return
        }
        /*初始化适配器相关*/
        initAdapter()
        /*初始化事件*/
        initEvent()
        /*初始化观察者*/
        initObserve()
    }

    private fun initObserve() {
        viewModel.placeLiveData.observe(this, Observer { result ->
            /**/
            val places = result.getOrNull()
            if (places != null) {
                /*不为空时*/
                rv_placeAddress.visibility = View.VISIBLE
                iv_frgPlace_bg.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                placeAdapter.notifyDataSetChanged()
            } else {
                ToastUtil.showToast("未查询到任何地点")
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }

    private fun initEvent() {
        /*适配器条目点击事件*/
        placeAdapter.setOnPlaceItemClickListener(this)
        /*所搜框清空按钮点击事件*/
        iv_clear_searchBox.setOnClickListener {
            /*清空输入框内容*/
            et_placeSearchBox.setText("")
            /*设置相关控件的显示和隐藏*/
            rv_placeAddress.visibility = View.GONE
            iv_frgPlace_bg.visibility = View.VISIBLE
        }
        /*搜索框搜索监听事件*/
        et_placeSearchBox.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    /*获取搜索词*/
                    val keyWord = v?.text.toString()
                    if (keyWord.isEmpty()) {
                        /*如果搜索内容为空直接返回*/
                        return false
                    }
                    /*发起搜索*/
                    viewModel.searchPlaces(keyWord)
                    rv_placeAddress.scrollToPosition(0)//设置搜索后条目的显示位置
                    /*设置键盘隐藏*/
                    KeyBordUtils.hide(BaseApplication.context, et_placeSearchBox)
                }
                return false
            }

        })
        /*搜索框内容变化监听事件*/
        et_placeSearchBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                /*当输入框内容发生变化时*/
                val content = s.toString()//获取输入内容
                if (content.isEmpty()) {
                    /*当搜索内容为空时*/
                    rv_placeAddress.visibility = View.GONE//隐藏列表显示控件
                    iv_frgPlace_bg.visibility = View.VISIBLE//显示背景图片
                    viewModel.placeList.clear()//清空集合内容
                    placeAdapter.notifyDataSetChanged()//更新适配器
                }
                /*设置清空按钮的显示*/
                val hasContent: Boolean = s.toString().isNotEmpty()//判断输入框是否有内容
                iv_clear_searchBox.visibility =
                    if (hasContent) View.VISIBLE else View.GONE//有内容则显示，无内容则隐藏
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun initAdapter() {
        /*设置布局管理器*/
        rv_placeAddress.layoutManager = LinearLayoutManager(activity)
        /*设置适配器*/
        placeAdapter = PlaceAdapter(this, viewModel.placeList)
        rv_placeAddress.adapter = placeAdapter
    }

    /**
     * 适配器条目点击方法实现
     */
    override fun onItemClick(position: Int, fragment: PlaceFragment) {
        /*获取数据*/
        val place = viewModel.placeList[position]
        /*判断当前是否是WeatherActivity*/
        val activity = fragment.activity//获取当前Activity
        if (activity is WeatherActivity) {
            activity.dl_leftCon.closeDrawers()//关闭滑动菜单
            activity.weatherViewModel.locationLng = place.location.lng//将经度赋值
            activity.weatherViewModel.locationLat = place.location.lat//将纬度赋值
            activity.weatherViewModel.placeName = place.name//将地区名称赋值
            activity.refreshWeather()//调用刷新天气方法
        } else {
            /*跳转到天气Activity*/
            val intent = Intent(activity, WeatherActivity::class.java).apply {
                putExtra(KEY_LOCATION_LNG, place.location.lng)
                putExtra(KEY_LOCATION_LAT, place.location.lat)
                putExtra(KEY_PLACE_NAME, place.name)
            }
            fragment.startActivity(intent)//启动跳转
            activity?.finish()//关闭当前Fragment
        }
        fragment.viewModel.savePlace(place)//通过sp保存当前地区数据
    }
}