package com.api.app.dto;

import java.util.List;
import lombok.Data;

@Data
public class PokeApiJsonAllDTO {
    private Integer count;
    private List<Result> results;
    
}
