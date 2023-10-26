package zlc.season.yasha.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import zlc.season.sange.datasource.FetchState

@Composable
fun ComposeStateItemUI(composeStateItem: ComposeStateItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (composeStateItem.state == FetchState.Fetching) {
            CircularProgressIndicator()
        } else if (composeStateItem.state == FetchState.FetchingError) {
            Button(modifier = Modifier.align(Alignment.Center)) {

            }

        }
    }
}

