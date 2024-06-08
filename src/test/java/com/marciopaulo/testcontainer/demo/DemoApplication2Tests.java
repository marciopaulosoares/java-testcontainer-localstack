package com.marciopaulo.testcontainer.demo;

import com.marciopaulo.testcontainer.demo.entity.PlayerHistoryEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.DockerComposeContainer;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.io.File;
import java.time.Instant;
import java.util.UUID;

@SpringBootTest
class DemoApplication2Tests extends AbstractIntegrationTest {

	@Autowired
	private DynamoDbTemplate dynamoDbTemplate;

	@Test
	void shouldTestDynamoDB2() {
		var entity = new PlayerHistoryEntity();
		entity.setScore(1d);
		entity.setCreatedAt(Instant.now());
		entity.setUsername("MP2");
		entity.setGameId(UUID.randomUUID());

		dynamoDbTemplate.save(entity);

		var key = Key.builder().partitionValue("MP2").build();
		var condition = QueryConditional.keyEqualTo(key);
		var query = QueryEnhancedRequest.builder()
				.queryConditional(condition)
				.build();

		var history = dynamoDbTemplate.query(query,PlayerHistoryEntity.class);

		Assert.assertEquals(1, history.stream().count());
	}

	@Test
	void shouldTestDynamoDB3() {
		var entity = new PlayerHistoryEntity();
		entity.setScore(1d);
		entity.setCreatedAt(Instant.now());
		entity.setUsername("MP3");
		entity.setGameId(UUID.randomUUID());

		dynamoDbTemplate.save(entity);

		var key = Key.builder().partitionValue("MP3").build();
		var condition = QueryConditional.keyEqualTo(key);
		var query = QueryEnhancedRequest.builder()
				.queryConditional(condition)
				.build();

		var history = dynamoDbTemplate.query(query,PlayerHistoryEntity.class);

		Assert.assertEquals(1, history.stream().count());
	}

}
