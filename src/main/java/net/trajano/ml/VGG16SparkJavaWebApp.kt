package net.trajano.ml


import dagger.Component
import org.datavec.image.loader.NativeImageLoader
import org.deeplearning4j.nn.graph.ComputationGraph
import org.deeplearning4j.nn.modelimport.keras.trainedmodels.TrainedModels
import org.deeplearning4j.util.ModelSerializer
import org.nd4j.linalg.api.ndarray.INDArray
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization
import org.nd4j.linalg.dataset.api.preprocessor.VGG16ImagePreProcessor
import org.scalatest.tags.Network

import javax.servlet.MultipartConfigElement
import java.io.File
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption

import spark.Spark.*
import javax.inject.Inject
import javax.inject.Singleton

//import org.nd4j.linalg.dataset.api.preprocessor.

/**
 * Created by tomhanlon on 1/25/17.
 */

@Singleton
@Component(modules = arrayOf(VggAppModule::class))
interface App {
    fun fileProvider() : FileProvider
    //fun networkProvider() : NetworkProvider
}

fun main(args: Array<String>) {

    /*
    Demonstration instructions
    This takes at least 4 minutes to load
    When loaded You will see jetty activity in the log
    Point browser at http://localhost:4567/VGGpredict
    And load an image into the form
     */

    // Load Neural Network from serialized format
    //File savedNetwork = new ClassPathResource("vgg16.zip").getFile();

    val app = DaggerApp.create()

    val savedNetwork = app.fileProvider().get()
    println(savedNetwork)
    //val vgg16 = app.networkProvider().get()
    //println(vgg16)
    val vgg16 = ModelSerializer.restoreComputationGraph(savedNetwork)


    // make upload directory to store loaded images
    val uploadDir = File("upload")
    uploadDir.mkdir() // create the upload directory if it doesn't exist


    // form to allow user to choose image to upload
    val form = """
        |<form method='post' action='getPredictions' enctype='multipart/form-data'>
        |<input type='file' name='uploaded_file'>
        |<button>Upload picture</button>
        |</form>
        """.trimMargin()

    // spark java settings to display form or results
    staticFiles.location("/Users/tomhanlon/SkyMind/webcontent") // Static files
    get("/hello") { req, res -> "Hello World" }
    get("VGGpredict") { req, res -> form }
    //post("getPredictions",(req, res) -> "GET RESULTS");

    post("/getPredictions") { req, res ->
        val tempFile = Files.createTempFile(uploadDir.toPath(), "", "")
        req.attribute("org.eclipse.jetty.multipartConfig", MultipartConfigElement("/temp"))

        req.raw().getPart("uploaded_file").inputStream.use { // getPart needs to use same "name" as input field in form
            input ->
            Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING)
        }


        val file = tempFile.toFile()

        // define native image loaders
        val loader = NativeImageLoader(224, 224, 3)
        val image = loader.asMatrix(file)

        // Scale image in same manner as network was trained on
        val scaler = VGG16ImagePreProcessor()
        scaler.transform(image)
        file.delete()
        val output = vgg16.output(false, image)
        // just added
        //Map<String, INDArray> mine = vgg16.feedForward();
        //System.out.println(mine);
        // just added
        val predictions = TrainedModels.VGG16.decodePredictions(output[0])

        "<h1> '" + predictions + "' </h1>" +
                "Would you like to try another" +
                form

        //return "<h1>Your image is: '" + tempFile.getName(1).toString() + "' </h1>";


    }

}
