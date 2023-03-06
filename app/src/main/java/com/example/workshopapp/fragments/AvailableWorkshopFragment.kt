package com.example.workshopapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshopapp.R
import com.example.workshopapp.activities.LoginRegisterActivity
import com.example.workshopapp.activities.MainActivity
import com.example.workshopapp.adapter.WorkshopAdapter
import com.example.workshopapp.dB.WorkshopDatabase
import com.example.workshopapp.databinding.FragmentAvailableWorkshopBinding
import com.example.workshopapp.databinding.FragmentLoginBinding
import com.example.workshopapp.listener.Listener
import com.example.workshopapp.models.WorkshopListItem
import com.example.workshopapp.sharedPreference.SavedDataPreference
import com.example.workshopapp.viewModels.WorkshopViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AvailableWorkshopFragment : Fragment(), Listener {

    private lateinit var binding: FragmentAvailableWorkshopBinding
    private lateinit var workshopAdapter: WorkshopAdapter
    private lateinit var workshopViewModel: WorkshopViewModel
    private lateinit var savedDataPreference: SavedDataPreference
    private lateinit var workshopDatabase: WorkshopDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAvailableWorkshopBinding.inflate(inflater, container, false)
        workshopDatabase = WorkshopDatabase.getDataBase(requireActivity())
        savedDataPreference = SavedDataPreference(requireActivity())
        workshopAdapter = WorkshopAdapter(this, workshopDatabase, savedDataPreference, requireActivity())
        workshopViewModel = (activity as MainActivity).workshopViewModel
        savedDataPreference = SavedDataPreference(requireContext())
        auth = FirebaseAuth.getInstance()

        workshopAdapter.submitList(workshopViewModel.getList())



//        CoroutineScope(Dispatchers.IO).launch {
//            val workshops = workshopDatabase.workshopDao().getWorkshops()
//            for (workshop in workshops) {
//                val count =  workshopDatabase.workshopDao().searchItem(workshop.name)
//                workshop.isApplied = count > 0
//            }

//        }

        binding.workshopRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager =linearLayoutManager
            adapter = workshopAdapter

            setHasFixedSize(true)
        }

        binding.userNameTxt.text = "Welcome! " + savedDataPreference.getUserName()





        return binding.root
    }

    override fun onItemClickListener(position: Int, workshopListItem: WorkshopListItem) {

        if (auth.currentUser!=null){
            savedDataPreference.saveWorkshopData(workshopListItem)

            val fragmentManager = activity?.supportFragmentManager
            val fragmentTransaction = fragmentManager?.beginTransaction()
            val newFragment = WorkshopDetails()
            fragmentTransaction?.replace(R.id.frame_layout, newFragment)
            fragmentTransaction?.addToBackStack(null)
            fragmentTransaction?.commit()
        }else{
            Toast.makeText(requireActivity(),"You have to login your account", Toast.LENGTH_SHORT).show()
            val intent = Intent(activity, LoginRegisterActivity::class.java)
            startActivity(intent)
            requireActivity()
        }

    }


}