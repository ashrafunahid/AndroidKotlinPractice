package com.ashrafunahid.tourmate.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ashrafunahid.tourmate.models.ExpenseModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel: ViewModel() {
    enum class AuthenticationStatus {
        AUTHENTICATED,
        UNAUTHENTICATED,
    }
    private val auth = FirebaseAuth.getInstance()
    var user = auth.currentUser
    var errMsgLiveData: MutableLiveData<String> = MutableLiveData()
    val authStatusLiveData: MutableLiveData<AuthenticationStatus> = MutableLiveData()

    init {
        user?.let {
            authStatusLiveData.postValue(AuthenticationStatus.AUTHENTICATED)
        } ?: authStatusLiveData.postValue(AuthenticationStatus.UNAUTHENTICATED)
    }

    fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    authStatusLiveData.postValue(AuthenticationStatus.AUTHENTICATED)
                }
            }
            .addOnFailureListener { error ->
                errMsgLiveData.value = error.localizedMessage
            }
    }

    fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authResult ->
                if (authResult.isSuccessful) {
                    authStatusLiveData.postValue(AuthenticationStatus.AUTHENTICATED)
                }
            }
            .addOnFailureListener { error ->
                errMsgLiveData.value = error.localizedMessage
            }
    }
}