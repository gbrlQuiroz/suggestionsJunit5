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
            // ya existe un registro con el mismo 'name' no lo da de alta
            cityNameRepeated(cV.getName());

            City city = cityConverter.toEntity(cV, new City());
            cityRepository.save(city);
            return cityConverter.toView(city);

        } catch (ResponseException rex) {
            throw rex;
        } catch(Exception ex) {
            log.error("--->>>CityServiceImpl--->createCity--->>>exception: {}",ex.getMessage());
            return null;
        }
    }


    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    @Override
    public CityView updateCity(CityView cV) {
        try {
            City tempo = cityExists(cV.getId());
            tempo = cityConverter.toEntity(cV, tempo);
            cityRepository.save(tempo);
            return cityConverter.toView(tempo);
        } catch (ResponseException rex) {
            throw rex;
        } catch(Exception ex) {
            log.error("--->>>CityServiceImpl--->updateCity--->>>exception: {}",ex.getMessage());
            return null;
        }
    }


    // revisa que exista el registro
    private City cityExists(Long id) {
        Optional<City> exist = cityRepository.findById(id);
        if(!exist.isPresent()) {
            throw new ResponseException("No se encuentra City con id: " + id, HttpStatus.NOT_FOUND);
        }
        return exist.get();
    }

    // revisa si esta repetido el campo 'name' 
    private void cityNameRepeated(String name) {
        if(cityRepository.findByName(name) != null) {
            throw new ResponseException("Ya existe un registro con este nombre: " + name, HttpStatus.CONFLICT);
        }
    }
}
