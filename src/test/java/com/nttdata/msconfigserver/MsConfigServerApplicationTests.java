package com.nttdata.msconfigserver;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClient;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
		properties = "spring.cloud.config.server.native.search-locations=classpath:/test-config-repo")
class MsConfigServerApplicationTests {

	@Value("${local.server.port}")
	private int port;

	@Test
	void servesConfigurationForAServiceFromTheNativeRepository() {
		RestClient client = RestClient.create("http://localhost:" + port);

		String body = client.get()
				.uri("/ms-customer/default")
				.retrieve()
				.body(String.class);

		assertThat(body).contains("\"name\":\"ms-customer\"");
		assertThat(body).contains("demo.message");
		assertThat(body).contains("hola desde config server");
	}

}
