import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class TestPresenter(
    testRepository: TestRepository = TestRepository(),
    presenterScope: CoroutineScope,
) {

    val data: StateFlow<ResultState<TestData>> = testRepository.getErrorData().resultFlow.stateIn(
        scope = presenterScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ResultState.Loading
    )

    private var tryCnt = 0

    private val retryData = testRepository.getRetryErrorData().retry { tryCnt++ < 3 }

    val zipData: StateFlow<ResultState<TestZipData>> =
        testRepository.getSuccessData().zip(retryData) { testData1, testData2 ->
            TestZipData(testData1, testData2)
        }.resultFlow.stateIn(
            scope = presenterScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ResultState.Loading
        )

}