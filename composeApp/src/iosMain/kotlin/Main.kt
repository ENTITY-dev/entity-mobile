import androidx.compose.ui.window.ComposeUIViewController
import com.entity.app.ui.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
