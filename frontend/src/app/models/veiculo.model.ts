export interface Veiculo {
  id?: number;
  veiculo: string;
  marca: string;
  ano: number;
  cor?: string;
  descricao?: string;
  vendido: boolean;
  created?: string;
  updated?: string;
}

export interface FiltroVeiculo {
  marca?: string;
  ano?: number;
  cor?: string;
}

export interface DistribuicaoDecada {
  decada: string;
  quantidade: number;
  percentual: number;
}

export interface DistribuicaoFabricante {
  fabricante: string;
  quantidade: number;
  percentual: number;
}
