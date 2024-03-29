package com.example.myappointments.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myappointments.R
import com.example.myappointments.io.ApiService
import com.example.myappointments.model.User
import com.example.myappointments.util.PreferenceHelper
import com.example.myappointments.util.PreferenceHelper.get
import com.example.myappointments.util.PreferenceHelper.set
import com.example.myappointments.util.toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_menu.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuActivity : AppCompatActivity() {

    private val apiService by lazy {
        ApiService.create()
    }

    private val preferences by lazy {
        PreferenceHelper.defaultPrefs(this)
    }

    private val authHeader by lazy {
        val jwt = preferences["jwt", ""]
        "Bearer $jwt"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val storeToken = intent.getBooleanExtra("store_token", false)
        if (storeToken)
            storeToken()

        setOnClickListeners()
    }

    private fun setOnClickListeners(){

        btnProfile.setOnClickListener{
            editProfile()
        }

        btnCreateAppointment.setOnClickListener{
            createAppointment(it)
        }

        btnMyAppointments.setOnClickListener {
            val intent = Intent(this, AppointmentsActivity::class.java)
            startActivity(intent)
        }

        btnLogout.setOnClickListener{
            performLogout()
        }
    }

    private fun createAppointment(view: View) {
        val call = apiService.getUser(authHeader)
        call.enqueue(object: Callback<User>{
            override fun onFailure(call: Call<User>, t: Throwable) {
                toast(t.localizedMessage)
            }

            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val user = response.body()
                    val phoneLenght = user?.phone?.length ?: 0

                    if ( phoneLenght >= 6) {
                        val intent = Intent(this@MenuActivity, CreateAppointmentActivity::class.java)
                        startActivity(intent)
                    } else {
                        Snackbar.make(view, R.string.you_need_a_phone, Snackbar.LENGTH_LONG).show()
                    }
                }
            }

        })


    }

    private fun editProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun storeToken() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) {
                instanceIdResult -> val deviceToken = instanceIdResult.token
            val call = apiService.postToken(authHeader, deviceToken)

            call.enqueue(object: retrofit2.Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    toast(t.localizedMessage)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Log.d(Companion.TAG, "Token registrado correctamente")
                    } else {
                        Log.d(Companion.TAG, "Hubo problema al registrar el token")
                    }
                }

            })
        }
    }

    private fun performLogout() {
        val call = apiService.postLogout(authHeader)
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

    companion object {
        private const val TAG = "MenuActivity"
    }
}
