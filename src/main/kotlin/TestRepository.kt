import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestRepository(
    private val testDataSource: TestDataSource = TestDataSource()
) {

    fun getErrorData(): Flow<TestData> = testDataSource.getErrorData()
        .map { TestData(it.id, it.text) }

    fun getRetryErrorData(): Flow<TestData> = testDataSource.getRetryErrorData()
        .map { TestData(it.id, it.text) }

    fun getSuccessData(): Flow<TestData> = testDataSource.getSuccessData()
        .map { TestData(it.id, it.text) }

}