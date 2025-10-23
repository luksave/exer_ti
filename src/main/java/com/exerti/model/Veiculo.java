package com.exerti.model;

import com.exerti.validation.ValidMarca;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um veículo no sistema")
public class Veiculo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do veículo", example = "1")
    private Long id;
    
    @NotBlank(message = "Veículo é obrigatório")
    @Column(name = "veiculo", nullable = false)
    @Schema(description = "Nome/modelo do veículo", example = "Civic", required = true)
    private String veiculo;
    
    @NotBlank(message = "Marca é obrigatória")
    @ValidMarca(message = "Marca deve ser uma das marcas aceitas pelo sistema")
    @Column(name = "marca", nullable = false)
    @Schema(description = "Marca do veículo", example = "Honda", required = true)
    private String marca;
    
    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano deve ser maior que 1900")
    @Max(value = 2025, message = "Ano deve ser menor que 2026")
    @Column(name = "ano", nullable = false)
    @Schema(description = "Ano de fabricação do veículo", example = "2020", minimum = "1900", maximum = "2025", required = true)
    private Integer ano;
    
    @Column(name = "descricao", columnDefinition = "TEXT")
    @Schema(description = "Descrição detalhada do veículo", example = "Veículo em excelente estado, único dono")
    private String descricao;
    
    @NotNull(message = "Status de venda é obrigatório")
    @Column(name = "vendido", nullable = false)
    @Schema(description = "Indica se o veículo foi vendido", example = "false", required = true)
    private Boolean vendido = false;
    
    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    @Schema(description = "Data e hora de criação do registro", example = "2024-01-15T10:30:00")
    private LocalDateTime created;
    
    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    @Schema(description = "Data e hora da última atualização do registro", example = "2024-01-15T14:45:00")
    private LocalDateTime updated;
}
