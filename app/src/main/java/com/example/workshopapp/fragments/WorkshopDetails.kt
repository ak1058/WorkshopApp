package com.example.workshopapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.workshopapp.R
import com.example.workshopapp.dB.WorkshopDatabase
import com.example.workshopapp.databinding.FragmentWorkshopDetailsBinding
import com.example.workshopapp.models.WorkshopListItem
import com.example.workshopapp.sharedPreference.SavedDataPreference
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WorkshopDetails : Fragment() {

    private lateinit var binding: FragmentWorkshopDetailsBinding
    private lateinit var savedDataPreference: SavedDataPreference
    private lateinit var workshopDatabase: WorkshopDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWorkshopDetailsBinding.inflate(inflater, container, false)
        savedDataPreference = SavedDataPreference(requireActivity())
        workshopDatabase = WorkshopDatabase.getDataBase(requireActivity())

        val shouldHideButton = arguments?.getBoolean("shouldHideButton") ?: false

        // getting the json data from the sharedpreference of the workshop item which is clicked
        val jsonWorkshopData = savedDataPreference.getWorkshopData()
        //parsing it and getting the workshopDAta which is clicked
        val workshopData = Gson().fromJson(jsonWorkshopData, WorkshopListItem::class.java)
        setWorkshopData(workshopData)

        if (shouldHideButton){
            binding.applyBtn.visibility = View.GONE
        }





        binding.applyBtn.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                workshopDatabase.workshopDao().insertItemIfNotExists(workshopData)
                workshopData.isApplied = true
            }
            showToast("Successfully Applied")
            val fragmentManager = requireActivity().supportFragmentManager
            fragmentManager.popBackStack()

        }
        return binding.root
    }

    private fun setWorkshopData(workshopListItem: WorkshopListItem){
        binding.nameTxt.text = workshopListItem.name
        binding.dateText.text = workshopListItem.date
        binding.time.text = workshopListItem.time
        binding.descTxt.text = workshopListItem.details
        binding.instructorName.text = "Instructor: " + workshopListItem.instructor
    }

    private fun showToast(message: String){
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}