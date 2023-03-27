import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class Presenter(
    testRepository: TestRepository = TestRepository(),
    viewModelScope: CoroutineScope,
) {

    val data: StateFlow<ResultState<TestData>> = testRepository.getData().resultFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ResultState.Loading
    )

}