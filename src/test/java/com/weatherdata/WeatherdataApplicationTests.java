package com.weatherdata;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class WeatherdataApplicationTests {

	@Autowired
	WeatherdataController weatherdataController;

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void contextLoads() {
		assertThat(weatherdataController).isNotNull();
	}

	@Test
	public void firstPageShouldStartWith() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/weatherdata/",
				String.class)).startsWith("[{\"id\":");
	}
}
