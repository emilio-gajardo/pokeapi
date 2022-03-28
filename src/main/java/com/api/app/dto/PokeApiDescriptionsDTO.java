package com.api.app.dto;

import lombok.Data;
import java.io.Serializable;
import java.util.List;

@Data
public class PokeApiDescriptionsDTO implements Serializable {
    private List<PokeApiDescriptionDTO> descriptions;
}
