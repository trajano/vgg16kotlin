package net.trajano.ml

import dagger.Component
import dagger.Module
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.util.ModelSerializer
import javax.inject.Inject
import javax.inject.Singleton

interface NetworkProvider {
    fun get(): ComputationGraph
}

@Singleton
class VggNetworkProvider @Inject constructor(val fileProvider: FileProvider): NetworkProvider {

    override fun get(): ComputationGraph {
        return ModelSerializer.restoreComputationGraph(fileProvider.get())
    }
}