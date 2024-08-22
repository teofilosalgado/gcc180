package br.ufla.gcc180.domain.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 255, nullable = false)
    public String nome;

    @Column(length = 11, nullable = false)
    public String cpf;

    @Column(nullable = false)
    public LocalDate nascimento;
    
    @Column(length = 255, nullable = false)
    public String email;
}
