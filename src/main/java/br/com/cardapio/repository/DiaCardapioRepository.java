package br.com.cardapio.repository;

import br.com.cardapio.domain.DiaCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DiaCardapioRepository extends JpaRepository<DiaCardapio, Long> {
    Optional<DiaCardapio> findByData(LocalDate data);
}
