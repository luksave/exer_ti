import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Veiculo, FiltroVeiculo, DistribuicaoDecada, DistribuicaoFabricante } from '../models/veiculo.model';

@Injectable({
  providedIn: 'root'
})
export class VeiculoService {
  private apiUrl = 'http://localhost:8080/api/veiculos';

  constructor(private http: HttpClient) { }

  // Buscar todos os veículos com filtros opcionais
  getVeiculos(filtro?: FiltroVeiculo): Observable<Veiculo[]> {
    let params = new HttpParams();
    
    if (filtro?.marca) {
      params = params.set('marca', filtro.marca);
    }
    if (filtro?.ano) {
      params = params.set('ano', filtro.ano.toString());
    }
    if (filtro?.cor) {
      params = params.set('cor', filtro.cor);
    }

    return this.http.get<Veiculo[]>(this.apiUrl, { params });
  }

  // Buscar veículo por ID
  getVeiculoById(id: number): Observable<Veiculo> {
    return this.http.get<Veiculo>(`${this.apiUrl}/${id}`);
  }

  // Criar novo veículo
  createVeiculo(veiculo: Veiculo): Observable<Veiculo> {
    return this.http.post<Veiculo>(this.apiUrl, veiculo);
  }

  // Atualizar veículo completo
  updateVeiculo(id: number, veiculo: Veiculo): Observable<Veiculo> {
    return this.http.put<Veiculo>(`${this.apiUrl}/${id}`, veiculo);
  }

  // Atualizar veículo parcial
  updateVeiculoParcial(id: number, veiculo: Partial<Veiculo>): Observable<Veiculo> {
    return this.http.patch<Veiculo>(`${this.apiUrl}/${id}`, veiculo);
  }

  // Excluir veículo
  deleteVeiculo(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // Contar veículos vendidos
  contarVeiculosVendidos(): Observable<number> {
    return this.http.get<number>(`${this.apiUrl}/vendidos/count`);
  }

  // Obter distribuição por década
  obterDistribuicaoPorDecada(): Observable<DistribuicaoDecada[]> {
    return this.http.get<DistribuicaoDecada[]>(`${this.apiUrl}/distribuicao/decada`);
  }

  // Obter distribuição por fabricante
  obterDistribuicaoPorFabricante(): Observable<DistribuicaoFabricante[]> {
    return this.http.get<DistribuicaoFabricante[]>(`${this.apiUrl}/distribuicao/fabricante`);
  }

  // Obter veículos da última semana
  obterVeiculosUltimaSemana(): Observable<Veiculo[]> {
    return this.http.get<Veiculo[]>(`${this.apiUrl}/ultima-semana`);
  }

  // Obter marcas válidas
  obterMarcasValidas(): Observable<string[]> {
    return this.http.get<string[]>(`${this.apiUrl}/marcas-validas`);
  }
}
