package br.com.cardapio.service;

import br.com.cardapio.domain.DiaCardapio;
import br.com.cardapio.domain.SemanaCardapio;
import br.com.cardapio.dto.CardapioDiaDTO;
import br.com.cardapio.repository.DiaCardapioRepository;
import br.com.cardapio.repository.SemanaCardapioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class DiaCardapioService {

    private final SemanaCardapioRepository semanaRepository;
    private final DiaCardapioRepository diaCardapioRepository;

    public CardapioDiaDTO buscarCardapioDoDia(LocalDate hoje) {

        // valida fim de semana
        if (isFimDeSemana(hoje)) {
            throw new RuntimeException("Hoje é fim de semana, não há cardápio.");
        }

        LocalDate segundaFeira = hoje.with(DayOfWeek.MONDAY);

        SemanaCardapio semanaCardapio = semanaRepository.findByDataInicioSemana(segundaFeira)
                .orElseThrow(() -> new RuntimeException("Cardápio da semana não cadastrado"));

        DiaCardapio dia = semanaCardapio.getDias().stream()
                .filter(d -> d.getData().equals(hoje))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cardápio do dia não encontrado"));

        return new CardapioDiaDTO(
                dia.getData(),
                traduzirDia(dia.getDiaSemana()),
                dia.getAlmoco(),
                dia.getLanche(),
                dia.getLancheTarde()
        );
    }

    public DiaCardapio buscarDiaPorData(LocalDate data) {
        return diaCardapioRepository.findByData(data)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Nenhum cardápio encontrado para a data: " + data
                ));
    }

    public DiaCardapio atualizarDiaCardapioPorData(LocalDate data, DiaCardapio dadosAtualizados) {

        DiaCardapio diaExistente = buscarDiaPorData(data);

        diaExistente.setAlmoco(dadosAtualizados.getAlmoco());
        diaExistente.setLanche(dadosAtualizados.getLanche());
        diaExistente.setLancheTarde(dadosAtualizados.getLancheTarde());

        return diaCardapioRepository.save(diaExistente);
    }

    // ----------------- helpers -----------------

    private boolean isFimDeSemana(LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        return dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
    }

    private String traduzirDia(DayOfWeek dia) {
        return dia.getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));
    }
}