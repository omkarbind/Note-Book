package com.om.notebook.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel: ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    var isLoginSuccess = mutableStateOf(false)

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("LOGIN", "Success")
                isLoginSuccess.value = true
            }
            .addOnFailureListener { e ->
                Log.e("LOGIN", "Error: ${e.message}")
                isLoginSuccess.value = false
            }
    }
    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
    }

    fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
    }
}