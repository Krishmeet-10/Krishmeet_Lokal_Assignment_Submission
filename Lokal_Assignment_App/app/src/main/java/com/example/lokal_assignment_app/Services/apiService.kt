package com.example.lokal_assignment_app.Services

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.example.lokal_assignment_app.jobs_data_Class.Result
import com.example.lokal_assignment_app.jobs_data_Class.jobs_Data

class ApiService(context: Context) {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(context)
    private val gson = Gson()

    fun fetchJobData(url: String, onSuccess: (List<Result>) -> Unit, onError: (Exception) -> Unit) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    val jobsData = gson.fromJson(response.toString(), jobs_Data::class.java)
                    onSuccess(jobsData.results)
                } catch (e: Exception) {
                    onError(e)
                }
            },
            { error ->
                onError(Exception(error.message))
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

}
