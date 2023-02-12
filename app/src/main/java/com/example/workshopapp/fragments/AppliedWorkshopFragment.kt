package com.example.workshopapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.workshopapp.R
import com.example.workshopapp.adapter.AppliedWorkshopAdapter
import com.example.workshopapp.adapter.WorkshopAdapter
import com.example.workshopapp.dB.WorkshopDatabase
import com.example.workshopapp.databinding.FragmentAppliedWorkshopBinding
import com.example.workshopapp.databinding.FragmentLoginBinding
import com.example.workshopapp.listener.Listener
import com.example.workshopapp.models.WorkshopListItem
import com.example.workshopapp.sharedPreference.SavedDataPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AppliedWorkshopFragment : Fragment(), Listener {

    private lateinit var binding: FragmentAppliedWorkshopBinding
    private lateinit var savedDataPreference: SavedDataPreference
    private lateinit var workshopAdapter: AppliedWorkshopAdapter
    private lateinit var workshopDatabase: WorkshopDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentAppliedWorkshopBinding.inflate(inflater, container, false)
        workshopAdapter = AppliedWorkshopAdapter(this)
        savedDataPreference = SavedDataPreference(requireActivity())
        workshopDatabase = WorkshopDatabase.getDataBase(requireActivity())

        CoroutineScope(Dispatchers.IO).launch {
            val appliedWorkshopList = workshopDatabase.workshopDao().getWorkshops()
            workshopAdapter.submitList(appliedWorkshopList)
        }

        CoroutineScope(Dispatchers.IO).launch {
            val count = workshopDatabase.workshopDao().getWorkshopCount()
            withContext(Dispatchers.Main) {
                if (count == 0) {
                    binding.arrow.visibility = View.VISIBLE
                    binding.noWorkshopText.visibility = View.VISIBLE
                } else {
                    binding.arrow.visibility = View.GONE
                    binding.arrow.visibility = View.GONE
                }
            }
        }


        binding.workshopRecyclerView.apply {
            val linearLayoutManager = LinearLayoutManager(activity?.applicationContext)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager =linearLayoutManager
            adapter = workshopAdapter

            setHasFixedSize(true)
        }




        return binding.root
    }

    override fun onItemClickListener(position: Int, workshopListItem: WorkshopListItem) {
        savedDataPreference.saveWorkshopData(workshopListItem)
        val bundle = Bundle()
        bundle.putBoolean("shouldHideButton", true)

        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        val newFragment = WorkshopDetails()
        newFragment.arguments = bundle
        fragmentTransaction?.replace(R.id.frame_layout, newFragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }




}