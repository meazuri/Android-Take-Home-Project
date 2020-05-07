package com.seint.takehome.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.seint.takehome.model.AppDatabase
import com.seint.takehome.model.Appointment
import com.seint.takehome.model.LocalRepository

class AppointmentViewModel  ( application: Application) : AndroidViewModel(application) {

    var appointmentListObservable: LiveData<List<Appointment>> = MutableLiveData<List<Appointment>>()
    val localRepository : LocalRepository

    init {
        val appointmentDao = AppDatabase.getDataBase(application).appointmentDao()
        localRepository = LocalRepository(appointmentDao,application)

        //init live data
        appointmentListObservable = localRepository.allAppointments
    }

    fun insert(appointment: Appointment) {
        localRepository.bookAppointment(appointment)
    }
}