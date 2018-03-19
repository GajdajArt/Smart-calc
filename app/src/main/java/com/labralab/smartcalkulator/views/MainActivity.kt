package com.labralab.smartcalkulator.views

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.labralab.smartcalkulator.App
import com.labralab.smartcalkulator.R
import com.labralab.smartcalkulator.presenters.MainPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainPresenter: MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Injecting
        App.plusPresenters().inject(this)

        //Running firs fragment
        mainPresenter.setMainActivity(this)
        mainPresenter.runExpListFragment()
    }
}
