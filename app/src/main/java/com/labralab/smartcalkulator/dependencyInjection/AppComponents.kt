package com.labralab.smartcalkulator.dependencyInjection

import com.labralab.calk.views.fragments.ExpressionListFragment
import com.labralab.smartcalkulator.presenters.ExpListPresenter
import com.labralab.smartcalkulator.presenters.ExpPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import com.labralab.smartcalkulator.presenters.adapters.ExpRecyclerViewHolder
import com.labralab.smartcalkulator.views.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class), (PresentersModule::class)])
interface AppComponents {

    fun inject(mainActivity: MainActivity)
    fun inject(expPresenter: ExpPresenter)
    fun inject(mainPresenter: MainPresenter)
    fun inject(expressionListFragment: ExpressionListFragment)
    fun inject(expListPresenter: ExpListPresenter)
    fun inject(holder: ExpRecyclerViewHolder)

}