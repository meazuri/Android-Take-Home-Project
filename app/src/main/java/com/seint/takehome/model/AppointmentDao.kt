package com.seint.takehome.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM Appointment ORDER BY appointmentDate ASC")
    fun  getAllAppointments():LiveData<List<Appointment>>

    @Query("DELETE FROM Appointment")
    fun nukeTable()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAppointment(appointment: Appointment)

    @Update
    fun update(appointment: Appointment)

    @Delete
    fun delete(appointment: Appointment)

}