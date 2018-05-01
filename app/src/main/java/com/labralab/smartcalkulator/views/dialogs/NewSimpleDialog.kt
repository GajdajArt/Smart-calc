package com.labralab.smartcalkulator.views.dialogs

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.view.View
import android.widget.EditText
import com.labralab.calk.repository.Repository
import com.labralab.smartcalkulator.App
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.models.Expression
import com.labralab.smartcalkulator.presenters.ExpPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import javax.inject.Inject

@SuppressLint("ValidFragment")
class NewSimpleDialog(var flag: Int, var expPresenter: ExpPresenter?) : DialogFragment() {

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var repository: Repository

    companion object {
        const val NEW_EXP_FLAG = 0
        const val NEW_CONST_FLAG = 1
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        App.presenterComponents!!.inject(this)

        val builder = AlertDialog.Builder(activity)


        //object for working with Dialog
        val inflater = activity.layoutInflater
        //find the layout.dialog_task and all the elements within it.
        val container = inflater.inflate(R.layout.new_dialog_maket, null)


        builder.setView(container)

        val tilTitle: TextInputLayout = container.findViewById<View>(R.id.tILSimpleTitle) as TextInputLayout
        var etTitle: EditText = tilTitle.editText as EditText


        when (flag) {

            NEW_CONST_FLAG -> {
                tilTitle.hint = getString(R.string.constanta)
            }
            NEW_EXP_FLAG -> {
                tilTitle.hint = getString(R.string.title)
                etTitle.inputType = InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE
            }
        }


        //Creates button OK in the bottom of the dialog
        builder.setPositiveButton(getString(R.string.next)) { dialog, _ ->

            when (flag) {
                NEW_CONST_FLAG -> {
                    expPresenter!!.addConst(etTitle.text.toString())
                }
                NEW_EXP_FLAG -> {

                    //New Expression saving to Realm
                    var expression = Expression()
                    expression.title = etTitle.text.toString()
                    repository.createExp(expression)


                    //Running ExpFragment()
                    val bundle = Bundle()
                    bundle.putString("title", etTitle.text.toString())

                    mainPresenter.runExpFragment(bundle)
                }
            }
            dialog.cancel()
        }

        //Creates button CANCEL in the bottom of the dialog
        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->

            when (flag) {

                NEW_CONST_FLAG -> {
                    expPresenter!!.expFrag.varSp.setSelection(0)
                    dialog.cancel()
                }
                NEW_EXP_FLAG -> {
                    dialog.cancel()
                }
            }
        }

        return builder.create()

    }
}