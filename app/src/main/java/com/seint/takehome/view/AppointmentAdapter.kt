package com.seint.takehome.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seint.takehome.APPOINTMENT_FORMAT
import com.seint.takehome.DOB_FORMAT
import com.seint.takehome.R
import com.seint.takehome.formatDate
import com.seint.takehome.model.Appointment
import java.text.SimpleDateFormat
import java.util.*

class AppointmentAdapter (private val context: Context) : RecyclerView.Adapter<AppointmentAdapter.MyViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    private var appointmentList : List<Appointment> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = layoutInflater.inflate(R.layout.item_appointment_list,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return appointmentList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val appointment = appointmentList.get(position)
        holder.dataPosition = position
        holder.tvDate?.text = getFormattedDate(appointment.appointmentDate)
        holder.tvTitle?.text = appointment.name

    }


    inner class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val tvTitle = itemView?.findViewById<TextView?>(R.id.tvPatient)
        var tvDate = itemView?.findViewById<TextView?>(R.id.tvDate)
        var imgView = itemView?.findViewById<ImageView?>(R.id.imageView)
        var dataPosition = 0
        init {
            itemView?.setOnClickListener {

            }

        }
    }
    fun  setAppointmentData( imageData : List<Appointment>){
        appointmentList = imageData
        notifyDataSetChanged()
    }
    private fun getFormattedDate(lastUpdated: Date?): String {
        var time = "Appointment Date : "
        time += lastUpdated?.let {
            lastUpdated.formatDate(APPOINTMENT_FORMAT)
        } ?: "Not Found"
        return time
    }
}

