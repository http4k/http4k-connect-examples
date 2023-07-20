import io.kotest.matchers.shouldBe
import org.http4k.aws.AwsSdkClient
import org.http4k.connect.amazon.dynamodb.FakeDynamoDb
import org.junit.Test
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeDefinition
import software.amazon.awssdk.services.dynamodb.model.KeySchemaElement
import software.amazon.awssdk.services.dynamodb.model.KeyType
import software.amazon.awssdk.services.dynamodb.model.ScalarAttributeType
import java.util.UUID

class CatsRepoTest {

    private val client = DynamoDbClient.builder()
        .httpClient(AwsSdkClient(FakeDynamoDb()))
        .credentialsProvider { AwsBasicCredentials.create("key_id", "secret_key") }
        .build()

    init {
        client.createTable {
            it.tableName("cats")
            it.attributeDefinitions(
                AttributeDefinition.builder().attributeName("id").attributeType(ScalarAttributeType.S).build()
            )
            it.keySchema(
                KeySchemaElement.builder().attributeName("id").keyType(KeyType.HASH).build()
            )
        }
    }

    private val testObj = CatsRepo(client, "cats")

    @Test
    fun `set and get`() {
        val cat = Cat(UUID.randomUUID(), "Kratos")

        testObj += cat
        testObj[cat.id] shouldBe cat
    }
}
