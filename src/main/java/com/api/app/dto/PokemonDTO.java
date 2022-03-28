package com.api.app.dto;

import java.util.List;
import lombok.Data;

@Data
public class PokemonDTO {

    private Integer id;
    private String name;
    private Integer weight;
    
    private List<PokeApiPokemonTypeDTO> types;
    private List<PokeApiPokemonSpritesDTO> sprites;
    private List<PokeApiDescriptionsDTO> descriptions;
}
