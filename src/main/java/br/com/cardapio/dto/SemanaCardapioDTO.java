package br.com.cardapio.dto;

import br.com.cardapio.domain.DiaCardapio;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SemanaCardapioDTO {

    private LocalDate dataInicioSemana;
    private List<DiaCardapioDTO> dias;



    // getters e setters

}
