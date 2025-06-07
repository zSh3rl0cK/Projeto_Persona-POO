DROP DATABASE IF EXISTS Projeto_persona;
CREATE DATABASE IF NOT EXISTS Projeto_persona;
USE Projeto_persona;

CREATE TABLE IF NOT EXISTS ativador (
  idAtivador INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  Tipo VARCHAR(45)
);

-- Tabela protagonista corrigida para corresponder aos dados do Java
-- Backup dos dados se necessário
DROP TABLE IF EXISTS protagonista;

-- Recriar com a estrutura correta
CREATE TABLE IF NOT EXISTS protagonista (
  idProtagonista INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45),
  idade INT,
  genero VARCHAR(45),
  nivel INT,
  arcana VARCHAR(45),
  hp DOUBLE,
  sp DOUBLE,
  saldo DOUBLE,
  Ativador_idAtivador INT NOT NULL,
  CONSTRAINT fk_Protagonista_Ativador FOREIGN KEY (Ativador_idAtivador)
    REFERENCES ativador(idAtivador) ON DELETE CASCADE
);

DROP TABLE IF EXISTS personas;
CREATE TABLE IF NOT EXISTS personas (
  idPersona INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45),
  nivel INT,
  arcana varchar(10),
  tipo1 varchar(10),
  tipo2 varchar(10),
  fraqueza varchar(20),
  resistencia varchar(20),
  dano int,
  Ativador_idAtivador INT,
  CONSTRAINT fk_Persona_Ativador FOREIGN KEY (Ativador_idAtivador)
    REFERENCES ativador(idAtivador) ON DELETE CASCADE
);

DROP TABLE IF EXISTS protagonistapersona;
CREATE TABLE IF NOT EXISTS protagonistapersona (
	Protagonista_idProtagonista INT NOT NULL,
    Persona_idPersona INT NOT NULL,
    
    CONSTRAINT fk_protagonistapersona_Protagonista FOREIGN KEY (Protagonista_idProtagonista)
		REFERENCES protagonista(idProtagonista) ON DELETE CASCADE,
        
	CONSTRAINT fk_protagonistapersona_Persona FOREIGN KEY (Persona_idPersona)
		REFERENCES personas(idPersona) ON DELETE CASCADE
);

-- Ou, se preferir uma tabela de relacionamento separada:
DROP TABLE IF EXISTS habilidades;
CREATE TABLE IF NOT EXISTS habilidades(
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) UNIQUE,
    tipo VARCHAR(50),
    efeito VARCHAR(255),
    dano DOUBLE DEFAULT 0
);

DROP TABLE IF EXISTS persona_has_habilidades;
CREATE TABLE IF NOT EXISTS persona_has_habilidades (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    persona_id INT NOT NULL,
    habilidade_id INT NOT NULL,
    CONSTRAINT fk_PersonaHab_Persona 
        FOREIGN KEY (persona_id) REFERENCES personas(idPersona) ON DELETE CASCADE,
    CONSTRAINT fk_PersonaHab_Habilidade 
        FOREIGN KEY (habilidade_id) REFERENCES habilidades(id) ON DELETE CASCADE,
    UNIQUE KEY uk_persona_habilidade (persona_id, habilidade_id)
);

DROP TABLE IF EXISTS npc;
CREATE TABLE IF NOT EXISTS npc (
  idNPC INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45),
  idade INT,
  genero VARCHAR(45),
  ocupacao varchar(60),
  arcana varchar(10),
  Protagonista_idProtagonista INT NULL,
  CONSTRAINT fk_NPC_Protagonista FOREIGN KEY (Protagonista_idProtagonista)
    REFERENCES protagonista(idProtagonista) ON DELETE CASCADE
);

-- Recriar tabela usuarios sem campos obrigatórios de FK
DROP TABLE IF EXISTS usuarios;
CREATE TABLE usuarios (
  idUsuarios INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  nome VARCHAR(45),
  idade INT,
  genero VARCHAR(45),
  nivel INT,
  arcana VARCHAR(10),
  hp DOUBLE,
  sp DOUBLE,
  papel VARCHAR(60),
  vilao BOOLEAN DEFAULT FALSE
);

-- Recriar a tabela de relacionamento
DROP TABLE IF EXISTS usuario_has_persona;
CREATE TABLE usuario_has_persona (
    idUsuarios INT NOT NULL,
    idPersona INT NOT NULL,
    
    PRIMARY KEY (idUsuarios, idPersona),
    
    CONSTRAINT fk_usuario_has_persona_usuario 
        FOREIGN KEY (idUsuarios) REFERENCES usuarios(idUsuarios) ON DELETE CASCADE,
        
    CONSTRAINT fk_usuario_has_persona_persona 
        FOREIGN KEY (idPersona) REFERENCES personas(idPersona) ON DELETE CASCADE
);

DROP TABLE IF EXISTS shadow;
CREATE TABLE IF NOT EXISTS shadow (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    nivel INT NOT NULL,
    arcana VARCHAR(20),
    tipos VARCHAR(100), -- Para armazenar tipos separados por vírgula
    fraqueza VARCHAR(20),
    resistencia VARCHAR(20),
    dano DOUBLE NOT NULL
);

DROP TABLE IF EXISTS equipamento;
CREATE TABLE IF NOT EXISTS equipamento (
  idEquipamento INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  Nome VARCHAR(45),
  Dano INT,
  Tipo VARCHAR(45),
  Qualidade VARCHAR(45),
  Efeito VARCHAR(45),
  Protagonista_idProtagonista INT NOT NULL,
  CONSTRAINT fk_Equipamento_Protagonista FOREIGN KEY (Protagonista_idProtagonista)
    REFERENCES protagonista(idProtagonista) ON DELETE CASCADE
);

DROP TABLE IF EXISTS usuario_has_equipamento;
CREATE TABLE IF NOT EXISTS usuario_has_equipamento (
  Usuarios_idUsuarios INT NOT NULL,
  Equipamento_idEquipamento INT NOT NULL,
  PRIMARY KEY (Usuarios_idUsuarios, Equipamento_idEquipamento),
  CONSTRAINT fk_Usuario_has_Equipamento_Usuarios FOREIGN KEY (Usuarios_idUsuarios)
    REFERENCES usuarios(idUsuarios) ON DELETE CASCADE,
  CONSTRAINT fk_Usuario_has_Equipamento_Equipamento FOREIGN KEY (Equipamento_idEquipamento)
    REFERENCES equipamento(idEquipamento) ON DELETE CASCADE
);

-- Tabela para Armas
DROP TABLE IF EXISTS Arma;
CREATE TABLE IF NOT EXISTS Arma (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    valor DOUBLE NOT NULL,
    status VARCHAR(50),
    dano DOUBLE NOT NULL
);

-- Tabela para Consumíveis
DROP TABLE IF EXISTS consumiveis;
CREATE TABLE IF NOT EXISTS consumiveis (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    valor DOUBLE NOT NULL,
    status VARCHAR(255) -- Para descrições mais longas como "Revive um aliado com 50% de HP"
);

-- Tabela base para Itens (se você quiser uma estrutura mais organizada)
-- Esta tabela pode servir como referência para todos os tipos de itens
DROP TABLE IF EXISTS itens;
CREATE TABLE IF NOT EXISTS itens (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    tipo_item ENUM('ARMA', 'CONSUMIVEL') NOT NULL,
    referencia_id INT NOT NULL, -- ID da tabela específica (Arma ou consumiveis)
    INDEX idx_tipo_ref (tipo_item, referencia_id)
);

-- Tabela para Loja de Itens (assumindo que TABLE se refere a uma tabela de estoque)
-- Baseado no seu código, parece que você está usando uma constante TABLE
DROP TABLE IF EXISTS loja_itens;
CREATE TABLE IF NOT EXISTS loja_itens (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    item_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    UNIQUE KEY uk_item (item_id)
);

-- OU, se você preferir uma abordagem mais específica:
DROP TABLE IF EXISTS loja_estoque;
CREATE TABLE IF NOT EXISTS loja_estoque (
    id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    item_id INT NOT NULL,
    quantidade INT NOT NULL DEFAULT 1,
    data_adicao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE KEY uk_item_estoque (item_id)
);

-- Triggers ou procedures para manter consistência (opcional)
-- Exemplo de trigger para atualizar a tabela itens quando inserir uma arma:
DELIMITER //
CREATE TRIGGER after_arma_insert 
AFTER INSERT ON Arma
FOR EACH ROW
BEGIN
    INSERT INTO itens (nome, tipo_item, referencia_id) 
    VALUES (NEW.nome, 'ARMA', NEW.id);
END//

CREATE TRIGGER after_consumivel_insert 
AFTER INSERT ON consumiveis
FOR EACH ROW
BEGIN
    INSERT INTO itens (nome, tipo_item, referencia_id) 
    VALUES (NEW.nome, 'CONSUMIVEL', NEW.id);
END//
DELIMITER ;



-- ========================
-- TABELA: ativador
-- ========================
INSERT INTO ativador (Tipo) VALUES ('Evoker'), ('App de Invocação'), ('Máscara');
DELETE FROM ativador WHERE idAtivador = 2;
INSERT INTO ativador (Tipo) VALUES ('Invocador de Sombras');
-- IDs: 1 - Evoker, 3 - Máscara, 4 - Invocador de Sombras

UPDATE ativador SET Tipo = 'Evoker teurgia' WHERE idAtivador = 1;
UPDATE ativador SET Tipo = 'Máscara Roubada' WHERE idAtivador = 3;

/* 
======================== Trecho comentado para não dar conflito com o JAVA ========================

-- ========================
-- TABELA: protagonista
-- ========================
INSERT INTO protagonista (Nome, Idade, genero, Ativador_idAtivador) VALUES
('Makoto Arisato', 17, 'Masculino', 1),     -- id = 1
('Ren Amamiya', 16, 'Masculino', 3),     -- id = 2
('Joker Clone', 17, 'Masculino', 4);     -- id = 3

DELETE FROM protagonista WHERE idProtagonista = 3;

INSERT INTO protagonista (Nome, Idade, genero, Ativador_idAtivador) VALUES
('Yu Narukami', 17, 'Masculino', 4);     -- id = 4

UPDATE protagonista SET Nome = 'Makoto Yuki' WHERE idProtagonista = 1;
UPDATE protagonista SET Idade = 18 WHERE idProtagonista = 2;

-- ========================
-- TABELA: persona (SEM referência à arcana)
-- ========================
INSERT INTO personas (nome, nivel, Ativador_idAtivador) VALUES
('Izanagi', 1, 4),          -- id = 1
('Izanagi-no-Okami', 2, 3), -- id = 2
('Orpheus', 3, 1);          -- id = 3

DELETE FROM personas WHERE idPersona = 3;

INSERT INTO personas (nome, nivel, Ativador_idAtivador) VALUES
('Thanatos', 50, 1);        -- id = 4

UPDATE personas SET nivel = 5 WHERE idPersona = 1;
UPDATE personas SET nome = 'Arsene' WHERE idPersona = 2;

-- ========================
-- TABELA: habilidades
-- ========================
INSERT INTO habilidades (Nome, Tipo, efeito) VALUES
('Agi', 'Mágica', 'Fogo'),     -- id = 1
('Zio', 'Mágica', 'Raio'),     -- id = 2
('Cleave', 'Física', 'Corte'); -- id = 3

DELETE FROM habilidades WHERE id = 3;

INSERT INTO habilidades (Nome, Tipo, efeito) VALUES
('Garula', 'Mágica', 'Vento'); -- id = 4

UPDATE habilidades SET Nome = 'Maragi' WHERE id = 1;
UPDATE habilidades SET efeito = 'Choque' WHERE id = 2;

-- ========================
-- TABELA: persona_has_habilidade
-- ========================
INSERT INTO persona_has_habilidades (persona_id, habilidade_id) VALUES
(1, 1), (4, 2), (2, 4);

DELETE FROM persona_has_habilidades WHERE persona_id = 2 AND habilidade_id = 4;
INSERT INTO persona_has_habilidades (persona_id, habilidade_id) VALUES (2, 4);

UPDATE persona_has_habilidades SET habilidade_id = 1 WHERE persona_id = 2;
UPDATE persona_has_habilidades SET habilidade_id = 2 WHERE persona_id = 1;

-- ========================
-- TABELA: npc (SEM referência à arcana)
-- ========================
INSERT INTO npc (nome, idade, genero, Protagonista_idProtagonista) VALUES
('Fuuka Yamagishi', 16, 'Feminino', 1),
('Ann Takamaki', 17, 'Feminino', 2),
('Chie Satonaka', 17, 'Feminino', 4);

DELETE FROM npc WHERE idNPC = 2;

INSERT INTO npc (nome, idade, genero, Protagonista_idProtagonista) VALUES
('Rise Kujikawa', 17, 'Feminino', 2);

UPDATE npc SET nome = 'Mitsuru Kirijo' WHERE idNPC = 1;
UPDATE npc SET idade = 18 WHERE idNPC = 3;

-- ========================
-- TABELA: usuarios
-- ========================
INSERT INTO usuarios (nome, idade, genero) VALUES
('Junpei Iori', 17, 'Masculino'),   -- id = 1
('Ryuji Sakamoto', 17, 'Masculino'),-- id = 2
('Yosuke Hanamura', 17, 'Masculino');-- id = 3

DELETE FROM usuarios WHERE idUsuarios = 2;

INSERT INTO usuarios (nome, idade, genero) VALUES
('Yusuke Kitagawa', 17, 'Masculino'); -- id = 4

UPDATE usuarios SET nome = 'Sanada Akihiko' WHERE idUsuarios = 1;
UPDATE usuarios SET idade = 18 WHERE idUsuarios = 3;

-- ========================
-- TABELA: equipamento
-- ========================
INSERT INTO equipamento (Nome, Dano, Tipo, Qualidade, Efeito, Protagonista_idProtagonista) VALUES
('Espada Curta', 50, 'Física', 'Comum', 'Crítico +5%', 1), -- id = 1
('Chicote Flamejante', 40, 'Fogo', 'Raro', 'Fogo +10%', 2), -- id = 2
('Kunai Dupla', 45, 'Física', 'Incomum', 'Velocidade +5%', 4); -- id = 3

DELETE FROM equipamento WHERE idEquipamento = 3;

INSERT INTO equipamento (Nome, Dano, Tipo, Qualidade, Efeito, Protagonista_idProtagonista) VALUES
('Bastão Mágico', 35, 'Mágica', 'Comum', 'Agi +5', 1); -- id = 4

UPDATE equipamento SET Qualidade = 'Épico' WHERE idEquipamento = 1;
UPDATE equipamento SET Efeito = 'Dano mágico +10%' WHERE idEquipamento = 2;

-- ========================
-- TABELA: usuario_has_equipamento
-- ========================
INSERT INTO usuario_has_equipamento (Usuarios_idUsuarios, Equipamento_idEquipamento) VALUES
(1, 1), (4, 4), (3, 2);

DELETE FROM usuario_has_equipamento WHERE Usuarios_idUsuarios = 3 AND Equipamento_idEquipamento = 2;
INSERT INTO usuario_has_equipamento VALUES (3, 2);

UPDATE usuario_has_equipamento SET Equipamento_idEquipamento = 2 WHERE Usuarios_idUsuarios = 1;
UPDATE usuario_has_equipamento SET Equipamento_idEquipamento = 1 WHERE Usuarios_idUsuarios = 3;

*/

-- Selects para ver todas as tabelas
SELECT * FROM ativador;
SELECT * FROM protagonista;
SELECT * FROM personas;
SELECT * FROM habilidades;
SELECT * FROM equipamento;
SELECT * FROM usuarios;
SELECT * FROM npc;

-- Selects com join:

SELECT 
    p.nome AS nomeProtagonista,
    GROUP_CONCAT(pe.nome SEPARATOR ', ') AS personas
FROM 
    protagonistapersona pp
JOIN 
    protagonista p ON pp.Protagonista_idProtagonista = p.idProtagonista
JOIN 
    personas pe ON pp.Persona_idPersona = pe.idPersona
GROUP BY 
    p.nome;

    
    -- Persona e Habilidade + persona_has_habilidade:
SELECT 
    p.Nome AS NomePersona,
    h.Nome AS NomeHabilidade,
    h.Tipo AS TipoHabilidades,
    h.Elemento AS ElementoHabilidade
FROM 
    personas p 
    JOIN persona_has_habilidade ph ON p.idPersona = ph.Persona_idPersona
    JOIN habilidades h ON h.idHabilidades = ph.Habilidades_idHabilidades
ORDER BY
    p.Nome, h.Nome;
    
    -- Usuários e Equipamentos + usuario_has_equipamento:
SELECT 
    u.idUsuarios,
    u.nome AS NomeUsuario,
    u.idade,
    u.genero,
    e.Nome AS NomeEquipamento,
    e.Dano,
    e.Tipo AS TipoEquipamento,
    e.Qualidade,
    e.Efeito
FROM 
    usuarios u
    JOIN usuario_has_equipamento ue ON u.idUsuarios = ue.Usuarios_idUsuarios
    JOIN equipamento e ON e.idEquipamento = ue.Equipamento_idEquipamento
ORDER BY 
    u.Nome, e.Nome;
    
    -- Protagonistas e Personas (sem arcana):
SELECT 
    p.idProtagonista,
    p.nome AS NomeProtagonista,
    pe.nome AS NomePersona,
    pe.nome,
    a.Tipo AS TipoAtivador
FROM 
    protagonista p
    JOIN ativador a ON p.Ativador_idAtivador = a.idAtivador
    JOIN personas pe ON a.idAtivador = pe.Ativador_idAtivador
ORDER BY 
    p.nome, pe.nome;
    
-- Uso de Functions --> Fazer um retorno do nome do Protagonista de índice arbitrário x
-- Essa Function é importante para busca rápida do nome do protagonista por seu ID 
DELIMITER $$
CREATE FUNCTION nome_protagonista(id INT)
RETURNS VARCHAR(45)
DETERMINISTIC
BEGIN
  DECLARE nome_resultado VARCHAR(45);
  
  SELECT Nome INTO nome_resultado
  FROM protagonista
  WHERE idProtagonista = id;
  
  RETURN nome_resultado;
END$$
DELIMITER ;

SELECT nome_protagonista(1);

-- Uso de View é muito interessante para consultas complexas:
-- No caso, as informações de um NPC podem ser essenciais para criação de um Menu de mostraInfo
-- As informações de NPCS menores do que 20 anos podem ser associadas aos alunos da escola
    -- Informações dos NPCs com idade < 20:
CREATE VIEW view_npcs_menores_20 AS
SELECT 
    idNPC,
    nome,
    idade,
    genero
FROM npc
WHERE Idade < 20;

SELECT * FROM view_npcs_menores_20;