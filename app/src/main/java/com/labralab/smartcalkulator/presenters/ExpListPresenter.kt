package com.labralab.smartcalkulator.presenters

import android.content.Context
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.labralab.calk.repository.Repository
import com.labralab.smartcalkulator.App


import com.labralab.smartcalkulator.models.Expression
import com.labralab.smartcalkulator.presenters.adapters.ExpListAdapter
import com.labralab.smartcalkulator.views.dialogs.NewSimpleDialog
import javax.inject.Inject

/**
 * Created by pc on 09.03.2018.
 */
class ExpListPresenter {


    lateinit var adapter: ExpListAdapter

    @Inject
    lateinit var mainPresenter: MainPresenter

    init {
        App.presenterComponents!!.inject(this)
    }

    fun runList(context: Context, mainRecyclerView: RecyclerView){

        val layoutManager = LinearLayoutManager(context)
        adapter = ExpListAdapter(context)


        mainRecyclerView.layoutManager = layoutManager
        mainRecyclerView.adapter = adapter
        mainRecyclerView.addItemDecoration(DividerItemDecoration(context,
                LinearLayoutManager.VERTICAL))
    }

    fun addNewExpression(){

        val newDialog = NewSimpleDialog(NewSimpleDialog.NEW_EXP_FLAG, null)
        newDialog.show(mainPresenter.supportFragmentManager, "TAG")

    }

    fun showHint(context: Context){

        if(adapter.itemCount == 0){

            Toast.makeText(context, "Нажмите на \"+\" чтоб добавить формулу", Toast.LENGTH_SHORT)
                    .show()
        } else{

            Toast.makeText(context, "Нажмите на эллемент " +
                    "списка чтоб перейти к введению параметров", Toast.LENGTH_SHORT)
                    .show()
        }
    }
}