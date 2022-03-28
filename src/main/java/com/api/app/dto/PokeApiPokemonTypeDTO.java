package com.api.app.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class PokeApiPokemonTypeDTO implements Serializable {

    private PokeApiNamedApiResource type;
}
