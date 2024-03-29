package com.example.myappointments.ui

import android.os.Build
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myappointments.R
import com.example.myappointments.model.Appointment
import kotlinx.android.synthetic.main.item_appointment.view.*

class AppointmentAdapter : RecyclerView.Adapter<AppointmentAdapter.ViewHolder>(){

    var appointments = ArrayList<Appointment>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        fun bind(appointment: Appointment) = with (itemView) {
                tvAppointmentId.text = context.getString(R.string.item_appointment_id, appointment.id)
                tvDoctorName.text = appointment.doctor.name
                tvScheduledDate.text = context.getString(R.string.item_appointment_date, appointment.scheduledDate)
                tvScheduledTime.text = context.getString(R.string.item_appointment_time, appointment.scheduledTime)

            tvSpecialty.text = appointment.specialty.name
            tvDescription.text = appointment.description
            tvStatus.text = appointment.status
            tvType.text = appointment.type
            tvCreateAt.text = context.getString(R.string.item_appointment_created_at, appointment.createdAt)

            ibExpand.setOnClickListener{

                TransitionManager.beginDelayedTransition(parent as ViewGroup,AutoTransition())

                if (linearLayoutDetails.visibility == View.VISIBLE) {
                    linearLayoutDetails.visibility = View.GONE
                    ibExpand.setImageResource(R.drawable.ic_expand_more)
                } else {
                    linearLayoutDetails.visibility = View.VISIBLE
                    ibExpand.setImageResource(R.drawable.ic_expand_less)
                }
            }
        }
    }
    //Inflates XML items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_appointment,
                parent,
                false
            )
        )
    }

    //Binds data
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val appointment = appointments[position]

        holder.bind(appointment)
    }

    //Number of elements
    override fun getItemCount() = this.appointments.size
}