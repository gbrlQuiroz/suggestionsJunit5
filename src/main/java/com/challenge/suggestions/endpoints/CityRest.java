package com.challenge.suggestions.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.suggestions.services.SuggestionService;
import com.challenge.suggestions.views.SuggestionView;


@RestController
@RequestMapping("suggestions")
public class CityRest {

    private SuggestionService suggestionService;

    @Autowired
    public void setCityService(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @GetMapping("")
    public ResponseEntity<SuggestionView> getSuggestions(@RequestParam(name = "q") String texto,
            @RequestParam(name = "latitude", required = false, defaultValue = "0") Double lati,
            @RequestParam(name = "longitude", required = false, defaultValue = "0") Double longi) {

        SuggestionView respuesta = suggestionService.getSuggestions(texto, lati, longi);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(respuesta);
    }
}
