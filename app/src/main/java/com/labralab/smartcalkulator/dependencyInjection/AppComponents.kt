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
@Component(modules = [(AppModule::class)])
interface AppComponents {

    fun plusPresenterComponents(presentersModule: PresentersModule): PresenterComponents

}