package com.example.carsmanagementapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import io.reactivex.Completable

class AuthenticationRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val refUser: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")
    private val actualUser: MutableLiveData<FirebaseUser> = MutableLiveData<FirebaseUser>()

    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("login", "succesfull")
                actualUser.value = currentUser()
            }
            else
                Log.e("login", "error")

        }

    }
    fun register(email: String, password: String, userToDatabase: User) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.e("register", "succesfull")
                val user = firebaseAuth.currentUser
                user?.sendEmailVerification()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.e("sendEmail", "succesfull")
                            addUser(firebaseAuth.currentUser!!.uid, userToDatabase)
                            logout()
                        }

                    }
            }

        }
    }

    fun getLogin(email: String, password: String): LiveData<FirebaseUser?> {
        login(email, password)
        return actualUser
    }
    fun logout() = firebaseAuth.signOut()

    fun currentUser() = firebaseAuth.currentUser

    private fun addUser(uid: String, user: User) {
        refUser.child(uid).setValue(user)
    }
}