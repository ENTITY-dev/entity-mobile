import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.entity.app.ui.App
import com.entity.app.utils.generateImageLoader
import com.entity.app.utils.initSentry
import com.seiko.imageloader.LocalImageLoader
import io.ktor.client.HttpClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
  return ComposeUIViewController {
    val holder = HttpClientHolder()
    initSentry(null)
    CompositionLocalProvider(
      LocalImageLoader provides generateImageLoader(holder.httpClient)
    ) {
      App()
    }
  }
}

class HttpClientHolder: KoinComponent {
  val httpClient by inject<HttpClient>()
}
