package com.challenge.suggestions;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.CityRepository;

@DataJpaTest
@ActiveProfiles("test")
public class CityRepositoryTest {

    @Autowired
    private CityRepository cityRepository;

    @AfterEach
    void tearDown() {
        cityRepository.deleteAll();
    }

    // mvn clean ; mvn test -Dtest=CityRepositoryTest#itShouldCheckIfCityExist
    @Test
    public void itShouldCheckIfCityExist() {
        // given
        String name = "Pachuca";
        City city = new City(null, name, 333.333, 999.99);
        cityRepository.save(city);

        // when
        boolean expected = cityRepository.existName(name);

        // then
        assertThat(expected).isTrue();
    }

}
