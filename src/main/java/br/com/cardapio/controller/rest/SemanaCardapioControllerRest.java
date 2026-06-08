package br.com.cardapio.controller.rest;

import br.com.cardapio.domain.SemanaCardapio;
import br.com.cardapio.dto.SemanaCardapioDTO;
import br.com.cardapio.service.SemanaCardapioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/semana-rest")
@RequiredArgsConstructor
public class SemanaCardapioControllerRest {

    private final SemanaCardapioService service;

    @PostMapping
    public ResponseEntity<SemanaCardapio> criarSemana(
            @RequestBody SemanaCardapioDTO dto
    ) {

        SemanaCardapio semana = service.criarSemanaComDias(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(semana);
    }

    @GetMapping("/atual")
    public ResponseEntity<SemanaCardapioDTO> mostrarSemana() {

        SemanaCardapioDTO semanaDTO = service.buscarCardapioSemana();

        return ResponseEntity.ok(semanaDTO);
    }
}