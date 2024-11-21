package com.packt.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

@AutoConfigureWireMock(port = 0)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "PLAYERS_URI=http://localhost:${wiremock.server.port}",
        "ALBUMS_URI=http://localhost:${wiremock.server.port}"
})
class GatewayApplicationTests {

    @Autowired
    private WebTestClient webClient;

    @Test
    void playersRouteTest() {
        stubFor(get(urlEqualTo("/players"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                                [
                                    {
                                        "id": "325636",
                                        "jerseyNumber": 11,
                                        "name": "Alexia PUTELLAS",
                                        "position": "Midfielder",
                                        "dateOfBirth": "1994-02-04"
                                    },
                                    {
                                         "id": "396930",
                                         "jerseyNumber": 2,
                                         "name": "Ona BATLLE",
                                         "position": "Defender",
                                         "dateOfBirth": "1999-06-10"
                                     }
                                  ]""")));

		webClient.get().uri("/api/players").exchange()
				.expectStatus().isOk()
				.expectBody()
				.jsonPath("$[0].name").isEqualTo("Alexia PUTELLAS")
				.jsonPath("$[1].name").isEqualTo("Ona BATLLE");
	}

}
