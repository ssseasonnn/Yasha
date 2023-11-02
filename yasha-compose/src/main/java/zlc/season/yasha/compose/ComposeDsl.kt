package zlc.season.yasha.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LinearList(
    modifier: Modifier = Modifier,
    data: List<ComposeItem>,
    renderItem: @Composable (ComposeItem) -> Unit
) {
    LazyColumn(modifier) {
        items(data) {
            if (it is ComposeStateItem) {
                ComposeStateItemUI(it)
            } else {
                renderItem(it)
            }
        }
    }
}