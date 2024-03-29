package data

import com.example.pointatob.R
import model.Service
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*
import kotlin.math.roundToInt
import kotlin.random.Random

class Datasource {


    fun loadServices(selected: String?, distance: Double) : List<Service> {
        val rightNow = Calendar.getInstance()
        val currentHour: Int = rightNow.get(Calendar.HOUR_OF_DAY)
        var surge = 1.00
        if (currentHour in 17..23) {
            surge = 2.00
        }
        val timeToRide = ((distance / 25) * 60).roundToInt()
        val timeToScooter = ((distance / 12) * 60).roundToInt()
        val timeToLyft = Random.nextInt(2, 8) + timeToRide
        val timeToUber = Random.nextInt(1, 6) + timeToRide
        val timeToUberX = Random.nextInt(4, 10) + timeToRide
        val timeToWalk = ((distance / 4) * 60).roundToInt()


        val randomUberVal = Random.nextDouble(1.00, 1.20)
        val randomLyftVal = Random.nextDouble(1.00, 1.20)
        val randomLimeVal = Random.nextDouble(0.45, 0.55)
        val randomBirdVal = Random.nextDouble(0.45, 0.55)

        val uberXPrice = (2 + distance * 2.50 * surge)//.roundToInt()
        val uberPrice = (2 + distance * 2.00 * randomUberVal * surge)//.roundToInt()
        val lyftPrice = (2 + distance * 2.00 * randomLyftVal * surge)//.roundToInt()

        var limePrice = 0.00
        var birdPrice = 0.00
        if (distance < 5) {
            limePrice = (1 + randomLimeVal * timeToScooter)
            birdPrice = (1 + randomBirdVal * timeToScooter)
        }


        val serviceList = mutableListOf(
//            Service("Lyft", lyftPrice, timeToLyft),
//            Service("Uber", uberPrice, timeToUber),
//            Service("Uber X", uberXPrice, timeToUberX)
            Service("Lyft", timeToLyft, twoDecimal(lyftPrice), (lyftPrice + timeToLyft) / 2, R.drawable.lyft, "https://play.google.com/store/apps/details?id=me.lyft.android&hl=en_US&gl=US"),
            Service("Uber", timeToUber, twoDecimal(uberPrice), (uberPrice + timeToUber) / 2, R.drawable.uber, "https://play.google.com/store/apps/details?id=com.ubercab&hl=en_US&gl=US"),
            Service("Uber X", timeToUberX, twoDecimal(uberXPrice), (uberXPrice + timeToUberX) / 2, R.drawable.uber, "https://play.google.com/store/apps/details?id=com.ubercab&hl=en_US&gl=US")
        )
        if (distance < 5) {
            serviceList.add(Service("Walking", timeToWalk, twoDecimal(0.00), timeToWalk / 2.00, 0, ""))
            serviceList.add(Service("Bird", timeToScooter, twoDecimal(birdPrice), (birdPrice + timeToScooter) / 2, R.drawable.bird, "https://play.google.com/store/apps/details?id=co.bird.android&hl=en_US&gl=US"))
            serviceList.add(Service("Lime", timeToScooter, twoDecimal(limePrice), (limePrice + timeToScooter) / 2, R.drawable.lime, "https://play.google.com/store/apps/details?id=com.limebike&hl=en_US&gl=US"))
        }

        //hardcode
        return if (selected == "Cheapest") {
            serviceList.sortedWith(compareBy { it.price })
        } else if (selected == "Fastest") {
            serviceList.sortedWith(compareBy { it.time })
        } else {
            serviceList.sortedWith(compareBy {it.avg})
        }

    }
    fun twoDecimal(d: Double) : BigDecimal{
        return BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN)
    }

}