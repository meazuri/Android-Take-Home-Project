package com.seint.takehome.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import java.io.Serializable
import java.util.*

@Entity
class Appointment  (
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id : Int =0,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "dob")
    var dob: Date,

    @ColumnInfo(name = "gender")
    var gender: String,

    @ColumnInfo(name = "appointmentDate")
    var appointmentDate: Date
):Serializable