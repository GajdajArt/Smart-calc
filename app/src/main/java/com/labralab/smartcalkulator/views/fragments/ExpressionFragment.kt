package com.labralab.smartcalkulator.views.fragments


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.labralab.smartcalkulator.App
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.presenters.ExpPresenter
import kotlinx.android.synthetic.main.fragment_expression.*
import javax.inject.Inject
import android.view.View.OnFocusChangeListener
import android.widget.Spinner
import android.widget.TextView
import com.labralab.calk.repository.Repository
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_parameters.*


class ExpressionFragment : Fragment() {

    lateinit var dispET: TextView
    lateinit var varSp: Spinner
    lateinit var expTitleTV: TextView

    @Inject
    lateinit var expPresenter: ExpPresenter
    @Inject
    lateinit var repository: Repository
    @Inject
    lateinit var realm: Realm

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Injecting
        App.presenterComponents!!.inject(this)
        expPresenter.expFrag = this
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_expression, container, false)

    }

    override fun onStart() {
        super.onStart()

        //Running view
        setViews()

        //Setting listeners to buttons
        buttonOnClick(cosButton, ExpPresenter.COS) //Cos Button
        buttonOnClick(sinButton, ExpPresenter.SIN) //Sin Button
        buttonOnClick(tanButton, ExpPresenter.TAN) //Tan Button
        buttonOnClick(piButton, ExpPresenter.PI) //PI Button

        buttonOnClick(openButton, ExpPresenter.OPEN) //( Button
        buttonOnClick(closeButton, ExpPresenter.CLOSE) //) Button

        buttonOnClick(plusButton, ExpPresenter.PLUS) //+ Button
        buttonOnClick(minusButton, ExpPresenter.MINUS) //- Button
        buttonOnClick(multiplyButton, ExpPresenter.MULTIPLY) //* Button
        buttonOnClick(divideButton, ExpPresenter.DIVIDE) // / Button

        buttonOnClick(rootButton, ExpPresenter.ROOT) //Root Button
        buttonOnClick(degreeButton, ExpPresenter.DEGREE) //Degree Button

        deleteButton.setOnClickListener {
            expPresenter.deleteVariable()
        }

        pasteButton.setOnClickListener {
            expPresenter.pasteVar()
        }

        //Ok Button
        doneButton.setOnClickListener {
            expPresenter.done()
        }

        backspaceButton.setOnClickListener {
            expPresenter.backspaceClick()
        }

    }

    private fun buttonOnClick(button: Button, flag: String) {
        button.setOnClickListener {
            expPresenter.addFunction(flag)
        }
    }

    //View params for a presenter
    private fun setViews() {

        varSp = varSpinner
        //Setting base items to spinner
        expPresenter.createBaseItems()
        dispET = displayET
        expTitleTV = expTitle
        expPresenter.setArgs()
    }
}
