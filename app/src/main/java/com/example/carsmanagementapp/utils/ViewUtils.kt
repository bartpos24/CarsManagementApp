package com.example.carsmanagementapp.utils

import android.content.Context
import android.content.Intent
import com.example.carsmanagementapp.MainActivity
import com.example.carsmanagementapp.ui.login.LoginActivity

fun Context.startMainActivity() =
    Intent(this, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
fun Context.startLoginActivity() =
    Intent(this, LoginActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }