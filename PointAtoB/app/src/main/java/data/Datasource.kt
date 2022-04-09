package data

import com.example.pointatob.R
import model.Service

class Datasource {
    fun loadServices() : List<Service>{

        val service1Array = arrayOf(R.string.serviceName1, R.integer.price1, R.integer.time1)
        val service2Array = arrayOf(R.string.serviceName2, R.integer.price2, R.integer.time2)
        val service3Array = arrayOf(R.string.serviceName3, R.integer.price3, R.integer.time3)
        val service4Array = arrayOf(R.string.serviceName4, R.integer.price4, R.integer.time4)
        val service5Array = arrayOf(R.string.serviceName5, R.integer.price5, R.integer.time5)
        val service6Array = arrayOf(R.string.serviceName6, R.integer.price6, R.integer.time6)

//        val mixedArray : Array<Array> = arrayOf(service1Array, service2Array, service3Array, service4Array, service5Array, service6Array)

        return listOf<Service>(
            Service(R.string.serviceName1),
            Service(R.string.serviceName2),
            Service(R.string.serviceName3),
            Service(R.string.serviceName4),
            Service(R.string.serviceName5),
            Service(R.string.serviceName6)
        )
    }
}