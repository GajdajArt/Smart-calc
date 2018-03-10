package com.labralab.smartcalkulator

import android.app.Application
import android.content.Context
import com.labralab.smartcalkulator.dependencyInjection.AppComponents
import com.labralab.smartcalkulator.dependencyInjection.AppModule
import com.labralab.smartcalkulator.dependencyInjection.DaggerAppComponents
import com.labralab.smartcalkulator.dependencyInjection.PresentersModule
import io.realm.Realm

/**
 * Created by pc on 05.03.2018.
 */
class App : Application() {

    lateinit var context: Context

    companion object {
        lateinit var appComponents: AppComponents
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        appComponents = buildComponents()

        Realm.init(context)
    }

    private fun buildComponents(): AppComponents{
        return DaggerAppComponents.builder()
                .appModule(AppModule())
                .presentersModule(PresentersModule())
                .build()
    }
}