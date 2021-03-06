package com.zhongyang.xiangyangweather.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zhongyang.xiangyangweather.R
import com.zhongyang.xiangyangweather.logic.model.Place
import com.zhongyang.xiangyangweather.ui.fragment.PlaceFragment

/**
 * @项目名称 XiangyangWeather
 * @类名 PlaceAdapter
 * @包名 com.zhongyang.xiangyangweather.ui.adapter
 * @创建时间 2021/2/1 15:32
 * @作者 钟阳
 * @描述 显示获取到的地区的适配器
 */
class PlaceAdapter(private val fragment: PlaceFragment, private val mData: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.InnerHolder>() {

    private lateinit var mOnPlaceItemClickListener: OnPlaceItemClickListener

    inner class InnerHolder(view: View) : RecyclerView.ViewHolder(view) {
        /*初始化控件*/
        val placeName: TextView = view.findViewById(R.id.tv_placeName)
        val placeAddress: TextView = view.findViewById(R.id.tv_placeAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceAdapter.InnerHolder {
        /*载布局*/
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_place_item, parent, false)
        return InnerHolder(view)
    }

    override fun onBindViewHolder(holder: PlaceAdapter.InnerHolder, position: Int) {
        /*绑定数据*/
        val place = mData[position]
        holder.placeName.text = place.name//设置地区名称
        holder.placeAddress.text = place.address//设置地区地址
        /*设置条目点击事件*/
        holder.itemView.setOnClickListener {
            mOnPlaceItemClickListener.onItemClick(position,fragment)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    /**
     * 设置条目点击接口
     */
    interface OnPlaceItemClickListener {
        fun onItemClick(position: Int, fragment: PlaceFragment)
    }

    /**
     * 设置点击方法
     */
    fun setOnPlaceItemClickListener(listener: OnPlaceItemClickListener) {
        this.mOnPlaceItemClickListener = listener
    }
}