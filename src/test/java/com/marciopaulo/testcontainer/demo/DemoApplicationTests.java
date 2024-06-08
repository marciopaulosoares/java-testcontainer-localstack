package com.marciopaulo.testcontainer.demo;

import com.marciopaulo.testcontainer.demo.entity.PlayerHistoryEntity;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import java.io.File;
import java.time.Instant;
import java.util.UUID;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

@SpringBootTest
class DemoApplicationTests extends AbstractIntegrationTest {

	@Autowired
	private DynamoDbTemplate dynamoDbTemplate;

	@Test
	void shouldTestDynamoDB() {
		var entity = new PlayerHistoryEntity();
		entity.setScore(1d);
		entity.setCreatedAt(Instant.now());
		entity.setUsername("MP");
		entity.setGameId(UUID.randomUUID());

		dynamoDbTemplate.save(entity);

		var key = Key.builder().partitionValue("MP").build();
		var condition = QueryConditional.keyEqualTo(key);
		var query = QueryEnhancedRequest.builder()
				.queryConditional(condition)
				.build();

		var history = dynamoDbTemplate.query(query,PlayerHistoryEntity.class);

		Assert.assertEquals(1, history.stream().count());
	}

}
