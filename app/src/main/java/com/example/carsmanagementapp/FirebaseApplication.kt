package com.example.carsmanagementapp

import android.app.Application
import com.example.carsmanagementapp.data.firebase.FirebaseSource
import com.example.carsmanagementapp.data.repositories.UserRepositories
import com.example.carsmanagementapp.ui.login.AuthViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FirebaseApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@FirebaseApplication))

        bind() from singleton { FirebaseSource() }
        bind() from singleton { UserRepositories(instance()) }
        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { MainViewModelFactory(instance()) }

    }
}