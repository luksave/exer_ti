package com.exerti.controller;

import com.exerti.dto.DistribuicaoDecadaDTO;
import com.exerti.dto.DistribuicaoFabricanteDTO;
import com.exerti.model.Veiculo;
import com.exerti.service.VeiculoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VeiculoController.class)
class VeiculoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService veiculoService;

    @Autowired
    private ObjectMapper objectMapper;

    private Veiculo veiculo;
    private List<Veiculo> veiculos;

    @BeforeEach
    void setUp() {
        veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setVeiculo("Civic");
        veiculo.setMarca("Honda");
        veiculo.setAno(2020);
        veiculo.setDescricao("Veículo em excelente estado");
        veiculo.setVendido(false);
        veiculo.setCreated(LocalDateTime.now());
        veiculo.setUpdated(LocalDateTime.now());

        veiculos = Arrays.asList(veiculo);
    }

    @Test
    void deve_listar_todos_os_veiculos() throws Exception {
        when(veiculoService.findByFiltro(any())).thenReturn(veiculos);

        mockMvc.perform(get("/api/veiculos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].veiculo").value("Civic"))
                .andExpect(jsonPath("$[0].marca").value("Honda"));

        verify(veiculoService).findByFiltro(any());
    }

    @Test
    void deve_filtrar_veiculos_por_parametros() throws Exception {
        when(veiculoService.findByFiltro(any())).thenReturn(veiculos);

        mockMvc.perform(get("/api/veiculos")
                .param("marca", "Honda")
                .param("ano", "2020"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(veiculoService).findByFiltro(any());
    }

    @Test
    void deve_rejeitar_marca_invalida() throws Exception {
        mockMvc.perform(get("/api/veiculos")
                .param("marca", "MarcaInvalida"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deve_buscar_veiculo_por_id() throws Exception {
        when(veiculoService.findById(1L)).thenReturn(Optional.of(veiculo));

        mockMvc.perform(get("/api/veiculos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.veiculo").value("Civic"))
                .andExpect(jsonPath("$.marca").value("Honda"));

        verify(veiculoService).findById(1L);
    }

    @Test
    void deve_retornar_404_quando_veiculo_nao_existe() throws Exception {
        when(veiculoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/veiculos/1"))
                .andExpect(status().isNotFound());

        verify(veiculoService).findById(1L);
    }

    @Test
    void deve_criar_veiculo_com_dados_validos() throws Exception {
        when(veiculoService.save(any(Veiculo.class))).thenReturn(veiculo);

        mockMvc.perform(post("/api/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.veiculo").value("Civic"));

        verify(veiculoService).save(any(Veiculo.class));
    }

    @Test
    void deve_rejeitar_veiculo_com_dados_invalidos() throws Exception {
        Veiculo veiculoInvalido = new Veiculo();
        veiculoInvalido.setMarca("MarcaInvalida");

        mockMvc.perform(post("/api/veiculos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculoInvalido)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deve_atualizar_veiculo_existente() throws Exception {
        when(veiculoService.findById(1L)).thenReturn(Optional.of(veiculo));
        when(veiculoService.save(any(Veiculo.class))).thenReturn(veiculo);

        mockMvc.perform(put("/api/veiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));

        verify(veiculoService).findById(1L);
        verify(veiculoService).save(any(Veiculo.class));
    }

    @Test
    void deve_retornar_404_ao_atualizar_veiculo_inexistente() throws Exception {
        when(veiculoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/veiculos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isNotFound());

        verify(veiculoService).findById(1L);
        verify(veiculoService, never()).save(any(Veiculo.class));
    }

    @Test
    void deve_excluir_veiculo_existente() throws Exception {
        when(veiculoService.findById(1L)).thenReturn(Optional.of(veiculo));

        mockMvc.perform(delete("/api/veiculos/1"))
                .andExpect(status().isNoContent());

        verify(veiculoService).findById(1L);
        verify(veiculoService).deleteById(1L);
    }

    @Test
    void deve_retornar_404_ao_excluir_veiculo_inexistente() throws Exception {
        when(veiculoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/veiculos/1"))
                .andExpect(status().isNotFound());

        verify(veiculoService).findById(1L);
        verify(veiculoService, never()).deleteById(any());
    }

    @Test
    void deve_contar_veiculos_vendidos() throws Exception {
        when(veiculoService.contarVeiculosVendidos()).thenReturn(5L);

        mockMvc.perform(get("/api/veiculos/vendidos/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));

        verify(veiculoService).contarVeiculosVendidos();
    }

    @Test
    void deve_obter_distribuicao_por_decada() throws Exception {
        List<DistribuicaoDecadaDTO> distribuicao = Arrays.asList(
                new DistribuicaoDecadaDTO("2020-2029", 5L, 50.0)
        );
        when(veiculoService.obterDistribuicaoPorDecada()).thenReturn(distribuicao);

        mockMvc.perform(get("/api/veiculos/distribuicao/decada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].decada").value("2020-2029"))
                .andExpect(jsonPath("$[0].quantidade").value(5))
                .andExpect(jsonPath("$[0].percentual").value(50.0));

        verify(veiculoService).obterDistribuicaoPorDecada();
    }

    @Test
    void deve_obter_distribuicao_por_fabricante() throws Exception {
        List<DistribuicaoFabricanteDTO> distribuicao = Arrays.asList(
                new DistribuicaoFabricanteDTO("Honda", 3L, 30.0)
        );
        when(veiculoService.obterDistribuicaoPorFabricante()).thenReturn(distribuicao);

        mockMvc.perform(get("/api/veiculos/distribuicao/fabricante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].fabricante").value("Honda"))
                .andExpect(jsonPath("$[0].quantidade").value(3))
                .andExpect(jsonPath("$[0].percentual").value(30.0));

        verify(veiculoService).obterDistribuicaoPorFabricante();
    }

    @Test
    void deve_obter_veiculos_da_ultima_semana() throws Exception {
        when(veiculoService.obterVeiculosCadastradosNaUltimaSemana()).thenReturn(veiculos);

        mockMvc.perform(get("/api/veiculos/ultima-semana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(veiculoService).obterVeiculosCadastradosNaUltimaSemana();
    }

    @Test
    void deve_retornar_marcas_validas() throws Exception {
        mockMvc.perform(get("/api/veiculos/marcas-validas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty());
    }
}
