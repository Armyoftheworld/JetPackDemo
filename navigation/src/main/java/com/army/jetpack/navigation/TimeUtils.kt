package com.army.jetpack.navigation

import java.text.SimpleDateFormat
import java.util.*

/**
 * @author daijun
 * @date 2020/2/21
 * @description
 */

val dataFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.CHINA)


fun currentTime() = dataFormat.format(Date())