package com.example.workshopapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.workshopapp.activities.LoginRegisterActivity
import com.example.workshopapp.activities.MainActivity
import com.example.workshopapp.databinding.FragmentLoginBinding
import com.example.workshopapp.databinding.FragmentStudentDashboardBinding
import com.example.workshopapp.sharedPreference.SavedDataPreference
import com.google.firebase.auth.FirebaseAuth


class StudentDashBoard : Fragment() {

    private lateinit var binding: FragmentStudentDashboardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var savedDataPreference: SavedDataPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentStudentDashboardBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        savedDataPreference = SavedDataPreference(requireActivity())

        val currentUser = auth.currentUser

        if(currentUser != null){
            binding.emailText.text = auth.currentUser?.email
            binding.userNameTxt.text = savedDataPreference.getUserName()
            binding.signOutBtn.setOnClickListener {
                auth.signOut()
                val intent = Intent(activity, LoginRegisterActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }else{
            binding.userNameTxt.text = "Please login or Signup"
            binding.emailText.visibility = View.INVISIBLE
            binding.textView6.visibility = View.INVISIBLE
            binding.signOutBtn.text = "Login/SignUp"
            binding.signOutBtn.setOnClickListener {
                val intent = Intent(activity, LoginRegisterActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }



        return binding.root
    }


}