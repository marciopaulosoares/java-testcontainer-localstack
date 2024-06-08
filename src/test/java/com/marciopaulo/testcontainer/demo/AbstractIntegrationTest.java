package com.marciopaulo.testcontainer.demo;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.io.File;

public abstract class AbstractIntegrationTest{

    @ClassRule
    static DockerComposeContainer dockerComposeContainer = new DockerComposeContainer(new File("src/test/resources/docker-compose.yaml"))
            .withLocalCompose(true)
            .waitingFor("localstack_1", Wait.forLogMessage(".*### END CREATE TABLES ###.*\\n", 1));

    @BeforeAll
    static void startContainers(){
        dockerComposeContainer.start();
        System.out.println("start");
    }

    @AfterAll
    static void stopContainers(){
        //dockerComposeContainer.stop();
        System.out.println("AfterAll");
    }
}
