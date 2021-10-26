package com.challenge.suggestions.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.suggestions.services.CityService;

import com.challenge.suggestions.converters.CityConverter;
import com.challenge.suggestions.views.*;
import com.challenge.suggestions.models.City;
import com.challenge.suggestions.persistences.CityRepository;

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
            City city = cityConverter.toEntity(cV);
            cityRepository.save(city);
            return cityConverter.toView(city);

        } catch(Exception ex) {
            log.error("--->>>CityServiceImpl--->createCity--->>>exception: {}",ex.getMessage());
            return null;
        }
    }
}
