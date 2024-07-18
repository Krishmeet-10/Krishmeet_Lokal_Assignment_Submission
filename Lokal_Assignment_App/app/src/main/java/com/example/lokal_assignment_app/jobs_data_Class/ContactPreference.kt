package com.example.lokal_assignment_app.jobs_data_Class

import java.io.Serializable

data class ContactPreference(
    val preference: Int,
    val preferred_call_end_time: String,
    val preferred_call_start_time: String,
    val whatsapp_link: String
): Serializable