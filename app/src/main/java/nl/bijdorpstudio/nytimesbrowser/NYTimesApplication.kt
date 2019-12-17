package nl.bijdorpstudio.nytimesbrowser

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class NYTimesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
