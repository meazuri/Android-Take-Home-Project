package com.seint.takehome

import com.seint.takehome.model.Appointment
import com.seint.takehome.model.Gender
import java.util.*

class TestUtils {
    companion object {

        fun createAppointment() : Appointment {
            var appointment = Appointment(dob =parseDateString("28/05/1990")?.time!!,name = "susie",gender = Gender.OTHER.name,appointmentDate = Date())
            return appointment
        }
    }


}
