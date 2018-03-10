package com.labralab.smartcalkulator.models

import io.realm.RealmList
import io.realm.RealmObject

/**
 * Created by pc on 25.02.2018.
 */
open class Expression: RealmObject() {

    lateinit var title: String
    lateinit var exp: String
    lateinit var varList: RealmList<Variable>

}