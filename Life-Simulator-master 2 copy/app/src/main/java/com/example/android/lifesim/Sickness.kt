package com.example.android.lifesim

import java.io.Serializable

class Sickness internal constructor(// Getter functions
        val title: String) : Serializable {
    var description: String? = null
        private set
    var damagePerTurn: Int = 0
        private set
    var happyDamagePerTurn: Int = 0
        private set
    // Setter functions
    var yearsWith: Int = 0
    var costToTreat: Double = 0.toDouble()
        private set
    var type = "physical"
        private set


    init {

        when (title) {
            "Influenza" -> {
                this.description = "the normal flu, doc says you should be fine if you pay him a visit.\n"
                this.damagePerTurn = 6
                this.happyDamagePerTurn = 12
                this.costToTreat = 40.0
                this.yearsWith = 0
            }
            "a Common Cold" -> {
                this.description = "an easy fix by going to see a doctor\n"
                this.damagePerTurn = 3
                this.happyDamagePerTurn = 6
                this.costToTreat = 10.0
                this.yearsWith = 0
            }
            "Pneumonia" -> {
                this.description = "brought on by cold weather, you should see a doctor for this.\n"
                this.damagePerTurn = 10
                this.happyDamagePerTurn = 9
                this.costToTreat = 235.0
                this.yearsWith = 0
            }
            "a Gambling Addiction" -> {
                this.description = "Excessive or uncontrollable gambling to an unhealthy extent. You should see a therapist about this.\n"
                this.damagePerTurn = 0
                this.happyDamagePerTurn = 15
                this.costToTreat = 100.0
                this.yearsWith = 0
                this.type = "mental"
            }
            "a Sprained Ankle" -> {
                this.description = "an injury that occurs when the ankle rolls, twists, or turns in an awkward way.\n"
                this.damagePerTurn = 2
                this.happyDamagePerTurn = 10
                this.costToTreat = 49.0
                this.yearsWith = 0
            }
            "a Broken Arm" -> {
                this.description = "a complete or partial break in an arm bone.\n"
                this.damagePerTurn = 6
                this.happyDamagePerTurn = 12
                this.costToTreat = 210.0
                this.yearsWith = 0
            }
            "a Torn ACL" -> {
                this.description = "a torn anterior cruciate ligament in the knee. You need surgery!\n"
                this.damagePerTurn = 20
                this.happyDamagePerTurn = 15
                this.costToTreat = 6852.0
                this.yearsWith = 0
            }
        }
    }

    // Adds a year to the sickness
    fun addYearToSickness() {
        this.yearsWith += 1
    }


}
