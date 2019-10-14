package com.example.android.lifesim

import android.app.ActionBar
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.provider.ContactsContract
import android.support.annotation.RestrictTo
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

import java.text.NumberFormat
import java.util.Random

class MainGame : AppCompatActivity() {

    internal var doctorNames = arrayOf("Mike Rable", "Murphy Morgan", "John Seplin", "Morgan Johnson", "Hank Freeman", "Wilson Bennett", "Amy Peterson", "Ty Milone")
    internal var physicalInjuries = arrayOf("a Sprained Ankle", "a Broken Arm", "a Torn ACL")
    internal var popuptemplates11to17 = arrayOf("Marijuana", "Bullying", "Amusement Park")
    internal var popuptemplates5to17 = arrayOf("Grandma", "Bicycle", "Study", "Corn")
    internal var popuptemplates18forLife = arrayOf("Homeless", "Cousin", "Horse Race", "Cops")
    internal var gen: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_game)

        // Hides Action Bar on this page
        val actionBar = actionBar
        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        /* loads in data from inputActivity screen */
        val intent = intent
        val genderText = intent.getStringExtra("gender")
        val firstNameText = intent.getStringExtra("firstName")
        val lastNameText = intent.getStringExtra("lastName")
        this.gen = genderText == "Male"

        val nameView = findViewById<View>(R.id.nameView) as TextView
        val nameViewLast = findViewById<View>(R.id.nameViewLast) as TextView
        nameView.text = firstNameText
        nameViewLast.text = lastNameText

        // Initializing main character
        val mainPerson = Person("$firstNameText $lastNameText", randomNumberInBetweenMaxMin(1, 5), 0, randomNumberInBetweenMaxMin(70, 100).toDouble(), randomNumberInBetweenMaxMin(80, 100), randomNumberInBetweenMaxMin(80, 100), gen)
        printFirstTextView(mainPerson)

        val textView = findViewById<View>(R.id.neighborhood) as TextView
        textView.text = mainPerson.neighborhood.neighborhood
        val textView2 = findViewById<View>(R.id.Description) as TextView
        textView2.text = mainPerson.neighborhood.description
        // BankAccount Button onClick Function
        val bankButton = findViewById<View>(R.id.bankView) as Button
        bankButton.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@MainGame).create()
            alertDialog.setTitle("Financial Information")
            alertDialog.setMessage("Bank Account Balance: " + formatToCurrency(mainPerson.bankBalance))
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()
        }

        // Up Age button onClick function
        val upAgeButton = findViewById<View>(R.id.progressAge) as Button
        upAgeButton.setOnClickListener {
            mainPerson.upAge()
            nextAgeTextView(mainPerson)
            nextAgeImgView(mainPerson)
        }

        // Activity Popup Back Button function
        val backButton = findViewById<View>(R.id.activityPopupBackButton) as ImageButton
        backButton.setOnClickListener { activityBackButtonFunction() }


        // Activity Button onClick function
        val activitiesButton = findViewById<View>(R.id.buttonActivities) as Button
        activitiesButton.setOnClickListener {
            val activityPopup = findViewById<View>(R.id.activityPopup) as LinearLayout
            val topBarLayout = findViewById<View>(R.id.topbarlayout) as LinearLayout
            val activityBarLinearLayout = findViewById<View>(R.id.activityBarLinearLayout) as LinearLayout

            if (activityPopup.visibility == View.GONE) {
                activityPopup.visibility = View.VISIBLE
                topBarLayout.visibility = View.GONE
                activityBarLinearLayout.visibility = View.GONE

            }
        }

        // Go to doctor office onclick button
        val doctorOfficeButton = findViewById<View>(R.id.doctorOfficeButton) as LinearLayout
        doctorOfficeButton.setOnClickListener {
            // if person is not sick
            if (mainPerson.sickness == null) {
                val drerrortoast = Toast.makeText(applicationContext,
                        "You must be sick to go to the doctor.",
                        Toast.LENGTH_SHORT)

                drerrortoast.show()
            } else {
                hideActivityBarBringBackTopBar()
                chooseDoctor(mainPerson)

            }// if they are sick
        }
        //party onClick button
        val partyButton = findViewById<View>(R.id.partyButton) as LinearLayout
        partyButton.setOnClickListener {
            if (mainPerson.isWentToPartyThisTurn()) {
                val workedOut = Toast.makeText(applicationContext,
                        "You already went out. Wait until next year",
                        Toast.LENGTH_LONG)
                workedOut.show()

            }

            // must be 12 or older to workout
            val age = mainPerson.age
            if (mainPerson.age >= 21 && mainPerson.age < 50) {
                partyFunc(mainPerson)
            } else if (age <= 20) {
                val underage = Toast.makeText(applicationContext,
                        "You must be at least 21 to party",
                        Toast.LENGTH_LONG)
                underage.show()
            } else {
                val underage = Toast.makeText(applicationContext,
                        "You're too old to party",
                        Toast.LENGTH_LONG)
                underage.show()
            }
        }

        // Buy Lottery Ticket onClick button
        val lotteryTicketButton = findViewById<View>(R.id.lotteryTicketButton) as LinearLayout
        lotteryTicketButton.setOnClickListener { lotteryOddsFunc(mainPerson) }

        // if they choose Doctor 1
        val drButton1 = findViewById<View>(R.id.drbutton1) as Button
        drButton1.setOnClickListener {
            if (!mainPerson.isWentToDoctorThisTurn) {
                curePerson(mainPerson, 1)
            } else {
                val failToast = Toast.makeText(applicationContext,
                        "You already went to the doctor. Wait until next year",
                        Toast.LENGTH_LONG)
                failToast.show()
            }
        }

        // if they choose Doctor 2
        val drButton2 = findViewById<View>(R.id.drbutton2) as Button
        drButton2.setOnClickListener {
            if (!mainPerson.isWentToDoctorThisTurn) {
                curePerson(mainPerson, 2)
            } else {
                val failToast = Toast.makeText(applicationContext,
                        "You already went to the doctor. Wait until next year",
                        Toast.LENGTH_LONG)
                failToast.show()
            }
        }

        // if they choose Doctor 3
        val drButton3 = findViewById<View>(R.id.drbutton3) as Button
        drButton3.setOnClickListener {
            if (!mainPerson.isWentToDoctorThisTurn) {
                curePerson(mainPerson, 3)
            } else {
                val failToast = Toast.makeText(applicationContext,
                        "You already went to the doctor. Wait until next year",
                        Toast.LENGTH_LONG)
                failToast.show()
            }
        }

        // Doctor Popup Back Button function
        val drBackButton = findViewById<View>(R.id.drbackbutton) as ImageButton
        drBackButton.setOnClickListener {
            val doctorLayout = findViewById<View>(R.id.doctorPopup) as RelativeLayout
            doctorLayout.visibility = View.GONE
        }

        // Workout button onclick function
        val workoutButton = findViewById<View>(R.id.workoutButton) as LinearLayout
        workoutButton.setOnClickListener {
            if (mainPerson.isWentToWorkoutThisTurn) {
                val workedOut = Toast.makeText(applicationContext,
                        "You already worked out. Wait until next year",
                        Toast.LENGTH_LONG)
                workedOut.show()

            }

            // must be 12 or older to workout
            if (mainPerson.age > 12) {
                workoutFunc(mainPerson)
            } else {
                val underage = Toast.makeText(applicationContext,
                        "You must be at least 13 to workout",
                        Toast.LENGTH_LONG)
                underage.show()
            }
        }

        // Therapist button onClick function
        val therapistButton = findViewById<View>(R.id.therapistButton) as LinearLayout
        therapistButton.setOnClickListener { visitTherapist(mainPerson) }


    }

    internal fun nextAgeImgView(mainPerson: Person) {
        val age = mainPerson.age
        val img = findViewById<View>(R.id.lifecycleImage) as ImageView
        if (gen) {
            if (age < 3) {
                img.setImageResource(R.drawable.ic_person1mbr)
            } else if (age < 40) {
                img.setImageResource(R.drawable.ic_person2mbr)
            } else {
                img.setImageResource(R.drawable.ic_person3mbr)
            }
        } else {
            if (age < 3) {
                img.setImageResource(R.drawable.ic_person1fbr)
            } else if (age < 40) {
                img.setImageResource(R.drawable.ic_person2fbr)
            } else {
                img.setImageResource(R.drawable.ic_person3fbr)
            }
        }
    }

    internal fun nextAgeTextView(mainPerson: Person) {

        // if their health hits 0 they die
        if (mainPerson.health <= 0) {
            killPerson(mainPerson)
        }

        mainPerson.isWentToDoctorThisTurn = false
        mainPerson.isWentToWorkoutThisTurn = false
        mainPerson.iswentToPartyThisTurn = false

        // update person's bank account if they have a job
        if (mainPerson.job != null) {
            mainPerson.bankBalance = mainPerson.bankBalance + mainPerson.job!!.jobSalary
        }


        // DYAMICALLY ADDS TEXVIEW TO SCROLLVIEW
        //create a TextView with Layout parameters according to your needs
        val lparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        //if your parent Layout is relativeLayout, just change the word LinearLayout with RelativeLayout
        val topView = TextView(this)
        topView.layoutParams = lparams
        topView.textSize = 15f
        topView.setTextColor(Color.parseColor("#3F51B5"))
        topView.text = getString(R.string.InitialTextViewAge, mainPerson.age)

        val tv = TextView(this)
        tv.layoutParams = lparams
        tv.textSize = 15f
        tv.setTextColor(Color.BLACK)
        tv.setPadding(0, 0, 0, 40)

        /*Sets Drawable for border on bottom of textview in scrollview*/
        val sdk = android.os.Build.VERSION.SDK_INT   // gets int version of os build
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
        // if os is less than Jelly Bean then make it drawable
        {
            tv.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.maingametextviewborderbottom))
        } else {
            tv.background = ContextCompat.getDrawable(this, R.drawable.maingametextviewborderbottom)
        }

        //get the parent layout for your new TextView and add the new TextView to it
        val linearLayout = findViewById<LinearLayout>(R.id.insideScrollView)
        linearLayout.addView(topView)
        linearLayout.addView(tv)

        ageFuncs(mainPerson, mainPerson.age, tv)
        maintainScrollViewDown()


        // Displays bankaccount balance now
        val bankBalanceString = formatToCurrency(mainPerson.bankBalance)
        val bankAccountView = findViewById<View>(R.id.bankView) as Button
        bankAccountView.text = "Bank Account\n$bankBalanceString"

        // Updates ProgressBars
        val healthBar = findViewById<View>(R.id.progressbarHealth) as ProgressBar
        healthBar.progress = mainPerson.health
        val happyBar = findViewById<View>(R.id.progressbarHappy) as ProgressBar
        happyBar.progress = mainPerson.happiness


        // Person possibly gets sick here
        mainPerson.randomSickness()
        printSickness(mainPerson, tv)


        maintainScrollViewDown() // keeps scroll focused downward.


        // What happens when you click the assets button
        val assetsButton = findViewById<View>(R.id.assetsButton) as Button
        assetsButton.setOnClickListener {
            val balance = mainPerson.bankBalance
            if (balance < 500000) {
                val broke = Toast.makeText(applicationContext,
                        "Not enough money to invest",
                        Toast.LENGTH_LONG)
                broke.show()
            } else if (balance < 100000) {
                val broke = Toast.makeText(applicationContext,
                        "You've bought your first Home!",
                        Toast.LENGTH_LONG)
                broke.show()
            } else if (balance < 1000000) {
                val broke = Toast.makeText(applicationContext,
                        "You've bought a high-rise!",
                        Toast.LENGTH_LONG)
                broke.show()
            }
        }


    }

    // Depending on age, can make life choices
    private fun ageFuncs(mainPerson: Person, personAge: Int, tv: TextView) {

        var specAge = false

        when (personAge) {
            13 -> {
                thirteenSexualOrientation(mainPerson, tv)
                specAge = true
            }
        }


        // if no popup has been shown and they're from ages 5 - 17
        if (!specAge && personAge >= 5 && personAge <= 17) {

            val randNum = randomNumberInBetweenMaxMin(1, 10)

            // 10% chance of this popup
            if (randNum == 6) {
                val popupEvent = randArrayTitle(popuptemplates5to17) // generates string in this array
                val randGenEvent = popuptemplate(popupEvent) // makes object of event

                alertDialogFunc(randGenEvent.description, randGenEvent.option1, randGenEvent.option2, randGenEvent.yesHappy, randGenEvent.yesHealth, randGenEvent.noHappy, randGenEvent.noHealth, randGenEvent.wealthEffect.toDouble(), randGenEvent.wealthEffectNo.toDouble(), mainPerson, tv)
                // String questionTitle, final String option1, final String option2, final int yesHappy, final int yesHealth, final int noHappy, final int noHealth, final double wealthEffect, final double wealthEffectNo, final Person mainPerson, final TextView tv

            }
        }


        // if they haven't already had a popup at this age and they're between 11-17
        if (!specAge && personAge >= 11 && personAge <= 17) {
            val randNum = randomNumberInBetweenMaxMin(1, 10)
            // 20% chance
            if (randNum < 8 && randNum >= 6) {
                val popupEvent = randArrayTitle(popuptemplates11to17) // generates string in this array
                val randGenEvent = popuptemplate(popupEvent) // makes object of event

                alertDialogFunc(randGenEvent.description, randGenEvent.option1, randGenEvent.option2, randGenEvent.yesHappy, randGenEvent.yesHealth, randGenEvent.noHappy, randGenEvent.noHealth, randGenEvent.wealthEffect.toDouble(), randGenEvent.wealthEffectNo.toDouble(), mainPerson, tv)

            }
        }

        if (!specAge && personAge >= 18) {
            val randNum = randomNumberInBetweenMaxMin(1, 10)
            if (randNum < 8 && randNum >= 6) {
                val popupEvent = randArrayTitle(popuptemplates18forLife)
                val randGenEvent = popuptemplate(popupEvent)
                alertDialogFunc(randGenEvent.description, randGenEvent.option1, randGenEvent.option2, randGenEvent.yesHappy, randGenEvent.yesHealth, randGenEvent.noHappy, randGenEvent.noHealth, randGenEvent.wealthEffect.toDouble(), randGenEvent.wealthEffectNo.toDouble(), mainPerson, tv)

            }
        }

    }

    // Template code for building a popup scenario
    private fun popupTemplate(mainPerson: Person, tv: TextView, title: String, description: String, option1: String, option2: String, yesHappy: Int, yesHealth: Int, noHappy: Int, noHealth: Int, shortDesc: String) {

        val buttonParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        buttonParams.setMargins(0, 0, 0, 120)


        // Hide Layouts
        val activityBarButtons = findViewById<View>(R.id.activityBarLinearLayout) as LinearLayout
        val scroll = findViewById<View>(R.id.scrollviewmain) as ScrollView
        scroll.visibility = View.GONE
        activityBarButtons.visibility = View.GONE

        // Show layouts
        val emptypopuplayout = findViewById<View>(R.id.emptypopuplayout) as LinearLayout
        emptypopuplayout.visibility = View.VISIBLE
        val emptypopupbuttonlayout = findViewById<View>(R.id.emptypopupbuttonlayout) as LinearLayout
        emptypopupbuttonlayout.visibility = View.VISIBLE

        val emptypopuptitletext = findViewById<View>(R.id.emptypopuptitletext) as TextView
        emptypopuptitletext.text = title

        val emptypopupdescription = findViewById<View>(R.id.emptypopupdescription) as TextView
        emptypopupdescription.text = description
        emptypopupdescription.visibility = View.VISIBLE

        // Add Buttons
        val button1 = Button(this)
        val button2 = Button(this)
        button1.layoutParams = buttonParams
        button2.layoutParams = buttonParams
        button1.text = option1 // yes option
        button2.text = option2 // no option
        button1.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.buttonbackground))
        button2.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.buttonbackground))
        button1.textSize = 18f
        button2.textSize = 18f
        button1.setPadding(60, 30, 60, 30)
        button2.setPadding(60, 30, 60, 30)

        button1.setOnClickListener {
            activityBarButtons.visibility = View.VISIBLE // Makes old views visible
            scroll.visibility = View.VISIBLE
            emptypopupbuttonlayout.removeAllViews() // Deletes all buttons
            emptypopupdescription.visibility = View.GONE
            emptypopuplayout.visibility = View.GONE // Hides empty linear layout

            mainPerson.happiness = mainPerson.happiness + yesHappy
            mainPerson.health = mainPerson.health + yesHealth
            mainPerson.bankBalance = mainPerson.money * 10.0
            maintainScrollViewDown()
            tv.text = "You decided to $shortDesc\n"

            popupTemplateTextHelper(tv, yesHappy, yesHealth, noHappy, noHealth, true)
        }

        button2.setOnClickListener {
            activityBarButtons.visibility = View.VISIBLE // Makes old views visible
            scroll.visibility = View.VISIBLE
            emptypopupbuttonlayout.removeAllViews() // Deletes all buttons
            emptypopupdescription.visibility = View.GONE
            emptypopuplayout.visibility = View.GONE // Hides empty linear layout

            mainPerson.happiness = mainPerson.happiness + noHappy
            mainPerson.health = mainPerson.health + noHealth


            maintainScrollViewDown()
            tv.text = "You decided not to $shortDesc\n"

            popupTemplateTextHelper(tv, yesHappy, yesHealth, noHappy, noHealth, false)
        }

        emptypopupbuttonlayout.addView(button1)
        emptypopupbuttonlayout.addView(button2)


    }

    // Used in popupTemplate to help display text to TV
    private fun popupTemplateTextHelper(tv: TextView, yesHappy: Int, yesHealth: Int, noHappy: Int, noHealth: Int, yesOrNo: Boolean) {

        if (yesOrNo == true) {
            // Output to console
            if (yesHappy < 0) {
                tv.append("Happiness: $yesHappy\n")
            } else {
                tv.append("Happiness: +$yesHappy\n")
            }
            if (yesHealth < 0) {
                tv.append("Health: $yesHealth\n")
            } else {
                tv.append("Health: +$yesHealth\n")
            }
        } else if (yesOrNo == false) {
            // Output to console
            if (noHappy < 0) {
                tv.append("Happiness: $noHappy\n")
            } else {
                tv.append("Happiness: +$noHappy\n")
            }
            if (noHealth < 0) {
                tv.append("Health: $noHealth\n")
            } else {
                tv.append("Health: +$noHealth\n")
            }
        }

    }

    // Popup at 13 years old which asks them for sexual orientation
    private fun thirteenSexualOrientation(mainPerson: Person, tv: TextView) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sexual Orientation")
        builder.setCancelable(false)

        // add a list
        val options = arrayOf("Gay", "Straight", "Bisexual")
        builder.setItems(options) { dialog, which ->
            val bankView = findViewById<Button>(R.id.bankView)
            when (which) {
                0 -> {
                    // Option 1 clicked
                    mainPerson.sexualOrientation = "Gay"
                    tv.append("You are gay")
                    maintainScrollViewDown()
                }
                1 -> {
                    // Option 2 clicked
                    mainPerson.sexualOrientation = "Straight"
                    tv.append("You are straight")
                    maintainScrollViewDown()
                }
                2 -> {
                    // Option 3 Clicked
                    mainPerson.sexualOrientation = "Bisexual"
                    tv.append("You are bisexual")
                    maintainScrollViewDown()
                }
            }
        }

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()

    }

    // Allows player to play the lottery
    private fun lotteryOddsFunc(mainPerson: Person) {
        if (mainPerson.age < 18) {
            val underage = Toast.makeText(applicationContext,
                    "You must be 18 to gamble",
                    Toast.LENGTH_LONG)
            underage.show()
            return
        }

        val randomNum = randomNumberInBetweenMaxMin(1, 1000)
        val winning: Int
        var won = true

        // Winning logic
        if (randomNum == 1000) {
            winning = 150000
        } else if (randomNum <= 999 && randomNum >= 996) {
            winning = 10000
        } else if (randomNum <= 995 && randomNum >= 990) {
            winning = 1000
        } else if (randomNum <= 989 && randomNum >= 980) {
            winning = 500
        } else if (randomNum <= 979 && randomNum >= 930) {
            winning = 50
        } else if (randomNum <= 929 && randomNum >= 830) {
            winning = 20
        } else if (randomNum <= 829 && randomNum >= 700) {
            winning = 10
        } else if (randomNum <= 699 && randomNum >= 500) {
            winning = 5
        } else {
            winning = 0
            won = false
        }

        if (won) {
            val winToast = Toast.makeText(applicationContext,
                    "Congrats! You won " + formatToCurrency(winning.toDouble()),
                    Toast.LENGTH_LONG)
            winToast.show()
        } else {
            val loseToast = Toast.makeText(applicationContext,
                    "Sorry, you didn't win anything...",
                    Toast.LENGTH_LONG)
            loseToast.show()
        }

        activityBackButtonFunction()

        mainPerson.bankBalance = mainPerson.bankBalance - 5 + winning
        val bankButton = findViewById<View>(R.id.bankView) as Button
        bankButton.text = "Bank Account\n" + formatToCurrency(mainPerson.bankBalance)

        val randNum2 = randomNumberInBetweenMaxMin(1, 200)

        // 0.5% chance to get sick with Gambling Addiction
        if (randNum2 == 157 && mainPerson.sickness == null) {
            val gambleSick = Sickness("a Gambling Addiction")
            mainPerson.sickness = gambleSick
        }

    }

    //function for partying
    private fun partyFunc(mainPerson: Person) {
        if (mainPerson.age < 21) {
            val underage = Toast.makeText(applicationContext,
                    "You must be 21 to party",
                    Toast.LENGTH_LONG)
            underage.show()
            return
        }

        val randomNum = randomNumberInBetweenMaxMin(1, 10)
        val drunk = randomNumberInBetweenMaxMin(1, 10) % 2 == 0
        val metSomeone = randomNumberInBetweenMaxMin(1, 10) % 5 == 0
        val bar = mainPerson.neighborhood.getbar(randomNumberInBetweenMaxMin(0, 2))
        if (drunk) {
            val currentHealth = mainPerson.health
            mainPerson.health = currentHealth - randomNum
            val drunkToast = Toast.makeText(applicationContext,
                    "You got drunk,not feeling good",
                    Toast.LENGTH_LONG)
            drunkToast.show()
        } else if (metSomeone) {
            mainPerson.happiness = mainPerson.happiness + 10
            val metToast = Toast.makeText(applicationContext,
                    "You met someone lastnight at" + bar + "you're so happy!",
                    Toast.LENGTH_LONG)
            metToast.show()
        } else {
            mainPerson.happiness = mainPerson.happiness + randomNum
            val partyToast = Toast.makeText(applicationContext, "You had so much fun,you're so happy!",
                    Toast.LENGTH_LONG)
            partyToast.show()
        }

        activityBackButtonFunction()

        mainPerson.bankBalance = mainPerson.bankBalance - 10 * mainPerson.money
        val bankButton = findViewById<View>(R.id.bankView) as Button
        bankButton.text = "Bank Account\n" + formatToCurrency(mainPerson.bankBalance)

    }


    // Prints sickness to tv if they're sick and takes away their health/happiness and adds year to sickness
    internal fun printSickness(mainPerson: Person, tv: TextView) {

        // if person is sick, display to screen
        if (mainPerson.sickness != null) {

            if (mainPerson.sickness!!.yearsWith > 0) {
                tv.append("You're still suffering from " + mainPerson.sickness!!.title + ".")
            } else if (mainPerson.sickness!!.yearsWith == 0) {
                tv.append("Oh no! You just got " + mainPerson.sickness!!.title + ", which is " + mainPerson.sickness!!.description)
            }

            mainPerson.sickness!!.addYearToSickness()

            // Takes away their health/happy based on damage per turn
            val currentHappy = mainPerson.happiness
            val currentHealth = mainPerson.health
            val damageHappy = mainPerson.sickness!!.happyDamagePerTurn
            val damageHealth = mainPerson.sickness!!.damagePerTurn
            mainPerson.health = currentHealth - damageHealth
            mainPerson.happiness = currentHappy - damageHappy
        }
    }

    // Prints first TextView to the ScrollView
    internal fun printFirstTextView(mainPerson: Person) {


        /* DYAMICALLY ADDS TEXVIEW TO SCROLLVIEW */
        //create a TextView with Layout parameters according to your needs
        val lparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        //if your parent Layout is relativeLayout, just change the word LinearLayout with RelativeLayout
        val topView = TextView(this)
        topView.layoutParams = lparams
        topView.textSize = 15f
        topView.setTextColor(Color.parseColor("#3F51B5"))
        topView.text = getString(R.string.InitialTextViewAge, mainPerson.age)

        val tv = TextView(this)
        tv.layoutParams = lparams
        tv.textSize = 15f
        tv.setTextColor(Color.BLACK)
        tv.setPadding(0, 0, 0, 40)

        /*Sets Drawable for border on bottom of textview in scrollview*/
        val sdk = android.os.Build.VERSION.SDK_INT   // gets int version of os build
        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN)
        // if os is less than Jelly Bean then make it drawable
        {
            tv.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.maingametextviewborderbottom))
        } else {
            tv.background = ContextCompat.getDrawable(this, R.drawable.maingametextviewborderbottom)
        }


        // Set Text Here !!!!!!!!!!!!!!!
        tv.text = getString(R.string.InitialTextViewIntro, mainPerson.name)


        //get the parent layout for your new TextView and add the new TextView to it
        val linearLayout = findViewById<LinearLayout>(R.id.insideScrollView)
        linearLayout.addView(topView)
        linearLayout.addView(tv)


        // Displays bankaccount balance now
        val bankBalanceString = formatToCurrency(mainPerson.bankBalance)
        val bankAccountView = findViewById<View>(R.id.bankView) as Button
        bankAccountView.text = "Bank Account\n$bankBalanceString"

        // Updates ProgressBars
        val healthBar = findViewById<View>(R.id.progressbarHealth) as ProgressBar
        healthBar.progress = mainPerson.health
        val happyBar = findViewById<View>(R.id.progressbarHappy) as ProgressBar
        happyBar.progress = mainPerson.happiness

    }

    // Constant --> Keeps scrollview focused downwards
    internal fun maintainScrollViewDown() {

        val scrollView = findViewById<View>(R.id.scrollviewmain) as ScrollView
        scrollView.post { scrollView.fullScroll(View.FOCUS_DOWN) }
    }

    /*Takes in a double and returns a string formatted to currency*/
    fun formatToCurrency(money: Double): String {

        val format = NumberFormat.getCurrencyInstance()

        return format.format(money)
    }

    // Returns a random number in between a min/max
    internal fun randomNumberInBetweenMaxMin(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min

    }

    // hides activity bar and brings back top bar
    private fun hideActivityBarBringBackTopBar() {
        val activityPopup = findViewById<View>(R.id.activityPopup) as LinearLayout
        val topBarLayout = findViewById<View>(R.id.topbarlayout) as LinearLayout
        val activityBarLinearLayout = findViewById<View>(R.id.activityBarLinearLayout) as LinearLayout

        activityPopup.visibility = View.GONE
        topBarLayout.visibility = View.VISIBLE
        activityBarLinearLayout.visibility = View.VISIBLE
        maintainScrollViewDown()
    }

    // Randomly returns a string in an array
    fun randArrayTitle(jobTitles: Array<String>): String {

        val size = jobTitles.size - 1 // size of array


        val randomNum = (Math.random() * (size + 1)).toInt()
        return jobTitles[randomNum]

    }

    // Allows them to choose a doctor from the list
    private fun chooseDoctor(mainPerson: Person) {
        // Makes Doctor Bar visible
        val doctorPopup = findViewById<View>(R.id.doctorPopup) as RelativeLayout
        doctorPopup.visibility = View.VISIBLE

        val drbutton1 = findViewById<View>(R.id.drbutton1) as Button
        val drbutton2 = findViewById<View>(R.id.drbutton2) as Button
        val drbutton3 = findViewById<View>(R.id.drbutton3) as Button
        val feedbackText = findViewById<View>(R.id.feedbacktextdr) as TextView

        val docName1 = randArrayTitle(doctorNames)
        var docName2 = ""
        var docName3 = ""
        do {
            docName2 = randArrayTitle(doctorNames)
        } while (docName2 == docName1)
        do {
            docName3 = randArrayTitle(doctorNames)
        } while (docName3 == docName1 || docName3 == docName2)

        val costToTreat = mainPerson.sickness!!.costToTreat

        drbutton1.text = docName1 + " " + formatToCurrency(costToTreat / 2)
        drbutton2.text = docName2 + " " + formatToCurrency(costToTreat)
        drbutton3.text = docName3 + " " + formatToCurrency(costToTreat * 2)


    }

    // Makes back button work after you click activities button
    internal fun activityBackButtonFunction() {
        hideActivityBarBringBackTopBar()
    }

    // Possibly cures the person if they are sick
    private fun curePerson(mainPerson: Person, doctorChosen: Int) {
        val doctorPopup = findViewById<View>(R.id.doctorPopup) as RelativeLayout

        // If they have a sickness needing a therapist
        if (mainPerson.sickness!!.title == "Gambling Addiction") {
            val therapistToast = Toast.makeText(applicationContext,
                    "The doctor says you should see a therapist",
                    Toast.LENGTH_LONG)
            therapistToast.show()
            maintainScrollViewDown()
            doctorPopup.visibility = View.GONE
            return
        }

        val randomInt = randomNumberInBetweenMaxMin(1, 10)
        val costToTreat = mainPerson.sickness!!.costToTreat
        val failToast = Toast.makeText(applicationContext,
                "You failed to get cured. Better luck next time!",
                Toast.LENGTH_LONG)
        val successToast = Toast.makeText(applicationContext,
                "You were cured!", Toast.LENGTH_LONG)


        if (doctorChosen == 1) {
            // 40% chance of cure
            if (randomInt <= 4) {
                mainPerson.sickness = null
                successToast.show()
                doctorPopup.visibility = View.GONE
            } else {
                failToast.show()
                doctorPopup.visibility = View.GONE
            }

            mainPerson.bankBalance = mainPerson.bankBalance - costToTreat / 2
        } else if (doctorChosen == 2) {
            if (randomInt <= 6) {
                mainPerson.sickness = null
                successToast.show()
                doctorPopup.visibility = View.GONE
            } else {
                failToast.show()
                doctorPopup.visibility = View.GONE
            }
            mainPerson.bankBalance = mainPerson.bankBalance - costToTreat
        } else if (doctorChosen == 3) {
            if (randomInt <= 8) {
                mainPerson.sickness = null
                successToast.show()
                doctorPopup.visibility = View.GONE
            } else {
                failToast.show()
                doctorPopup.visibility = View.GONE
            }
            mainPerson.bankBalance = mainPerson.bankBalance - costToTreat * 2
        }

        val bankButton = findViewById<View>(R.id.bankView) as Button
        bankButton.text = "Bank Account: \n" + formatToCurrency(mainPerson.bankBalance)
        mainPerson.isWentToDoctorThisTurn = true
        maintainScrollViewDown()

    }

    // Ends the life of the person and ends the game
    private fun killPerson(mainPerson: Person) {

        val alertDialog = AlertDialog.Builder(this@MainGame).create()
        alertDialog.setCancelable(false)
        alertDialog.setTitle("You died!")
        alertDialog.setMessage(mainPerson.name + " died at age " + mainPerson.age + ".")
        alertDialog.setIcon(R.drawable.deathsymbol)
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, which ->
            dialog.dismiss()
            finish()
        }
        alertDialog.show()
    }

    // Person works out and health/happy update
    private fun workoutFunc(mainPerson: Person) {
        val randNum = randomNumberInBetweenMaxMin(1, 20)

        // 5% chance of injury
        if (randNum == 9) {
            // if they're already sick
            if (mainPerson.sickness != null) {
                val badWorkout = Toast.makeText(applicationContext,
                        "You left since there were too many people there working out.",
                        Toast.LENGTH_LONG)
                badWorkout.show()
            } else {
                val randomInjury = randArrayTitle(physicalInjuries)
                val injury = Sickness(randomInjury)
                mainPerson.sickness = injury
                val bankButton = findViewById<View>(R.id.bankView) as Button
                bankButton.text = "Bank Account\n" + mainPerson.bankBalance
            }
        } else {

            val newHappy = mainPerson.happiness + 5
            val newHealth = mainPerson.health + 5
            mainPerson.happiness = newHappy
            mainPerson.health = newHealth
            val goodWorkout = Toast.makeText(applicationContext,
                    "You had a successful workout",
                    Toast.LENGTH_LONG)
            goodWorkout.show()

            val happyBar = findViewById<View>(R.id.progressbarHappy) as ProgressBar
            val healthBar = findViewById<View>(R.id.progressbarHealth) as ProgressBar

            happyBar.progress = newHappy
            healthBar.progress = newHealth
        }

        mainPerson.isWentToWorkoutThisTurn = true
    }

    // Allows mentally sick people to see the therapist
    private fun visitTherapist(mainPerson: Person) {
        // if person is not sick
        if (mainPerson.sickness == null) {
            val drerrortoast = Toast.makeText(applicationContext,
                    "You must be unwell to go to the therapist.",
                    Toast.LENGTH_SHORT)

            drerrortoast.show()
        } else if (mainPerson.sickness!!.type != "mental") {

            val notMental = Toast.makeText(applicationContext,
                    "You don't have a mental illness. Go see the doctor.", Toast.LENGTH_LONG)
            notMental.show()

        } else {
            hideActivityBarBringBackTopBar()
            chooseDoctor(mainPerson)
        }// if they really are mentally ill
        // if it is not a mental illness
    }

    private fun addOrRemoveProperty(view: View, property: Int, flag: Boolean) {
        val layoutParams = view.layoutParams as RelativeLayout.LayoutParams
        if (flag) {
            layoutParams.addRule(property)
        } else {
            layoutParams.removeRule(property)
        }
        view.layoutParams = layoutParams
    }

    // For Alert Dialogs in ageFunc
    private fun alertDialogFunc(questionTitle: String?, option1: String?, option2: String?, yesHappy: Int, yesHealth: Int, noHappy: Int, noHealth: Int, wealthEffect: Double, wealthEffectNo: Double, mainPerson: Person, tv: TextView) {

        val builder = AlertDialog.Builder(this)
        builder.setTitle(questionTitle)
        builder.setCancelable(false)

        // add a list
        val options = arrayOf<String?>(option1, option2)
        builder.setItems(options) { dialog, which ->
            val bankView = findViewById<Button>(R.id.bankView)
            when (which) {
                0 -> {
                    // Option 1 clicked
                    mainPerson.health = mainPerson.health + yesHealth
                    mainPerson.happiness = mainPerson.happiness + yesHappy
                    mainPerson.bankBalance = mainPerson.bankBalance + wealthEffect
                    bankView.text = "Bank Account\n" + formatToCurrency(mainPerson.bankBalance)
                    tv.append("You chose to $option1.\n")
                    if (yesHappy != 0) {
                        tv.append("Happiness: " + plusMinusString(yesHappy.toDouble()) + "\n")
                    }
                    if (yesHealth != 0) {
                        tv.append("Health: " + plusMinusString(yesHealth.toDouble()) + "\n")
                    }
                    if (wealthEffect != 0.0) {
                        var wealthEffectString = ""
                        if (wealthEffectNo < 0) {
                            wealthEffectString = "-" + formatToCurrency(wealthEffect)
                        } else {
                            wealthEffectString = "+" + formatToCurrency(wealthEffect)
                        }
                        tv.append("Bank Account: $wealthEffectString\n")
                    }
                    maintainScrollViewDown()
                }
                1 -> {
                    // Option 2 clicked
                    mainPerson.health = mainPerson.health + noHealth
                    mainPerson.happiness = mainPerson.happiness + noHappy
                    mainPerson.bankBalance = mainPerson.bankBalance + wealthEffectNo
                    bankView.text = "Bank account \n" + formatToCurrency(mainPerson.bankBalance)
                    tv.append("You chose to $option2.\n")
                    if (noHappy != 0) {
                        tv.append("Happiness: " + plusMinusString(noHappy.toDouble()) + "\n")
                    }
                    if (noHappy != 0) {
                        tv.append("Health: " + plusMinusString(noHappy.toDouble()) + "\n")
                    }
                    if (wealthEffectNo != 0.0) {
                        var wealthEffectString = ""
                        if (wealthEffectNo < 0) {
                            wealthEffectString = "-" + formatToCurrency(wealthEffectNo)
                        } else {
                            wealthEffectString = "+" + formatToCurrency(wealthEffectNo)
                        }
                        tv.append("Bank Account: $wealthEffectString\n")
                    }
                    maintainScrollViewDown()
                }
            }
        }

        // create and show the alert dialog
        val dialog = builder.create()
        dialog.show()
    }

    // Returns + and - in front of attribute
    private fun plusMinusString(attribute: Double): String {
        return if (attribute < 0) {
            "" + attribute
        } else if (attribute > 0) {
            "+$attribute"
        } else {
            "Unchanged"
        }
    }

}
