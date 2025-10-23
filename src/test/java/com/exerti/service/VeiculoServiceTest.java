package com.exerti.service;

import com.exerti.controller.FiltroVeiculo;
import com.exerti.dto.DistribuicaoDecadaDTO;
import com.exerti.dto.DistribuicaoFabricanteDTO;
import com.exerti.model.Veiculo;
import com.exerti.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository veiculoRepository;

    @InjectMocks
    private VeiculoService veiculoService;

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
    void deve_buscar_veiculos_por_filtro() {
        FiltroVeiculo filtro = new FiltroVeiculo("Honda", 2020, "Branco");
        when(veiculoRepository.findByFiltro(filtro)).thenReturn(veiculos);

        List<Veiculo> resultado = veiculoService.findByFiltro(filtro);

        assertEquals(1, resultado.size());
        assertEquals("Honda", resultado.get(0).getMarca());
        verify(veiculoRepository).findByFiltro(filtro);
    }

    @Test
    void deve_encontrar_veiculo_por_id() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));

        Optional<Veiculo> resultado = veiculoService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Civic", resultado.get().getVeiculo());
        verify(veiculoRepository).findById(1L);
    }

    @Test
    void deve_retornar_vazio_quando_veiculo_nao_existe() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Veiculo> resultado = veiculoService.findById(1L);

        assertTrue(resultado.isEmpty());
        verify(veiculoRepository).findById(1L);
    }

    @Test
    void deve_salvar_veiculo() {
        when(veiculoRepository.save(veiculo)).thenReturn(veiculo);

        Veiculo resultado = veiculoService.save(veiculo);

        assertEquals(veiculo, resultado);
        verify(veiculoRepository).save(veiculo);
    }

    @Test
    void deve_excluir_veiculo() {
        doNothing().when(veiculoRepository).deleteById(1L);

        veiculoService.deleteById(1L);

        verify(veiculoRepository).deleteById(1L);
    }

    @Test
    void deve_buscar_veiculos_disponiveis() {
        when(veiculoRepository.findVeiculosDisponiveis()).thenReturn(veiculos);

        List<Veiculo> resultado = veiculoService.findVeiculosDisponiveis();

        assertEquals(1, resultado.size());
        verify(veiculoRepository).findVeiculosDisponiveis();
    }

    @Test
    void deve_buscar_veiculos_por_periodo() {
        when(veiculoRepository.findByAnoBetween(2020, 2022)).thenReturn(veiculos);

        List<Veiculo> resultado = veiculoService.findByAnoBetween(2020, 2022);

        assertEquals(1, resultado.size());
        verify(veiculoRepository).findByAnoBetween(2020, 2022);
    }

    @Test
    void deve_contar_veiculos_por_status_venda() {
        when(veiculoRepository.countByVendido(true)).thenReturn(5L);

        long resultado = veiculoService.countByVendido(true);

        assertEquals(5L, resultado);
        verify(veiculoRepository).countByVendido(true);
    }

    @Test
    void deve_buscar_veiculos_por_descricao() {
        when(veiculoRepository.findByDescricaoContaining("excelente")).thenReturn(veiculos);

        List<Veiculo> resultado = veiculoService.findByDescricaoContaining("excelente");

        assertEquals(1, resultado.size());
        verify(veiculoRepository).findByDescricaoContaining("excelente");
    }

    @Test
    void deve_marcar_veiculo_como_vendido() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.of(veiculo));
        when(veiculoRepository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo resultado = veiculoService.marcarComoVendido(1L);

        assertNotNull(resultado);
        assertTrue(resultado.getVendido());
        verify(veiculoRepository).findById(1L);
        verify(veiculoRepository).save(veiculo);
    }

    @Test
    void deve_retornar_null_ao_tentar_vender_veiculo_inexistente() {
        when(veiculoRepository.findById(1L)).thenReturn(Optional.empty());

        Veiculo resultado = veiculoService.marcarComoVendido(1L);

        assertNull(resultado);
        verify(veiculoRepository).findById(1L);
        verify(veiculoRepository, never()).save(any(Veiculo.class));
    }

    @Test
    void deve_contar_veiculos_vendidos() {
        when(veiculoRepository.countByVendido(true)).thenReturn(3L);

        long resultado = veiculoService.contarVeiculosVendidos();

        assertEquals(3L, resultado);
        verify(veiculoRepository).countByVendido(true);
    }

    @Test
    void deve_obter_distribuicao_por_decada() {
        List<Object[]> resultados = Arrays.<Object[]>asList(
                new Object[]{"2020-2029", 5L}
        );
        when(veiculoRepository.findDistribuicaoPorDecada()).thenReturn(resultados);
        when(veiculoRepository.count()).thenReturn(10L);

        List<DistribuicaoDecadaDTO> resultado = veiculoService.obterDistribuicaoPorDecada();

        assertEquals(1, resultado.size());
        assertEquals("2020-2029", resultado.get(0).getDecada());
        assertEquals(5L, resultado.get(0).getQuantidade());
        assertEquals(50.0, resultado.get(0).getPercentual());
        verify(veiculoRepository).findDistribuicaoPorDecada();
        verify(veiculoRepository).count();
    }

    @Test
    void deve_obter_distribuicao_por_fabricante() {
        List<Object[]> resultados = Arrays.<Object[]>asList(
                new Object[]{"Honda", 3L}
        );
        when(veiculoRepository.findDistribuicaoPorFabricante()).thenReturn(resultados);
        when(veiculoRepository.count()).thenReturn(10L);

        List<DistribuicaoFabricanteDTO> resultado = veiculoService.obterDistribuicaoPorFabricante();

        assertEquals(1, resultado.size());
        assertEquals("Honda", resultado.get(0).getFabricante());
        assertEquals(3L, resultado.get(0).getQuantidade());
        assertEquals(30.0, resultado.get(0).getPercentual());
        verify(veiculoRepository).findDistribuicaoPorFabricante();
        verify(veiculoRepository).count();
    }

    @Test
    void deve_obter_veiculos_da_ultima_semana() {
        when(veiculoRepository.findVeiculosCadastradosNaUltimaSemana(any(LocalDateTime.class)))
                .thenReturn(veiculos);

        List<Veiculo> resultado = veiculoService.obterVeiculosCadastradosNaUltimaSemana();

        assertEquals(1, resultado.size());
        verify(veiculoRepository).findVeiculosCadastradosNaUltimaSemana(any(LocalDateTime.class));
    }
}
