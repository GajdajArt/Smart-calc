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




class ExpressionFragment : Fragment() {

    lateinit var dispET: EditText

    @Inject
    lateinit var expPresenter: ExpPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //Injecting
        App.appComponents.inject(this)
        expPresenter.expFrag = this
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_expression, container, false)

    }

    override fun onStart() {
        super.onStart()


        dispET = displayET


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

        //Ok Button
        doneButton.setOnClickListener {

        }

    }

    private fun buttonOnClick(button: Button, flag: String) {
        button.setOnClickListener {
            expPresenter.addFunction(flag)
        }
    }
}
