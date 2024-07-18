package com.example.lokal_assignment_app.jobs_data_Class

import java.io.Serializable

data class PrimaryDetails(
    val Experience: String,
    val Fees_Charged: String,
    val Job_Type: String,
    val Place: String,
    val Qualification: String,
    val Salary: String
) : Serializable