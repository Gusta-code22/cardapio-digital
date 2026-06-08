package br.com.cardapio.service;

import br.com.cardapio.domain.DiaCardapio;
import br.com.cardapio.domain.SemanaCardapio;
import br.com.cardapio.dto.DiaCardapioDTO;
import br.com.cardapio.dto.SemanaCardapioDTO;
import br.com.cardapio.repository.SemanaCardapioRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


@Service
@RequiredArgsConstructor

public class SemanaCardapioService {

    private final SemanaCardapioRepository semanaRepository;



    public SemanaCardapio criarSemanaComDias(SemanaCardapioDTO dto){
        SemanaCardapio semana = new SemanaCardapio();

        semana.setDataInicioSemana(dto.getDataInicioSemana());

        List<DiaCardapio> dias = new ArrayList<>();

        for (DiaCardapioDTO diaDTO : dto.getDias()){
            DiaCardapio dia = new DiaCardapio();

            dia.setDiaSemana(diaDTO.getDiaSemana());

            dia.setData(diaDTO.getData());

            dia.setAlmoco(diaDTO.getAlmoco());

            dia.setLanche(diaDTO.getLanche());

            dia.setLancheTarde(diaDTO.getLancheTarde());

            dia.setSemana(semana);

            dias.add(dia);
        }

        semana.setDias(dias);

        return semanaRepository.save(semana);
    }


    public SemanaCardapioDTO buscarCardapioSemana(){
        LocalDate hoje = LocalDate.now();

        LocalDate segundaFeira = hoje.with(DayOfWeek.MONDAY);


        SemanaCardapio semanaCardapio = semanaRepository.findByDataInicioSemana(segundaFeira)
                .orElseThrow(() ->
                        new RuntimeException("Cardápio da semana não cadastrado"));

        List<DiaCardapioDTO> diasDTO = semanaCardapio.getDias().stream()
                .sorted((d1, d2) -> d1.getDiaSemana().compareTo(d2.getDiaSemana()))
                .map(d -> new DiaCardapioDTO(
                        d.getData(),
                        d.getDiaSemana(),
                        traduzirDia(d.getDiaSemana()),
                        d.getAlmoco(),
                        d.getLanche(),
                        d.getLancheTarde()
                )).toList();


        SemanaCardapioDTO semanaDTO = new SemanaCardapioDTO();
        semanaDTO.setDataInicioSemana(semanaCardapio.getDataInicioSemana());
        semanaDTO.setDias(diasDTO);

        return semanaDTO;

    }

    private String traduzirDia(DayOfWeek dia) {
        return dia.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    }

}
