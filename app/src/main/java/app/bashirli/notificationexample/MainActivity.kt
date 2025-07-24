package app.bashirli.notificationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import app.bashirli.notificationexample.presentation.screen.HomeScreen
import app.bashirli.notificationexample.presentation.ui.theme.NotificationExampleTheme
import app.bashirli.notificationexample.service.NotificationHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var notificationHandler : NotificationHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotificationExampleTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomeScreen(
                        onClickDelivery = notificationHandler::startLiveNotification,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}
