package com.api.app.controllers;

import com.api.app.dto.PokeApiDescriptionsDTO;
import com.api.app.dto.PokeApiJsonAllDTO;
import com.api.app.dto.PokeApiPokemonTypeDTO;
import com.api.app.dto.PokeApiSpritesDTO;
import com.api.app.dto.Result;
import com.api.app.dto.PokeApiByPokemonDTO;
import com.api.app.exception.BusinessException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.apache.commons.lang3.StringUtils;

@RestController
@RequestMapping("/pokemon")
public class PokemonController {

    private final String URL = "https://pokeapi.co/api/v2";

    /**
     * @return ArrayList Retorna todos los registros
     */
    @GetMapping("/all")
    @ApiOperation(value = "Retorna todos los registros", response = PokeApiByPokemonDTO.class)
    public ResponseEntity<ArrayList<HashMap<String, String>>> getAll() {

        String edp = "/pokemon/";
        String paginacion = "?limit=150";
        String url = String.format("%s%s%s", URL, edp, paginacion);
        RestTemplate restTemplate = new RestTemplate();
        PokeApiJsonAllDTO result = restTemplate.getForObject(url, PokeApiJsonAllDTO.class);

        ArrayList<HashMap<String, String>> listaHashMap = new ArrayList<HashMap<String, String>>();

        for (Result res : result.getResults()) {
            int idFormat = Integer.parseInt(res.getUrl().substring(34).replace("/", ""));
            res.setId(idFormat);

            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("id", res.getId() + "");
            hashMap.put("name", res.getName());
            listaHashMap.add(hashMap);
        }

        return new ResponseEntity<>(listaHashMap, HttpStatus.OK);
    }

    /**
     * @param nombre
     * @return HashMap Retorna un registro filtrado por el nombre
     */
    @GetMapping(value = "/nombre/{nombre}")
    @ApiOperation(value = "Retorna un registro filtrado por el nombre", response = PokeApiByPokemonDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "El registro fue encontrado"),
        @ApiResponse(code = 404, message = "El registro NO fue encontrado")
    })
    public ResponseEntity<HashMap<String, Object>> getByNombre(@PathVariable("nombre") String nombre) {
        
        boolean nombreValido = false;
        System.out.println("nombre: " + nombre);

        for (int x = 0; x < nombre.length(); x++) {
            char c = nombre.charAt(x);
            // Si no está entre a y z
            if (!((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))) {
                nombreValido = false;
            } else {
                nombreValido = true;
            }
        }

        if (nombre.isEmpty()) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El nombre no debe ser vacío");

        } else if (nombreValido == false) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El nombre no debe ser numérico");

        } else {
            
            // Conversion de parametro nombre a minusculas
            String nombreLowerCase = StringUtils.lowerCase(nombre);
            System.out.println("nombreLowerCase: " + nombreLowerCase);
            
            String edp = "/pokemon/";
            String url = String.format("%s%s%s", URL, edp, nombreLowerCase);
            RestTemplate restTemplate = new RestTemplate();
            PokeApiByPokemonDTO result = restTemplate.getForObject(url, PokeApiByPokemonDTO.class);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            ArrayList<String> tipos = new ArrayList<>();
            hashMap.put("id", result.getId());
            hashMap.put("name", result.getName());
            hashMap.put("weight", result.getWeight());
            for (PokeApiPokemonTypeDTO types : result.getTypes()) {
                tipos.add(types.getType().getName());
            }
            hashMap.put("types", tipos);
            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        }

    }

    /**
     * @param id
     * @return HashMap Retorna un registro filtrado por el id
     */
    @GetMapping(value = "/id/{id}")
    @ApiOperation(value = "Retorna un registro filtrado por el id", response = PokeApiByPokemonDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "El registro fue encontrado"),
        @ApiResponse(code = 404, message = "El registro NO fue encontrado")
    })
    public ResponseEntity<HashMap<String, Object>> getById(@PathVariable("id") String id) {

        boolean idIsInt = id.matches("-?\\d+");
        System.out.println("id: " + id);

        if (id.isEmpty()) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El id no debe ser vacío");

        } else if (idIsInt == false) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El id no es válido");

        } else {
            String edp = "/pokemon/";
            String url = String.format("%s%s%s", URL, edp, id);
            RestTemplate restTemplate = new RestTemplate();
            PokeApiByPokemonDTO result = restTemplate.getForObject(url, PokeApiByPokemonDTO.class);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            ArrayList<String> tipos = new ArrayList<>();
            hashMap.put("id", result.getId());
            hashMap.put("name", result.getName());
            hashMap.put("weight", result.getWeight());
            for (PokeApiPokemonTypeDTO types : result.getTypes()) {
                tipos.add(types.getType().getName());
            }
            hashMap.put("types", tipos);
            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        }
    }

    /**
     * @param id
     * @return HashMap Retorna las caracteristicas del registro filtrado por el
     * id
     */
    @GetMapping(value = "/caracteristicas/{id}")
    @ApiOperation(value = "Retorna las caracteristicas del registro filtrado por el id", response = PokeApiByPokemonDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "El registro fue encontrado"),
        @ApiResponse(code = 404, message = "El registro NO fue encontrado")
    })
    public ResponseEntity<HashMap<String, Object>> getDescById(@PathVariable("id") String id) {

        boolean idIsInt = id.matches("-?\\d+");
        System.out.println("id: " + id);

        if (id.isEmpty()) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El id no debe ser vacío");

        } else if (idIsInt == false) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El id no es válido");

        } else {
            String edp = "/characteristic/";
            String url = String.format("%s%s%s", URL, edp, id);
            RestTemplate restTemplate = new RestTemplate();
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            PokeApiDescriptionsDTO result = restTemplate.getForObject(url, PokeApiDescriptionsDTO.class);
            hashMap.put("descriptions", result.getDescriptions());
            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        }
    }

    /**
     * @param id
     * @return HashMap Retorna la imagen por defecto del pokemon
     */
    @GetMapping(value = "/img/id/{id}")
    @ApiOperation(value = "Retorna la imagen del registro filtrado por el id", response = PokeApiByPokemonDTO.class)
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "El registro fue encontrado"),
        @ApiResponse(code = 404, message = "El registro NO fue encontrado")
    })
    public ResponseEntity<HashMap<String, Object>> getImgById(@PathVariable("id") String id) {

        boolean idIsInt = id.matches("-?\\d+");
        System.out.println("id: " + id);

        if (id.isEmpty()) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El id no debe ser vacío");

        } else if (idIsInt == false) {
            throw new BusinessException("P-404", HttpStatus.BAD_REQUEST, "El id no es válido");

        } else {
            String edp = "/pokemon/";
            String url = String.format("%s%s%s", URL, edp, id);
            RestTemplate restTemplate = new RestTemplate();
            PokeApiSpritesDTO result = restTemplate.getForObject(url, PokeApiSpritesDTO.class);
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            hashMap.put("sprites", result.getSprites().getFront_default());
            return new ResponseEntity<>(hashMap, HttpStatus.OK);
        }
    }
}
