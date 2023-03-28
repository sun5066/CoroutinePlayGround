import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>): Unit = runBlocking {
    val testPresenter = TestPresenter(presenterScope = this)

    launch {
        testPresenter.data.collectResultState(
            onLoading = { println("로딩중..") },
            onSuccess = { println("성공! $it") },
            onFail = { println("실패..!") }
        )
    }

    launch {
        testPresenter.zipData.collectResultState(
            onLoading = { println("로딩중..") },
            onSuccess = { println("성공! $it") },
            onFail = { println("실패..!") }
        )
    }
}

suspend fun <T> Flow<ResultState<T>>.collectResultState(
    onLoading: suspend () -> Unit = {},
    onSuccess: suspend (T) -> Unit = {},
    onFail: suspend (Throwable?) -> Unit = {}
): Unit = collect { state ->
    when (state) {
        is ResultState.Loading -> onLoading.invoke()
        is ResultState.Success -> onSuccess.invoke(state.data)
        is ResultState.Fail -> onFail.invoke(state.error)
    }
}

sealed interface ResultState<out T> {

    object Loading : ResultState<Nothing>

    data class Fail(val error: Throwable?) : ResultState<Nothing>

    data class Success<T>(val data: T) : ResultState<T>

}

val <T> Flow<T>.resultFlow: Flow<ResultState<T>>
    get() = map<T, ResultState<T>> { ResultState.Success(it) }
        .onStart { emit(ResultState.Loading) }
        .catch { emit(ResultState.Fail(it)) }