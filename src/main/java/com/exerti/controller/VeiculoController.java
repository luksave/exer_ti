package com.exerti.controller;

import com.exerti.dto.DistribuicaoDecadaDTO;
import com.exerti.dto.DistribuicaoFabricanteDTO;
import com.exerti.enums.MarcaVeiculo;
import com.exerti.model.Veiculo;
import com.exerti.service.VeiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/veiculos")
@Tag(name = "Veículos", description = "API para gerenciamento de veículos")
public class VeiculoController {
    
    @Autowired
    private VeiculoService veiculoService;
    

    /**
     * Não vejo necessidade de separar um endpoint /veiculos e um endpoint /veiculo?marca=marca&ano=ano&cor=cor
     */
    @Operation(
        summary = "Listar veículos",
        description = "Retorna uma lista de veículos com filtros opcionais por marca, ano e cor"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de veículos retornada com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Veiculo.class)))
    })
    @GetMapping
    public ResponseEntity<List<Veiculo>> getAllVeiculos(
            @Parameter(description = "Filtrar por marca do veículo") 
            @RequestParam(required = false) String marca,
            @Parameter(description = "Filtrar por ano do veículo") 
            @RequestParam(required = false) Integer ano,
            @Parameter(description = "Filtrar por cor do veículo") 
            @RequestParam(required = false) String cor
    ) {
        // Validação e sanitização dos parâmetros. Cuidado com SQL Injection
        if (marca != null && marca.length() > 50) {
            return ResponseEntity.badRequest().build();
        }
        if (ano != null && (ano < 1900 || ano > 2025)) {
            return ResponseEntity.badRequest().build();
        }
        if (cor != null && cor.length() > 30) {
            return ResponseEntity.badRequest().build();
        }

        // Validação da marca usando o enum
        if (marca != null && !MarcaVeiculo.isValidMarca(marca)) {
            return ResponseEntity.badRequest().build();
        }

        List<Veiculo> veiculos;
        FiltroVeiculo filtro = new FiltroVeiculo(marca, ano, cor);
        veiculos = veiculoService.findByFiltro(filtro);

        return ResponseEntity.ok(veiculos);
    }
    
    @Operation(
        summary = "Buscar veículo por ID",
        description = "Retorna um veículo específico pelo seu ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo encontrado com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getVeiculoById(
            @Parameter(description = "ID do veículo a ser buscado") 
            @PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        if (veiculo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(veiculo.get());
    }
    
    @Operation(
        summary = "Criar novo veículo",
        description = "Cria um novo veículo no sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Veículo criado com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<Veiculo> createVeiculo(
            @Parameter(description = "Dados do veículo a ser criado") 
            @Valid @RequestBody Veiculo veiculo) {
        Veiculo savedVeiculo = veiculoService.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedVeiculo);
    }
    
    @Operation(
        summary = "Atualizar veículo completamente",
        description = "Atualiza todos os campos de um veículo existente (PUT)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> updateVeiculo(
            @Parameter(description = "ID do veículo a ser atualizado") 
            @PathVariable Long id, 
            @Parameter(description = "Dados completos do veículo") 
            @Valid @RequestBody Veiculo veiculo) {
        // PUT: substitui completamente
        Optional<Veiculo> existingVeiculo = veiculoService.findById(id);
        if (existingVeiculo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        veiculo.setId(id); // Garante o ID correto
        // Todos os campos devem estar presentes no body 
        Veiculo updatedVeiculo = veiculoService.save(veiculo);
        return ResponseEntity.ok(updatedVeiculo);
    }

    @Operation(
        summary = "Atualizar veículo parcialmente",
        description = "Atualiza apenas os campos fornecidos de um veículo existente (PATCH)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Veículo atualizado com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Veiculo.class))),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<Veiculo> updateParcialVeiculo(
            @Parameter(description = "ID do veículo a ser atualizado") 
            @PathVariable Long id, 
            @Parameter(description = "Dados parciais do veículo") 
            @RequestBody Veiculo partialVeiculo) {
        // PATCH: atualiza apenas os campos no body da requisição
        Optional<Veiculo> existingVeiculoOpt = veiculoService.findById(id);
        if (existingVeiculoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Veiculo existingVeiculo = existingVeiculoOpt.get();

        Veiculo updatedVeiculo = veiculoService.save(existingVeiculo);
        return ResponseEntity.ok(updatedVeiculo);
    }
    
    @Operation(
        summary = "Excluir veículo",
        description = "Remove um veículo do sistema (exclusão permanente)"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Veículo excluído com sucesso"),
        @ApiResponse(responseCode = "404", description = "Veículo não encontrado")
    })
    @DeleteMapping("/{id}")
    //Hard delete
    public ResponseEntity<Void> deleteVeiculo(
            @Parameter(description = "ID do veículo a ser excluído") 
            @PathVariable Long id) {
        Optional<Veiculo> veiculo = veiculoService.findById(id);
        if (veiculo.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        veiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @Operation(
        summary = "Contar veículos vendidos",
        description = "Retorna a quantidade total de veículos que foram vendidos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Quantidade de veículos vendidos retornada com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Long.class)))
    })
    @GetMapping("/vendidos/count")
    public ResponseEntity<Long> contarVeiculosVendidos() {
        long quantidadeVendidos = veiculoService.contarVeiculosVendidos();
        return ResponseEntity.ok(quantidadeVendidos);
    }
    
    @Operation(
        summary = "Distribuição por década",
        description = "Retorna a distribuição de veículos agrupados por década de fabricação"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distribuição por década retornada com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = DistribuicaoDecadaDTO.class)))
    })
    @GetMapping("/distribuicao/decada")
    public ResponseEntity<List<DistribuicaoDecadaDTO>> obterDistribuicaoPorDecada() {
        List<DistribuicaoDecadaDTO> distribuicao = veiculoService.obterDistribuicaoPorDecada();
        return ResponseEntity.ok(distribuicao);
    }
    
    @Operation(
        summary = "Distribuição por fabricante",
        description = "Retorna a distribuição de veículos agrupados por fabricante/marca"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Distribuição por fabricante retornada com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = DistribuicaoFabricanteDTO.class)))
    })
    @GetMapping("/distribuicao/fabricante")
    public ResponseEntity<List<DistribuicaoFabricanteDTO>> obterDistribuicaoPorFabricante() {
        List<DistribuicaoFabricanteDTO> distribuicao = veiculoService.obterDistribuicaoPorFabricante();
        return ResponseEntity.ok(distribuicao);
    }
    
    @Operation(
        summary = "Veículos da última semana",
        description = "Retorna todos os veículos cadastrados nos últimos 7 dias"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de veículos da última semana retornada com sucesso",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Veiculo.class)))
    })
    @GetMapping("/ultima-semana")
    public ResponseEntity<List<Veiculo>> obterVeiculosCadastradosNaUltimaSemana() {
        List<Veiculo> veiculos = veiculoService.obterVeiculosCadastradosNaUltimaSemana();
        return ResponseEntity.ok(veiculos);
    }
    
    @Operation(
        summary = "Listar marcas válidas",
        description = "Retorna a lista de todas as marcas aceitas pelo sistema"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de marcas válidas retornada com sucesso",
                    content = @Content(mediaType = "application/json"))
    })
    @GetMapping("/marcas-validas")
    public ResponseEntity<List<String>> obterMarcasValidas() {
        List<String> marcas = java.util.Arrays.stream(MarcaVeiculo.values())
            .map(MarcaVeiculo::getNome)
            .sorted()
            .collect(java.util.stream.Collectors.toList());
        return ResponseEntity.ok(marcas);
    }
    
}
