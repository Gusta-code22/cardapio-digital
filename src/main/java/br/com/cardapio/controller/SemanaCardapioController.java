package br.com.cardapio.controller;


import br.com.cardapio.domain.SemanaCardapio;
import br.com.cardapio.dto.DiaCardapioDTO;
import br.com.cardapio.dto.SemanaCardapioDTO;
import br.com.cardapio.service.SemanaCardapioService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SemanaCardapioController {

    private final SemanaCardapioService service;


    @PostMapping
    public ResponseEntity<SemanaCardapio> criarSemana(
            @RequestBody SemanaCardapioDTO dto
    ) {
        SemanaCardapio semana = service.criarSemanaComDias(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(semana);
    }


    @GetMapping("/semana-atual")
    public ResponseEntity<SemanaCardapioDTO> mostrarSemana() {
        SemanaCardapioDTO semanaDTO = service.buscarCardapioSemana();
        return ResponseEntity.ok(semanaDTO);
    }

    @GetMapping("/semana")
    public String semana(Model model) {
        SemanaCardapioDTO semanaDTO = service.buscarCardapioSemana();

        model.addAttribute("semanaCardapio", semanaDTO);

        return "semana";
    }



}
