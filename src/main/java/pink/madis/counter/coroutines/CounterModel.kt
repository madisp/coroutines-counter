package pink.madis.counter.coroutines

import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import pink.madis.counter.coroutines.MviLite.Input
import pink.madis.counter.coroutines.MviLite.Output
import javax.inject.Inject

@ActivityScoped
class CounterModel @Inject constructor() : MviLite.Model {
  override val outputs: Flow<Output>
    get() = modelLoop()
  override val inputs = Channel<Input>()


  private fun modelLoop() = flow {
    var counter = 1
    while (true) {
      emit(Output.ViewState.Configure(counter))
      when (inputs.receive()) {
        Input.INCREMENT -> {
          emit(Output.ViewState.Configure(++counter))
        }

        Input.DECREMENT -> {
          if (counter > 1) {
            emit(Output.ViewState.Configure(--counter))
          } else {
            emit(Output.Effect.Snack(R.string.stay_positive))
          }
        }

        Input.SLEEP -> {
          for (countdown in counter downTo 1) {
            emit(Output.ViewState.Sleeping(countdown))
            delay(1_000L)
          }
        }
      }
    }
  }
}
