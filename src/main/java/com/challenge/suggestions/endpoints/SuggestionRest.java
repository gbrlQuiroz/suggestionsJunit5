package com.challenge.suggestions.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.suggestions.services.SuggestionService;
import com.challenge.suggestions.views.*;

import com.challenge.suggestions.exceptions.*;


@RestController
@RequestMapping("suggestions")
public class SuggestionRest {

    private SuggestionService suggestionService;

    @Autowired
    public void setCityService(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("")
    public ResponseEntity<SuggestionView> readSuggestions(@RequestParam(name = "q") String texto,
            @RequestParam(name = "latitude", required = false, defaultValue = "0") Double lati,
            @RequestParam(name = "longitude", required = false, defaultValue = "0") Double longi)  {

        SuggestionView respuesta = suggestionService.readSuggestions(texto, lati, longi);

        if (respuesta.getSuggestions().isEmpty()) {
            throw new NotFoundException("No se encontró registro con valor: " + texto);
        }

        return ResponseEntity.ok(respuesta);
    }

}
