package com.labralab.smartcalkulator.presenters

import android.widget.Toast
import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.models.Expression
import java.util.*

/**
 * Created by pc on 09.03.2018.
 */
class ParamsPresenter(private val paramsFragment: ParametersFragment) {

    private var bufferNum: StringBuilder = StringBuilder()
    private var baseExp: String

    private var i = 0
    private var currentParamVal: String
    private var currentParamTitle: String
    private var currentExp: String

    private var title: String
    private var exp: Expression

    private var isPoint = false

    private var backSteck: Stack<String>

    private var cancelClick = 0
    private var firstNumAfterPoint = false

    init {

        title = paramsFragment.arguments.getString("title")

        exp = paramsFragment.repository.getExp(title)

        currentParamVal = exp.varList[i]!!.value
        currentParamTitle = exp.varList[i]!!.title
        currentExp = exp.exp

        baseExp = exp.exp

        backSteck = Stack()
        backSteck.push(baseExp)

        showHint()
        changeMainTVContent()

    }

    companion object {

        const val ONE = 1
        const val TWO = 2
        const val THREE = 3
        const val FOUR = 4
        const val FIVE = 5
        const val SIX = 6
        const val SEVEN = 7
        const val EYT = 8
        const val NINE = 9
        const val ZERO = 0

    }

    fun setNumber(flag: Int) {

        if(firstNumAfterPoint){

            bufferNum.delete(bufferNum.length - 1, bufferNum.length)
            bufferNum.append(flag)
            firstNumAfterPoint = false

        }else{

            bufferNum.append(flag)

        }
        currentExp = baseExp.replace(currentParamVal, bufferNum.toString())
        changeMainTVContent()
        cancelClick = 0

    }

    fun insertPoint() {

        if (!isPoint && bufferNum.isNotEmpty()) {

            bufferNum.append(".0")
            currentExp = baseExp.replace(currentParamVal, bufferNum.toString())
            isPoint = true
            showHint()
            changeMainTVContent()
            cancelClick = 0
            firstNumAfterPoint = true
        }
    }

    fun cancel() {

        if (backSteck.size > 0) {

            when (cancelClick) {

            //first cancel click
                0 -> {

                    cancelClick = 1

                    currentParamVal = exp.varList[i]!!.value
                    currentParamTitle = exp.varList[i]!!.title

                    currentExp = backSteck.lastElement()

                    isPoint = false
                    bufferNum.setLength(0)
                    changeMainTVContent()
                    showHint()

                }

            //next cancel click
                1 -> {
                    if (i > 0) {

                        i--
                        currentParamVal = exp.varList[i]!!.value
                        currentParamTitle = exp.varList[i]!!.title
                        backSteck.pop()

                        currentExp = backSteck.lastElement()
                        baseExp = currentExp

                        isPoint = false
                        bufferNum.setLength(0)
                        changeMainTVContent()
                        showHint()

                    }
                }
            }
        }
    }

    fun nextOrDone() {

        if (i < exp.varList.size - 1) {

            backSteck.push(currentExp)
            baseExp = currentExp
            nextParam()
            showHint()
            changeMainTVContent()

        } else {
            Toast.makeText(paramsFragment.context, "результат", Toast.LENGTH_SHORT).show()
        }
    }

    private fun nextParam() {

        i++
        currentParamVal = exp.varList[i]!!.value
        currentParamTitle = exp.varList[i]!!.title
        bufferNum.setLength(0)
        isPoint = false
    }

    private fun showHint() {

        paramsFragment.hintTV.text = "Введите $currentParamVal"

    }

    private fun changeMainTVContent() {

        paramsFragment.mainTV.text = currentExp
    }
}