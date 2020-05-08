package com.seint.takehome

import com.seint.takehome.model.Appointment
import com.seint.takehome.model.Gender
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.text.SimpleDateFormat
import java.util.*

class AppointmentUnitTest {

    private  val patientName = "Susie"
    private  val dateOfBirth: Date = parseDateString("23/10/1975")!!.time
    private  val gender :Gender = Gender.FEMALE
    private  val appointment = Date()
    private  val defaultDate = appointment


    @Mock
    var appointmentEntity: Appointment? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(appointmentEntity!!.name).thenReturn(patientName)
        Mockito.`when`(appointmentEntity!!.dob).thenReturn(dateOfBirth)
        Mockito.`when`(appointmentEntity!!.gender).thenReturn(gender.name)
        Mockito.`when`(appointmentEntity!!.appointmentDate).thenReturn(appointment)

    }

    @Test
    fun testName() {
        Mockito.`when`(appointmentEntity!!.name).thenReturn(patientName)
        Assert.assertEquals("Susie", appointmentEntity!!.name)
    }

    @Test
    fun testDateOfBirth() {
        Mockito.`when`(appointmentEntity!!.dob).thenReturn(dateOfBirth)
        Assert.assertEquals(parseDateString("23/10/1975")!!.time, appointmentEntity!!.dob)
    }

    @Test
    fun testGender() {
        Mockito.`when`(appointmentEntity!!.gender).thenReturn(gender.name)
        Assert.assertEquals(Gender.FEMALE.name, appointmentEntity!!.gender)
    }

    @Test
    fun testAppointment() {
        Mockito.`when`(appointmentEntity!!.appointmentDate).thenReturn(appointment)
        Assert.assertEquals(defaultDate, appointmentEntity!!.appointmentDate)
    }



    @Test
    fun testPatientNameIncorrect() {
        Mockito.`when`(appointmentEntity!!.name).thenReturn(patientName)
        Assert.assertNotEquals("Title", appointmentEntity!!.name)
    }

    @Test
    fun testGenderIncorrect() {
        Mockito.`when`(appointmentEntity!!.gender).thenReturn(gender.name)
        Assert.assertNotEquals(Gender.OTHER.name, appointmentEntity!!.gender)
    }

    @Test
    fun dateOfBirthFormatCorrect(){
        Assert.assertEquals(true,isValidBirthday("19/08/2000"))
    }
    @Test
    fun dateOfBirthFormatInCorrectCase1(){
        Assert.assertNotEquals(true,isValidBirthday("19/08/1700"))
    }

    @Test
    fun dateOfBirthFormatInCorrectCase2(){
        val dateCalendar = Calendar.getInstance()
        dateCalendar.add(Calendar.DAY_OF_YEAR,1)
        val format1 = SimpleDateFormat("dd/MM/yyyy")
        val formatted = format1.format(dateCalendar.getTime())
        Assert.assertNotEquals(true,isValidBirthday(formatted))
    }
    @Test
    fun  appointmentDateCorrectCase(){
        val dateCalendar = Calendar.getInstance()
        dateCalendar.add(Calendar.DAY_OF_YEAR,1)
        Assert.assertEquals(true, isValidAppointmentDate(dateCalendar.time))
    }
    @Test
    fun  appointmentDateInCorrectCase(){
        val dateCalendar = Calendar.getInstance()
        dateCalendar.add(Calendar.DAY_OF_YEAR,-1)
        Assert.assertNotEquals(true, isValidAppointmentDate(dateCalendar.time))
    }
    @After
    @Throws(Exception::class)
    fun tearDown() {
        appointmentEntity = null
    }
}

