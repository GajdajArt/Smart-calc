package com.labralab.calk.repository


import com.labralab.smartcalkulator.models.Expression
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults



class Repository {

    private val realm = Realm.getDefaultInstance()!!

    fun createDay (exp: Expression){

        realm.beginTransaction()
        realm.insert(exp)
        realm.commitTransaction()
    }

    fun getExpList(): ArrayList<Expression> {

        val query: RealmQuery<Expression> = realm.where(Expression::class.java)
        val realmResult: RealmResults<Expression> = query.findAll()

        var list: ArrayList<Expression> = ArrayList()
        list.addAll(realmResult)

        return list
    }

    fun removeExp(title: String){

        realm.executeTransaction { realm ->
            val result = realm.where(Expression::class.java)
                    .equalTo("title", title)
                    .findAll()
            result.deleteAllFromRealm()
        }
    }

    fun getExp(title: String): Expression{

        var expression = Expression()

        realm.executeTransaction { realm ->
            val result: RealmResults<Expression> = realm.where(Expression::class.java)
                    .equalTo("title", title)
                    .findAll()
            expression = result.first()!!
        }

        return expression
    }
}