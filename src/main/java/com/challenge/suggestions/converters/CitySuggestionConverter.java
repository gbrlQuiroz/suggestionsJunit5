package com.challenge.suggestions.converters;

import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import com.challenge.suggestions.models.City;
import com.challenge.suggestions.views.*;

@Component
@Slf4j
public class CitySuggestionConverter {
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
    public CitySuggestionView toView(City city, Double dist) {
        CitySuggestionView cSV = new CitySuggestionView();
        cSV.setName(city.getName());
        cSV.setLatitude(city.getLatitude().toString());
        cSV.setLongitude(city.getLongitude().toString());

        // Se convierte a Integer para compararlo en los 'if' más fácil
        Integer valor = dist.intValue();
        log.debug("--->>>CitySuggestionConverter--->>>toView()--->>>citySuggestionView: {}",cSV);
        
        if (valor < -1) {
            cSV.setScore(1.0);
            return cSV;
        }
        if (valor < 100000) {
            cSV.setScore(1.0);
            return cSV;
        }
        if (valor > 100000 && valor < 200000) {
            cSV.setScore(0.9);
            return cSV;
        }
        if (valor > 200000 && valor < 300000) {
            cSV.setScore(0.8);
            return cSV;
        }
        if (valor > 300000 && valor < 400000) {
            cSV.setScore(0.7);
            return cSV;
        }
        if (valor > 400000 && valor < 500000) {
            cSV.setScore(0.6);
            return cSV;
        }
        if (valor > 500000 && valor < 600000) {
            cSV.setScore(0.5);
            return cSV;
        }
        if (valor > 600000 && valor < 700000) {
            cSV.setScore(0.4);
            return cSV;
        }
        if (valor > 700000 && valor < 800000) {
            cSV.setScore(0.3);
            return cSV;
        }
        if (valor > 800000 && valor < 900000) {
            cSV.setScore(0.2);
            return cSV;
        }
        if (valor > 900000) {
            cSV.setScore(0.1);
            return cSV;
        }

        return null;

    }

}

