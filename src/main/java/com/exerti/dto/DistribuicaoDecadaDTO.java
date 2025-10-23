package com.exerti.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa a distribuição de veículos por década de fabricação")
public class DistribuicaoDecadaDTO {
    
    @Schema(description = "Década de fabricação", example = "2020")
    private String decada;
    
    @Schema(description = "Quantidade de veículos na década", example = "15")
    private Long quantidade;
    
    @Schema(description = "Percentual da década em relação ao total", example = "25.5")
    private Double percentual;
}
