import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Veiculo } from '../../models/veiculo.model';
import { VeiculoService } from '../../services/veiculo.service';

@Component({
  selector: 'app-veiculo-form',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './veiculo-form.component.html',
  styleUrls: ['./veiculo-form.component.scss']
})
export class VeiculoFormComponent implements OnInit {
  @Input() veiculo: Veiculo | null = null;
  @Input() marcasValidas: string[] = [];
  @Output() veiculoSalvo = new EventEmitter<void>();
  @Output() cancelar = new EventEmitter<void>();

  formData: Veiculo = {
    veiculo: '',
    marca: '',
    ano: new Date().getFullYear(),
    cor: '',
    descricao: '',
    vendido: false
  };

  loading = false;
  error = '';

  constructor(private veiculoService: VeiculoService) { }

  ngOnInit(): void {
    if (this.veiculo) {
      this.formData = { ...this.veiculo };
    }
  }

  onSubmit(): void {
    if (!this.validarFormulario()) {
      return;
    }

    this.loading = true;
    this.error = '';

    const operacao = this.veiculo?.id 
      ? this.veiculoService.updateVeiculo(this.veiculo.id, this.formData)
      : this.veiculoService.createVeiculo(this.formData);

    operacao.subscribe({
      next: () => {
        this.loading = false;
        this.veiculoSalvo.emit();
      },
      error: (err) => {
        this.loading = false;
        this.error = 'Erro ao salvar veículo: ' + err.message;
      }
    });
  }

  onCancel(): void {
    this.cancelar.emit();
  }

  private validarFormulario(): boolean {
    if (!this.formData.veiculo?.trim()) {
      this.error = 'Nome do veículo é obrigatório';
      return false;
    }

    if (!this.formData.marca?.trim()) {
      this.error = 'Marca é obrigatória';
      return false;
    }

    if (!this.formData.ano || this.formData.ano < 1900 || this.formData.ano > new Date().getFullYear() + 1) {
      this.error = 'Ano deve ser entre 1900 e ' + (new Date().getFullYear() + 1);
      return false;
    }

    if (!this.marcasValidas.includes(this.formData.marca)) {
      this.error = 'Marca deve ser uma das marcas aceitas pelo sistema';
      return false;
    }

    return true;
  }
}
