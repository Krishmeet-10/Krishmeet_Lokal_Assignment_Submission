package com.example.lokal_assignment_app.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.lokal_assignment_app.Activities.job_Description
import com.example.lokal_assignment_app.Database_files.AppDatabase
import com.example.lokal_assignment_app.Database_files.StringEntry
import com.example.lokal_assignment_app.Database_files.StringEntryDao
import com.example.lokal_assignment_app.R
import com.example.lokal_assignment_app.jobs_data_Class.Result
import com.google.gson.Gson
import kotlinx.coroutines.launch

class rcv_Adapter(private val context: Context,private val dataSet: ArrayList<Result>,private val lifecycleOwner: LifecycleOwner, private var check_type:Int)
    : RecyclerView.Adapter<rcv_Adapter.ViewHolder>() {

    private lateinit var db: AppDatabase
    private lateinit var dao: StringEntryDao

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var title:TextView = view.findViewById(R.id.rcv_title)
        var company:TextView = view.findViewById(R.id.rcv_company)
        var contact:TextView = view.findViewById(R.id.rcv_contact)
        var salary:TextView = view.findViewById(R.id.rcv_salary)
        var location:TextView = view.findViewById(R.id.rcv_location)
        var save_btn : ImageButton = view.findViewById(R.id.save_btn_rcv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_page_rcv_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.filter { it.title?.isEmpty() == false }.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val filteredList = dataSet.filter { it.title?.isEmpty() == false }
        val filteredItem = filteredList[position]

        holder.title.text = filteredItem.title ?: ""
        holder.company.text = filteredItem.company_name ?: ""

        holder.contact.text = "Mobile: " + (filteredItem.whatsapp_no ?: "Unavailable")
        val salaryMin = filteredItem.salary_min ?: 0
        val salaryMax = filteredItem.salary_max ?: 0
        holder.salary.text = if (salaryMin == 0 && salaryMax == 0) {
            "Salary : Undisclosed"
        } else {
            "Salary : "+"$salaryMin k to $salaryMax k"
        }

        holder.location.text =
            (filteredItem.primary_details?.Place ?: "") + ", India"
        db = AppDatabase.getDatabase(context)
        dao = db.stringEntryDao()

        val newValue = resultToJson(filteredItem)

        if(check_type==0){
            holder.save_btn.setOnClickListener {
                lifecycleOwner.lifecycleScope.launch {
                    val existingEntry = dao.getEntryByValue(newValue)
                    if (existingEntry == null) {
                        dao.insert(StringEntry(value = newValue))
                        println("Inserted new entry: $newValue")
                        Toast.makeText(context,"Job Bookmarked",Toast.LENGTH_SHORT).show()
                    } else {
                        println("Entry already exists: ${existingEntry.value}")
                        Toast.makeText(context,"Job Already Bookmarked",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        else if(check_type==1){
            holder.save_btn.setOnClickListener{
                lifecycleOwner.lifecycleScope.launch {
                    val deletedRows = dao.deleteById(filteredItem.id)

                    if (deletedRows > 0) {
                        // Entry successfully deleted
                        println("Job entry with ID $filteredItem.id deleted from bookmarks.")
                    } else {
                        println("No job entry found with ID $filteredItem.id or deletion failed.")
                    }
                }
            }
        }



        holder.itemView.setOnClickListener{
            val intent = Intent(context, job_Description::class.java).apply {
                putExtra("job_obj_data", filteredItem)
            }
            context.startActivity(intent)
        }
    }

    fun resultToJson(result: Result): String {
        val gson = Gson()
        return gson.toJson(result)
    }

}