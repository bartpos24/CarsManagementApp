package com.example.carsmanagementapp.Model

data class User(var name: String, var surname: String, var email: String) {
    /*var name = name
    var surname = surname
    var email = email*/

    constructor() : this ("", "", "")
}