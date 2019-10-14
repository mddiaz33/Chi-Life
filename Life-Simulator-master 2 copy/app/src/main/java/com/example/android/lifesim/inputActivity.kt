package com.example.android.lifesim

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton

class inputActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
    }

    fun beginMainGame(view: View) {
        val firstNameMale = arrayOf("Mike", "Rob", "Carson", "Brandon", "Norman", "John", "Roberto", "Saul", "Antonio", "Sean", "Marcus")
        val firstNameFemale = arrayOf("Reyna", "Carolina", "Carley", "Rebecca", "Gretchen", "Mercedes", "Tiara", "Macie", "Tamara", "Makenna", "Madeline", "Tiffany", "Sasha", "Kathy", "Frances", "Rihanna", "Ally", "Deanna", "Payton", "Kenzie", "Carla", "Melody", "Jacey", "Carmen", "Eileen", "Elaina", "Avery", "Katelynn", "Kendra", "Kyra", "Hannah", "Cadence", "Amanda", "Karlee", "Paulina", "Alexis", "Haley", "Jaylin", "Carson", "Iyana", "Halle", "Elsa", "Liana", "Elizabeth", "Danielle", "Crystal", "April", "Taylor", "Delaney", "Kaylyn", "Jaylyn", "Kate", "Leslie", "Amy", "Jocelyn", "Kara", "Olivia", "Piper", "Kaylynn", "Patricia", "Cecilia", "Linda", "Hazel", "Jasmine", "Emily", "Anna", "Mila", "Mylie", "Kylie", "Meghan", "Tessa", "Rory", "Jaiden", "Bella", "Alayna", "Paula", "Tanya", "Larissa", "Veronica", "Lucy", "Courtney", "Marley", "Lillian", "Cynthia", "Nora", "Kelly", "Murphy", "Sydney", "Mia", "Melissa", "Addy", "Kay", "Rylie", "Jessica")
        val lastNameArray = arrayOf("Carroll", "Bradley", "Rice", "Vargas", "Huber", "Allison", "Mullins", "Burton", "Frye", "White", "Bradshaw", "Salas", "Melendez", "Mcintosh", "Petersen", "Shaffer", "Richmond", "Ferrell", "Alvarado", "Cameron", "Cook", "Horn", "Mcbride", "Woods", "Marks", "Dickson", "Aguirre", "Castaneda", "Dyer", "Hughes", "Odom", "Huerta", "Farrell", "Benson", "Mcpherson", "Johns", "Weber", "Glenn", "Tran", "King", "Swanson", "Ryan", "Durham", "Daniels", "Ashley", "Braun", "Fletcher", "Valenzuela", "Singh", "Hogan", "Rosales", "Fuentes", "Pollard", "Wyatt", "Davidson", "Ray", "Pace", "Hobbs", "Rasmussen", "Mclaughlin", "Mcdowell", "Vincent", "Vaughn", "Potts", "Cooper", "Zimmerman", "Morales", "Ware", "Nunez", "Decker", "Hess", "Cunningham", "Everett", "Mccall", "Moyer", "Whitehead", "Gentry", "Chung", "Li", "Hicks", "Flynn", "Pena", "Meyer", "Solomon", "Branch", "Conley", "Mann", "Howe", "Roberson", "Mooney", "Caldwell", "Figueroa", "Kaiser", "Koch", "Zuniga", "Mckinney", "Lester", "Lin", "Lloyd", "Stout")


        val intent = Intent(this, MainGame::class.java)

        val firstName = findViewById<View>(R.id.firstName) as EditText
        val lastName = findViewById<View>(R.id.lastName) as EditText
        val btnMale = findViewById<View>(R.id.btnMale) as ToggleButton
        val btnFemale = findViewById<View>(R.id.btnFemale) as ToggleButton
        val errorView = findViewById<View>(R.id.errorTextBox) as TextView

        val randomCheckbox = findViewById<View>(R.id.randomCheckbox) as CheckBox
        if (randomCheckbox.isChecked) {
            val genderText = maleOrFemale()
            var firstNameText = ""
            val lastNameText = randArrayTitle(lastNameArray)

            if (genderText === "Male") {
                firstNameText = randArrayTitle(firstNameMale)
            } else {
                firstNameText = randArrayTitle(firstNameFemale)
            }


            intent.putExtra("firstName", firstNameText)
            intent.putExtra("lastName", lastNameText)
            intent.putExtra("gender", genderText)

            startActivity(intent)
            return
        }

        val firstNameText = firstName.text.toString().substring(0, 1).toUpperCase() + firstName.text.toString().substring(1)
        val lastNameText = lastName.text.toString().substring(0, 1).toUpperCase() + lastName.text.toString().substring(1)
        var genderText = ""


        /*     Error checking here     */
        if (firstName.text.toString().isEmpty() || lastName.text.toString().isEmpty()) { // if either name is blank
            errorView.text = "No characters in name"
            return
        }
        if (checkWhiteSpace(firstNameText) || checkWhiteSpace(lastNameText)) { // if there is whitespace
            errorView.text = "No Spaces in Name"
            return
        }
        if (isNameOverTenChars(firstNameText) || isNameOverTenChars(lastNameText)) { // if names are over 10 characters
            errorView.text = "Keep name under 10 characters"
            return
        }
        if (btnFemale.isChecked && btnMale.isChecked || !btnFemale.isChecked && !btnMale.isChecked) {
            errorView.text = "Choose one gender"
            return
        }

        if (btnMale.isChecked) {
            genderText = "Male"
        } else if (btnFemale.isChecked) {
            genderText = "Female"
        }

        intent.putExtra("firstName", firstNameText)
        intent.putExtra("lastName", lastNameText)
        intent.putExtra("gender", genderText)


        startActivity(intent)
    }

    fun maleOrFemale(): String {
        val size = 1
        val randomNum = (Math.random() * (size + 1)).toInt()

        if (randomNum == 1) {
            return "Male"
        } else if (randomNum == 0) {
            return "Female"
        }

        return ""
    }

    // Randomly returns a string in an array
    fun randArrayTitle(jobTitles: Array<String>): String {

        val size = jobTitles.size - 1 // size of array


        val randomNum = (Math.random() * (size + 1)).toInt()
        return jobTitles[randomNum]

    }

    companion object {

        // returns bool if string has any whitespace or not
        fun checkWhiteSpace(testString: String?): Boolean {
            if (testString != null) {
                for (i in 0 until testString.length) {
                    if (Character.isWhitespace(testString[i])) {
                        return true
                    }
                }
            }
            return false
        }

        fun isNameOverTenChars(testString: String?): Boolean {
            var len = 0
            if (testString != null) {
                for (i in 0 until testString.length) {
                    len++
                    if (i >= 10) {
                        return true
                    }
                }
            }
            return false

        }
    }


}
