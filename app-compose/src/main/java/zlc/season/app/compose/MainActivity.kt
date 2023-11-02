package zlc.season.app.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import zlc.season.app.compose.ui.theme.YashaTheme
import zlc.season.yasha.compose.LinearList
import zlc.season.yasha.compose.swiperefresh.SwipeRefresh
import zlc.season.yasha.compose.swiperefresh.rememberSwipeRefreshState

class MainActivity : ComponentActivity() {
    private val demoDataSource by lazy { DemoDataSource(lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YashaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val dataList = demoDataSource.dataFlow.collectAsState(emptyList())
                    val isRefreshing = demoDataSource.isRefreshing.collectAsState()
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing.value),
                        onRefresh = { demoDataSource.invalidate(true) },
                    ) {
                        LinearList(modifier = Modifier.fillMaxSize(), data = dataList.value) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .padding(10.dp)
                                    .background(Color.Gray, RoundedCornerShape(8.dp)),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(text = "Item $it")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )

    Button(onClick = { /*TODO*/ }) {

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YashaTheme {
        Greeting("Android")
    }
}