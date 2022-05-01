package data

import model.Service

class Datasource {


    fun loadServices(selected: String?, distance: String?) : List<Service> {

        //hardcode
        if (selected == "Cheapest") {
            val serviceList = listOf<Service>(
                Service("Lyft", 20, 14),
                Service("Uber", 17, 16))
            return serviceList
        } else if (selected == "Fastest") {
            val serviceList = listOf<Service>(
                Service("Lyft", 20, 14),
                Service("Uber", 17, 16))
            return serviceList
        } else {
            val serviceList = listOf<Service>(
                Service("Lyft", 20, 14),
                Service("Uber", 17, 16))
            return serviceList
        }
    }

}