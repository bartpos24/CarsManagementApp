package com.example.carsmanagementapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.carsmanagementapp.Model.User
import com.example.carsmanagementapp.R
import com.example.carsmanagementapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var registerButton: Button
    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var repeatPasswordEditText: EditText
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("Users")
        initUI()
        registerButton.setOnClickListener { registerUser() }
    }

    private fun initUI() {
        registerButton = findViewById(R.id.register_button)
        emailEditText = findViewById(R.id.email_register_editText)
        passwordEditText = findViewById(R.id.password_register_editText)
        nameEditText = findViewById(R.id.name_register_editText)
        surnameEditText = findViewById(R.id.surname_register_editText)
        repeatPasswordEditText = findViewById(R.id.passwordRepeat_register_editText)
        progressBar = findViewById(R.id.progressBar)
        progressBar.visibility = View.GONE
    }

    private fun registerUser() {
        if (nameEditText.text.toString().isEmpty()) {
            nameEditText.error = resources.getString(R.string.e_enter_name)
            nameEditText.requestFocus()
            return
        }
        if (surnameEditText.text.toString().isEmpty()) {
            surnameEditText.error = resources.getString(R.string.e_enter_surname)
            surnameEditText.requestFocus()
            return
        }
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
        if (repeatPasswordEditText.text.toString().isEmpty()) {
            repeatPasswordEditText.error = resources.getString(R.string.e_enter_password)
            repeatPasswordEditText.requestFocus()
            return
        }
        if (repeatPasswordEditText.text.toString() != passwordEditText.text.toString()) {
            repeatPasswordEditText.error = resources.getString(R.string.e_repeatPassword)
            repeatPasswordEditText.requestFocus()
            return
        }

        if (passwordEditText.text.length <= 5) {
            passwordEditText.error = resources.getString(R.string.e_password_requirements)
            passwordEditText.requestFocus()
            return
        }

        progressBar.visibility = View.VISIBLE
        auth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                try {

                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val userToDatabase = User(nameEditText.text.toString(), surnameEditText.text.toString(), emailEditText.text.toString())

                        user?.sendEmailVerification()
                            ?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    progressBar.visibility = View.GONE
                                    Toast.makeText(baseContext, resources.getString(R.string.p_register_success), Toast.LENGTH_LONG).show()
                                    val uid = auth.currentUser!!.uid
                                    ref.child(uid).setValue(userToDatabase)
                                    auth.signOut()
                                    startActivity(Intent(this, LoginActivity::class.java))
                                    finish()
                                }
                            }

                    }
                    else {
                        progressBar.visibility = View.GONE
                        throw task.exception as Throwable
                    }
                }
                catch (existsEmail: FirebaseAuthUserCollisionException) {
                    Toast.makeText(baseContext, resources.getString(R.string.e_email_exists), Toast.LENGTH_LONG).show()
                }
                catch (e: Throwable) {
                    Toast.makeText(baseContext, e.message.toString(), Toast.LENGTH_LONG).show()
                }
            }
    }
}