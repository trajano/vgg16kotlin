package net.trajano.ml

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class VggAppModule {

    @Singleton @Provides
    fun provideFileProvider(): FileProvider {
        return DefaultFileProvider()
    }

    @Singleton @Provides
    fun provideNetworkProvider(p: VggNetworkProvider): NetworkProvider {
        return p
    }
}