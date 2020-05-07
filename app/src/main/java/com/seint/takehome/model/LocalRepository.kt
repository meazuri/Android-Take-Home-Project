package com.seint.takehome.model

import androidx.lifecycle.LiveData
import android.app.Application
import android.os.AsyncTask


class LocalRepository (private val appointmentDao: AppointmentDao,private val application: Application) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    var allAppointments: LiveData<List<Appointment>> =appointmentDao.getAllAppointments()


    fun clearData() {
        appointmentDao.nukeTable()
    }

    fun bookAppointment(appointment: Appointment){
        InsertAsyncTask(appointmentDao).execute(appointment)
    }
    companion object {
        private class InsertAsyncTask(private val appointmentDao: AppointmentDao) : AsyncTask<Appointment, Void, Void>() {

            override fun doInBackground(vararg appointments: Appointment): Void? {
                appointmentDao.insertAppointment(appointments[0])
                return null
            }
        }

        private class UpdateAsyncTask(private val appointmentDao: AppointmentDao) : AsyncTask<Appointment, Void, Void>() {

            override fun doInBackground(vararg appointments: Appointment): Void? {
                appointmentDao.update(appointments[0])
                return null
            }
        }

        private class DeleteAsyncTask(private val appointmentDao: AppointmentDao) : AsyncTask<Appointment, Void, Void>() {

            override fun doInBackground(vararg appointments: Appointment): Void? {
                appointmentDao.delete(appointments[0])
                return null
            }
        }
    }
}