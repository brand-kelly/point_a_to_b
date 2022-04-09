package data

import com.example.pointatob.R
import model.Service
import java.util.*

class Datasource {


    fun loadServices(selected : String?) : List<Service> {

//        if (selected == "Cheapest") {
//            var myIndex = 1
//        } else if (selected == "Fastest") {
//            var myIndex = 2
//        } else {
//            var myIndex = 0
//        }
//
//
//        var serviceList = listOf<Int>(R.string.serviceName1, R.string.serviceName2, R.string.serviceName3, R.string.serviceName4, R.string.serviceName5, R.string.serviceName6)
//        var priceList = listOf<Int>(R.integer.price1, R.integer.price2, R.integer.price3, R.integer.price4, R.integer.price5, R.integer.price6)
//        var timeList = listOf<Int>(R.integer.time1, R.integer.time2, R.integer.time3, R.integer.time4, R.integer.time5, R.integer.time6)
//
//
//        var sortedServiceList : MutableList<Int> = mutableListOf()
//        var sortedPriceList : MutableList<Int> = mutableListOf()
//        var sortedTimeList : MutableList<Int> = mutableListOf()
//
//        var temp = priceList[0]
//        var curr = 0
//
//        while (priceList.isNotEmpty()) {
//
//            for (i in priceList.indices) {
//                if (priceList[i] < temp) {
//                    curr = i
//                    temp = priceList[i]
//                }
//
//            }
//
//            println("Exited For loop")
//            println(curr)
//            println(priceList[curr])
//
//
//            sortedServiceList.add(serviceList[curr])
//            sortedPriceList.add(priceList[curr])
//            sortedTimeList.add(timeList[curr])
//            serviceList.drop(serviceList[curr])
//            priceList.drop(priceList[curr])
//            timeList.drop(timeList[curr])
//        }
//
//
//
//
//
//
//        return listOf<Service>(
//            Service(sortedServiceList[0], sortedTimeList[0], sortedPriceList[0]),
//            Service(sortedServiceList[1], sortedTimeList[1], sortedPriceList[1]),
//            Service(sortedServiceList[2], sortedTimeList[2], sortedPriceList[2]),
//            Service(sortedServiceList[3], sortedTimeList[3], sortedPriceList[3]),
//            Service(sortedServiceList[4], sortedTimeList[4], sortedPriceList[4]),
//            Service(sortedServiceList[5], sortedTimeList[5], sortedPriceList[5])
//        )


        val serviceList = listOf<Service>(
            Service(R.string.serviceName1, R.integer.time1, R.integer.price1),
            Service(R.string.serviceName2, R.integer.time2, R.integer.price2),
            Service(R.string.serviceName3, R.integer.time3, R.integer.price3),
            Service(R.string.serviceName4, R.integer.time4, R.integer.price4),
            Service(R.string.serviceName5, R.integer.time5, R.integer.price5),
            Service(R.string.serviceName6, R.integer.time6, R.integer.price6)
        )

        return serviceList
    }

}