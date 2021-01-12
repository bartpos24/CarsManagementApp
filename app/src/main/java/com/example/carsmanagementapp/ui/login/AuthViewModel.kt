package com.example.carsmanagementapp.ui.login

import android.content.Intent
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.data.repositories.UserRepositories
import com.example.carsmanagementapp.ui.register.RegisterActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel(private val repository: UserRepositories): ViewModel() {

    //email and password for the input
    var email: String? = null
    var password: String? = null
    var name: String? = null
    var surname: String? = null
    var repeatPassword: String? = null

    //auth listener
    var authListener: AuthListener? = null

    //disposable to dispose the Completable
    private val disposables = CompositeDisposable()

    val user by lazy {
        repository.currentUser()
    }

    fun login() {
        if (email.isNullOrEmpty()) {
            authListener?.onFailure(R.string.e_enter_mail.toString())
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authListener?.onFailure(R.string.e_enter_valid_mail.toString())
            return
        }
        if (password.isNullOrEmpty()) {
            authListener?.onFailure(R.string.e_enter_password.toString())
            return
        }

        authListener?.onStarted()

        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe( {
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun register() {
        if (name.isNullOrEmpty()) {
            authListener?.onFailure(R.string.e_enter_name.toString())
            return
        }
        if (surname.isNullOrEmpty()) {
            authListener?.onFailure(R.string.e_enter_surname.toString())
            return
        }
        if (email.isNullOrEmpty()) {
            authListener?.onFailure(R.string.e_enter_mail.toString())
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            authListener?.onFailure(R.string.e_enter_valid_mail.toString())
            return
        }
        if (password.isNullOrEmpty() || repeatPassword.isNullOrEmpty()) {
            authListener?.onFailure(R.string.e_enter_password.toString())
            return
        }
        if (password == repeatPassword) {
            authListener?.onFailure(R.string.e_repeatPassword.toString())
            return
        }
        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                authListener?.onSuccess()
            }, {
                authListener?.onFailure(it.message!!)
            })
        disposables.add(disposable)
    }

    fun goToRegister(view: View) {
        Intent(view.context, RegisterActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun goToLogin(view: View) {
        Intent(view.context, LoginActivity::class.java).also {
            view.context.startActivity(it)
        }
    }
    //disposing the disposables
    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}