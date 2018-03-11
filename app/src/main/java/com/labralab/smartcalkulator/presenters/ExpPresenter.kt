package com.labralab.smartcalkulator.presenters

import android.widget.Toast
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment

/**
 * Created by pc on 09.03.2018.
 */
class ExpPresenter{

    lateinit var expFrag: ExpressionFragment

    companion object {

        const val PLUS = "+"
        const val MINUS = "-"
        const val MULTIPLY = "*"
        const val DIVIDE = "/"

        const val OPEN = "("
        const val CLOSE = ")"

        const val COS = "cos"
        const val SIN = "sin"
        const val TAN = "tan"

        const val PI = "Pi"
        const val ROOT = "root"
        const val DEGREE = "degree"
    }


    fun addFunction(func: String){

        expFrag.dispET.setText(func)


//        Toast.makeText(expFrag.context, func, Toast.LENGTH_SHORT).show()

    }

}