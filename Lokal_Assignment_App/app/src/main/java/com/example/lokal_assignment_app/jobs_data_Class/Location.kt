package com.example.lokal_assignment_app.jobs_data_Class

import java.io.Serializable

data class Location(
    val id: Int,
    val locale: String,
    val state: Int
) : Serializable