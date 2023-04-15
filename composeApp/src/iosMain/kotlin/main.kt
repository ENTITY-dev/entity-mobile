import androidx.compose.ui.window.ComposeUIViewController
import com.entity.app.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController { App() }
}
