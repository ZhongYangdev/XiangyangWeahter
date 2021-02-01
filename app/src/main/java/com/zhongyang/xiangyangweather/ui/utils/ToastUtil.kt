package com.zhongyang.xiangyangweather.ui.utils

import android.annotation.SuppressLint
import android.widget.Toast

/**
 * @项目名称 XiangyangWeather
 * @类名 ToastUtil
 * @包名 com.zhongyang.xiangyangweather.ui.utils
 * @创建时间 2021/1/31 18:42
 * @作者 钟阳
 * @描述 Toast工具类
 */
object ToastUtil {

    private var mToast: Toast? = null

    @SuppressLint("ShowToast")
    fun showToast(tips: String) {

        if (mToast == null) {
            //TODO: mToast = Toast.makeText(context, tips, Toast.LENGTH_SHORT)
        } else {
            mToast?.setText(tips)
        }
        /*显示*/
        mToast?.show()
    }

}