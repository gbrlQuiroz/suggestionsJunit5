package com.challenge.suggestions.persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotNull;

import java.util.List;
import com.challenge.suggestions.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    // buscar por campo 'name' con LIKE
    List<City> findByNameContains(@NotNull String name);

}