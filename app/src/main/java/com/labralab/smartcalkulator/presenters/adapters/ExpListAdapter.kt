package com.labralab.smartcalkulator.presenters.adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.labralab.calk.repository.Repository
import com.labralab.smartcalkulator.App

import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.models.Expression
import com.labralab.smartcalkulator.presenters.MainPresenter
import javax.inject.Inject


/**
 * Created by pc on 27.02.2018.
 */
class ExpListAdapter(var context: Context) : RecyclerView.Adapter<ExpRecyclerViewHolder>() {

    @Inject
    lateinit var repository: Repository
    @Inject
    lateinit var mainPresenter: MainPresenter

    private var items: List<Expression>

    init {

        App.presenterComponents!!.inject(this)
        items = repository.getExpList()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expressiom_lisl_maket, parent, false)
        return ExpRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpRecyclerViewHolder, position: Int) {


        var item = items[position]

        holder.title.text = item.title
        holder.formula.text = item.exp
        holder.position.text = "${position + 1}."

        holder.editButton.setOnClickListener {
            editItem(item.title)
        }

        holder.delButton.setOnClickListener {
            deleteItem(item.title)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }


    private fun deleteItem(title: String) {

        val builder = AlertDialog.Builder(context)
        builder.setMessage("Удалить $title?")
        builder.setPositiveButton("Да", { dialog, _ ->

            repository.removeExp(title)
            items = repository.getExpList()
            notifyDataSetChanged()
            dialog.cancel()

        })

        builder.setNegativeButton("Нет", { dialog, _ ->
            dialog.cancel()
        })

        val removeDialog = builder.create()
        removeDialog.show()
    }

    private fun editItem(title: String) {

        val bundle = Bundle()
        bundle.putString("title", title)

        mainPresenter.runExpFragment(bundle)

    }

}

class ExpRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    @Inject
    lateinit var mainPresenter: MainPresenter

    var title: TextView
    var position: TextView
    var formula: TextView
    var editButton: ImageButton
    var delButton: ImageButton


    init {

        //Injecting
        App.presenterComponents!!.inject(this)

        itemView.setOnClickListener(this)

        title = itemView.findViewById<View>(R.id.title_in_list) as TextView
        position = itemView.findViewById<View>(R.id.pos_in_list) as TextView
        formula = itemView.findViewById<View>(R.id.exp_in_list) as TextView
        editButton = itemView.findViewById<View>(R.id.editButton) as ImageButton
        delButton = itemView.findViewById<View>(R.id.delButton) as ImageButton

    }

    override fun onClick(view: View) {

        val bundle = Bundle()
        bundle.putString("title", title.text.toString())
        mainPresenter.runParamsFragment(bundle)

    }
}

