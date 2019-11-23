package com.example.myappointments.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myappointments.util.PreferenceHelper
import com.example.myappointments.util.PreferenceHelper.set
import com.example.myappointments.util.PreferenceHelper.get
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.util.toast
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MenuActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnCreateAppointment.setOnClickListener{
            val intent = Intent(this, CreateAppointmentActivity::class.java)
            startActivity(intent)
        }

        btnMyAppointments.setOnClickListener {
            val intent = Intent(this, AppointmentsActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener{
            performLogout()
        }
    }

    private fun performLogout() {
        val jwt = preferences["jwt", ""]
        val call = apiService.postLogout("Bearer $jwt")
        call.enqueue(object: retrofit2.Callback<Void> {
            override fun onFailure(call: Call<Void>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                clearSessionPreference()
                val intent = Intent(this@MenuActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

        })
    }

    private fun clearSessionPreference() {
        preferences["jwt"] = ""
    }
}
