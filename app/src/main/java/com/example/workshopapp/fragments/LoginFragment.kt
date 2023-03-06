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
import com.example.workshopapp.databinding.FragmentLoginBinding
import com.example.workshopapp.sharedPreference.SavedDataPreference
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var savedDataPreference: SavedDataPreference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentLoginBinding.inflate(inflater, container, false)

        auth = FirebaseAuth.getInstance()
        savedDataPreference = SavedDataPreference(requireActivity())

        binding.dontHaveAccountRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }

        binding.loginBtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            loginUser()
        }



        return binding.root
    }

    private fun loginUser() {
        val email = binding.emailText.editText?.text.toString()
        val password = binding.passwordText.editText?.text.toString()

        //checking email and password
        if (email.isNotEmpty()&&password.isNotEmpty()){
            //signing using firebase
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful){
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    binding.progressBar.visibility = View.GONE
                    savedDataPreference.saveUserId(auth.currentUser!!.uid)
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