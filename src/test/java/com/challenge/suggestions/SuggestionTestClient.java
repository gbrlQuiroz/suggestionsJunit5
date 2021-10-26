package com.challenge.suggestions;


import org.junit.runner.RunWith;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// import org.junit.jupiter.api.Test; //esta es la original, no corre en terminal
import org.junit.Test;  //esta si corre en la terminal

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.assertj.core.api.Assertions.assertThat;

import com.challenge.suggestions.views.SuggestionView;


@RunWith(SpringJUnit4ClassRunner.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
// We create a `@SpringBootTest`, starting an actual server on a `RANDOM_PORT`
public class SuggestionTestClient {

    // Spring Boot will create a `WebTestClient` for you,
    // already configure and ready to issue requests against "localhost:RANDOM_PORT"
    @Autowired
    private WebTestClient webTestClient;

    /**
     * Probar endpoint con un criterio exacto
     * 
     * mvn clean ; mvn test -Dtest=SuggestionsTestClient#getSuggestionsOnlyOne
     */
    @Test
    public void getSuggestionsOnlyOne() {

        webTestClient
                .get()
                .uri("/suggestions?q=Ajax")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(SuggestionView.class).value(suggestion -> {
                    assertThat(suggestion.getSuggestions().toString())
                    .isEqualTo("[CityView(name=Ajax, latitude=43.85012, longitude=-79.03288, score=1.0)]");
                });
    }
}
