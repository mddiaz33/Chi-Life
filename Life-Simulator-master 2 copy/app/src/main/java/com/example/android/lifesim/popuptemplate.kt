package com.example.android.lifesim

import java.util.Random

class popuptemplate(// Getters
        val title: String) {
    var description: String? = null
        private set
    val shortdesc: String? = null
    var option1: String? = null
        private set // "You chose to"
    var option2: String? = null
        private set // "You chose to"
    var yesHappy: Int = 0
        private set
    var noHappy: Int = 0
        private set
    var yesHealth: Int = 0
        private set
    var noHealth: Int = 0
        private set
    var wealthEffect: Int = 0
        private set
    var wealthEffectNo: Int = 0
        private set

    init {

        when (title) {
            "Cops" -> {
                this.description = "You get pulled over by the cops "
                this.option1 = "speed off "
                this.option2 = "pullover"
                this.yesHappy = 10
                this.yesHealth = -5
                this.noHappy = -10
                this.noHealth = 0
                this.wealthEffectNo = 0
                this.wealthEffect = -500
            }
            "Marijuana" -> {
                this.description = "Your friend offers you marijuana"
                this.option1 = "Take it"
                this.option2 = "Decline"
                this.yesHappy = 10
                this.yesHealth = -5
                this.noHappy = -10
                this.noHealth = 0
                this.wealthEffectNo = 0
                this.wealthEffect = 0
            }
            "Corn" -> {
                this.description = "Your friend asks you to get an elote"
                this.option1 = "ok!"
                this.option2 = "No ty"
                this.yesHappy = 10
                this.yesHealth = 0
                this.noHappy = -10
                this.noHealth = 0
                this.wealthEffectNo = 0
                this.wealthEffect = 0
            }

            "Bullying" -> {
                this.description = "You see a kid being picked on in the hallway"
                this.option1 = "Help him"
                this.option2 = "Walk away"
                this.yesHappy = 10
                this.yesHealth = 0
                this.noHappy = -10
                this.noHealth = 0
                this.wealthEffectNo = 0
                this.wealthEffect = 0
            }
            "Amusement Park" -> {
                this.description = "A girl ask's you to the bean"
                this.option1 = "Go"
                this.option2 = "Stay home"
                this.yesHappy = 15
                this.yesHealth = 0
                this.noHappy = -15
                this.noHealth = 0
                this.wealthEffect = 0
                this.wealthEffectNo = 0
            }
            "Grandma" -> {
                this.description = "You see a huge rat on the side walk"
                this.option1 = "run"
                this.option2 = "feed it"
                this.yesHappy = -5
                this.yesHealth = 0
                this.noHealth = -5
                this.noHappy = 5
                this.wealthEffect = 100
                this.wealthEffectNo = 50
            }
            "Bicycle" -> {
                this.description = "You crashed your bike and hit your head"
                this.option1 = "Go to the ER"
                this.option2 = "Keep riding"
                this.yesHappy = 0
                this.yesHealth = 0
                this.noHappy = -5
                this.noHealth = -20
                this.wealthEffect = 0
                this.wealthEffectNo = 0
            }
            "Study" -> {
                this.description = "You forgot to study for your test"
                this.option1 = "Cheat"
                this.option2 = "Take test without cheating"
                this.yesHealth = 0
                this.yesHappy = -5
                this.noHealth = 0
                this.noHappy = 5
                this.wealthEffect = 0
                this.wealthEffectNo = 0
            }
            "Homeless" -> {
                this.description = "You came across a homeless person"
                this.option1 = "Give him $10"
                this.option2 = "Walk on"
                this.yesHealth = 0
                this.yesHappy = 10
                this.noHealth = 0
                this.noHappy = -10
                this.wealthEffect = -10
                this.wealthEffectNo = 0
            }
            "Cousin" -> {
                this.description = "Your cousin you haven't seen in years called"
                this.option1 = "Go to lunch with your cousin ($15)"
                this.option2 = "Ignore your cousin"
                this.yesHappy = 15
                this.yesHealth = 0
                this.noHappy = -5
                this.noHealth = 0
                this.wealthEffect = -15
                this.wealthEffectNo = 0
            }
            "Horse Race" -> {
                this.description = "You know a dice game is going to be rigged"
                this.option1 = "Bet $500"
                this.option2 = "Don't bet. That's wrong."
                this.yesHappy = 0
                this.yesHealth = 0
                this.noHealth = 0
                this.noHappy = 5
                this.wealthEffect = 1000
                this.wealthEffectNo = 0
            }
        }

    }

    // Returns a random number in between a min/max
    internal fun randomNumberInBetweenMaxMin(min: Int, max: Int): Int {
        return Random().nextInt(max - min + 1) + min

    }


    // Setters

}
