package com.labralab.calk.views.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.labralab.smartcalkulator.App

import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.presenters.ExpListPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import com.labralab.smartcalkulator.views.MainActivity
import kotlinx.android.synthetic.main.fragment_expression_list.*
import javax.inject.Inject


class ExpressionListFragment : Fragment() {

    @Inject
    lateinit var expListPresenter: ExpListPresenter
    @Inject
    lateinit var mainPresenter: MainPresenter


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //Injecting
        App.removePresenterComponents()
        App.plusPresenters().inject(this)
        mainPresenter.setMainActivity(activity as MainActivity)
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_expression_list, container, false)
    }

    override fun onStart() {
        super.onStart()

        //Running view (list and hint)
        expListPresenter.runList(activity, mainRecyclerView)
        expListPresenter.showHint(context)

        fab.setOnClickListener {
            expListPresenter.addNewExpression()
        }
    }
}
