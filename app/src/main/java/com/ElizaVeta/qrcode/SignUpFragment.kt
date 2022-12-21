package com.ElizaVeta.qrcode

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ElizaVeta.qrcode.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [SignUpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SignUpFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding
            ?: throw RuntimeException("FragmentSignUpBinding is not initialized or null ")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firebaseConnect()
        SignUp()

    }

    private fun firebaseConnect() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun SignUp() {
        binding.textView.setOnClickListener {
            loadFragment()
        }
        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            loadFragment()
                        } else {
                            Toast.makeText(
                                binding.root.context,
                                it.exception.toString(),
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                } else {
                    Toast.makeText(
                        binding.root.context,
                        "Password is not matching",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    binding.root.context,
                    "Empty Fields Are not Allowed in Sign Up !!",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    private fun loadFragment() {
        val transaction = (context as AppCompatActivity).supportFragmentManager.beginTransaction()
        transaction.remove(this)
//        transaction.replace(R.id.container, )
//        transaction.addToBackStack(null)
//        transaction.commit()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SignUpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            SignUpFragment()
    }
}