# Frontend - Sistema de Veículos

Interface web para gerenciamento de veículos, construída com Angular 17.

## O que faz

Este frontend permite gerenciar um catálogo de veículos com funcionalidades básicas de CRUD e algumas estatísticas interessantes.

### Principais funcionalidades:
- Listar veículos com filtros (marca, ano, cor)
- Cadastrar novos veículos
- Editar informações existentes
- Marcar veículos como vendidos
- Dashboard com estatísticas de vendas
- Distribuição por década de fabricação
- Contagem por fabricante

## Como rodar

### Pré-requisitos
- Node.js 18 ou superior
- npm 8 ou superior

### Instalação
```bash
npm install
npm start
```

A aplicação ficará disponível em `http://localhost:4200`.

### Build para produção
```bash
npm run build
```

## Configuração

Por padrão, o frontend se conecta com a API em `http://localhost:8080`. Se precisar alterar isso, edite o arquivo `src/app/services/veiculo.service.ts`:

```typescript
private apiUrl = 'http://localhost:8080/api/veiculos';
```

## Estrutura do projeto

```
src/app/
├── components/
│   ├── veiculo-list/     # Lista de veículos
│   ├── veiculo-form/     # Formulário de cadastro/edição
│   └── dashboard/        # Estatísticas
├── services/             # Comunicação com API
├── models/               # Interfaces TypeScript
└── shared/               # Componentes reutilizáveis
```

## Componentes principais

**VeiculoListComponent**: Lista todos os veículos com filtros e ações (criar, editar, excluir, marcar como vendido).

**VeiculoFormComponent**: Formulário para cadastro e edição com validações.

**DashboardComponent**: Mostra estatísticas de vendas e distribuições.

## API endpoints utilizados

- `GET /api/veiculos` - Listar veículos
- `POST /api/veiculos` - Criar veículo
- `PUT /api/veiculos/{id}` - Atualizar veículo
- `DELETE /api/veiculos/{id}` - Excluir veículo
- `GET /api/veiculos/vendidos/count` - Contar vendidos
- `GET /api/veiculos/distribuicao/decada` - Distribuição por década
- `GET /api/veiculos/distribuicao/fabricante` - Distribuição por fabricante
- `GET /api/veiculos/ultima-semana` - Veículos da última semana

## Tecnologias

- Angular 17
- TypeScript
- SCSS
- RxJS
- HTTP Client

## Problemas comuns

**Erro de CORS**: Verifique se o backend está configurado para aceitar requisições do frontend.

**Dependências não instaladas**: Execute `npm install`.

**Porta em uso**: Use `ng serve --port 4201` para usar outra porta.

---

Este projeto faz parte de um exercício de entrevista técnica.