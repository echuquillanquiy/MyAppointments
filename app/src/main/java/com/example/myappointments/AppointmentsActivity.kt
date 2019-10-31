package com.example.myappointments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myappointments.model.Appointment
import kotlinx.android.synthetic.main.activity_appointments.*

class AppointmentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_appointments)

        val appointments = ArrayList<Appointment>()
        appointments.add(
            Appointment(1, "Médico Test", "12/12/2019", "3:00 PM"  )
        )

        appointments.add(
            Appointment(2, "Eduardo", "02/12/2019", "4:00 PM"  )
        )

        appointments.add(
            Appointment(3, "Sofia", "25/12/2019", "6:00 PM"  )
        )

        rvAppointments.layoutManager = LinearLayoutManager(this)
        rvAppointments.adapter = AppointmentAdapter(appointments)
    }
}
