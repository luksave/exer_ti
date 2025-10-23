import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { VeiculoService } from '../../services/veiculo.service';
import { Veiculo, FiltroVeiculo } from '../../models/veiculo.model';
import { VeiculoFormComponent } from '../veiculo-form/veiculo-form.component';

@Component({
  selector: 'app-veiculo-list',
  standalone: true,
  imports: [CommonModule, FormsModule, VeiculoFormComponent],
  templateUrl: './veiculo-list.component.html',
  styleUrls: ['./veiculo-list.component.scss']
})
export class VeiculoListComponent implements OnInit {
  veiculos: Veiculo[] = [];
  marcasValidas: string[] = [];
  filtro: FiltroVeiculo = {};
  loading = false;
  error = '';
  showForm = false;
  veiculoEditando: Veiculo | null = null;

  constructor(private veiculoService: VeiculoService) { }

  ngOnInit(): void {
    this.carregarVeiculos();
    this.carregarMarcasValidas();
  }

  carregarVeiculos(): void {
    this.loading = true;
    this.error = '';
    
    this.veiculoService.getVeiculos(this.filtro).subscribe({
      next: (veiculos) => {
        this.veiculos = veiculos;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar veículos: ' + err.message;
        this.loading = false;
      }
    });
  }

  carregarMarcasValidas(): void {
    this.veiculoService.obterMarcasValidas().subscribe({
      next: (marcas) => {
        this.marcasValidas = marcas;
      },
      error: (err) => {
        console.error('Erro ao carregar marcas:', err);
      }
    });
  }

  aplicarFiltros(): void {
    this.carregarVeiculos();
  }

  limparFiltros(): void {
    this.filtro = {};
    this.carregarVeiculos();
  }

  novoVeiculo(): void {
    this.veiculoEditando = null;
    this.showForm = true;
  }

  editarVeiculo(veiculo: Veiculo): void {
    this.veiculoEditando = { ...veiculo };
    this.showForm = true;
  }

  excluirVeiculo(id: number): void {
    if (confirm('Tem certeza que deseja excluir este veículo?')) {
      this.veiculoService.deleteVeiculo(id).subscribe({
        next: () => {
          this.carregarVeiculos();
        },
        error: (err) => {
          this.error = 'Erro ao excluir veículo: ' + err.message;
        }
      });
    }
  }

  marcarComoVendido(veiculo: Veiculo): void {
    if (veiculo.id) {
      this.veiculoService.updateVeiculoParcial(veiculo.id, { vendido: true }).subscribe({
        next: () => {
          this.carregarVeiculos();
        },
        error: (err) => {
          this.error = 'Erro ao marcar como vendido: ' + err.message;
        }
      });
    }
  }

  onVeiculoSalvo(): void {
    this.showForm = false;
    this.veiculoEditando = null;
    this.carregarVeiculos();
  }

  onCancelar(): void {
    this.showForm = false;
    this.veiculoEditando = null;
  }
}
