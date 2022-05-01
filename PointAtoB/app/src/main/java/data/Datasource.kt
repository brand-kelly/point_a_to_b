package data

import com.example.pointatob.R
import model.Service
import java.util.*

class Datasource {


    fun loadServices(selected: String?, distance: String?) : List<Service> {

        //hardcode
        if (selected == "Cheapest") {
            val serviceList = listOf<Service>(
                Service(R.string.serviceName5, R.integer.time5, R.integer.price5),
                Service(R.string.serviceName3, R.integer.time3, R.integer.price3),
                Service(R.string.serviceName4, R.integer.time4, R.integer.price4),
                Service(R.string.serviceName1, R.integer.time1, R.integer.price1),
                Service(R.string.serviceName2, R.integer.time2, R.integer.price2),
                Service(R.string.serviceName6, R.integer.time6, R.integer.price6))
            return serviceList
        } else if (selected == "Fastest") {
            val serviceList = listOf<Service>(
                Service(R.string.serviceName1, R.integer.time1, R.integer.price1),
                Service(R.string.serviceName6, R.integer.time6, R.integer.price6),
                Service(R.string.serviceName2, R.integer.time2, R.integer.price2),
                Service(R.string.serviceName4, R.integer.time4, R.integer.price4),
                Service(R.string.serviceName3, R.integer.time3, R.integer.price3),
                Service(R.string.serviceName5, R.integer.time5, R.integer.price5))
            return serviceList
        } else {
            val serviceList = listOf<Service>(
                Service(R.string.serviceName1, R.integer.time1, R.integer.price1),
                Service(R.string.serviceName2, R.integer.time2, R.integer.price2),
                Service(R.string.serviceName3, R.integer.time3, R.integer.price3),
                Service(R.string.serviceName4, R.integer.time4, R.integer.price4),
                Service(R.string.serviceName5, R.integer.time5, R.integer.price5),
                Service(R.string.serviceName6, R.integer.time6, R.integer.price6))
            return serviceList
        }
    }

}