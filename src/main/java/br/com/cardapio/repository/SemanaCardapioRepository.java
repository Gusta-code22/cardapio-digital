package br.com.cardapio.repository;

import br.com.cardapio.domain.SemanaCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface SemanaCardapioRepository extends JpaRepository<SemanaCardapio, Long> {
    Optional<SemanaCardapio> findByDataInicioSemana(LocalDate dataInicioSemana);

}
