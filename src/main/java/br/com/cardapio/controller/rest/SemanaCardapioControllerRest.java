package br.com.cardapio.controller.rest;

import br.com.cardapio.domain.SemanaCardapio;
import br.com.cardapio.dto.SemanaCardapioDTO;
import br.com.cardapio.service.SemanaCardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/weekly-menu")
@RequiredArgsConstructor
@Tag(
        name = "Weekly Menu",
        description = "Operations for creating and retrieving weekly school meal menus."
)
public class SemanaCardapioControllerRest {

    private final SemanaCardapioService service;

    @Operation(
            summary = "Create a weekly menu",
            description = "Creates and stores a complete weekly school meal menu."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Weekly menu created successfully"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request payload"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    @PostMapping
    public ResponseEntity<SemanaCardapio> createWeeklyMenu(

            @RequestBody(
                    description = "Weekly menu data containing all daily meal entries.",
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = SemanaCardapioDTO.class
                            )
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody SemanaCardapioDTO dto
    ) {

        SemanaCardapio weeklyMenu = service.criarSemanaComDias(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(weeklyMenu);
    }

    @Operation(
            summary = "Retrieve current weekly menu",
            description = "Returns the currently active weekly school meal menu."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Weekly menu retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No weekly menu found"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Unexpected server error"
            )
    })
    @GetMapping("/current")
    public ResponseEntity<SemanaCardapioDTO> getCurrentWeeklyMenu() {

        SemanaCardapioDTO weeklyMenu = service.buscarCardapioSemana();

        return ResponseEntity.ok(weeklyMenu);
    }
}