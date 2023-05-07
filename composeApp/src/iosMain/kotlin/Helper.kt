import com.entity.app.di.appModule
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.core.context.startKoin

fun initKoin() {
  startKoin {
    modules(appModule())
  }
}

fun initLog() {
  Napier.base(DebugAntilog())
}