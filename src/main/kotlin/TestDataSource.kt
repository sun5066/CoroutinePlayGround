import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestDataSource {

    private var tryCnt = 0

    fun getErrorData(): Flow<TestDataResponse> = flow {
        emit(TestDataResponse(404, null))
    }

    fun getRetryErrorData(): Flow<TestDataResponse> = flow {
        val (id, text) = if (tryCnt++ < 3) {
            404 to null
        } else {
            200 to "성공쓰"
        }

        println("$tryCnt 번째 츄라이 츄라이")

        emit(TestDataResponse(id, text))
    }

    fun getSuccessData(): Flow<TestDataResponse> = flow {
        emit(TestDataResponse(200, "success"))
    }

}