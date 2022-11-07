package pt.isel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Compose for Desktop"
    ) {
        Column {
            Greetings("ISEL")
            Text("In Collumn")
            Row {
                Greetings("Super")
                Text("In Row")
            }
            val nrOfClicks: MutableState<Int> = remember { mutableStateOf(0) }
            ClickCounter(nrOfClicks) { nrOfClicks.value += 1 }
        }
    }
}

@Composable
fun Greetings(name: String) {
    Text("Hello $name")
}

@Composable
fun ClickCounter(clicks: State<Int>, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text("I've been clicked ${clicks.value} times")
    }
}
