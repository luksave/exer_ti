package com.exerti.service;

import com.exerti.controller.FiltroVeiculo;
import com.exerti.dto.DistribuicaoDecadaDTO;
import com.exerti.dto.DistribuicaoFabricanteDTO;
import com.exerti.model.Veiculo;
import com.exerti.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    
    @Autowired
    private VeiculoRepository veiculoRepository;
    
    public List<Veiculo> findByFiltro(FiltroVeiculo filtro) {
        return veiculoRepository.findByFiltro(filtro);
    }
    
    public Optional<Veiculo> findById(Long id) {
        return veiculoRepository.findById(id);
    }
    
    public Veiculo save(Veiculo veiculo) {
        return veiculoRepository.save(veiculo);
    }
    
    public void deleteById(Long id) {
        veiculoRepository.deleteById(id);
    }

    public List<Veiculo> findVeiculosDisponiveis() {
        return veiculoRepository.findVeiculosDisponiveis();
    }
    
    public List<Veiculo> findByAnoBetween(Integer anoInicio, Integer anoFim) {
        return veiculoRepository.findByAnoBetween(anoInicio, anoFim);
    }
    
    public long countByVendido(Boolean vendido) {
        return veiculoRepository.countByVendido(vendido);
    }
    
    public List<Veiculo> findByDescricaoContaining(String texto) {
        return veiculoRepository.findByDescricaoContaining(texto);
    }
    
    public Veiculo marcarComoVendido(Long id) {
        Optional<Veiculo> veiculoOpt = veiculoRepository.findById(id);
        if (veiculoOpt.isPresent()) {
            Veiculo veiculo = veiculoOpt.get();
            veiculo.setVendido(true);
            return veiculoRepository.save(veiculo);
        }
        return null;
    }
    
    public long contarVeiculosVendidos() {
        return veiculoRepository.countByVendido(true);
    }
    
    public List<DistribuicaoDecadaDTO> obterDistribuicaoPorDecada() {
        List<Object[]> resultados = veiculoRepository.findDistribuicaoPorDecada();
        List<DistribuicaoDecadaDTO> distribuicao = new ArrayList<>();
        
        // Calcular total de veículos para calcular percentuais
        long totalVeiculos = veiculoRepository.count();
        
        for (Object[] resultado : resultados) {
            String decada = (String) resultado[0];
            Long quantidade = ((Number) resultado[1]).longValue();
            Double percentual = totalVeiculos > 0 ? (quantidade.doubleValue() / totalVeiculos) * 100 : 0.0;
            
            distribuicao.add(new DistribuicaoDecadaDTO(decada, quantidade, percentual));
        }
        
        return distribuicao;
    }
    
    public List<DistribuicaoFabricanteDTO> obterDistribuicaoPorFabricante() {
        List<Object[]> resultados = veiculoRepository.findDistribuicaoPorFabricante();
        List<DistribuicaoFabricanteDTO> distribuicao = new ArrayList<>();
        
        // Calcular total de veículos para calcular percentuais
        long totalVeiculos = veiculoRepository.count();
        
        for (Object[] resultado : resultados) {
            String fabricante = (String) resultado[0];
            Long quantidade = ((Number) resultado[1]).longValue();
            Double percentual = totalVeiculos > 0 ? (quantidade.doubleValue() / totalVeiculos) * 100 : 0.0;
            
            distribuicao.add(new DistribuicaoFabricanteDTO(fabricante, quantidade, percentual));
        }
        
        return distribuicao;
    }
    
    public List<Veiculo> obterVeiculosCadastradosNaUltimaSemana() {
        // Calcular data de 7 dias atrás
        LocalDateTime dataInicio = LocalDateTime.now().minusDays(7);
        
        return veiculoRepository.findVeiculosCadastradosNaUltimaSemana(dataInicio);
    }
}
