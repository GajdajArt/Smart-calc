package com.labralab.smartcalkulator.presenters

import android.view.View
import android.widget.ArrayAdapter
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.models.Variable
import com.labralab.smartcalkulator.views.dialogs.NewVarDialog
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment
import android.widget.AdapterView
import android.widget.Toast
import com.labralab.smartcalkulator.models.Expression
import com.labralab.smartcalkulator.views.dialogs.NewSimpleDialog


class ExpPresenter(var expFrag: ExpressionFragment) {

    private lateinit var expTitle: String
    private lateinit var exp: Expression
    private var paramsCounter = 0

    private var varList = ArrayList<Variable>()
    private var forSpinnerList = ArrayList<String>()

    private lateinit var adapterOne: ArrayAdapter<String>

    private var bracketCounter: Int = 0
    private var dispText: StringBuilder = StringBuilder()
    private var waitVar = false

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

    //Adding function
    fun addFunction(func: String) {

        if (expFrag.dispET.text.isEmpty()) {
            waitVar = true
        }

        if (waitVar) {

            if (func == COS || func == SIN || func == TAN) {

                dispText.append(func)
                dispText.append("(")
                dispText.append(" ")
                waitVar = true
                bracketCounter++

            }
            expFrag.dispET.text = dispText.toString()


        } else {

            if (func == CLOSE) {

                if (bracketCounter > 0) {

                    dispText.append(")")
                    dispText.append(" ")
                    bracketCounter--
                    expFrag.dispET.text = dispText.toString()

                }

            } else if (func != OPEN && func != COS && func != SIN && func != TAN) {

                dispText.append(func)
                dispText.append(" ")
                waitVar = true
                expFrag.dispET.text = dispText.toString()

            }else if (func == OPEN) {

                dispText.append("(")
                dispText.append(" ")
                bracketCounter++
                waitVar = true
                expFrag.dispET.text = dispText.toString()

            } else {
                Toast.makeText(expFrag.context, "error", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //inserting a variable into the formula
    fun pasteVar() {

        if (paramsCounter < 50) {

            if (expFrag.varSp.selectedItem != expFrag.context.getString(R.string.empty)) {

                when {
                    expFrag.dispET.text.isEmpty() -> {

                        dispText.append(expFrag.varSp.selectedItem)
                        dispText.append(" ")
                        expFrag.dispET.text = dispText.toString()
                        waitVar = false
                        paramsCounter++

                    }
                    waitVar -> {

                        dispText.append(expFrag.varSp.selectedItem)
                        dispText.append(" ")
                        expFrag.dispET.text = dispText.toString()
                        waitVar = false
                        paramsCounter++

                    }
                    else -> Toast.makeText(expFrag.context, "Параметр уже вставлен", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(expFrag.context, "Слишком длинная формула", Toast.LENGTH_SHORT).show()
        }
    }

    //Adding new variable
    fun addVariable(variable: Variable) {

        varList.add(variable)
        forSpinnerList.add(0, variable.value)
        removeEmptyItem()
        expFrag.varSp.setSelection(0)

    }

    //removing variable
    fun deleteVariable() {

        var title = expFrag.varSp.selectedItem


        if (forSpinnerList.size == 3) {

            var item = expFrag.context.getString(R.string.empty)
            forSpinnerList.add(0, item)

            adapterOne = ArrayAdapter(expFrag.context,
                    android.R.layout.simple_spinner_item, forSpinnerList)
            adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            expFrag.varSp.adapter = adapterOne

            expFrag.varSp.setSelection(0)
        }

        forSpinnerList.remove(title)

        for (v in varList) {
            if (v.title == title) {
                varList.remove(v)
            }
        }


    }

    //Adding constants
    fun addConst(const: String) {

        forSpinnerList.add(0, const)
        removeEmptyItem()
        expFrag.varSp.setSelection(0)

    }

    fun setArgs() {

        if (expFrag.arguments != null) {

            expTitle = expFrag.arguments.getString("title")
            expFrag.expTitleTV.text = expTitle

            exp = expFrag.repository.getExp(expTitle)
        }
    }

    //Running dialog for adding new variable
    fun showNewVarDialog() {

        val newVarDialog = NewVarDialog(this)
        newVarDialog.show(expFrag.fragmentManager, "TAG")
    }

    //Running dialog for adding new constance
    fun showNewConstDialog() {

        val newConsDialog = NewSimpleDialog(NewSimpleDialog.NEW_CONST_FLAG, this)
        newConsDialog.show(expFrag.fragmentManager, "TAG")
    }

    //Removing empty item when list of variable is not empty
    private fun removeEmptyItem() {

        forSpinnerList.remove(expFrag.context.getString(R.string.empty))
    }


    //Creating base item when list of variable is empty
    fun createBaseItems() {

        if (forSpinnerList.size == 0) {

            var newVar = expFrag.context.getString(R.string.addVar)
            forSpinnerList.add(newVar)
            var newConst = expFrag.context.getString(R.string.constanta)
            forSpinnerList.add(newConst)
            var item = expFrag.context.getString(R.string.empty)
            forSpinnerList.add(0, item)

            adapterOne = ArrayAdapter(expFrag.context,
                    android.R.layout.simple_spinner_item, forSpinnerList)
            adapterOne.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            expFrag.varSp.adapter = adapterOne

            choseBaseItem()
        }
    }

    //
    private fun choseBaseItem() {

        expFrag.varSp.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(arg0: AdapterView<*>, arg1: View, arg2: Int, arg3: Long) {

                if (expFrag.varSp.selectedItem == expFrag.context.getString(R.string.addVar)) {
                    showNewVarDialog()
                }
                if (expFrag.varSp.selectedItem == expFrag.context.getString(R.string.constanta)) {
                    showNewConstDialog()
                }
            }

            override fun onNothingSelected(arg0: AdapterView<*>) {}
        }
    }

    //Saving expression and varList to Realm Item
    fun done() {

        if (bracketCounter == 0) {

            expFrag.realm.executeTransaction {
                exp.exp = dispText.toString()
                exp.varList.addAll(varList)
            }

            expFrag.fragmentManager.popBackStack()

        } else {
            Toast.makeText(expFrag.context, "error", Toast.LENGTH_SHORT).show()
        }
    }

    //Removing elements from expression
    fun backspaceClick() {


        var i = dispText.length - 1

        if (i >= 0) {

            var c: Char

            do {

                dispText.deleteCharAt(i)

                if (i > 0) {

                    i--
                    c = dispText[i]

                } else {
                    break
                }

            } while (c != ' ')

            waitVar = !waitVar
            expFrag.dispET.text = dispText.toString()
        }
    }
}

