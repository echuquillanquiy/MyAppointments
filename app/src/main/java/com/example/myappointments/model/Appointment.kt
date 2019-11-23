package com.example.myappointments.model

import com.google.gson.annotations.SerializedName

/*
        "id": 13,
        "description": "In saepe ipsam omnis unde.",
        "scheduled_date": "2019-07-27",
        "type": "Examen",
        "created_at": "2019-11-15 12:15:02",
        "status": "Cancelada",
        "scheduled_time_12": "8:24 AM",
        "specialty": {
            "id": 1,
            "name": "Oftalmología"
        },
        "doctor": {
            "id": 55,
            "name": "Cielo Satterfield"
        }
* */


data class Appointment (
    val id: Int,
    val description: String,
    val type: String,
    val status: String,

    val doctorName: String,
    @SerializedName  ("scheduled_date") val scheduledDate: String,
    @SerializedName ("scheduled_time_12") val scheduledTime: String,
    @SerializedName ("created_at") val createdAt: String,

    val specialty: Specialty,
    val doctor: Doctor
)