package com.example.lokal_assignment_app.jobs_data_Class

import java.io.Serializable

data class Creative(
    val creative_type: Int,
    val `file`: String,
    val image_url: String,
    val order_id: Int,
    val thumb_url: String
) : Serializable