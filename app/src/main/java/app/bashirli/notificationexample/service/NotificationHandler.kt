package app.bashirli.notificationexample.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.graphics.Color
import androidx.compose.ui.util.fastForEach
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Action.*
import androidx.core.graphics.drawable.IconCompat
import androidx.core.graphics.toColorInt
import app.bashirli.notificationexample.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created on 24.07.25.
 * @author Sahib Bashirli
 */
class NotificationHandler @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager
) {

    init {
        notificationManager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                IMPORTANCE_DEFAULT
            )
        )
    }

    private enum class DeliveryStatusEnum {
        START,
        STATION_A,
        STATION_B,
        END
    }

    private fun buildBaseNotification(
        deliveryStatus: DeliveryStatusEnum,
        progress : Int
    ): NotificationCompat.Builder {
        val notificationBuilder = NotificationCompat
            .Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setShortCriticalText("Delivery")
            .setOngoing(true)
            .setRequestPromotedOngoing(true)

        return when (deliveryStatus) {
            DeliveryStatusEnum.START -> notificationBuilder
                .setContentTitle("Courier is going to take packages.")
                .setContentText("Direction : Warehouse")
                .setStyle(
                    buildBaseProgressStyle(deliveryStatus).setProgress(progress)
                )

            DeliveryStatusEnum.STATION_A -> notificationBuilder
                .setContentTitle("Courier is on the way.")
                .setContentText("Direction : Station A")
                .setStyle(
                    buildBaseProgressStyle(deliveryStatus).setProgress(progress)
                )
            DeliveryStatusEnum.STATION_B -> notificationBuilder
                .setContentTitle("Courier is on the way.")
                .setContentText("Direction : Station B")
                .setStyle(
                    buildBaseProgressStyle(deliveryStatus)
                        .setProgress(progress)
                )
            DeliveryStatusEnum.END -> notificationBuilder
                .setContentTitle("Orders has been delivered.")
                .setStyle(
                    buildBaseProgressStyle(deliveryStatus)
                        .setProgress(progress)
                )
                .addAction(
                    Builder(
                        null,
                        "Rate delivery",
                        null
                    ).build()
                )
        }
    }

    private fun buildBaseProgressStyle(deliveryStatus: DeliveryStatusEnum): NotificationCompat.ProgressStyle {
        val pointColor = "#32a852".toColorInt()
        val progressStyle = NotificationCompat.ProgressStyle()
            .setProgressStartIcon(
                IconCompat.createWithResource(
                    context, R.drawable.bycicle
                )
            )
            .setProgressTrackerIcon(
                IconCompat.createWithResource(
                    context, R.drawable.bicycle
                )
            )
            .setProgressEndIcon(
                IconCompat.createWithResource(
                    context, R.drawable.bycicle
                )
            )
            .setProgressSegments(
                listOf(
                    NotificationCompat.ProgressStyle.Segment(100).setColor(Color.GREEN),
                )
            )
            .setProgressPoints(
                listOf(
                    NotificationCompat.ProgressStyle.Point(25).setColor(pointColor),
                    NotificationCompat.ProgressStyle.Point(50).setColor(pointColor),
                    NotificationCompat.ProgressStyle.Point(75).setColor(pointColor),
                    NotificationCompat.ProgressStyle.Point(100).setColor(pointColor)
                )
            )
        when (deliveryStatus) {
            DeliveryStatusEnum.START -> progressStyle.setProgressPoints(
                listOf(
                    NotificationCompat.ProgressStyle.Point(25).setColor(pointColor)
                )
            )
            DeliveryStatusEnum.STATION_A -> progressStyle.setProgressPoints(
                listOf(
                    NotificationCompat.ProgressStyle.Point(25).setColor(pointColor),
                    NotificationCompat.ProgressStyle.Point(50).setColor(pointColor)
                )
            )
            DeliveryStatusEnum.STATION_B ->  progressStyle.setProgressPoints(
                listOf(
                    NotificationCompat.ProgressStyle.Point(25).setColor(pointColor),
                    NotificationCompat.ProgressStyle.Point(50).setColor(pointColor),
                    NotificationCompat.ProgressStyle.Point(75).setColor(pointColor)
                )
            )
            DeliveryStatusEnum.END -> Unit
        }
        return progressStyle
    }


    fun startLiveNotification() {
        var progress = 0

        //These lanes just for simulate delivery.
        CoroutineScope(Dispatchers.IO).launch {
            DeliveryStatusEnum.entries.fastForEach { deliveryStatus ->
                while(
                    when(deliveryStatus){
                        DeliveryStatusEnum.START -> progress<25
                        DeliveryStatusEnum.STATION_A -> progress<50
                        DeliveryStatusEnum.STATION_B -> progress<75
                        DeliveryStatusEnum.END -> progress<=100
                    }
                ){
                    notificationManager.notify(NOTIFICATION_ID, buildBaseNotification(deliveryStatus,progress).build())
                    delay(if(progress%25==0) 1000 else 200)
                    progress++
                }
            }
        }
    }

    companion object Companion {
        private const val CHANNEL_ID = "channel_0"
        private const val CHANNEL_NAME = "live_channel"
        private const val NOTIFICATION_ID = 1234
    }

}