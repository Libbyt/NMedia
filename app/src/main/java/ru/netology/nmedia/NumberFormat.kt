package ru.netology.nmedia


import android.icu.number.Notation
import android.icu.number.NumberFormatter
import android.os.Build
import androidx.annotation.RequiresApi
import java.math.RoundingMode
import java.util.*

@RequiresApi(Build.VERSION_CODES.R)
fun numberFormat(number: Int): String {
    return NumberFormatter.with().roundingMode(RoundingMode.FLOOR)
        .notation(Notation.compactShort()).locale(Locale.getDefault()).format(number).toString()
}