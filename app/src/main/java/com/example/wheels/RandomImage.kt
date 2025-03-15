package com.example.wheels

object RandomImage {
    val images = listOf(
        R.drawable.ace_of_clubs,
//        R.drawable.ace_of_spade,
//        R.drawable.ace_of_heart,
//        R.drawable.ace_of_diamond,

        R.drawable.two_of_clubs,
//        R.drawable.two_of_spade,
//        R.drawable.two_of_heart,
//        R.drawable.two_of_diamond,

        R.drawable.three_of_clubs,
//        R.drawable.three_of_spade,
//        R.drawable.three_of_heart,
//        R.drawable.three_of_diamond,

        R.drawable.four_of_clubs,
//        R.drawable.four_of_spade,
//        R.drawable.four_of_heart,
//        R.drawable.four_of_diamond,

        R.drawable.five_of_clubs,
//        R.drawable.five_of_spade,
//        R.drawable.five_of_heart,
//        R.drawable.five_of_diamond,

        R.drawable.six_of_clubs,
//        R.drawable.six_of_spade,
//        R.drawable.six_of_heart,
//        R.drawable.six_of_diamond,

        R.drawable.seven_of_clubs,
//        R.drawable.seven_of_spade,
//        R.drawable.seven_of_heart,
//        R.drawable.seven_of_diamond,

        R.drawable.eight_of_clubs,
//        R.drawable.eight_of_spade,
//        R.drawable.eight_of_heart,
//        R.drawable.eight_of_diamond,

        R.drawable.nine_of_clubs,
//        R.drawable.nine_of_spade,
//        R.drawable.nine_of_heart,
//        R.drawable.nine_of_diamond,

//        R.drawable.ten_of_clubs,
//        R.drawable.ten_of_spade,
//        R.drawable.ten_of_heart,
        R.drawable.ten_of_diamond,

        R.drawable.jack_of_clubs,
//        R.drawable.jack_of_spade,
//        R.drawable.jack_of_heart,
//        R.drawable.jack_of_diamond,

//        R.drawable.queen_of_clubs,
//        R.drawable.queen_of_spade,
//        R.drawable.queen_of_heart,
        R.drawable.queen_of_diamond,

        R.drawable.king_of_clubs,
//        R.drawable.king_of_spade,
//        R.drawable.king_of_heart,
//        R.drawable.king_of_diamond,

//        R.drawable.joker_red,
        R.drawable.joker_black,
    )

    fun getRandomCards(count: Int = 1): List<Int> {
        return images.shuffled().take(count) // Shuffle and take 5 unique images
    }
}