package br.com.cardapio.controller.rest;

import br.com.cardapio.dto.CardapioDiaDTO;
import br.com.cardapio.service.DiaCardapioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@Tag(
        name = "Daily Menu",
        description = "REST endpoints for school meal menus."
)
public class CardapioApiController {

    private final DiaCardapioService diaCardapioService;

    @Operation(
            summary = "Get today's menu",
            description = "Returns today's school meal menu."
    )
    @GetMapping("/today")
    public ResponseEntity<CardapioDiaDTO> getTodayMenu() {
        return ResponseEntity.ok(
                diaCardapioService.buscarCardapioDoDia(LocalDate.now())
        );
    }
}