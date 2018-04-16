package com.labralab.calk.repository


import com.labralab.smartcalkulator.models.Expression
import com.labralab.smartcalkulator.models.Result
import io.realm.Realm
import io.realm.RealmQuery
import io.realm.RealmResults



class Repository {

    private val realm = Realm.getDefaultInstance()!!

    fun createResult(r: Result){

        realm.beginTransaction()
        realm.insert(r)
        realm.commitTransaction()

    }

    fun getResultList(): ArrayList<Result>{

        val query: RealmQuery<Result> = realm.where(Result::class.java)
        val realmResult: RealmResults<Result> = query.findAll()

        var list: ArrayList<Result> = ArrayList()
        list.addAll(realmResult)

        return list
    }

    fun removeResult(r: Double){

        var flag = false
        if(!realm.isInTransaction){
            realm.beginTransaction()
            flag = true
        }

        val result = realm.where(Result::class.java)
                .equalTo("res", r)
                .findAll()
        result.deleteFirstFromRealm()

        if(flag){
            realm.commitTransaction()
        }


    }

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

        var flag = false
        if(!realm.isInTransaction){
            realm.beginTransaction()
            flag = true
        }

        val result = realm.where(Expression::class.java)
                .equalTo("title", title)
                .findAll()
        result.deleteFirstFromRealm()

        if(flag){
            realm.commitTransaction()
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