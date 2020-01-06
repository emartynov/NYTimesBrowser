package nl.bijdorpstudio.nytimesbrowser

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import nl.bijdorpstudio.feature.search.DI
import nl.bijdorpstudio.lib.retrofit.ApiKey

class NYTimesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

        DI.navigator = NavigatorImpl(this)
        DI.apiKey = ApiKey.of("***REMOVED***")
    }
}
