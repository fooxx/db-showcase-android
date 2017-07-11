package cz.koto.misak.dbshowcase.android.mobile.utility

import java.util.*


fun getRandomBirthday(minAge: Int, maxAge: Int): Date {
    val cal = Calendar.getInstance()
    val today = cal.time
    cal.add(Calendar.YEAR, -getRandomInt(minAge, maxAge))
    return cal.time
}


fun getRandomInt(minValue: Int, maxValue: Int): Int {
    val number = Random(System.nanoTime())
    var ret = minValue
    while (ret <= minValue) {
        ret = number.nextInt(maxValue)
    }
    return ret
}


fun getRandomString(minLenght: Int, maxLenght: Int): String {
    val generator = Random(System.nanoTime())
    val randomStringBuilder = StringBuilder()
    val randomLength = getRandomInt(minLenght, maxLenght)
    var tempChar: Char
    for (i in 0..randomLength - 1) {
        tempChar = (generator.nextInt(96) + 32).toChar()
        randomStringBuilder.append(tempChar)
    }
    return randomStringBuilder.toString()
}