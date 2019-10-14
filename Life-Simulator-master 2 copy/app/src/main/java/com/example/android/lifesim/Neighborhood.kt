package com.example.android.lifesim

import java.io.Serializable

class Neighborhood internal constructor(nh: Int) : Serializable {
    var money: Int = 0
        private set
    var neighborhood: String? = null
        private set
    // Getter functions

    var description: String? = null
        private set
    private var bars: Array<String>? = null

    init {

        when (nh) {
            1 -> {
                this.neighborhood = "Humboldt Park "
                this.description = "Humboldt is home to one of the most incredible parks in Chicago -- with flower gardens, ponds, playgrounds, and more -- not to mention the brand new 606 trail, which is just two blocks away. There are plenty of hidden gem restaurants, and everyone who lives east of Ashland Ave thinks it’s dangerous which keeps the rents low, so don't spoil it."
                this.money = 2
                this.bars = arrayOf("Tiny's party", "Humble Bar", "El Citio")
            }
            2 -> {
                this.neighborhood = "Lincoln Park "
                this.description = "Lincoln Park has the appeal of green grass and trees in what\'s otherwise a sea of steel, glass, and concrete. It\'s home for the wealthy, young families, and young professionals. And it's not too hard to snag a sweet skyline view from your apartment’s rooftop.\n"
                this.money = 10
                this.bars = arrayOf("Delilah's", "The arrogant Frog Bar", "Rose's")
            }
            3 -> {
                this.neighborhood = "Little Village "
                this.description = "Little Village is bursting with culture. Find the best mexican food in chicago,art graffiti, and night life.\n"
                this.money = 1
                this.bars = arrayOf("La Cueva", "Volcan", "Los Globos")
            }
            4 -> {
                this.neighborhood = "Austin"
                this.description = "A once thriving manufacturing district, neighborhood is working to rebrand it self by  cultivating hybrid art spaces, planning for urban communities, and tapping into alternative approaches to community organizing.  .\n"
                this.money = 2
                this.bars = arrayOf("Ted's", "Corolyn's", "Ms Ann's")
            }
            5 -> {
                this.neighborhood = "Lakeview"
                this.description = "Lakeview’s laidback atmosphere and picture-perfect shoreline make it a favorite hang out among locals. And spots like lively Wrigleyville and the Belmont Theater District make it a major entertainment hub, too.\n"
                this.money = 5
                this.bars = arrayOf("the cubs game", "Scarlet", "Roscoe's")
            }
        }
    }

    fun getbar(i: Int): String {
        return bars!![i]
    }


}
