package com.telegrambot.jd501.controllers.Dog;

import com.telegrambot.jd501.model.dog.DogInformationMessage;
import com.telegrambot.jd501.service.DogService.DogInformationMessageService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * class for work with InformationMessage
 * have CRUD operation
 */
@RestController
@RequestMapping("/dog/informationMessage")
public class DogInformationMessageController {

    private final DogInformationMessageService dogInformationMessageService;

    public DogInformationMessageController(DogInformationMessageService dogInformationMessageService) {
        this.dogInformationMessageService = dogInformationMessageService;
    }

    /**
     * get All DogInformationMessage from DataBase
     * Use method of DogInformationMessage servise {@link DogInformationMessageService#getAllDogInformationMessage()} ()} (Collection< DogInformationMessage >)}
     *
     * @return collection of DogInformationMessage
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Show all DogInformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Collection.class)
                    )
            )
    })
    @GetMapping
    public Collection <DogInformationMessage> getAllDogInformationMessage() {
        return dogInformationMessageService.getAllDogInformationMessage();
    }

    /**
     * add new DogInformationMessage in DataBase
     *
     * @param dogInformationMessage Use method of DogInformationMessageServise {@link DogInformationMessageService#createDogInformationMessage(DogInformationMessage)}
     * @return DogInformationMessage
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Create new DogInformationMessage",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogInformationMessage.class)
                    )
            )
    })
    @PostMapping
    public ResponseEntity<DogInformationMessage> createDogInformationMessage(@RequestBody DogInformationMessage dogInformationMessage) {
        return ResponseEntity.ok(dogInformationMessageService.createDogInformationMessage(dogInformationMessage));
    }

    /**
     * change DogInformationMessage in DataBase
     * Use method of Servise {@link DogInformationMessageService#updateDogInformationMessage(DogInformationMessage)}
     *
     * @param dogInformationMessage
     * @return DogInformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if DogInformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Change DogInformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogInformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "InformationMessage not found"
            )
    })
    @PutMapping
    public ResponseEntity<DogInformationMessage> updateInformationMessage(@RequestBody DogInformationMessage dogInformationMessage) {
        return ResponseEntity.ok(dogInformationMessageService.updateDogInformationMessage(dogInformationMessage));
    }

    /**
     * delete DogInformationMessage from DataBase by id
     * Use method of Servise {@link DogInformationMessageService#deleteDogInformationMessage(Long id)}
     *
     * @param id
     * @return Deleted DogInformationMessage
     * @throws com.telegrambot.jd501.Exceptions.InformationMessageNotFoundException if DogInformationMessage with id not found
     */
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Delete DogInformationMessage By Id",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DogInformationMessage.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "DogInformationMessage not found"
            )
    })
    @DeleteMapping("{id}")
    ResponseEntity<DogInformationMessage> deleteInformationMessage(@PathVariable Long id) {
        return ResponseEntity.ok(dogInformationMessageService.deleteDogInformationMessage(id));
    }
}
