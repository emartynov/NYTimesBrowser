package nl.bijdorpstudio.nytimesbrowser

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import nl.bijdorpstudio.common.navigation.Navigator
import nl.bijdorpstudio.common.navigation.NavigatorProvider

class NYTimesApplication : Application(), NavigatorProvider {
    override val navigator: Navigator = NavigatorImpl(this)

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}
