package com.seint.takehome.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.seint.takehome.APPOINTMENT_FORMAT
import com.seint.takehome.DOB_FORMAT
import com.seint.takehome.R
import com.seint.takehome.formatDate
import com.seint.takehome.model.Appointment
import com.seint.takehome.model.Gender
import com.seint.takehome.viewModel.CreateAppointmentViewModel
import kotlinx.android.synthetic.main.activity_book_appointment.*
import java.util.*


class BookAppointmentActivity : AppCompatActivity() {
    lateinit var appointmentViewModel: CreateAppointmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)
        supportActionBar?.title = resources.getString(R.string.book_appointment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)



        appointmentViewModel = ViewModelProviders.of(this).get(CreateAppointmentViewModel::class.java)
        appointmentViewModel.appointmentDate?.let {
            tvAppointment.text = it.formatDate("").toString()
        }
        appointmentViewModel.dob?.let {
            tvDateOfBirth.text = it.formatDate("").toString()
        }

        tvDateOfBirth.setOnClickListener {
            val builder =
                MaterialDatePicker.Builder.datePicker()
            builder.setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)


            val constraintsBuilder = CalendarConstraints.Builder()  // 1

            //set start date for date of birth
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR,calendar.get(Calendar.YEAR)- 120)
            calendar.set(Calendar.MONTH,0)
            calendar.set(Calendar.DAY_OF_YEAR,1)
            constraintsBuilder.setStart(calendar.timeInMillis)   //   2

            //set until current date and time
            calendar.roll(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR))   //   3
            constraintsBuilder.setEnd(Calendar.getInstance().timeInMillis)   // 4

            builder.setCalendarConstraints(constraintsBuilder.build())   //  5

            val picker: MaterialDatePicker<*> = builder.build()
            builder.setSelection(appointmentViewModel.dob?.time)
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {

                // Get the offset from our timezone and UTC.
                val timeZoneUTC = TimeZone.getDefault()
                // It will be negative, so that's the -1
                val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
                // Create a date format, then a date object with our offset
                val date = Date(it as Long + offsetFromUTC)
                appointmentViewModel.dob = date
                tvDateOfBirth.text = appointmentViewModel.dob?.formatDate(DOB_FORMAT).toString()
            }
        }

        tvAppointment.setOnClickListener {
            val builder =
                MaterialDatePicker.Builder.datePicker()
            builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)


            val constraintsBuilder = CalendarConstraints.Builder()  // 1
            val calendar = Calendar.getInstance()
            constraintsBuilder.setStart(calendar.timeInMillis)   //   2
            calendar.roll(Calendar.YEAR, 1)   //   3
            constraintsBuilder.setEnd(calendar.timeInMillis)   // 4
            builder.setCalendarConstraints(constraintsBuilder.build())   //  5
            builder.setSelection(appointmentViewModel.appointmentDate?.time)
            val picker: MaterialDatePicker<*> = builder.build()
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {

                // Get the offset from our timezone and UTC.
                val timeZoneUTC = TimeZone.getDefault()
                // It will be negative, so that's the -1
                val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
                // Create a date format, then a date object with our offset
                val date = Date(it as Long + offsetFromUTC)
                appointmentViewModel.appointmentDate =date
                tvAppointment.text = appointmentViewModel.appointmentDate?.formatDate(
                    APPOINTMENT_FORMAT).toString()
            }

        }
        val adapter: ArrayAdapter<Gender> = ArrayAdapter<Gender>(
            this,
            android.R.layout.simple_dropdown_item_1line, Gender.values()
        )
        autoTextGender.setAdapter(adapter)
        autoTextGender.setOnItemClickListener(OnItemClickListener { adapterView, view, i, l ->
            val itemName: Gender? = adapter.getItem(i)
            appointmentViewModel.gender = itemName.toString()
        })
        btnBook.setOnClickListener {

            var newAppointment = appointmentViewModel.dob?.let { it1 ->
                appointmentViewModel.appointmentDate?.let { it2 ->
                    Appointment(
                        name = etPatientName.text.toString(),
                        dob = it1,
                        gender = appointmentViewModel.gender!!,
                        appointmentDate = it2
                    )
                }
            }
            val resultIntent = Intent()
            resultIntent.putExtra(NEW_APPOINTMENT, newAppointment)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }
    }

    companion object {
        const val NEW_APPOINTMENT = "NEW_APPOINTMENT"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

