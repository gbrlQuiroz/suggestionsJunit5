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
public class CityView implements Serializable {
    private String name;
    private String latitude;
    private String longitude;
    private Double score;

}
