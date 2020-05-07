package com.seint.takehome

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface


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