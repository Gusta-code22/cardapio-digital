package br.com.cardapio.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "semana_cardapio")
@ToString(exclude = "dias")  // ← Evita loop no toString
public class SemanaCardapio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private LocalDate dataInicioSemana;

    @OneToMany(mappedBy = "semana", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<DiaCardapio> dias;  // ← Sem @JsonIgnore (JSON vai funcionar)
}
