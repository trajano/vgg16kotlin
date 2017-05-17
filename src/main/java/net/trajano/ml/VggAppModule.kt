package net.trajano.ml

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created by trajano on 2017-05-17.
 */
//@Module(includes = arrayOf(VggAppBindings::class))
//class VggAppModule {
//}
//
//@Module
//interface VggAppBindings {
//    @Binds
//    fun provideFileProvider(p: DefaultFileProvider): FileProvider
//}

//@Module
//interface VggAppModule {
//    @Binds
//    fun provideFileProvider(p: DefaultFileProvider): FileProvider
//}

//abstract class VggAppModule {
//    @Binds
//    abstract fun provideFileProvider(p: DefaultFileProvider): FileProvider
//}

@Module
class VggAppModule {

    @Provides
    fun provideFileProvider(): FileProvider {
        return DefaultFileProvider()
    }

//    @Provides
//    fun provideNetworkProvider(): NetworkProvider {
//        return VggNetworkProvider()
//    }

    //@Binds
    //fun provideNetworkProvider(p: VggNetworkProvider): NetworkProvider
}