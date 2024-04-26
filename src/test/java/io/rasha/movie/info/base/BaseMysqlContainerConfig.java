package io.rasha.movie.info.base;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class BaseMysqlContainerConfig {

  private static final MySQLContainer MYSQL_CONTAINER;

  static {
    MYSQL_CONTAINER = new MySQLContainer(DockerImageName.parse("mysql:5.7"));
    MYSQL_CONTAINER.withDatabaseName("movie-inf-mysql");
    MYSQL_CONTAINER.withUsername("test");
    MYSQL_CONTAINER.withPassword("test");
    MYSQL_CONTAINER.withCommand("--max_connections=1000");
    MYSQL_CONTAINER.start();
  }

  @DynamicPropertySource
  static void overrideTestProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
    registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
  }
}
