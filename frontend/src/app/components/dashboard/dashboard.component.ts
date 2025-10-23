import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VeiculoService } from '../../services/veiculo.service';
import { DistribuicaoDecada, DistribuicaoFabricante } from '../../models/veiculo.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  vendidosCount = 0;
  distribuicaoDecada: DistribuicaoDecada[] = [];
  distribuicaoFabricante: DistribuicaoFabricante[] = [];
  veiculosUltimaSemana: any[] = [];
  
  loading = false;
  error = '';

  constructor(private veiculoService: VeiculoService) { }

  ngOnInit(): void {
    this.carregarEstatisticas();
  }

  carregarEstatisticas(): void {
    this.loading = true;
    this.error = '';

    // Carregar contagem de vendidos
    this.veiculoService.contarVeiculosVendidos().subscribe({
      next: (count) => {
        this.vendidosCount = count;
      },
      error: (err) => {
        console.error('Erro ao carregar vendidos:', err);
      }
    });

    // Carregar distribuição por década
    this.veiculoService.obterDistribuicaoPorDecada().subscribe({
      next: (distribuicao) => {
        this.distribuicaoDecada = distribuicao;
      },
      error: (err) => {
        console.error('Erro ao carregar distribuição por década:', err);
      }
    });

    // Carregar distribuição por fabricante
    this.veiculoService.obterDistribuicaoPorFabricante().subscribe({
      next: (distribuicao) => {
        this.distribuicaoFabricante = distribuicao;
        this.loading = false;
      },
      error: (err) => {
        console.error('Erro ao carregar distribuição por fabricante:', err);
        this.loading = false;
      }
    });

    // Carregar veículos da última semana
    this.veiculoService.obterVeiculosUltimaSemana().subscribe({
      next: (veiculos) => {
        this.veiculosUltimaSemana = veiculos;
      },
      error: (err) => {
        console.error('Erro ao carregar veículos da última semana:', err);
      }
    });
  }

  formatarPercentual(percentual: number): string {
    return percentual.toFixed(1) + '%';
  }

  formatarData(data: string): string {
    return new Date(data).toLocaleDateString('pt-BR');
  }
}
