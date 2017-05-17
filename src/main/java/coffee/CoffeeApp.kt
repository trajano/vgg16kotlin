package coffee

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(DripCoffeeModule::class))
interface Coffee {
    fun maker(): CoffeeMaker
}

fun main(args: Array<String>) {
    val coffee = DaggerCoffee.builder().build()
    coffee.maker().brew()
}
