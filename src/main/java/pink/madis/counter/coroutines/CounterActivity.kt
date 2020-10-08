package pink.madis.counter.coroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class CounterActivity : ComponentActivity() {
  @Inject internal lateinit var view: MviLite.View
  @Inject internal lateinit var model: MviLite.Model

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(view.androidView)

    lifecycleScope.launch(Dispatchers.Main) {
      model.outputs.collect {
        when (it) {
          is MviLite.Output.Effect.Snack -> {
            Snackbar.make(view.androidView, it.message, Snackbar.LENGTH_SHORT).show()
          }
          is MviLite.Output.ViewState -> {
            view.render(it)
          }
        }
      }
    }
  }
}