package pokemon.code.challenge.app.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import pokemon.code.challenge.app.R
import pokemon.code.challenge.app.data.remote.repository.LocationRemoteRepository
import pokemon.code.challenge.app.util.APP_TAG
import pokemon.code.challenge.app.util.getUser
import pokemon.code.challenge.app.util.toDateFormat
import java.util.Date
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var repository: LocationRemoteRepository

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()

        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val channelId = getString(R.string.notification_channel_id)
        val currentLocation = getString(R.string.current_location)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(currentLocation)
            .setSmallIcon(R.drawable.notification_icon)
            .setOngoing(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)  {
            val notificationChannel = NotificationChannel(channelId, currentLocation, NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(notificationChannel)
            notification.setChannelId(channelId)
        }

        locationClient
            .getLocationUpdates(120000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val lat = location.latitude
                val long = location.longitude
                val date = Date().toDateFormat()
                val text = getString(R.string.current_location_data, date, lat.toString(), long.toString())
                val updatedNotification = notification.setContentText(text)
                notificationManager.notify(1, updatedNotification.build())
                val user = this.getUser()
                Log.i(APP_TAG, "User: $user - text: $text")
                if (user.isNotEmpty()) {
                    repository.sendLocation(user, date, lat, long)
                }
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()

        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}