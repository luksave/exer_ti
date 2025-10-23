package com.exerti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa a distribuição de veículos por fabricante")
public class DistribuicaoFabricanteDTO {
    
    @Schema(description = "Nome do fabricante", example = "Ford")
    private String fabricante;
    
    @Schema(description = "Quantidade de veículos do fabricante", example = "14")
    private Long quantidade;
    
    @Schema(description = "Percentual do fabricante em relação ao total", example = "35.0")
    private Double percentual;
}
