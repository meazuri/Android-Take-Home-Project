package com.seint.takehome

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(format: String) :String{

    var time = ""

    if (format.isNotEmpty()){
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        time +=  sdf.format(this)

    }else{
        val sdf = SimpleDateFormat("HH:mm d MMM, yyyy", Locale.getDefault())
        time += sdf.format(this)
    }
    return time
}
