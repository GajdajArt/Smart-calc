package com.labralab.smartcalkulator.presenters

import android.os.Bundle
import android.support.v4.app.FragmentManager
import com.labralab.calk.views.fragments.ExpressionListFragment
import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.App
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.views.MainActivity
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment
import javax.annotation.Nullable
import javax.inject.Inject

/**
 * Created by pc on 09.03.2018.
 */
class MainPresenter {

    private lateinit var mainActivity: MainActivity
    lateinit var supportFragmentManager: FragmentManager

    @Inject
    lateinit var expressionListFragment: ExpressionListFragment

    lateinit var expressionFragment: ExpressionFragment
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
    fun runExpFragment(bundle: Bundle?){

        expressionFragment = ExpressionFragment()

        if(bundle !=  null){
            expressionFragment.arguments = bundle
        }

        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.dayContainer, expressionFragment)
                .addToBackStack(null)
                .commit()

    }

    //Run ParamsFragment()
    fun runParamsFragment(bundle: Bundle){

        parametersFragment = ParametersFragment()

        parametersFragment.arguments = bundle

        val tr = supportFragmentManager.beginTransaction()
        tr.replace(R.id.dayContainer, parametersFragment)
                .addToBackStack(null)
                .commit()

    }

}