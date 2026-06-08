package br.com.cardapio.dto;

import java.time.LocalDate;

public record CardapioDiaDTO(
        LocalDate data,
        String diaSemana,
        String almoco,
        String lanche,
        String lancheTarde
) {}
