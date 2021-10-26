package com.challenge.suggestions.converters;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.challenge.suggestions.models.City;
import com.challenge.suggestions.views.*;

@Component
@Slf4j
public class CityConverter {
    
    public City toEntity(CityView cityCreateView) {
        City city = new City();
        city.setName(cityCreateView.getName());
        city.setLatitude(cityCreateView.getLatitude());
        city.setLongitude(cityCreateView.getLongitude());
        log.debug("--->>>CityConverter--->>>toEntity()--->>>city: {}",city);

        return city;
    }

    public CityView toView(City city) {
        CityView cityView = new CityView();
        cityView.setId(city.getId());
        cityView.setName(city.getName());
        cityView.setLatitude(city.getLatitude());
        cityView.setLongitude(city.getLongitude());
        log.debug("--->>>CityConverter--->>>toView()--->>>cityView: {}",cityView);

        return cityView;
    }
}
