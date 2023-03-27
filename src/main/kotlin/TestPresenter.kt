import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TestPresenter(
    testRepository: TestRepository = TestRepository(),
    presenterScope: CoroutineScope,
) {

    val data: StateFlow<ResultState<TestData>> = testRepository.getData().resultFlow.stateIn(
        scope = presenterScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ResultState.Loading
    )

}