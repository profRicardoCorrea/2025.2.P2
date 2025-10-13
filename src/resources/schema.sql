-- =====================================================
-- SCHEMA COMPLETO DO SISTEMA DE EVENTOS CULTURAIS
-- =====================================================

-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS sistema_eventos_culturais;
USE sistema_eventos_culturais;

-- =====================================================
-- TABELAS PRINCIPAIS
-- =====================================================

-- Tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(10),
    data_nascimento DATE,
    genero VARCHAR(20),
    tipo_usuario VARCHAR(50) DEFAULT 'USUARIO_COMUM',
    ativo BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultimo_acesso TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    preferencias_culturais TEXT,
    interesses TEXT, -- Armazenar como string separada por vírgulas
    recebe_notificacoes BOOLEAN DEFAULT TRUE,
    foto_perfil VARCHAR(255)
);

-- Tabela de organizadores
CREATE TABLE IF NOT EXISTS organizadores (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    razao_social VARCHAR(255),
    cnpj VARCHAR(18) UNIQUE,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(10),
    website VARCHAR(255),
    redes_sociais TEXT,
    descricao TEXT,
    categoria VARCHAR(100), -- PRODUCAO, CASA_SHOW, TEATRO, GALERIA, ORGANIZACAO_EVENTOS
    tipo_organizacao VARCHAR(50) DEFAULT 'PESSOA_JURIDICA', -- PESSOA_FISICA, PESSOA_JURIDICA
    ativo BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    logo_url VARCHAR(255),
    certificacoes TEXT,
    experiencia TEXT,
    anos_experiencia INT,
    especialidades TEXT, -- Armazenar como string separada por vírgulas
    contato_responsavel VARCHAR(100),
    telefone_responsavel VARCHAR(20),
    email_responsavel VARCHAR(100),
    verificado BOOLEAN DEFAULT FALSE,
    status_verificacao VARCHAR(50) DEFAULT 'PENDENTE', -- PENDENTE, EM_ANALISE, APROVADO, REJEITADO
    observacoes_verificacao TEXT
);

-- Tabela de locais
CREATE TABLE IF NOT EXISTS locais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao TEXT,
    endereco VARCHAR(255) NOT NULL,
    cidade VARCHAR(100) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    cep VARCHAR(10),
    bairro VARCHAR(100),
    complemento VARCHAR(255),
    telefone VARCHAR(20),
    email VARCHAR(100),
    website VARCHAR(255),
    redes_sociais TEXT,
    tipo_local VARCHAR(50), -- TEATRO, CASA_SHOW, GALERIA, MUSEU, AUDITORIO, ESPACO_ABERTO
    capacidade INT,
    acessibilidade BOOLEAN DEFAULT FALSE,
    recursos_acessibilidade TEXT,
    estacionamento BOOLEAN DEFAULT FALSE,
    tipo_estacionamento VARCHAR(50), -- GRATUITO, PAGO, VALET
    preco_estacionamento DECIMAL(10,2),
    wifi BOOLEAN DEFAULT FALSE,
    ar_condicionado BOOLEAN DEFAULT FALSE,
    bar BOOLEAN DEFAULT FALSE,
    restaurante BOOLEAN DEFAULT FALSE,
    horario_funcionamento VARCHAR(255),
    dias_funcionamento VARCHAR(255),
    coordenadas_gps VARCHAR(100),
    imagem_url VARCHAR(255),
    planta_local VARCHAR(255),
    regras TEXT,
    restricoes TEXT,
    ativo BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    organizador_id BIGINT,
    status VARCHAR(50) DEFAULT 'ATIVO', -- ATIVO, INATIVO, EM_REFORMA, FECHADO_TEMPORARIAMENTE
    fotos TEXT, -- Armazenar URLs de fotos separadas por vírgulas
    video_url VARCHAR(255),
    mapa_url VARCHAR(255),
    observacoes TEXT,
    verificado BOOLEAN DEFAULT FALSE,
    status_verificacao VARCHAR(50) DEFAULT 'PENDENTE',
    FOREIGN KEY (organizador_id) REFERENCES organizadores(id) ON DELETE SET NULL
);

-- Tabela de categorias
CREATE TABLE IF NOT EXISTS categorias (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    cor VARCHAR(7), -- Ex: #RRGGBB
    icone VARCHAR(50),
    slug VARCHAR(100) UNIQUE NOT NULL,
    ativa BOOLEAN DEFAULT TRUE,
    ordem INT DEFAULT 0,
    categoria_pai_id BIGINT,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    metadados TEXT,
    numero_eventos INT DEFAULT 0,
    tags TEXT, -- Armazenar como string separada por vírgulas
    imagem_url VARCHAR(255),
    destaque BOOLEAN DEFAULT FALSE,
    tipo_categoria VARCHAR(50) DEFAULT 'PRINCIPAL', -- PRINCIPAL, SECUNDARIA, ESPECIAL
    FOREIGN KEY (categoria_pai_id) REFERENCES categorias(id) ON DELETE SET NULL
);

-- Tabela base de eventos (AbstractEvent)
CREATE TABLE IF NOT EXISTS eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    descricao TEXT,
    data_hora DATETIME,
    data_inicio DATE,
    data_fim DATE,
    local_nome VARCHAR(255), -- Nome do local, pode ser diferente do id do local
    endereco VARCHAR(255),
    cidade VARCHAR(100),
    estado VARCHAR(50),
    cep VARCHAR(10),
    preco DECIMAL(10,2),
    capacidade INT,
    ingressos_vendidos INT DEFAULT 0,
    categoria_nome VARCHAR(100), -- Nome da categoria, pode ser diferente do id da categoria
    status VARCHAR(50) DEFAULT 'ATIVO', -- ATIVO, CANCELADO, ENCERRADO
    imagem_url VARCHAR(255),
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    organizador_id BIGINT,
    local_id BIGINT,
    categoria_id BIGINT,
    tipo_evento_discriminator VARCHAR(50) NOT NULL, -- Para diferenciar Danca, Show, Teatro, etc.
    
    FOREIGN KEY (organizador_id) REFERENCES organizadores(id) ON DELETE SET NULL,
    FOREIGN KEY (local_id) REFERENCES locais(id) ON DELETE SET NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id) ON DELETE SET NULL
);

-- Tabela de avaliações
CREATE TABLE IF NOT EXISTS avaliacoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    nota INT NOT NULL, -- 1 a 5
    comentario TEXT,
    categoria_avaliacao VARCHAR(50), -- GERAL, PRODUCAO, ARTISTAS, LOCAL, PREÇO
    data_avaliacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    anonima BOOLEAN DEFAULT FALSE,
    moderada BOOLEAN DEFAULT FALSE,
    status VARCHAR(50) DEFAULT 'PENDENTE', -- PENDENTE, APROVADA, REJEITADA
    resposta_organizador TEXT,
    data_resposta TIMESTAMP,
    util BOOLEAN DEFAULT FALSE,
    numero_votos_util INT DEFAULT 0,
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- Tabela de ingressos
CREATE TABLE IF NOT EXISTS ingressos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) UNIQUE NOT NULL,
    evento_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    tipo_ingresso VARCHAR(50) NOT NULL, -- PADRAO, VIP, MEIA_ENTRADA, GRATUITO
    preco DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) DEFAULT 'RESERVADO', -- RESERVADO, PAGO, CANCELADO, UTILIZADO
    data_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_utilizacao TIMESTAMP,
    data_cancelamento TIMESTAMP,
    forma_pagamento VARCHAR(50),
    observacoes TEXT,
    transferivel BOOLEAN DEFAULT FALSE,
    nome_beneficiario VARCHAR(100),
    cpf_beneficiario VARCHAR(14),
    telefone_beneficiario VARCHAR(20),
    email_beneficiario VARCHAR(100),
    local_retirada VARCHAR(255),
    horario_retirada VARCHAR(100),
    assento VARCHAR(50),
    setor VARCHAR(50),
    fila VARCHAR(50),
    porta_entrada VARCHAR(50),
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE
);

-- =====================================================
-- TABELAS DE EVENTOS ESPECÍFICOS (HERANÇA)
-- =====================================================

-- Tabela para eventos de Dança
CREATE TABLE IF NOT EXISTS eventos_danca (
    evento_id BIGINT PRIMARY KEY,
    coreografo VARCHAR(100),
    companhia VARCHAR(100),
    estilo_danca VARCHAR(100), -- CLASSICA, CONTEMPORANEA, JAZZ, HIP_HOP, SALSA, TANGO
    duracao_minutos INT,
    bailarinos TEXT, -- Armazenar como string separada por vírgulas
    musica VARCHAR(255),
    compositor VARCHAR(100),
    cenografia VARCHAR(255),
    figurino VARCHAR(255),
    iluminacao VARCHAR(255),
    tem_solista BOOLEAN DEFAULT FALSE,
    bailarino_solista VARCHAR(100),
    nivel_tecnico VARCHAR(50), -- INICIANTE, INTERMEDIARIO, AVANCADO, PROFISSIONAL
    tem_interacao_publico BOOLEAN DEFAULT FALSE,
    tipo_apresentacao VARCHAR(50), -- SOLO, DUETO, GRUPO, COREOGRAFIA_COLETIVA
    restricao_idade VARCHAR(50),
    permite_entrada_infantil BOOLEAN DEFAULT FALSE,
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE
);

-- Tabela para eventos de Show
CREATE TABLE IF NOT EXISTS eventos_show (
    evento_id BIGINT PRIMARY KEY,
    artista_principal VARCHAR(255),
    artistas_convidados TEXT, -- Armazenar como string separada por vírgulas
    genero_musical VARCHAR(100),
    duracao_minutos INT,
    tipo_show VARCHAR(100), -- AO_VIVO, DJ, BANDA, SOLO
    tem_abertura BOOLEAN DEFAULT FALSE,
    artista_abertura VARCHAR(255),
    equipamento_especial VARCHAR(255),
    tem_vip BOOLEAN DEFAULT FALSE,
    preco_vip DECIMAL(10,2),
    restricao_idade VARCHAR(50),
    permite_entrada_infantil BOOLEAN DEFAULT FALSE,
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE
);

-- Tabela para eventos de Teatro
CREATE TABLE IF NOT EXISTS eventos_teatro (
    evento_id BIGINT PRIMARY KEY,
    diretor VARCHAR(100),
    autor VARCHAR(100),
    companhia VARCHAR(100),
    genero_teatral VARCHAR(100), -- DRAMA, COMEDIA, TRAGEDIA, MUSICAL, INFANTIL
    duracao_minutos INT,
    numero_atos INT,
    elenco TEXT, -- Armazenar como string separada por vírgulas
    sinopse TEXT,
    cenografia VARCHAR(255),
    figurino VARCHAR(255),
    iluminacao VARCHAR(255),
    sonoplastia VARCHAR(255),
    tem_intervalo BOOLEAN DEFAULT FALSE,
    duracao_intervalo INT,
    classificacao_indicativa VARCHAR(50),
    permite_entrada_infantil BOOLEAN DEFAULT FALSE,
    tipo_teatro VARCHAR(100), -- PALCO_ITALIANO, ARENA, TEATRO_DE_RUA
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE
);

-- Tabela para eventos de Exposição
CREATE TABLE IF NOT EXISTS eventos_exposicao (
    evento_id BIGINT PRIMARY KEY,
    curador VARCHAR(100),
    tipo_exposicao VARCHAR(100), -- PERMANENTE, TEMPORARIA, ITINERANTE
    artistas TEXT, -- Armazenar como string separada por vírgulas
    periodo_historico VARCHAR(100),
    tecnica_artistica VARCHAR(100),
    numero_obras INT,
    catalogo_url VARCHAR(255),
    tem_visita_guiada BOOLEAN DEFAULT FALSE,
    horario_visita VARCHAR(100),
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE
);

-- Tabela para eventos de Workshop
CREATE TABLE IF NOT EXISTS eventos_workshop (
    evento_id BIGINT PRIMARY KEY,
    instrutor VARCHAR(100),
    organizador VARCHAR(100),
    area_cultural VARCHAR(100), -- MUSICA, TEATRO, DANCA, ARTES_VISUAIS, LITERATURA
    duracao_minutos INT,
    numero_sessoes INT,
    vagas_disponiveis INT,
    nivel VARCHAR(50), -- INICIANTE, INTERMEDIARIO, AVANCADO
    material_necessario TEXT,
    pre_requisitos TEXT,
    certificado BOOLEAN DEFAULT FALSE,
    tipo_certificado VARCHAR(100),
    material_incluso BOOLEAN DEFAULT FALSE,
    preco_material DECIMAL(10,2),
    localizacao_especifica VARCHAR(255),
    tem_coffee_break BOOLEAN DEFAULT FALSE,
    horario_coffee_break VARCHAR(100),
    permite_auditoria BOOLEAN DEFAULT FALSE,
    preco_auditoria DECIMAL(10,2),
    metodologia TEXT,
    objetivo_aprendizado TEXT,
    
    FOREIGN KEY (evento_id) REFERENCES eventos(id) ON DELETE CASCADE
);

-- =====================================================
-- ÍNDICES PARA MELHORIA DE PERFORMANCE
-- =====================================================

CREATE INDEX idx_usuarios_email ON usuarios(email);
CREATE INDEX idx_usuarios_cpf ON usuarios(cpf);
CREATE INDEX idx_organizadores_email ON organizadores(email);
CREATE INDEX idx_organizadores_cnpj ON organizadores(cnpj);
CREATE INDEX idx_locais_cidade_estado ON locais(cidade, estado);
CREATE INDEX idx_locais_tipo_local ON locais(tipo_local);
CREATE INDEX idx_categorias_nome ON categorias(nome);
CREATE INDEX idx_categorias_slug ON categorias(slug);
CREATE INDEX idx_eventos_data_hora ON eventos(data_hora);
CREATE INDEX idx_eventos_cidade_estado ON eventos(cidade, estado);
CREATE INDEX idx_eventos_status ON eventos(status);
CREATE INDEX idx_eventos_organizador_id ON eventos(organizador_id);
CREATE INDEX idx_eventos_local_id ON eventos(local_id);
CREATE INDEX idx_eventos_categoria_id ON eventos(categoria_id);
CREATE INDEX idx_avaliacoes_evento_id ON avaliacoes(evento_id);
CREATE INDEX idx_avaliacoes_usuario_id ON avaliacoes(usuario_id);
CREATE INDEX idx_ingressos_evento_id ON ingressos(evento_id);
CREATE INDEX idx_ingressos_usuario_id ON ingressos(usuario_id);
CREATE INDEX idx_ingressos_codigo ON ingressos(codigo);

-- =====================================================
-- DADOS INICIAIS (OPCIONAL)
-- =====================================================

-- Inserir categorias padrão
INSERT INTO categorias (nome, descricao, slug, tipo_categoria) VALUES
('Dança', 'Eventos relacionados à dança em todas as suas modalidades', 'danca', 'PRINCIPAL'),
('Música', 'Shows, concertos e eventos musicais', 'musica', 'PRINCIPAL'),
('Teatro', 'Peças teatrais, musicais e apresentações dramáticas', 'teatro', 'PRINCIPAL'),
('Artes Visuais', 'Exposições, galerias e eventos de artes plásticas', 'artes-visuais', 'PRINCIPAL'),
('Workshop', 'Cursos, oficinas e atividades educativas', 'workshop', 'PRINCIPAL'),
('Literatura', 'Eventos literários, lançamentos de livros e saraus', 'literatura', 'SECUNDARIA'),
('Cinema', 'Festivais de cinema, mostras e exibições especiais', 'cinema', 'SECUNDARIA'),
('Cultura Popular', 'Festivais folclóricos e eventos de cultura popular', 'cultura-popular', 'SECUNDARIA');

-- Inserir tipos de locais padrão
INSERT INTO locais (nome, endereco, cidade, estado, tipo_local, capacidade, ativo) VALUES
('Teatro Municipal', 'Praça da República, 1', 'São Paulo', 'SP', 'TEATRO', 1000, TRUE),
('Casa de Show Blue Note', 'Rua Bela Cintra, 14', 'São Paulo', 'SP', 'CASA_SHOW', 500, TRUE),
('Galeria de Arte Moderna', 'Av. Paulista, 1578', 'São Paulo', 'SP', 'GALERIA', 200, TRUE),
('Auditório Ibirapuera', 'Parque Ibirapuera', 'São Paulo', 'SP', 'AUDITORIO', 800, TRUE);

-- Inserir organizador padrão
INSERT INTO organizadores (nome, email, telefone, categoria, tipo_organizacao, verificado, status_verificacao) VALUES
('Sistema Administrativo', 'admin@sistema.com', '(11) 99999-9999', 'ORGANIZACAO_EVENTOS', 'PESSOA_JURIDICA', TRUE, 'APROVADO');

-- =====================================================
-- VIEWS ÚTEIS PARA CONSULTAS
-- =====================================================

-- View para eventos com informações completas
CREATE VIEW vw_eventos_completos AS
SELECT 
    e.id,
    e.titulo,
    e.descricao,
    e.data_hora,
    e.preco,
    e.capacidade,
    e.status,
    e.imagem_url,
    o.nome as organizador_nome,
    l.nome as local_nome,
    l.endereco as local_endereco,
    l.cidade as local_cidade,
    l.estado as local_estado,
    c.nome as categoria_nome,
    e.tipo_evento_discriminator
FROM eventos e
LEFT JOIN organizadores o ON e.organizador_id = o.id
LEFT JOIN locais l ON e.local_id = l.id
LEFT JOIN categorias c ON e.categoria_id = c.id;

-- View para ingressos com informações do evento e usuário
CREATE VIEW vw_ingressos_completos AS
SELECT 
    i.id,
    i.codigo,
    i.tipo_ingresso,
    i.preco,
    i.status,
    i.data_compra,
    e.titulo as evento_titulo,
    e.data_hora as evento_data,
    u.nome as usuario_nome,
    u.email as usuario_email
FROM ingressos i
JOIN eventos e ON i.evento_id = e.id
JOIN usuarios u ON i.usuario_id = u.id;

-- View para avaliações com informações completas
CREATE VIEW vw_avaliacoes_completas AS
SELECT 
    a.id,
    a.nota,
    a.comentario,
    a.data_avaliacao,
    a.status,
    e.titulo as evento_titulo,
    u.nome as usuario_nome,
    o.nome as organizador_nome
FROM avaliacoes a
JOIN eventos e ON a.evento_id = e.id
JOIN usuarios u ON a.usuario_id = u.id
LEFT JOIN organizadores o ON e.organizador_id = o.id;

-- =====================================================
-- PROCEDURES ÚTEIS
-- =====================================================

DELIMITER //

-- Procedure para atualizar estatísticas de eventos
CREATE PROCEDURE sp_atualizar_estatisticas_evento(IN evento_id BIGINT)
BEGIN
    DECLARE total_ingressos INT DEFAULT 0;
    DECLARE total_avaliacoes INT DEFAULT 0;
    DECLARE media_notas DECIMAL(3,2) DEFAULT 0;
    
    -- Contar ingressos vendidos
    SELECT COUNT(*) INTO total_ingressos 
    FROM ingressos 
    WHERE evento_id = evento_id AND status IN ('PAGO', 'UTILIZADO');
    
    -- Contar avaliações e calcular média
    SELECT COUNT(*), AVG(nota) INTO total_avaliacoes, media_notas
    FROM avaliacoes 
    WHERE evento_id = evento_id AND status = 'APROVADA';
    
    -- Atualizar tabela de eventos
    UPDATE eventos 
    SET ingressos_vendidos = total_ingressos
    WHERE id = evento_id;
    
    -- Atualizar contador de eventos na categoria
    UPDATE categorias c
    SET numero_eventos = (
        SELECT COUNT(*) 
        FROM eventos e 
        WHERE e.categoria_id = c.id AND e.status = 'ATIVO'
    );
END //

-- Procedure para cancelar evento e ingressos
CREATE PROCEDURE sp_cancelar_evento(IN evento_id BIGINT, IN motivo TEXT)
BEGIN
    DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
        RESIGNAL;
    END;
    
    START TRANSACTION;
    
    -- Cancelar todos os ingressos não utilizados
    UPDATE ingressos 
    SET status = 'CANCELADO', 
        data_cancelamento = NOW(),
        observacoes = CONCAT(IFNULL(observacoes, ''), ' | Cancelado: ', motivo)
    WHERE evento_id = evento_id 
    AND status IN ('RESERVADO', 'PAGO');
    
    -- Cancelar o evento
    UPDATE eventos 
    SET status = 'CANCELADO',
        data_atualizacao = NOW()
    WHERE id = evento_id;
    
    COMMIT;
END //

DELIMITER ;

-- =====================================================
-- TRIGGERS PARA AUDITORIA E INTEGRIDADE
-- =====================================================

-- Trigger para atualizar data_atualizacao automaticamente
DELIMITER //

CREATE TRIGGER tr_eventos_update 
BEFORE UPDATE ON eventos
FOR EACH ROW
BEGIN
    SET NEW.data_atualizacao = NOW();
END //

CREATE TRIGGER tr_usuarios_update 
BEFORE UPDATE ON usuarios
FOR EACH ROW
BEGIN
    SET NEW.ultimo_acesso = NOW();
END //

CREATE TRIGGER tr_organizadores_update 
BEFORE UPDATE ON organizadores
FOR EACH ROW
BEGIN
    SET NEW.data_atualizacao = NOW();
END //

CREATE TRIGGER tr_locais_update 
BEFORE UPDATE ON locais
FOR EACH ROW
BEGIN
    SET NEW.data_atualizacao = NOW();
END //

CREATE TRIGGER tr_categorias_update 
BEFORE UPDATE ON categorias
FOR EACH ROW
BEGIN
    SET NEW.data_atualizacao = NOW();
END //

DELIMITER ;

-- =====================================================
-- COMENTÁRIOS FINAIS
-- =====================================================

/*
ESTRUTURA DO BANCO DE DADOS:

1. TABELAS PRINCIPAIS:
   - usuarios: Dados dos usuários do sistema
   - organizadores: Dados dos organizadores de eventos
   - locais: Locais onde os eventos acontecem
   - categorias: Categorias de eventos (com hierarquia)
   - eventos: Tabela base para todos os eventos
   - avaliacoes: Avaliações dos eventos pelos usuários
   - ingressos: Ingressos vendidos para os eventos

2. TABELAS DE HERANÇA:
   - eventos_danca: Campos específicos para eventos de dança
   - eventos_show: Campos específicos para shows musicais
   - eventos_teatro: Campos específicos para peças teatrais
   - eventos_exposicao: Campos específicos para exposições
   - eventos_workshop: Campos específicos para workshops

3. RECURSOS IMPLEMENTADOS:
   - Índices para melhorar performance
   - Views para consultas complexas
   - Procedures para operações específicas
   - Triggers para auditoria automática
   - Dados iniciais para categorias e locais padrão
   - Constraints de integridade referencial
   - Campos de auditoria (data_criacao, data_atualizacao)

4. CONSIDERAÇÕES TÉCNICAS:
   - Uso de AUTO_INCREMENT para IDs
   - Campos TEXT para dados longos
   - DECIMAL para valores monetários
   - TIMESTAMP para datas/horas
   - BOOLEAN para flags
   - VARCHAR com tamanhos apropriados
   - Foreign Keys com CASCADE/SET NULL apropriados
   - Índices em campos frequentemente consultados

5. ESCALABILIDADE:
   - Estrutura preparada para crescimento
   - Índices otimizados para consultas comuns
   - Views para simplificar consultas complexas
   - Procedures para operações em lote
   - Triggers para manter consistência automática
*/