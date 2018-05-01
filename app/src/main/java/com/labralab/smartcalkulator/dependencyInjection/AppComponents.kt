package com.labralab.smartcalkulator.dependencyInjection

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(AppModule::class)])
interface AppComponents {

    //for local singletons
    fun plusPresenterComponents(presentersModule: PresentersModule): PresenterComponents

}