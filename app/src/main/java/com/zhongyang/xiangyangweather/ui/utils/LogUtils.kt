package com.zhongyang.xiangyangweather.ui.utils

import android.util.Log

/**
 * @项目名称 XiangyangWeather
 * @类名 LogUtils
 * @包名 com.zhongyang.xiangyangweather.ui.utils
 * @创建时间 2021/1/31 18:53
 * @作者 钟阳
 * @描述 日志工具类
 */
object LogUtils {

    private const val VERBOSE = 1
    private const val DEBUG = 2
    private const val INFO = 3
    private const val WARN = 4
    private const val ERROR = 5

    private var level = VERBOSE//当前等级

    fun v(tag: String, msg: String) {
        if (level <= VERBOSE) {
            Log.v(tag, msg)
        }
    }

    fun d(tag: String, msg: String) {
        if (level <= DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun i(tag: String, msg: String) {
        if (level <= INFO) {
            Log.i(tag, msg)
        }
    }

    fun w(tag: String, msg: String) {
        if (level <= WARN) {
            Log.w(tag, msg)
        }
    }

    fun e(tag: String, msg: String) {
        if (level <= ERROR) {
            Log.e(tag, msg)
        }
    }
}