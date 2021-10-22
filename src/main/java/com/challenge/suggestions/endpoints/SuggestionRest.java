package com.challenge.suggestions.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.challenge.suggestions.services.SuggestionService;
import com.challenge.suggestions.views.*;

import java.net.URI;

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

        // // Si se desea usar el error por default de mismo resposeEntity
        // if (respuesta.readSuggestions().isEmpty()) {
        //     log.error("No se encontró ningun registro");
        //     return ResponseEntity.notFound().build();
        // }

        if (respuesta.getSuggestions().isEmpty()) {
            throw new NotFoundException("No se encontró registro con valor: " + texto);
        }

        return ResponseEntity.ok(respuesta);
    }

    @PostMapping("/city")
    public ResponseEntity<CityCreateView> createCity(@RequestBody CityCreateView cityCreateView) {
        CityCreateView cCV = suggestionService.createCity(cityCreateView);
        if (cCV == null) {
            return ResponseEntity.notFound().build();
        } else {
            // return new ResponseEntity<CityCreateView>(cCV, HttpStatus.CREATED);
            
            // Si se desea construir HATECOAS
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cCV.getId())
                .toUri();

            return ResponseEntity.created(uri).body(cCV);
        }
    }
}
