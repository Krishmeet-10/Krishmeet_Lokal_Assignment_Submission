package com.example.lokal_assignment_app.Activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.example.lokal_assignment_app.Database_files.AppDatabase
import com.example.lokal_assignment_app.Database_files.StringEntry
import com.example.lokal_assignment_app.Database_files.StringEntryDao
import com.example.lokal_assignment_app.R
import com.example.lokal_assignment_app.jobs_data_Class.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch


class job_Description : AppCompatActivity() {
    private lateinit var name : TextView
    private lateinit var title : TextView
    private lateinit var location: TextView
    private lateinit var save_btn : Button
    private lateinit var apply_btn : Button
    private lateinit var content : TextView
    private lateinit var num : TextView

    private lateinit var db: AppDatabase
    private lateinit var dao: StringEntryDao

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_description)

        val job_info = intent?.getSerializableExtra("job_obj_data", Result::class.java)

        name = findViewById(R.id.comp_name)
        title = findViewById(R.id.job_title)
        location = findViewById(R.id.job_loc)
        save_btn = findViewById(R.id.save_button)
        apply_btn = findViewById(R.id.apply_button)
        content = findViewById(R.id.job_content)
        num = findViewById(R.id.job_no)

        db = AppDatabase.getDatabase(this)
        dao = db.stringEntryDao()

        name.text = job_info?.company_name
        title.text = job_info?.title
        location.text = job_info?.primary_details?.Place + ", India"
        content.text = "Salary : "+ job_info?.primary_details?.Salary + "\n"+"Language : "+ job_info?.primary_details?.Job_Type +"\n" + "Experience Required : " + job_info?.primary_details?.Experience + "\n" + "Required Qualifications : " +job_info?.primary_details?.Qualification
        num.text = "Whatsapp Number : " + job_info?.whatsapp_no
        var i=0;

        val newValue : String = resultToJson(job_info)

        save_btn.setOnClickListener {
            Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
            i++

            lifecycleScope.launch {
                val existingEntry = dao.getEntryByValue(newValue)
                if (existingEntry == null) {
                    dao.insert(StringEntry(value = newValue))
                    println("Inserted new entry: $newValue")
                } else {
                    println("Entry already exists: ${existingEntry.value}")
                }
            }
        }

        apply_btn.setOnClickListener {

            lifecycleScope.launch {
                val entries = dao.getAllEntries()
                if (entries.isNotEmpty()) {
                    dao.delete(entries[0])
                    println("Deleted entry with ID: ${entries[0].id}")
                }
            }
        }
    }

    fun resultToJson(result: Result?): String {
        val gson = Gson()
        return gson.toJson(result)
    }


}