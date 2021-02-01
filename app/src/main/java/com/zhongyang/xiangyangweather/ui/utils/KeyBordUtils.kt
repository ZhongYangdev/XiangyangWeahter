package com.zhongyang.xiangyangweather.ui.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * @项目名称 XiangyangWeather
 * @类名 KeyBordUtils
 * @包名 com.zhongyang.xiangyangweather.ui.utils
 * @创建时间 2021/2/1 19:33
 * @作者 钟阳
 * @描述 键盘控制工具
 */
object KeyBordUtils {

    /**
     * 键盘隐藏方法
     */
    fun hide(context: Context, view: View) {
        /*获取之后再强转*/
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * 显示键盘方法
     */
    fun show(context: Context, view: View) {
        val im = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(view, 0)
    }
}