package net.trajano.ml

import java.io.File
import javax.inject.Singleton

/**
 * Created by trajano on 2017-05-17.
 */
interface FileProvider {
    fun get(): File
}

@Singleton
class DefaultFileProvider : FileProvider {
    override fun get() : File {
        return File("vgg16.zip")
    }
}