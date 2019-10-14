package com.example.android.lifesim

class Job(var jobName: String?) {
    // Getters
    // Setters
    var jobSalary: Double = 0.toDouble()

    init {

        when (jobName) {
            "" -> jobSalary = 0.0
        }
    }
}
