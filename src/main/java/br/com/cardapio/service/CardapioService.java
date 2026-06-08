package br.com.cardapio.service;

import br.com.cardapio.domain.DiaCardapio;
import br.com.cardapio.domain.SemanaCardapio;
import br.com.cardapio.repository.DiaCardapioRepository;
import br.com.cardapio.repository.SemanaCardapioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Optional;



@Service
@RequiredArgsConstructor

public class CardapioService {

    private final DiaCardapioRepository diaRepository;
    private final SemanaCardapioRepository repository;


    @Transactional
    public Optional<DiaCardapio> buscarCardapioDeHoje(){
        LocalDate hoje = LocalDate.now();
        DayOfWeek diaSemana = hoje.getDayOfWeek();

        if (diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY){
            return Optional.empty();
        }

        return diaRepository.findByData(hoje);
    }



}
