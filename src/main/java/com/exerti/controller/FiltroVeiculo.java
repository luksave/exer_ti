package com.exerti.controller;

import com.exerti.validation.ValidMarca;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiltroVeiculo {

  @ValidMarca(message = "Marca deve ser uma das marcas aceitas pelo sistema")
  private String marca;
  
  @Min(value = 1900, message = "Ano deve ser maior que 1900")
  @Max(value = 2025, message = "Ano deve ser menor que 2026")
  private Integer ano;
  
  @Size(max = 30, message = "Cor deve ter no máximo 30 caracteres")
  private String cor;

}
