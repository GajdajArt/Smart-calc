package com.labralab.smartcalkulator.presenters

import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.calkulator.SmartCalculator
import com.labralab.smartcalkulator.models.Expression
import kotlinx.android.synthetic.main.fragment_expression_list.*
import java.util.*

/**
 * Created by pc on 09.03.2018.
 */
class ParamsPresenter(var paramsFragment: ParametersFragment) {


    private var bufferNum: StringBuilder = StringBuilder()

    private var i = 0
    private var cancelClick = 0
    private var isPoint = false
    private var firstNumAfterPoint = false
    private var isDone = false
    private var isNull = false

    private lateinit var baseExp: String

    private lateinit var currentParamVal: String
    private lateinit var currentParamTitle: String
    private lateinit var currentExp: String

    private lateinit var title: String
    private lateinit var exp: Expression


    private lateinit var backStack: Stack<String>


    init {

        initThis()

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

    private fun initThis() {

        bufferNum = StringBuilder()

        i = 0
        cancelClick = 0
        isPoint = false
        firstNumAfterPoint = false
        isDone = false

        title = paramsFragment.arguments.getString("title")

        exp = paramsFragment.repository.getExp(title)

        currentParamVal = exp.varList[i]!!.value
        currentParamTitle = exp.varList[i]!!.title
        currentExp = exp.exp

        baseExp = exp.exp

        backStack = Stack()
        backStack.push(baseExp)
    }

    fun setNumber(flag: Int) {

        if (firstNumAfterPoint) {

            if (flag != 0) {
                bufferNum.delete(bufferNum.length - 1, bufferNum.length)
            }

            bufferNum.append(flag)
            firstNumAfterPoint = false

        } else {

            if (flag == 0) {

                if (!isNull && !isPoint) {

                    bufferNum.append(flag)
                    isNull = true
                }

                if (isPoint) {

                    bufferNum.append(flag)
                }

            } else {
                bufferNum.append(flag)
            }
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
            isNull = false
            showHint()
            changeMainTVContent()
            cancelClick = 0
            firstNumAfterPoint = true
        }
    }

    fun cancel() {

        if (backStack.size > 0) {

            when (cancelClick) {

            //first cancel click
                0 -> {

                    cancelClick = 1

                    currentParamVal = exp.varList[i]!!.value
                    currentParamTitle = exp.varList[i]!!.title

                    currentExp = backStack.lastElement()

                    isPoint = false
                    isNull = false
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
                        backStack.pop()

                        currentExp = backStack.lastElement()
                        baseExp = currentExp

                        isPoint = false
                        isNull = false
                        bufferNum.setLength(0)
                        changeMainTVContent()
                        showHint()

                    }
                }
            }
        }
    }

    fun nextOrDone() {

        if (!isDone) {

            if (i < exp.varList.size - 1) {

                backStack.push(currentExp)
                baseExp = currentExp
                nextParam()
                showHint()
                changeMainTVContent()


            } else {

                paramsFragment.mainTV.text = SmartCalculator.calculate(currentExp, 0).toString()
                paramsFragment.fab.setImageResource(R.drawable.ic_replay_white_24dp)
                isDone = true

            }

        } else {

            initThis()
            paramsFragment.fab.setImageResource(R.drawable.ic_action_play_arrow)
            showHint()
            changeMainTVContent()

        }
    }


    private fun nextParam() {

        i++
        currentParamVal = exp.varList[i]!!.value
        currentParamTitle = exp.varList[i]!!.title
        bufferNum.setLength(0)
        isPoint = false
        isNull = false
    }

    fun showHint() {

        paramsFragment.hintTV.text = "Введите $currentParamVal"

    }

    fun changeMainTVContent() {

        paramsFragment.mainTV.text = currentExp
    }
}