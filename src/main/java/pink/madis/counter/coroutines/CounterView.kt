package pink.madis.counter.coroutines

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.Channel
import pink.madis.counter.coroutines.MviLite.Input
import pink.madis.counter.coroutines.MviLite.Output.ViewState
import pink.madis.counter.coroutines.databinding.CounterBinding
import javax.inject.Inject

@ActivityScoped
class CounterView @Inject constructor(
    @ActivityContext context: Context,
    inputs: Channel<Input>
) : FrameLayout(context), MviLite.View {
  private val binding = CounterBinding.inflate(LayoutInflater.from(context), this, true)
  override val androidView: View
    get() = this

  init {
    binding.increment.setOnClickListener { inputs.offer(Input.INCREMENT) }
    binding.decrement.setOnClickListener { inputs.offer(Input.DECREMENT) }
    binding.sleep.setOnClickListener { inputs.offer(Input.SLEEP) }
  }

  override fun render(state: ViewState) {
    when (state) {
      is ViewState.Sleeping -> {
        binding.configureGroup.isVisible = false
        binding.sleeping.isVisible = true
        binding.sleeping.text = resources.getQuantityString(R.plurals.sleeping, state.countdown, state.countdown)
      }
      is ViewState.Configure -> {
        binding.configureGroup.isVisible = true
        binding.sleeping.isVisible = false
        binding.sleep.text = resources.getQuantityString(R.plurals.sleep, state.seconds, state.seconds)
      }
    }
  }

}