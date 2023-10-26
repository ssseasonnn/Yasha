package zlc.season.yasha.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LinearList(modifier: Modifier = Modifier, data: List<ComposeItem>) {
    LazyColumn(modifier) {
        items(data) {

        }
    }
}