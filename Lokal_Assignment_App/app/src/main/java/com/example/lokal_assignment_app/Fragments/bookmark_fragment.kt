package com.example.lokal_assignment_app.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lokal_assignment_app.Database_files.AppDatabase
import com.example.lokal_assignment_app.Database_files.StringEntryDao
import com.example.lokal_assignment_app.R
import com.example.lokal_assignment_app.jobs_data_Class.Result
import com.example.lokal_assignment_app.Adapters.rcv_Adapter
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class bookmark_fragment : Fragment() {

    private lateinit var db: AppDatabase
    private lateinit var dao: StringEntryDao

    private lateinit var rcv_bkm: RecyclerView
    private lateinit var jobdata_bkm: ArrayList<Result>
    private lateinit var rcvAdapter: rcv_Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark_fragment, container, false)

        db = AppDatabase.getDatabase(requireContext())
        dao = db.stringEntryDao()

        rcv_bkm = view.findViewById(R.id.rcv_bookmark)
        rcv_bkm.layoutManager = LinearLayoutManager(context)

        jobdata_bkm = ArrayList()
        rcvAdapter = rcv_Adapter(requireContext(), jobdata_bkm, viewLifecycleOwner,1)
        rcv_bkm.adapter = rcvAdapter

        loadBookmarks()

        return view
    }

    private fun loadBookmarks() {
        lifecycleScope.launch {
            val entries = withContext(Dispatchers.IO) {
                dao.getAllEntries()
            }
            val results = entries.mapNotNull { jsonToResult(it.value) }
            jobdata_bkm.addAll(results)
            rcvAdapter.notifyDataSetChanged()
        }
    }

    private fun jsonToResult(jsonString: String): Result? {
        val gson = Gson()
        return gson.fromJson(jsonString, Result::class.java)
    }

}
