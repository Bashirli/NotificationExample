package app.bashirli.notificationexample.presentation.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created on 24.07.25.
 * @author Sahib Bashirli
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClickDelivery : () -> Unit,
    innerPadding : PaddingValues = PaddingValues()
){

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            modifier = modifier.align(Alignment.Center),
            onClick = {
                onClickDelivery.invoke()
            },
            content = {
                Text(
                    text = "Start delivery"
                )
            }
        )
    }

}