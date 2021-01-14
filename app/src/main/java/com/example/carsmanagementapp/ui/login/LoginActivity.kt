package com.example.carsmanagementapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.carsmanagementapp.MainActivity
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.register.RegisterActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerBtn: Button
    private lateinit var progressBar: ProgressBar

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initUI()


        auth = FirebaseAuth.getInstance()


        loginBtn.setOnClickListener { doLogin() }
        registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        updateUI(currentUser)
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
        auth.signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    progressBar.visibility = View.GONE
                    val user = auth.currentUser
                    Toast.makeText(this, R.string.login_success, Toast.LENGTH_LONG).show()
                    updateUI(user)
                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(baseContext, resources.getString(R.string.e_login_fail), Toast.LENGTH_LONG).show()
                    updateUI(null)
                }
            }
    }


}