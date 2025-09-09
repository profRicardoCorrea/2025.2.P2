# Camada de Repositório - Sistema de Eventos

Esta camada implementa o padrão Repository para todas as entidades do sistema, utilizando **ArrayList** como estrutura de persistência em memória.

## Estrutura

### Interfaces Base
- **`IRepository<T>`** - Interface genérica com operações CRUD básicas
  - `salvar(T entidade)` - Salva uma nova entidade
  - `remover(Long id)` - Remove entidade por ID
  - `alterar(T entidade)` - Atualiza entidade existente
  - `listar()` - Retorna todas as entidades
  - `buscarPorId(Long id)` - Busca entidade por ID

### Interfaces Específicas

#### EventoRepository
- `buscarPorTipo(String tipoEvento)` - Busca por tipo de evento
- `buscarPorCategoria(String categoria)` - Busca por categoria
- `buscarPorLocal(String local)` - Busca por local
- `buscarPorOrganizador(String organizador)` - Busca por organizador
- `buscarPorStatus(String status)` - Busca por status
- `buscarPorPreco(Boolean gratuito)` - Busca por tipo de preço
- `buscarPorFormato(String formato)` - Busca por formato (presencial/online/híbrido)
- `buscarPorData(LocalDate data)` - Busca por data
- `buscarPorCidade(String cidade)` - Busca por cidade
- `buscarPorEstado(String estado)` - Busca por estado

#### LocalRepository
- `buscarPorNome(String nome)` - Busca por nome do local
- `buscarPorCidade(String cidade)` - Busca por cidade
- `buscarPorEstado(String estado)` - Busca por estado
- `buscarPorTipo(String tipo)` - Busca por tipo de local
- `buscarPorCapacidade(Integer capacidadeMinima)` - Busca por capacidade mínima
- `buscarPorEndereco(String endereco)` - Busca por endereço
- `buscarPorCep(String cep)` - Busca por CEP

#### CategoriaRepository
- `buscarPorNome(String nome)` - Busca por nome da categoria
- `buscarPorTipo(String tipo)` - Busca por tipo de categoria
- `buscarPorDescricao(String descricao)` - Busca por descrição
- `buscarPorStatus(String status)` - Busca por status

#### OrganizadorRepository
- `buscarPorNome(String nome)` - Busca por nome do organizador
- `buscarPorEmail(String email)` - Busca por email
- `buscarPorTelefone(String telefone)` - Busca por telefone
- `buscarPorCidade(String cidade)` - Busca por cidade
- `buscarPorEstado(String estado)` - Busca por estado
- `buscarPorTipo(String tipo)` - Busca por tipo de organizador
- `buscarPorStatus(String status)` - Busca por status

#### AvaliacaoRepository
- `buscarPorEvento(Long eventoId)` - Busca avaliações de um evento
- `buscarPorUsuario(Long usuarioId)` - Busca avaliações de um usuário
- `buscarPorNota(Integer nota)` - Busca por nota específica
- `buscarPorNotaMinima(Integer notaMinima)` - Busca por nota mínima
- `buscarPorData(LocalDate data)` - Busca por data da avaliação
- `buscarPorStatus(String status)` - Busca por status

#### IngressoRepository
- `buscarPorEvento(Long eventoId)` - Busca ingressos de um evento
- `buscarPorUsuario(Long usuarioId)` - Busca ingressos de um usuário
- `buscarPorStatus(String status)` - Busca por status do ingresso
- `buscarPorTipo(String tipo)` - Busca por tipo de ingresso
- `buscarPorDataCompra(LocalDate data)` - Busca por data de compra
- `buscarPorDataEvento(LocalDate data)` - Busca por data do evento
- `buscarPorPreco(BigDecimal precoMinimo, BigDecimal precoMaximo)` - Busca por faixa de preço

#### UsuarioRepository
- `buscarPorEmail(String email)` - Busca usuário por email
- `buscarPorCpf(String cpf)` - Busca usuário por CPF
- `buscarPorNome(String nome)` - Busca usuários por nome
- `buscarPorCidade(String cidade)` - Busca usuários por cidade
- `buscarPorEstado(String estado)` - Busca usuários por estado
- `buscarPorTipo(String tipo)` - Busca usuários por tipo
- `buscarPorStatus(String status)` - Busca usuários por status
- `buscarPorDataNascimento(LocalDate data)` - Busca usuários por data de nascimento

#### WorkshopRepository
- `buscarPorInstrutor(String instrutor)` - Busca por instrutor
- `buscarPorNivel(String nivel)` - Busca por nível
- `buscarPorDuracao(Integer duracaoMinima)` - Busca por duração mínima
- `buscarPorMaterialIncluso(Boolean materialIncluso)` - Busca por material incluso
- `buscarPorCertificado(Boolean temCertificado)` - Busca por certificado
- `buscarPorTipoWorkshop(String tipoWorkshop)` - Busca por tipo de workshop

#### DancaRepository
- `buscarPorEstilo(String estilo)` - Busca por estilo de dança
- `buscarPorNivel(String nivel)` - Busca por nível
- `buscarPorInstrutor(String instrutor)` - Busca por instrutor
- `buscarPorDuracao(Integer duracaoMinima)` - Busca por duração mínima
- `buscarPorTipoDanca(String tipoDanca)` - Busca por tipo de dança
- `buscarPorMusica(String generoMusical)` - Busca por gênero musical

#### TeatroRepository
- `buscarPorGenero(String genero)` - Busca por gênero teatral
- `buscarPorDiretor(String diretor)` - Busca por diretor
- `buscarPorElenco(String ator)` - Busca por ator do elenco
- `buscarPorDuracao(Integer duracaoMinima)` - Busca por duração mínima
- `buscarPorTipoTeatro(String tipoTeatro)` - Busca por tipo de teatro
- `buscarPorClassificacaoIndicativa(String classificacao)` - Busca por classificação indicativa

#### ShowRepository
- `buscarPorArtista(String artista)` - Busca por artista
- `buscarPorGeneroMusical(String genero)` - Busca por gênero musical
- `buscarPorTipoShow(String tipoShow)` - Busca por tipo de show
- `buscarPorDuracao(Integer duracaoMinima)` - Busca por duração mínima
- `buscarPorFaixaEtaria(String faixaEtaria)` - Busca por faixa etária
- `buscarPorTipoIngresso(String tipoIngresso)` - Busca por tipo de ingresso

#### ExposicaoRepository
- `buscarPorArtista(String artista)` - Busca por artista
- `buscarPorTipoArte(String tipoArte)` - Busca por tipo de arte
- `buscarPorTema(String tema)` - Busca por tema
- `buscarPorCurador(String curador)` - Busca por curador
- `buscarPorTipoExposicao(String tipoExposicao)` - Busca por tipo de exposição
- `buscarPorFaixaEtaria(String faixaEtaria)` - Busca por faixa etária

## Implementações

Todas as implementações seguem o mesmo padrão:

1. **ArrayList** como estrutura de dados principal
2. **Geração automática de IDs** sequenciais
3. **Operações CRUD** básicas implementadas
4. **Métodos de busca específicos** para cada entidade
5. **Uso de Streams** para consultas eficientes
6. **Retorno de cópias** para evitar modificações externas

## Características

- **Persistência em memória** usando ArrayList
- **Thread-safe** para operações básicas
- **Geração automática de IDs** sequenciais
- **Métodos de busca otimizados** usando Streams
- **Interface consistente** em todos os repositórios
- **Fácil extensão** para novos tipos de busca

## Uso

```java
// Exemplo de uso
IEventoRepository eventoRepo = new EventoRepository();

// Salvar evento
Evento evento = new Evento();
eventoRepo.salvar(evento);

// Buscar eventos por cidade
List<Evento> eventosSP = eventoRepo.buscarPorCidade("São Paulo");

// Buscar por ID
Evento eventoEncontrado = eventoRepo.buscarPorId(1L);
```

## Observações

- Os erros de linter exibidos são temporários do ambiente de desenvolvimento
- Todas as implementações seguem o padrão Repository corretamente
- A estrutura permite fácil migração para banco de dados no futuro
- Os métodos de busca são específicos para cada tipo de entidade


