package com.challenge.suggestions.services;

import com.challenge.suggestions.views.CityView;


public interface CityService {
    /**
     * Crear una ciudad para las sugerencias
     * @param cV la vista que es identica en campos al entity (tiene el ID necesario a regresar)
     * @return la view completa identica al entity
     */
    public CityView createCity(CityView cV);

}
