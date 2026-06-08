package br.com.cardapio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaCardapioDTO {

    private LocalDate data;
    private DayOfWeek diaSemana;
    private String diaSemanaFormatado;
    private String almoco;
    private String lanche;
    private String lancheTarde;
}
