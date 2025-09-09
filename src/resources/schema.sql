-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS loja_jogos;
USE loja_jogos;

-- Tabela de jogos digitais
CREATE TABLE IF NOT EXISTS jogos_digitais (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    desenvolvedora VARCHAR(100) NOT NULL,
    plataforma VARCHAR(50) NOT NULL,
    tamanho_gb DECIMAL(10,2) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Tabela de jogos físicos
CREATE TABLE IF NOT EXISTS jogos_fisicos (
    id BIGINT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL,
    desenvolvedora VARCHAR(100) NOT NULL,
    midia VARCHAR(50) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Índices para melhorar a performance das consultas
CREATE INDEX idx_jogos_digitais_plataforma ON jogos_digitais(plataforma);
CREATE INDEX idx_jogos_digitais_nome ON jogos_digitais(nome);
CREATE INDEX idx_jogos_fisicos_midia ON jogos_fisicos(midia);
CREATE INDEX idx_jogos_fisicos_nome ON jogos_fisicos(nome);

-- Comentários das tabelas
ALTER TABLE jogos_digitais COMMENT 'Tabela para armazenar informações de jogos digitais';
ALTER TABLE jogos_fisicos COMMENT 'Tabela para armazenar informações de jogos físicos'; 