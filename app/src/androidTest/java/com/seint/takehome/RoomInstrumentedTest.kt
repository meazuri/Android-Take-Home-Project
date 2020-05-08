package com.seint.takehome



import android.util.Log
import androidx.room.Room

import com.seint.takehome.model.AppDatabase
import com.seint.takehome.model.Appointment
import com.seint.takehome.model.AppointmentDao
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.runners.AndroidJUnit4


import org.junit.Assert.*

import java.io.IOException
import androidx.test.platform.app.InstrumentationRegistry

@RunWith(AndroidJUnit4::class)
class RoomInstrumentedTest  {
    private lateinit var apppintmenttDao: AppointmentDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val appContext = InstrumentationRegistry.getInstrumentation().context
        db = Room.inMemoryDatabaseBuilder(
            appContext, AppDatabase::class.java).build()
        apppintmenttDao = db.appointmentDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() {
        val appointment: Appointment = TestUtils.createAppointment().apply {
            name = "george"
        }
        apppintmenttDao.insertAppointment(appointment)
        val byName = apppintmenttDao.getPatientByName("george")

        assertThat(byName.get(0).name, equalTo(appointment.name))
    }
}