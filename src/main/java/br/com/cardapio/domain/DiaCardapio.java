package br.com.cardapio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Entity
@Table(name = "dia_cardapio")
@Getter
@Setter
@NoArgsConstructor  // ← Crucial pro Jackson
@AllArgsConstructor
public class DiaCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "dia_semana")
    private DayOfWeek diaSemana;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String almoco;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String lanche;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String lancheTarde;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "semana_cardapio_id", nullable = false)
    private SemanaCardapio semana;
}
