package com.labralab.smartcalkulator.dependencyInjection

import com.labralab.calk.views.fragments.ExpressionListFragment
import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.presenters.ExpListPresenter
import com.labralab.smartcalkulator.presenters.ExpPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import com.labralab.smartcalkulator.presenters.adapters.ExpListAdapter
import com.labralab.smartcalkulator.presenters.adapters.ExpRecyclerViewHolder
import com.labralab.smartcalkulator.views.MainActivity
import com.labralab.smartcalkulator.views.dialogs.NewSimpleDialog
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment
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
    fun inject(adapter: ExpListAdapter)
    fun inject(expressionFragment: ExpressionFragment)
    fun inject(newSimpleDialog: NewSimpleDialog)
    fun inject(parametersFragment: ParametersFragment)

}