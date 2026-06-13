package br.com.cardapio.controller;

import br.com.cardapio.domain.DiaCardapio;
import br.com.cardapio.dto.CardapioDiaDTO;

import br.com.cardapio.service.CardapioService;
import br.com.cardapio.service.DiaCardapioService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@Controller
@RequiredArgsConstructor

public class CardapioController {

    private final CardapioService service;
    private final DiaCardapioService diaCardapioService;

    @GetMapping
    public String cardapioDeHoje(Model model){
        LocalDate hoje = LocalDate.now();
        DiaCardapio cardapio = service.buscarCardapioDeHoje().orElse(null);

        model.addAttribute("cardapio", cardapio);
        model.addAttribute("dataHoje", formatarData(hoje));
        if (cardapio != null) {
            model.addAttribute("diaHojeFormatado", traduzirDia(cardapio.getDiaSemana()));
        }
        return "home";
    }


    private String formatarData(LocalDate data) {
        return data.format(DateTimeFormatter.ofPattern("EEEE, dd 'de' MMMM", new Locale("pt", "BR")));
    }

    private String traduzirDia(DayOfWeek dia) {
        return dia.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    }


}
