package com.seint.takehome.view

import android.app.Activity
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.MenuItem
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.seint.takehome.*
import com.seint.takehome.model.Appointment
import com.seint.takehome.model.Gender
import com.seint.takehome.viewModel.CreateAppointmentViewModel
import kotlinx.android.synthetic.main.activity_book_appointment.*
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class BookAppointmentActivity : AppCompatActivity() {
    lateinit var appointmentViewModel: CreateAppointmentViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)

        supportActionBar?.title = resources.getString(R.string.book_appointment)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        tvDateOfBirth.inputType = InputType.TYPE_CLASS_DATETIME
        tvAppointment.inputType = InputType.TYPE_NULL
        tvAppointment.setOnKeyListener(null)


        appointmentViewModel = ViewModelProviders.of(this).get(CreateAppointmentViewModel::class.java)
        appointmentViewModel.appointmentDate?.let {
            tvAppointment.setText(it.formatDate("").toString())
        }
        appointmentViewModel.dob?.let {
            tvDateOfBirth.setText(it.formatDate("").toString())
        }

        uiSetup()


    }

    fun uiSetup(){

        tvAppointment.setOnClickListener {
            val builder =
                MaterialDatePicker.Builder.datePicker()
            builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)

            builder.setSelection(appointmentViewModel.appointmentDate?.time)
            val picker: MaterialDatePicker<*> = builder.build()
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {



               var calendar = Calendar.getInstance()
                calendar.timeInMillis = it as Long

                var currentTime = Calendar.getInstance()
                calendar[Calendar.HOUR_OF_DAY] = currentTime.get(Calendar.HOUR_OF_DAY)
                calendar[Calendar.MINUTE] = currentTime.get(Calendar.MINUTE)
                calendar[Calendar.SECOND] = currentTime.get(Calendar.SECOND)
                appointmentViewModel.appointmentDate = calendar.time

                tvAppointment.setText(appointmentViewModel.appointmentDate?.formatDate(
                    APPOINTMENT_FORMAT).toString())
                validateRequiredFields()


                //If we what to pick Time
                // Get the offset from our timezone and UTC.
                val timeZoneUTC = TimeZone.getDefault()
                // It will be negative, so that's the -1
                val offsetFromUTC = timeZoneUTC.getOffset(Date().time) * -1
                // Create a date format, then a date object with our offset
                val date = Date(it as Long + offsetFromUTC)
                //showTimePicker(date)

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
            validateRequiredFields()
        })
        btnBook.setOnClickListener {

            if(validateRequiredFields()) {
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
    }
    fun  validateRequiredFields() : Boolean {

        var name = etPatientName.text.toString()
        if(name.isBlank() || name.isEmpty()){
            etPatientName.setError(resources.getString(R.string.patientNameisRequired))
            return false
        }
        val dateOfBirth = tvDateOfBirth.text.toString()
        if (dateOfBirth.isEmpty()){
            tvDateOfBirth.setError("Date Of Birth is required")
            return false
        }else{
            val valid_bday =  "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)"
            var validbday: Matcher = Pattern.compile(valid_bday).matcher(dateOfBirth);

             if(!validbday.matches())
            {
                tvDateOfBirth.setError("Invalid date format")
                return false
            }else {

                 if(isValidBirthday(dateOfBirth)) {
                     val format: DateFormat = SimpleDateFormat(DOB_FORMAT)
                     val date: Date? = format.parse(dateOfBirth)
                     appointmentViewModel.dob = date

                     tvDateOfBirth.setError(null)
                 }else {
                     tvDateOfBirth.setError("Invalid Date Of Birth")
                     return false

                 }
             }
        }
        if (appointmentViewModel.gender.isNullOrEmpty()){
            autoTextGender.setError("Gender is required")
            return false
        }else{
            autoTextGender.setError(null)
        }
        if (appointmentViewModel.appointmentDate == null) {
            tvAppointment.setError("Appointment Date is required")
            return  false
        }else{
            if(isValidAppointmentDate(appointmentViewModel.appointmentDate!!)){
                tvAppointment.setError(null)
            }else{
                tvAppointment.setError("Please choose different date .")
                return  false
            }

        }
        return true

    }


    fun showTimePicker(date:Date){
        val mcurrentTime = Calendar.getInstance()
        mcurrentTime.setTimeInMillis(date.getTime());
        val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
        val minute = mcurrentTime[Calendar.MINUTE]
        val mTimePicker: TimePickerDialog
        mTimePicker = TimePickerDialog(this,
            OnTimeSetListener { timePicker, selectedHour, selectedMinute ->

                mcurrentTime[Calendar.HOUR_OF_DAY] = selectedHour
                mcurrentTime[Calendar.MINUTE] = selectedMinute
                appointmentViewModel.appointmentDate = mcurrentTime.time

                tvAppointment.setText(appointmentViewModel.appointmentDate?.formatDate(
                    APPOINTMENT_FORMAT).toString())
                validateRequiredFields()

            },
            hour,
            minute,
            true
        ) //Yes 24 hour time

        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
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

