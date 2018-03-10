package com.labralab.smartcalkulator.presenters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.labralab.calk.views.fragments.ExpressionListFragment
import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.App
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.dependencyInjection.AppComponents
import com.labralab.smartcalkulator.views.MainActivity
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment
import javax.inject.Inject

/**
 * Created by pc on 09.03.2018.
 */
class MainPresenter {

    private lateinit var mainActivity: MainActivity
    private lateinit var supportFragmentManager: FragmentManager


    @Inject
    lateinit var expressionListFragment: ExpressionListFragment
    @Inject
    lateinit var expressionFragment: ExpressionFragment
    @Inject
    lateinit var parametersFragment: ParametersFragment

    init {
        App.appComponents.inject(this)
    }

    fun setMainActivity(mainActivity: MainActivity){
        this.mainActivity = mainActivity
        supportFragmentManager = mainActivity.supportFragmentManager
    }

    //Run ExpressionListFragment()
    fun runExpListFragment(){

        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.dayContainer, expressionListFragment)
                .commit()
    }

    //Run ExpFragment()
    fun runExpFragment(){

        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.dayContainer, expressionFragment)
                .addToBackStack(null)
                .commit()

    }

    //Run ParamsFragment()
    fun runParamsFragment(bundle: Bundle){

        parametersFragment.arguments = bundle

        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.dayContainer, parametersFragment)
                .addToBackStack(null)
                .commit()

    }

}