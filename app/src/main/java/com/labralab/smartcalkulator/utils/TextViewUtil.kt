package com.labralab.smartcalkulator.utils

import android.widget.TextView

/**
 * Created by pc on 16.04.2018.
 */
class TextViewUtil {

    companion object {

        //method for changing the size of the text depending on its number
        fun getTextSize(tv: TextView): Float {

            return when (tv.text.length) {

                in 1..7 -> 100.0F
                in 7..9 -> 80.0F
                in 9..12 -> 50.0F
                in 12..20 -> 30.0F
                else -> 20.0F
            }
        }
    }
}