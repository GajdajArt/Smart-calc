package com.labralab.smartcalkulator.dependencyInjection

import com.labralab.smartcalkulator.presenters.ExpListPresenter
import com.labralab.smartcalkulator.presenters.ExpPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import com.labralab.smartcalkulator.presenters.ParamsPresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentersModule {

    //Presenters
    @Provides
    @Singleton
    fun provideMainPres(): MainPresenter {
        return MainPresenter()
    }

    @Provides
    fun provideExpPres(): ExpPresenter {
        return ExpPresenter()
    }

    @Provides
    fun provideParamsPres(): ParamsPresenter {
        return ParamsPresenter()
    }

    @Provides
    fun provideExpListPres(): ExpListPresenter {
        return ExpListPresenter()
    }
}