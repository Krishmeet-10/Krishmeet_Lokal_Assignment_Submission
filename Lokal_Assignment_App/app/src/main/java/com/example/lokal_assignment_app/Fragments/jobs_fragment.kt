package com.example.lokal_assignment_app.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokal_assignment_app.Services.ApiService
import com.example.lokal_assignment_app.R
import com.example.lokal_assignment_app.jobs_data_Class.Result
import com.example.lokal_assignment_app.Adapters.rcv_Adapter

class jobs_fragment : Fragment() {
    private var url = "https://testapi.getlokalapp.com/common/jobs?page=3"
    private lateinit var jobDataObjList: ArrayList<Result>
    private lateinit var rcv: RecyclerView

    private val uniqueBy = { result: Result -> result.id } // Example: Check uniqueness by id


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_jobs_fragment, container, false)
        rcv = view.findViewById(R.id.rcv_jobs)
        rcv.layoutManager = LinearLayoutManager(context)

        jobDataObjList = ArrayList()
        val apiService = context?.let { ApiService(it) }

        apiService?.fetchJobData(url, { results ->
            val seenIds = HashSet<Int>() // Track seen IDs

            jobDataObjList.clear()
            jobDataObjList.addAll(results.filter {
                val isUnique = uniqueBy(it) !in seenIds
                if (isUnique) {
                    seenIds.add(uniqueBy(it)) // Add ID to seen set if unique
                }
                isUnique
            })

            Log.i("TAG_DATA", jobDataObjList.toString())
            val rcvAdapter = context?.let { rcv_Adapter(it, jobDataObjList, viewLifecycleOwner, 0) }
            rcv.adapter = rcvAdapter
        }, { error ->
            // Handle the error here
            error.printStackTrace()
        })

        return view
    }

}