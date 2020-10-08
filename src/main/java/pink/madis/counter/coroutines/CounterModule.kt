package pink.madis.counter.coroutines

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface UiModule {
  @Binds
  fun bindsView(counterView: CounterView): MviLite.View

  @Binds
  fun bindsModule(counterModel: CounterModel): MviLite.Model

  companion object {
    @Provides
    fun provideInputs(model: CounterModel) = model.inputs
  }
}