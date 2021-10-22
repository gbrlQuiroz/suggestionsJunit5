package com.challenge.suggestions.views;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CityCreateView implements Serializable {
    private Long id;
    private String name;
    private Double latitude;
    private Double longitude;

}

