package com.challenge.suggestions.views;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CityView implements Serializable {
    
    private Long id;
    
    @NotBlank(message = "campo es obligatorio")
    private String name;
    
    @NotNull(message = "campo es obligatorio")
    private Double latitude;
    
    @NotNull(message = "campo es obligatorio")
    private Double longitude;

}

