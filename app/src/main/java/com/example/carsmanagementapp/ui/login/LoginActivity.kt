package com.example.carsmanagementapp.ui.login

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.carsmanagementapp.MainActivity
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.repositories.AuthenticationRepository
import com.example.carsmanagementapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var loginViewModelFactory: LoginViewModelFactory
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()

        val repository = AuthenticationRepository()
        loginViewModelFactory = LoginViewModelFactory(repository)
        loginViewModel = ViewModelProviders.of(this, loginViewModelFactory).get(LoginViewModel::class.java)

        loginBtn.setOnClickListener {
            doLogin()
            loginViewModel.userLiveData.observe(this, Observer {
                if (it != null) {
                    progressBar.visibility = View.GONE
                    updateUI(it)
                }
            })
        }

        loginViewModel.loginMessageLiveData.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    override fun onStart() {
        super.onStart()
        if(isOnline(this))
        {
            var user = loginViewModel.getCurrentUser()
            if (user != null) {
                updateUI(user)
            }
        }
        else
        {

            val builder = AlertDialog.Builder(this)
            builder.setMessage(resources.getString(R.string.net_err_title))
                .setNeutralButton(resources.getString(R.string.net_err_btn_ok),
                    DialogInterface.OnClickListener { dialog, id ->
                        finishAffinity()
                    })
                .setOnDismissListener { finishAffinity() }
            builder.create()
            builder.show()
        }
    }


    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun initUI() {
        emailEditText = findViewById(R.id.email_editText)
        passwordEditText = findViewById(R.id.password_editText)
        loginBtn = findViewById(R.id.loginButton)
        registerBtn = findViewById(R.id.registerButton)
        progressBar = findViewById(R.id.progressBarLogin)
        progressBar.visibility = View.GONE

    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun doLogin() {
        if (emailEditText.text.toString().isEmpty()) {
            emailEditText.error = resources.getString(R.string.e_enter_mail)
            emailEditText.requestFocus()
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailEditText.text.toString()).matches()) {
            emailEditText.error = resources.getString(R.string.e_enter_valid_mail)
            emailEditText.requestFocus()
            return
        }
        if (passwordEditText.text.toString().isEmpty()) {
            passwordEditText.error = resources.getString(R.string.e_enter_password)
            passwordEditText.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        loginViewModel.getLogin(emailEditText.text.toString(), passwordEditText.text.toString())

        progressBar.visibility = View.GONE

    }


}