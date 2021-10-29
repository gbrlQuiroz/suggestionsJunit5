package com.challenge.suggestions.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.challenge.suggestions.services.CityService;
import com.challenge.suggestions.views.*;

import javax.validation.Valid;

// import java.net.URI;

import com.challenge.suggestions.exceptions.*;


@RestController
@RequestMapping("city")
public class CityRest {

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }


    @PostMapping("")
    public ResponseEntity<CityView> createCity(@Valid @RequestBody CityView cityView) {
        CityView cV = cityService.createCity(cityView);
        if (cV == null) {
            return ResponseEntity.notFound().build();
        } else {
            return new ResponseEntity<CityView>(cV, HttpStatus.CREATED);
            
            // // Si se desea construir HATEOAS
            // URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
            //     .path("/{id}")
            //     .buildAndExpand(cV.getId())
            //     .toUri();

            // return ResponseEntity.created(uri).body(cV);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityView> updateCity(@PathVariable Long id, @Valid @RequestBody CityView cityView) {
        cityView.setId(id);
        return new ResponseEntity<CityView>(cityService.updateCity(cityView), HttpStatus.OK);
    }
}
