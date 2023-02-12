package com.example.workshopapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.workshopapp.R
import com.example.workshopapp.activities.MainActivity
import com.example.workshopapp.databinding.FragmentSignupBinding
import com.example.workshopapp.sharedPreference.SavedDataPreference
import com.google.firebase.auth.FirebaseAuth


class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignupBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var savedDataPreference: SavedDataPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentSignupBinding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()
        savedDataPreference = SavedDataPreference(requireActivity())

        binding.alreadyHaveAccountRegister.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
        }

        binding.createAccountBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            registerUser()
        }



        return binding.root
    }

    private fun registerUser() {
        val email = binding.emailText.editText?.text.toString()
        val userName = binding.userNameTxt.editText?.text.toString()
        val password = binding.passwordText.editText?.text.toString()

        savedDataPreference.saveUserName(userName)

        if (email.isNotEmpty()&&password.isNotEmpty()){
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    binding.progressBar.visibility = View.GONE
                }
            }.addOnFailureListener {
                showMessage(it.localizedMessage)
                binding.progressBar.visibility = View.GONE
            }
        }else{
            showMessage("Please fill all the details carefully.")
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }


}