package com.example.carsmanagementapp.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.carsmanagementapp.Model.Car
import com.example.carsmanagementapp.Model.User
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.interfaces.ResponseAuthentication
import com.example.carsmanagementapp.interfaces.ResponseMainAction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import io.reactivex.Completable

class AuthenticationRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val refUser: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    fun login(email: String, password: String, callback: ResponseAuthentication){
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback.onMessage(R.string.login_success)
                callback.onSuccess(firebaseAuth.currentUser!!)
            }
            else
                callback.onMessage(R.string.e_login_fail)
        }
    }
    fun register(email: String, password: String, userToDatabase: User, callback: ResponseAuthentication) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = firebaseAuth.currentUser
                user?.sendEmailVerification()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            callback.onMessage(R.string.p_register_success)
                            addUser(firebaseAuth.currentUser!!.uid, userToDatabase)
                            logout()
                        }
                        else
                            callback.onMessage(R.string.e_sign_up_fail)
                    }
            }
            else
                callback.onMessage(R.string.e_sign_up_fail)

        }
    }

    fun logout() = firebaseAuth.signOut()

    fun signOut(callback: ResponseMainAction) {
        firebaseAuth.signOut()
        if (firebaseAuth.currentUser == null) {
            callback.onMessage(R.string.signOut)
        }
    }

    fun currentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    private fun addUser(uid: String, user: User) {
        refUser.child(uid).setValue(user)
    }

    fun getUser(callback: ResponseMainAction) {
        val ref = refUser.child(firebaseAuth.currentUser!!.uid)
        ref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                callback.onMessage(R.string.userLoadError)
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.getValue(User::class.java)
                    callback.onSuccess(user!!)
                }
                else
                    callback.onMessage(R.string.userLoadError)
            }
        })
    }
}