package com.marciopaulo.testcontainer.demo;

import com.marciopaulo.testcontainer.demo.entity.PlayerHistoryEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest
class DemoApplication3Tests extends AbstractIntegrationTest {

	@Autowired
	private DynamoDbTemplate dynamoDbTemplate;

	@Test
	void shouldTestDynamoDB4() {
		var entity = new PlayerHistoryEntity();
		entity.setScore(1d);
		entity.setCreatedAt(Instant.now());
		entity.setUsername("MP4");
		entity.setGameId(UUID.randomUUID());

		dynamoDbTemplate.save(entity);

		var key = Key.builder().partitionValue("MP4").build();
		var condition = QueryConditional.keyEqualTo(key);
		var query = QueryEnhancedRequest.builder()
				.queryConditional(condition)
				.build();

		var history = dynamoDbTemplate.query(query,PlayerHistoryEntity.class);

		Assert.assertEquals(1, history.stream().count());
	}
}
