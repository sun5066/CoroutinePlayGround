import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TestRepository(
    private val testDataSource: TestDataSource = TestDataSource()
) {

    fun getData(): Flow<TestData> = testDataSource.getData()
        .map { TestData(it.id, it.text) }

}