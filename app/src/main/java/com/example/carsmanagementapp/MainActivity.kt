package com.example.carsmanagementapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.carsmanagementapp.Model.User
import com.example.carsmanagementapp.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var nameAndSurname: TextView
    private lateinit var emailView: TextView
    private lateinit var logoutButton: ImageButton


    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val hView: View = navView.getHeaderView(0)

        nameAndSurname = hView.findViewById(R.id.nameAndSurname)
        emailView = hView.findViewById(R.id.emailView)
        logoutButton = hView.findViewById(R.id.logoutButton)


        auth = FirebaseAuth.getInstance()
        var ref = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser!!.uid)



        val loginListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                nameAndSurname.text = user?.name.toString() +" "+ user?.surname.toString()
                emailView.text = user?.email.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Dont' have data", Toast.LENGTH_LONG).show()
            }
        }
        ref.addListenerForSingleValueEvent(loginListener)

        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(applicationContext, LoginActivity::class.java))
            finish()
        }
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_prognosis,
                R.id.nav_statistics,
                R.id.nav_cars,
                R.id.nav_addCar
        ), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}