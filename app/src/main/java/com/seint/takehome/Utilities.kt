package com.seint.takehome

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Utilities{

fun showDialog(context : Context , title: String, message : String){

    AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(message)
        .setPositiveButton(R.string.ok,
            DialogInterface.OnClickListener { dialog, which ->
            })
        .setIcon(R.drawable.ic_dialog_info)
        .show()
}
}

fun isValidAppointmentDate(date: Date) :Boolean{
    val calendar: Calendar = Calendar.getInstance()
    calendar.timeInMillis =date.time

    val year = calendar[Calendar.YEAR]
    val date = calendar[Calendar.DAY_OF_YEAR]
    val thisYear = Calendar.getInstance()[Calendar.YEAR]
    val thisDate = Calendar.getInstance()[Calendar.DAY_OF_YEAR]
    return year >= thisYear && date >= thisDate

}

fun isValidBirthday(birthday: String?): Boolean {
    val calendar: Calendar? = parseDateString(birthday)
    if ( calendar != null) {
        val year = calendar[Calendar.YEAR]
        val date = calendar[Calendar.DAY_OF_YEAR]
        val thisYear = Calendar.getInstance()[Calendar.YEAR]
        val thisDate = Calendar.getInstance()[Calendar.DAY_OF_YEAR]
        return year in 1900 until  thisYear || (year ==  thisYear && date <= thisDate )
    }
    return false
}
fun parseDateString(date: String?): Calendar? {
    val calendar = Calendar.getInstance()
    val sourceFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy")
    try {
        var result =sourceFormat.parse(date)
        calendar.timeInMillis = result.time
    } catch (e: ParseException) {
    }
    Log.i("DOB",calendar.time.toString())
    return calendar
}