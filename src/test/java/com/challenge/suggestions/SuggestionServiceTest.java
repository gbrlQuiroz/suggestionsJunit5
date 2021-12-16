package com.challenge.suggestions;

import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.suggestions.persistences.CityRepository;
import com.challenge.suggestions.services.impl.SuggestionServiceImpl;

@ExtendWith(MockitoExtension.class) // con esto se pueden eliminar las lineas 24, 28, 33-36
public class SuggestionServiceTest {

    @Mock
    private CityRepository cityRepository;
    private SuggestionServiceImpl underTest;
    // private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        // autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new SuggestionServiceImpl();
        underTest.setCityRepository(cityRepository);
    }

    // @AfterEach
    // void tearDown() throws Exception {
    // autoCloseable.close();
    // }

    // mvn clean ; mvn test -Dtest=SuggestionServiceTest#canGetSuggestions
    @Test
    void canGetSuggestions() {
        // when
        underTest.readSuggestions("pachuca", 0.0, 0.0);
        // then
        verify(cityRepository).findByNameContains("pachuca");
    }
}
