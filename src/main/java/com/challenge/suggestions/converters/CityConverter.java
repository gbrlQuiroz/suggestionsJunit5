package com.challenge.suggestions.converters;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.challenge.suggestions.models.City;
import com.challenge.suggestions.views.CityView;

@Component
@Slf4j
public class CityConverter {

    public CityView toView(City city, Double dist) {
        CityView cityView = new CityView();
        cityView.setName(city.getName());
        cityView.setLatitude(city.getLatitude().toString());
        cityView.setLongitude(city.getLongitude().toString());

        //Se convierte a Integer para compararlo en los 'if' más fácil
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

}

