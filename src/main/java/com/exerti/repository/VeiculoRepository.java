package com.exerti.repository;

import com.exerti.controller.FiltroVeiculo;
import com.exerti.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    
    // Buscar veículos por filtro (valores podem ser nulos)
    @Query("SELECT v FROM Veiculo v " +
           "WHERE (:#{#filtro.marca} IS NULL OR v.marca = :#{#filtro.marca}) " +
           "AND (:#{#filtro.ano} IS NULL OR v.ano = :#{#filtro.ano}) " +
           "AND (:#{#filtro.cor} IS NULL OR v.cor = :#{#filtro.cor})")
    List<Veiculo> findByFiltro(@Param("filtro") FiltroVeiculo filtro);
    
    // Buscar veículos não vendidos
    @Query("SELECT v FROM Veiculo v WHERE v.vendido = false")
    List<Veiculo> findVeiculosDisponiveis();
    
    // Buscar veículos por período (ano)
    @Query("SELECT v FROM Veiculo v WHERE v.ano BETWEEN :anoInicio AND :anoFim")
    List<Veiculo> findByAnoBetween(@Param("anoInicio") Integer anoInicio, @Param("anoFim") Integer anoFim);
    
    // Contar veículos por status de venda
    long countByVendido(Boolean vendido);
    
    // Buscar veículos por texto na descrição (SEGURO)
    @Query("SELECT v FROM Veiculo v WHERE v.descricao LIKE CONCAT('%', :texto, '%')")
    List<Veiculo> findByDescricaoContaining(@Param("texto") String texto);
    
    // Buscar distribuição por década de fabricação
    @Query("SELECT CONCAT(CAST(FLOOR(v.ano/10)*10 AS string), '-', CAST(FLOOR(v.ano/10)*10+9 AS string)) as decada, " +
           "COUNT(v) as quantidade " +
           "FROM Veiculo v " +
           "GROUP BY FLOOR(v.ano/10) " +
           "ORDER BY FLOOR(v.ano/10)")
    List<Object[]> findDistribuicaoPorDecada();
    
    // Buscar distribuição por fabricante
    @Query("SELECT v.marca as fabricante, COUNT(v) as quantidade " +
           "FROM Veiculo v " +
           "GROUP BY v.marca " +
           "ORDER BY COUNT(v) DESC")
    List<Object[]> findDistribuicaoPorFabricante();
    
    // Buscar veículos cadastrados na última semana
    @Query("SELECT v FROM Veiculo v WHERE v.created >= :dataInicio ORDER BY v.created DESC")
    List<Veiculo> findVeiculosCadastradosNaUltimaSemana(@Param("dataInicio") LocalDateTime dataInicio);
}
