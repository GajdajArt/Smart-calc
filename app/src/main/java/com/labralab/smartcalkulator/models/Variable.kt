package com.labralab.smartcalkulator.models

import io.realm.RealmObject

/**
 * Created by pc on 25.02.2018.
 */
open class Variable: RealmObject(){

    lateinit var title: String
    lateinit var value: String
}