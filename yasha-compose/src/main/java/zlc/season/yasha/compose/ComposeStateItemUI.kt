package zlc.season.yasha.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import zlc.season.sange.datasource.FetchState

@Composable
fun ComposeStateItemUI(composeStateItem: ComposeStateItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        if (composeStateItem.state == FetchState.Fetching) {
            CircularProgressIndicator()
        } else if (composeStateItem.state == FetchState.FetchingError) {
            Button(
                modifier = Modifier,
                onClick = { composeStateItem.retry.invoke() }
            ) {
                Text(text = "Retry")
            }
        }
    }
}

