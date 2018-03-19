package com.labralab.smartcalkulator.dependencyInjection

import android.content.Context
import com.labralab.calk.repository.Repository
import com.labralab.calk.views.fragments.ExpressionListFragment
import com.labralab.calk.views.fragments.ParametersFragment
import com.labralab.smartcalkulator.presenters.ExpPresenter
import com.labralab.smartcalkulator.presenters.MainPresenter
import com.labralab.smartcalkulator.presenters.ParamsPresenter
import com.labralab.smartcalkulator.views.MainActivity
import com.labralab.smartcalkulator.views.fragments.ExpressionFragment
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton


@Module
class AppModule{


    @Provides
    @Singleton
    fun provideRepository(): Repository {
        return Repository()
    }

    @Provides
    fun provideRealm(): Realm {
        return Realm.getDefaultInstance()
    }

}