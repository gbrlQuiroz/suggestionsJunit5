package com.challenge.suggestions.persistences;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import javax.validation.constraints.NotNull;

import java.util.List;
import com.challenge.suggestions.models.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    // buscar por campo 'name' con LIKE
    List<City> findByNameContains(@NotNull String name);

    // buscar por campo 'name'
    City findByName(@NotNull String name);

    // revisa si existe un registro por campo name
    @Query(value="SELECT CASE WHEN COUNT(id) > 0 THEN TRUE ELSE FALSE END FROM cities c WHERE c.name= :name", nativeQuery = true)
    Boolean existName(@NotNull String name);

}