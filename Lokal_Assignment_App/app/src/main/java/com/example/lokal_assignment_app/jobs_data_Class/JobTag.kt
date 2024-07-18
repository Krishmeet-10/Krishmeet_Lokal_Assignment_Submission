package com.example.lokal_assignment_app.jobs_data_Class

import java.io.Serializable

data class JobTag(
    val bg_color: String,
    val text_color: String,
    val value: String
) : Serializable