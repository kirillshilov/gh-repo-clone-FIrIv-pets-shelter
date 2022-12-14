package com.telegrambot.jd501.controllers.cat;

import com.telegrambot.jd501.model.cat.Cat;
import com.telegrambot.jd501.service.cat_service.CatService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with Cat
 * has CRUD operation
 */
@RestController
@RequestMapping("/cat/cat")
public class CatController {
    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    /**
     * get All Cat-s from DataBase
     * Use method of service {@link CatService#getAllPets()} (Collection< Cat >)}
     *
     * @return collection of Cat
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all Cat",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection<Cat> getAllPets() {
        return catService.getAllPets();
    }

    /**
     * add new Cat in DataBase
     *
     * @param cat Use method of Service {@link CatService#createPet(Cat)}
     * @return Cat
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new cat",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            )
    })

    @PostMapping
    public ResponseEntity<Cat> createPet(@RequestBody Cat cat) {
        return ResponseEntity.ok(catService.createPet(cat));
    }

    /**
     * change Cat in DataBase
     * Use method of Service {@link CatService#updatePet(Cat)}
     *
     * @param cat (object)
     * @return Cat
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if Cat with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change Cat By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Cat not found"
            )
    })
    @PutMapping
    public ResponseEntity<Cat> updatePet(@RequestBody Cat cat) {
        return ResponseEntity.ok(catService.updatePet(cat));
    }

    /**
     * delete Cat from DataBase by id
     * Use method of Service {@link CatService#deletePet(Long id)}}
     *
     * @param id of Cat
     * @return Deleted Cat
     * @throws com.telegrambot.jd501.exceptions.PetReportNotFoundException if Cat with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete Cat By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cat.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "cat not found"
            )
    })
    @DeleteMapping("{id}")
    public ResponseEntity<Cat> deletePet(@PathVariable Long id) {
        return ResponseEntity.ok(catService.deletePet(id));
    }
}
