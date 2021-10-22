package com.challenge.suggestions.converters;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.challenge.suggestions.models.City;
import com.challenge.suggestions.views.*;

@Component
@Slf4j
public class CityConverter {
    /**
     * Convierte el registro a view
     * dependiendo de la distancia recibida en metros, se evalua en rangos de cientos de kilometros
     * 100km -> score=1.0,
     * 200km -> score=0.9,
     * 300km -> score=0.8,
     * ... 
     * 900km -> score=0.1
     * 
     * @param city el registro que será procesado
     * @param dist la distancia que nos servira para evaluar y asi asignar el valor del campo 'score'
     * @return
     */
    public CityView toView(City city, Double dist) {
        CityView cityView = new CityView();
        cityView.setName(city.getName());
        cityView.setLatitude(city.getLatitude().toString());
        cityView.setLongitude(city.getLongitude().toString());

        // Se convierte a Integer para compararlo en los 'if' más fácil
        Integer valor = dist.intValue();
        
        if (valor < -1) {
            cityView.setScore(1.0);
            return cityView;
        }
        if (valor < 100000) {
            cityView.setScore(1.0);
            return cityView;
        }
        if (valor > 100000 && valor < 200000) {
            cityView.setScore(0.9);
            return cityView;
        }
        if (valor > 200000 && valor < 300000) {
            cityView.setScore(0.8);
            return cityView;
        }
        if (valor > 300000 && valor < 400000) {
            cityView.setScore(0.7);
            return cityView;
        }
        if (valor > 400000 && valor < 500000) {
            cityView.setScore(0.6);
            return cityView;
        }
        if (valor > 500000 && valor < 600000) {
            cityView.setScore(0.5);
            return cityView;
        }
        if (valor > 600000 && valor < 700000) {
            cityView.setScore(0.4);
            return cityView;
        }
        if (valor > 700000 && valor < 800000) {
            cityView.setScore(0.3);
            return cityView;
        }
        if (valor > 800000 && valor < 900000) {
            cityView.setScore(0.2);
            return cityView;
        }
        if (valor > 900000) {
            cityView.setScore(0.1);
            return cityView;
        }

        return null;

    }

    public City toEntity(CityCreateView cityCreateView) {
        City city = new City();
        city.setName(cityCreateView.getName());
        city.setLatitude(cityCreateView.getLatitude());
        city.setLongitude(cityCreateView.getLongitude());

        return city;
    }

    public CityCreateView toViewCityCreate(City city) {
        CityCreateView cCV = new CityCreateView();
        cCV.setId(city.getId());
        cCV.setName(city.getName());
        cCV.setLatitude(city.getLatitude());
        cCV.setLongitude(city.getLongitude());

        return cCV;
    }

}

