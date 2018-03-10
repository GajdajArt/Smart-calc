package com.labralab.smartcalkulator.presenters.adapters

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.labralab.smartcalkulator.App

import com.labralab.smartcalkulator.views.MainActivity
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.models.Expression
import com.labralab.smartcalkulator.presenters.MainPresenter
import io.realm.Realm
import java.util.ArrayList
import javax.inject.Inject


/**
 * Created by pc on 27.02.2018.
 */
class ExpListAdapter(items: List<Expression>) : RecyclerView.Adapter<ExpRecyclerViewHolder>() {

    private var items: List<Expression> = ArrayList()
    private var realm: Realm? = null
    private var mainActivity: MainActivity? = null

    init {
        this.items = items
//        this.mainActivity = mainActivity
        realm = Realm.getDefaultInstance()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpRecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expressiom_lisl_maket, parent, false)
        return ExpRecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpRecyclerViewHolder, position: Int) {

//            val item = items[position]
//            holder.position.text = Integer.toString(position + 1) + "."
//            holder.title.text = item.title
//            holder.isRunning.isChecked = item.state!!
//
//            if (!item.paramsList!!.isEmpty()) {
//                holder.isRunning.visibility = View.VISIBLE
//                holder.isRunning.setOnCheckedChangeListener { _, b ->
//
//
//                    realm!!.executeTransaction({ _ ->
//
//                        item.state = b
//
//                        if (b) {
//                            mainActivity!!.volumeManager!!.cancelAlarm()
//                            mainActivity!!.volumeManager!!.checkTheSate(item)
//                            mainActivity!!.volumeManager!!.setDefaultParams()
//                            mainActivity!!.volumeManager!!.startAlarmManager(item)
//
//                            Toast.makeText(mainActivity, "${item.title} запущен", Toast.LENGTH_SHORT).show()
//
//                        } else {
//                            mainActivity!!.volumeManager!!.cancelAlarm()
//
//                            Toast.makeText(mainActivity, "${item.title} отключен", Toast.LENGTH_SHORT).show()
//                        }
//
//                        if (items.size > 1) {
//                            if (b) {
//                                for (day in items) {
//
//                                    if (item.title != day.title) {
//                                        day.state = false
//                                    }
//                                }
//                                this@MainRecyclerViewAdapter.notifyDataSetChanged()
//                            }
//                        }
//                    })
//                }
//
//            } else {
//                Toast.makeText(mainActivity, "Заполните ${item.title} параметрами", Toast.LENGTH_SHORT).show()
//                holder.isRunning.visibility = View.INVISIBLE
//            }
    }

    override fun getItemCount(): Int {
        return items.size
    }

}

class ExpRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, View.OnLongClickListener {

    @Inject
    lateinit var mainPresenter: MainPresenter

    var title: TextView
    lateinit var position: TextView
    lateinit var isRunning: Switch

    init {

        App.appComponents.inject(this)

        itemView.setOnClickListener(this)

//            itemView.setOnLongClickListener(this)
            title = itemView.findViewById<View>(R.id.title_in_list) as TextView
//            position = itemView.findViewById<View>(R.id.position) as TextView
//            isRunning = itemView.findViewById<View>(R.id.isRunning) as Switch
    }

    override fun onClick(view: View) {

        val bundle = Bundle()
        bundle.putString("title", title.text.toString())
        mainPresenter.runParamsFragment(bundle)

    }

    override fun onLongClick(p0: View?): Boolean {
//
//            val builder = AlertDialog.Builder(p0!!.context)
//            builder.setMessage("Удалить ${title.text}?")
//            builder.setPositiveButton("Да", { dialog, _ ->
//                removeDay(title.text.toString())
//
//                var activity: MainActivity = p0.context as MainActivity
//                activity.adapterNotifyDataSetChanged()
//
//                dialog.cancel()
//            })
//
//            builder.setNegativeButton("Нет", { dialog, _ ->
//                dialog.cancel()
//            })
//            val removeDialog = builder.create()
//            removeDialog.show()
//
//            return true
//        }
//
//        private fun removeDay(title: String) {
//            DayParamsList.remove(title)
        return true
    }
}

