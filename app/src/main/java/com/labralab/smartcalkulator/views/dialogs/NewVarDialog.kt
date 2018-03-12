package com.labralab.smartcalkulator.views.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.EditText
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.models.Variable
import com.labralab.smartcalkulator.presenters.ExpPresenter


@SuppressLint("ValidFragment")
class NewVarDialog(var expPresenter: ExpPresenter) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)

        //object for working with Dialog
        val inflater = activity.layoutInflater
        //find the layout.dialog_task and all the elements within it.
        val container = inflater.inflate(R.layout.new_var_dialog_maket, null)


        builder.setView(container)

        val tilTitle: TextInputLayout = container.findViewById<View>(R.id.tILTitle) as TextInputLayout
        var etTitle: EditText = tilTitle.editText as EditText
        val tilVar: TextInputLayout = container.findViewById<View>(R.id.tILVar) as TextInputLayout
        var etVar: EditText = tilVar.editText as EditText


        //Creates button OK in the bottom of the dialog
        builder.setPositiveButton(getString(R.string.next)) { dialog, _ ->

            var variable = Variable()
            variable.title = etTitle.text.toString()
            variable.value = etVar.text.toString()

            expPresenter.addVariable(variable)
            dialog.cancel()

        }

        //Creates button CANCEL in the bottom of the dialog
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            expPresenter.expFrag.varSp.setSelection(0)
            dialog.cancel()
        }


        return builder.create()

    }
}