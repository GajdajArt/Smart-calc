package com.labralab.smartcalkulator

import android.app.Application
import android.content.Context
import com.labralab.smartcalkulator.dependencyInjection.*
import io.realm.Realm

/**
 * Created by pc on 05.03.2018.
 */
class App : Application() {

    lateinit var context: Context


    companion object {
        lateinit var appComponents: AppComponents
        var presenterComponents: PresenterComponents? = null

        fun plusPresenters(): PresenterComponents {

            presenterComponents?.let {
                return presenterComponents!!
            } ?: run {
                presenterComponents = appComponents.plusPresentepComponents(PresentersModule())
                return presenterComponents!!
            }
            return presenterComponents!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        appComponents = buildComponents()

        Realm.init(context)
    }

    private fun buildComponents(): AppComponents {
        return DaggerAppComponents.builder()
                .appModule(AppModule())
                .build()
    }


}