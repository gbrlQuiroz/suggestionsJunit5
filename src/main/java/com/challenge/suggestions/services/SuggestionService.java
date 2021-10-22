package com.challenge.suggestions.services;

import com.challenge.suggestions.views.*;

public interface SuggestionService {
    /**
     * Obtener las sugerencias de ciudades 
     * 
     * @param texto la cadena de texto a buscar dentro del campo 'name' de la tabla
     * @param lati  latitud para calcular el score
     * @param longi longitud para calcular el score
     * @return la view donde se encuentra el atributo List de todas las sugerencias
     */
    public SuggestionView readSuggestions(String texto, Double lati, Double longi);

    /**
     * Crear una ciudad para las sugerencias
     * @param cCV la vista que es identica en campos al entity (tiene el ID necesario a regresar)
     * @return la view completa identica al entity
     */
    public CityCreateView createCity(CityCreateView cCV);
    
}
