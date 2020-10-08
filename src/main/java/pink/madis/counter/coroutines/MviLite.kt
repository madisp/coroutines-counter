package pink.madis.counter.coroutines

import androidx.annotation.StringRes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow

object MviLite {
  /**
   * An [Input] is some form of external input, e.g. a UI widget click.
   */
  enum class Input {
    INCREMENT, DECREMENT, SLEEP
  }

  /**
   * An [Output] is something that should be rendered to the user - either a side effect or a
   * view state. The former can be things like navigation, playing a sound or showing a snackbar,
   * the latter should be rendered to the user.
   */
  sealed class Output {
    sealed class Effect : Output() {
      data class Snack(@StringRes val message: Int) : Effect()
    }

    sealed class ViewState : Output() {
      data class Sleeping(val countdown: Int) : ViewState()
      data class Configure(val seconds: Int) : ViewState()
    }
  }

  /**
   * View owns an [android.view.View] and manages turning incoming [Output.ViewState] objects into
   * actual state of the underlying view via the [render] function.
   */
  interface View {
    val androidView: android.view.View
    fun render(state: Output.ViewState)
  }

  /**
   * Business logic lives in the model and it's a core part of the loop - it consumes [inputs] coming
   * from the view and emits [outputs] consisting of [Effect] and [ViewState] via a cold flow.
   */
  interface Model {
    val outputs: Flow<Output>
    val inputs: Channel<Input>
  }
}