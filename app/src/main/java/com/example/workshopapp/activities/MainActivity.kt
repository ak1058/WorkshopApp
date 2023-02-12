package com.example.workshopapp.activities

import android.os.Bundle
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.workshopapp.R
import com.example.workshopapp.dB.WorkshopDatabase
import com.example.workshopapp.databinding.ActivityMainBinding
import com.example.workshopapp.fragments.AppliedWorkshopFragment
import com.example.workshopapp.fragments.AvailableWorkshopFragment
import com.example.workshopapp.fragments.StudentDashBoard
import com.example.workshopapp.viewModels.WorkshopViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    lateinit var workshopViewModel: WorkshopViewModel
    private lateinit var workshopDatabase: WorkshopDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        workshopViewModel = WorkshopViewModel(this)
        workshopDatabase = WorkshopDatabase.getDataBase(this)
        replaceFragment(AppliedWorkshopFragment())


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.appliedMenu -> replaceFragment(AppliedWorkshopFragment())
                R.id.discoverMenu ->{replaceFragment(AvailableWorkshopFragment())}
                R.id.userMenu ->{replaceFragment(StudentDashBoard())}
            }
            true
        }


    }




    private fun replaceFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}