package com.challenge.suggestions.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.suggestions.services.CityService;

import java.util.Optional;
import com.challenge.suggestions.converters.CityConverter;
import com.challenge.suggestions.views.*;
import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.CityRepository;

import com.challenge.suggestions.exceptions.*;
import lombok.extern.slf4j.Slf4j;


@Service
@Transactional(readOnly = true)
@Slf4j
public class CityServiceImpl implements CityService {
    
    private CityRepository cityRepository;
    private CityConverter cityConverter;

    @Autowired
    public void setCityRepository(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Autowired
    public void setCityConverter(CityConverter cityConverter) {
        this.cityConverter = cityConverter;
    }

    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public CityView createCity(CityView cV) {
        try {
            if(cityRepository.findByName(cV.getName()) != null) {
                log.error("Ya existe un registro con este valor");
                throw new ResponseException("Ya existe un registro con este valor", HttpStatus.CONFLICT);
            }

            City city = cityConverter.toEntity(cV);
            cityRepository.save(city);
            return cityConverter.toView(city);

        } catch (ResponseException rex) {
            throw rex;
        } catch(Exception ex) {
            log.error("--->>>CityServiceImpl--->createCity--->>>exception: {}",ex.getMessage());
            return null;
        }
    }
}
