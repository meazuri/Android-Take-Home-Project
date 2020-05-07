package com.seint.takehome.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.seint.takehome.R
import com.seint.takehome.model.Appointment
import com.seint.takehome.view.BookAppointmentActivity.Companion.NEW_APPOINTMENT
import com.seint.takehome.viewModel.AppointmentViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var mAdapter: AppointmentAdapter
    lateinit var appointmentViewModel: AppointmentViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar.setTitle(resources.getString(R.string.appointment_list))
        setSupportActionBar(toolbar)

        mAdapter = AppointmentAdapter(this)
        val lm = LinearLayoutManager(this)
        lm.orientation = RecyclerView.VERTICAL

        recyclerView.layoutManager = lm
        recyclerView.adapter = mAdapter
        recyclerView.isNestedScrollingEnabled = false

        appointmentViewModel = ViewModelProviders.of(this).get(AppointmentViewModel::class.java)

        appointmentViewModel.appointmentListObservable.observe(this, Observer {

            if(it != null){
                mAdapter.setAppointmentData(it)
                mAdapter.notifyDataSetChanged()
            }
        })

        fab.setOnClickListener { view ->
            startActivityForResult(Intent(this,BookAppointmentActivity::class.java),1)
            overridePendingTransition(R.anim.slide_up,R.anim.stay_normal);

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1 && data != null){
            var newAppointment = data.getSerializableExtra(NEW_APPOINTMENT) as? Appointment
            appointmentViewModel.insert(newAppointment!!)
        }
    }


}
