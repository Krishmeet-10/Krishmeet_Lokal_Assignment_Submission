package com.example.lokal_assignment_app.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lokal_assignment_app.Fragments.bookmark_fragment
import com.example.lokal_assignment_app.Fragments.jobs_fragment
import com.example.lokal_assignment_app.R
import com.example.lokal_assignment_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replacefragment(jobs_fragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.jobs_bnv -> replacefragment(jobs_fragment())
                R.id.bookmarked_bnv -> replacefragment(bookmark_fragment())
                else ->{
                }
            }
            true
        }
    }

    private fun replacefragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }
}