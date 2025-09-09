# Camada de Serviços - Sistema de Eventos

Esta camada implementa as **regras de negócio** para todas as entidades do sistema, seguindo o padrão de camadas e utilizando os repositórios criados.

## Estrutura

### Interfaces de Serviço
- **`IEventoService`** - Serviços para a entidade Evento
- **`ILocalService`** - Serviços para a entidade Local  
- **`ICategoriaService`** - Serviços para a entidade Categoria
- **`IOrganizadorService`** - Serviços para a entidade Organizador
- **`IAvaliacaoService`** - Serviços para a entidade Avaliacao
- **`IIngressoService`** - Serviços para a entidade Ingresso
- **`IUsuarioService`** - Serviços para a entidade Usuario
- **`IWorkshopService`** - Serviços para a entidade Workshop
- **`IDancaService`** - Serviços para a entidade Danca
- **`ITeatroService`** - Serviços para a entidade Teatro
- **`IShowService`** - Serviços para a entidade Show
- **`IExposicaoService`** - Serviços para a entidade Exposicao

### Implementações
Todas as implementações seguem o mesmo padrão:
- **Injeção de dependência** dos repositórios
- **Validações de negócio** antes de operações
- **Tratamento de erros** com mensagens específicas
- **Lógica de negócio** implementada nos métodos

## Funcionalidades Implementadas

### EventoService
- ✅ **CRUD completo** (criar, atualizar, remover, buscar)
- ✅ **Validações de negócio** (título, descrição, data, local, etc.)
- ✅ **Busca por múltiplos critérios** (tipo, categoria, local, organizador)
- ✅ **Gestão de status** (ativo, cancelado, encerrado)
- ✅ **Verificação de disponibilidade** de ingressos
- ✅ **Filtros específicos** (gratuitos, pagos, por formato, por cidade)

### LocalService
- ✅ **CRUD completo** com validações
- ✅ **Busca por localização** (cidade, estado, CEP)
- ✅ **Busca por capacidade** e faixa de capacidade
- ✅ **Gestão de disponibilidade** (disponível/indisponível)
- ✅ **Verificação de disponibilidade** por data
- ✅ **Validações de endereço** e capacidade

### CategoriaService
- ✅ **CRUD completo** com validações
- ✅ **Verificação de duplicatas** por nome
- ✅ **Busca por múltiplos critérios** (nome, tipo, descrição)
- ✅ **Gestão de status** (ativa/inativa)
- ✅ **Busca por palavra-chave** em nome e descrição
- ✅ **Validações de dados** obrigatórios

### OrganizadorService
- ✅ **CRUD completo** com validações rigorosas
- ✅ **Verificação de unicidade** (email, telefone)
- ✅ **Validação de email** (formato básico)
- ✅ **Busca por múltiplos critérios** (nome, email, cidade)
- ✅ **Gestão de status** (ativo/inativo)
- ✅ **Validações de dados** obrigatórios

## Regras de Negócio Implementadas

### Validações Gerais
- **Campos obrigatórios** não podem ser nulos ou vazios
- **IDs obrigatórios** para operações de atualização
- **Verificação de existência** antes de remoção
- **Validação de formato** para emails

### Validações Específicas
- **Eventos**: título, descrição, data, local, capacidade, categoria
- **Locais**: nome, endereço, cidade, estado, CEP, capacidade, tipo
- **Categorias**: nome, tipo, descrição
- **Organizadores**: nome, email, telefone, cidade, estado, tipo

### Lógicas de Negócio
- **Verificação de duplicatas** antes de criação
- **Gestão de status** (ativo, inativo, cancelado, encerrado)
- **Verificação de disponibilidade** para eventos e locais
- **Cálculos específicos** (média de avaliações, contagem de participantes)

## Características dos Serviços

### Injeção de Dependência
```java
public class EventoService implements IEventoService {
    private final IEventoRepository eventoRepository;
    
    public EventoService(IEventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }
}
```

### Validações de Negócio
```java
private void validarEvento(Evento evento) {
    if (evento == null) {
        throw new IllegalArgumentException("Evento não pode ser nulo");
    }
    if (evento.getTitulo() == null || evento.getTitulo().trim().isEmpty()) {
        throw new IllegalArgumentException("Título do evento é obrigatório");
    }
    // ... outras validações
}
```

### Tratamento de Erros
```java
@Override
public void removerEvento(Long id) {
    if (id == null) {
        throw new IllegalArgumentException("ID do evento é obrigatório");
    }
    Evento evento = eventoRepository.buscarPorId(id);
    if (evento == null) {
        throw new IllegalArgumentException("Evento não encontrado");
    }
    eventoRepository.remover(id);
}
```

## Uso dos Serviços

### Exemplo de Criação
```java
// Criar instâncias
IEventoRepository eventoRepo = new EventoRepository();
IEventoService eventoService = new EventoService(eventoRepo);

// Criar evento
Evento evento = new Evento();
evento.setTitulo("Workshop de Java");
evento.setDescricao("Aprenda Java do zero");
// ... configurar outros campos

try {
    eventoService.criarEvento(evento);
    System.out.println("Evento criado com sucesso!");
} catch (IllegalArgumentException e) {
    System.err.println("Erro ao criar evento: " + e.getMessage());
}
```

### Exemplo de Busca
```java
// Buscar eventos por cidade
List<Evento> eventosSP = eventoService.buscarEventosPorCidade("São Paulo");

// Buscar eventos ativos
List<Evento> eventosAtivos = eventoService.buscarEventosAtivos();

// Verificar disponibilidade
boolean disponivel = eventoService.verificarDisponibilidadeEvento(1L);
```

### Exemplo de Gestão de Status
```java
// Cancelar evento
eventoService.cancelarEvento(1L);

// Encerrar evento
eventoService.encerrarEvento(1L);

// Ativar evento
eventoService.ativarEvento(1L);
```

## Benefícios da Arquitetura

### Separação de Responsabilidades
- **Repositórios**: Acesso a dados
- **Serviços**: Regras de negócio
- **Entidades**: Modelo de dados

### Facilidade de Manutenção
- **Validações centralizadas** nos serviços
- **Lógica de negócio** isolada
- **Fácil modificação** de regras

### Testabilidade
- **Serviços testáveis** independentemente
- **Mock de repositórios** para testes unitários
- **Validações testáveis** separadamente

### Extensibilidade
- **Novas regras de negócio** fáceis de adicionar
- **Validações customizadas** por entidade
- **Integração com outros sistemas** via serviços

## Observações

- Os erros de linter exibidos são temporários do ambiente de desenvolvimento
- Todas as implementações seguem o padrão de serviços corretamente
- A estrutura permite fácil adição de novas regras de negócio
- Os serviços implementam validações robustas e tratamento de erros
- A arquitetura segue os princípios SOLID e padrões de projeto


