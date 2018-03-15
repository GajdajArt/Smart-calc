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

    lateinit var expTitle: String
    lateinit var exp: Expression

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

        if (!waitVar) {

            if (func == COS || func == SIN || func == TAN) {

                dispText.append(func)
                dispText.append("(")
                dispText.append(" ")
                bracketCounter++

            } else if (func == OPEN) {

                dispText.append("(")
                dispText.append(" ")
                bracketCounter++

            } else if (func == CLOSE) {

                dispText.append(")")
                dispText.append(" ")
                bracketCounter--

            } else {
                dispText.append(func)
                dispText.append(" ")

            }
            expFrag.dispET.setText(dispText.toString())
            waitVar = true

        } else {
            Toast.makeText(expFrag.context, "error", Toast.LENGTH_SHORT).show()
        }

    }

    fun pasteVar() {

        when {
            expFrag.dispET.text.isEmpty() -> {

                dispText.append(expFrag.varSp.selectedItem)
                dispText.append(" ")
                expFrag.dispET.setText(dispText.toString())
                waitVar = false
            }
            waitVar -> {

                dispText.append(expFrag.varSp.selectedItem)
                dispText.append(" ")
                expFrag.dispET.setText(dispText.toString())
                waitVar = false

            }
            else -> Toast.makeText(expFrag.context, "error", Toast.LENGTH_SHORT).show()
        }
    }

    //Adding new variable
    fun addVariable(variable: Variable) {

        varList.add(variable)
        forSpinnerList.add(0, variable.value)
        removeEmptyItem()
        expFrag.varSp.setSelection(0)

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

    fun showNewVarDialog() {

        val newVarDialog = NewVarDialog(this)
        newVarDialog.show(expFrag.fragmentManager, "TAG")
    }

    fun showNewConstDialog() {

        val newConsDialog = NewSimpleDialog(NewSimpleDialog.NEW_CONST_FLAG, this)
        newConsDialog.show(expFrag.fragmentManager, "TAG")
    }

    private fun removeEmptyItem() {
        forSpinnerList.remove(expFrag.context.getString(R.string.empty))
    }


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

    fun done() {

        if (bracketCounter == 0) {

            //Saving expression and varList to Realm Item
            expFrag.realm.executeTransaction {
                exp.exp = dispText.toString()
                exp.varList.addAll(varList)
            }

            expFrag.fragmentManager.popBackStack()

        } else {
            Toast.makeText(expFrag.context, "error", Toast.LENGTH_SHORT).show()
        }
    }
}

