package com.example.android.lifesim

import java.io.Serializable
import java.util.Random

class Person internal constructor(// Attributes
        val name: String, nh: Int, age: Int, var bankBalance: Double, var happiness: Int, /*SETTER FUNCTIONS*/
        var health: Int, private val isAWoman: Boolean) : Serializable {
    internal var sicknessNames = arrayOf("Influenza", "a Common Cold", "Pneumonia")
    /*GETTER FUNCTIONS*/
    var age: Int = 0
        private set
    var sickness: Sickness? = null
    var isWentToDoctorThisTurn: Boolean = false
    var isWentToWorkoutThisTurn: Boolean = false
    var iswentToPartyThisTurn: Boolean = false
    var sexualOrientation: String? = null
    var job: Job? = null
    val money: Int
    val neighborhood: Neighborhood
    var race: String? = null
        private set
    private val neighborhoodName: String?

    init {
        this.age = age
        this.neighborhood = Neighborhood(nh)
        this.money = neighborhood.money
        //   this.neighborhood = neighborhood;
        this.isWentToDoctorThisTurn = false
        this.isWentToWorkoutThisTurn = false
        this.iswentToPartyThisTurn = false
        this.neighborhoodName = neighborhood.neighborhood
        val rand = randomNumberInBetweenMaxMin(0, 100)
        if (neighborhood.neighborhood == "Lake View" || neighborhood.neighborhood == "Lincoln Park ") {
            if (rand > 85) {
                race = "w"
            } else if (rand > 95) {
                race = "br"
            } else {
                race = "b"
            }
        }
        if (neighborhood.neighborhood == "Little Village " || neighborhood.neighborhood == "Humboldt Park ") {
            if (rand > 65) {
                race = "br"
            } else if (rand > 85) {
                race = "b"
            } else {
                race = "w"
            }
        }
        if (neighborhood.neighborhood == "Austin") {
            if (rand > 85) {
                race = "b"
            } else if (rand > 98) {
                race = "br"
            } else {
                race = "w"
            }
        }


    }

    fun isWentToPartyThisTurn(): Boolean {
        return isWentToWorkoutThisTurn
    }

    fun setWentToPartyThisTurn(wentToPartyThisTurn: Boolean) {
        this.iswentToPartyThisTurn = wentToPartyThisTurn
    }

    // makes person's age increase by one
    fun upAge() {
        this.age += 1
        //change picture

    }

    // Returns a random number in between a min/max
    private fun randomNumberInBetweenMaxMin(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min

    }

    // Randomly returns a string in an array
    fun randArrayTitle(jobTitles: Array<String>): String {

        val size = jobTitles.size - 1 // size of array


        val randomNum = (Math.random() * (size + 1)).toInt()
        return jobTitles[randomNum]

    }


    // Possibly makes person sick, unless they already have a sickness or are under 12
    fun randomSickness() {
        if (this.age < 15) {
            // Can't get sick
        } else if (this.sickness == null) {
            // 10% chance to get sick each turn
            val randNum = randomNumberInBetweenMaxMin(1, 100) // generate sickness name
            if (randNum >= 40 && randNum <= 49) { // 10% chance
                val sickness = Sickness(randArrayTitle(sicknessNames))

                // Sets their new sickness as this sickness
                this.sickness = sickness
                this.sickness!!.yearsWith = 0
            }
        }// if the person is not sick
    }

}
