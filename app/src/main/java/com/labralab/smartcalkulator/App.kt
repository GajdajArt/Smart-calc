package com.labralab.smartcalkulator

import android.app.Application
import android.content.Context
import com.labralab.smartcalkulator.dependencyInjection.*
import io.realm.Realm


class App : Application() {

    lateinit var context: Context


    companion object {
        lateinit var appComponents: AppComponents
        var presenterComponents: PresenterComponents? = null


        //Adding local singletons
        fun plusPresenters(): PresenterComponents {

            presenterComponents?.let {
                return presenterComponents!!
            } ?: run {
                presenterComponents = appComponents.plusPresenterComponents(PresentersModule())
                return presenterComponents!!
            }
            return presenterComponents!!
        }


        //Removing local singletons
        fun removePresenterComponents(){
            presenterComponents = null
        }
    }

    override fun onCreate() {
        super.onCreate()


        //Creating global singletons
        context = applicationContext
        appComponents = buildComponents()


        //Starting Realm
        Realm.init(context)
    }

    private fun buildComponents(): AppComponents {
        return DaggerAppComponents.builder()
                .appModule(AppModule())
                .build()
    }


}