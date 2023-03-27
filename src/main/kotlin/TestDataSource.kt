import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestDataSource {

    fun getData(): Flow<TestDataResponse> = flow {
        emit(TestDataResponse(1, null))
    }

}