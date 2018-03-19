package com.labralab.smartcalkulator.dependencyInjection

import com.labralab.calk.views.fragments.ExpressionListFragment
import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.presenters.ExpListPresenter
import com.labralab.smartcalkulator.presenters.ExpPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import com.labralab.smartcalkulator.presenters.ParamsPresenter
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    lateinit var expressionFragment: ExpressionFragment
    lateinit var parametersFragment: ParametersFragment
    lateinit var expressionListFragment: ExpressionListFragment


    //Fragments
    @Provides
    @ActivitySingleton
    fun provideExpFragment(): ExpressionFragment {
        expressionFragment = ExpressionFragment()
        return expressionFragment
    }

    @Provides
    @ActivitySingleton
    fun provideExpListFragment(): ExpressionListFragment {
        expressionListFragment = ExpressionListFragment()
        return expressionListFragment
    }

    @Provides
    @ActivitySingleton
    fun provideParamsFragment(): ParametersFragment{
        parametersFragment = ParametersFragment()
        return parametersFragment
    }

    @Provides
    @ActivitySingleton
    fun provideExpPres(): ExpPresenter {
        return ExpPresenter(expressionFragment)
    }

    //Presenters
    @Provides
    @ActivitySingleton
    fun provideMainPres(): MainPresenter {
        return MainPresenter()
    }

    @Provides
    @ActivitySingleton
    fun provideParamsPres(): ParamsPresenter {
        return ParamsPresenter(parametersFragment)
    }

    @Provides
    @ActivitySingleton
    fun provideExpListPres(): ExpListPresenter {
        return ExpListPresenter()
    }
}