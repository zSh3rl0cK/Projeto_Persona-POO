# TARTARUS

### Alunos: Felipe Tagawa Reis & Pedro Henrique Ribeiro Dias  
### Matrículas: 2037 e 529  
### Cursos: GEC e GES  

---

**TARTARUS** é um jogo inspirado em *Persona 3 Reload*, desenvolvido em **Java**. A proposta é simular o ambiente, as mecânicas e a atmosfera do jogo original, incluindo sistema de combate em turnos, interações sociais e músicas do jogo.

---

## 📦 Banco de Dados

Sistema de banco MySQL com tabelas estruturadas para representar os principais elementos do universo Persona:

- `Protagonista`  
- `Usuarios`  
- `Personas`  
- `Habilidades`  
- `ProtagonistaPersona`  
- `etc`...

---

## 📁 Estrutura de Arquivos

- `Projeto_Lab_BD`: Código SQL geral  
- `Projeto_UML`: Diagrama UML geral do projeto  
- `Integracao_SQL`: DAOs e classes de modelo para acesso ao banco  

---

## 🧩 Interação com o Usuário

Menus implementados:

- **Tela Inicial**  
  - Iniciar jogo  
  - Opções (volume)  
  - Créditos  
  - Sair

- **MenuDoJogo**  
  - Acesso aos menus principais

- **MenuGeral**  
  - Lógica geral de interações e chamadas de menus

- **MenuBuscas**  
  - Consultas de dados diversos do sistema

- **MenuCidade**  
  - Simula locomoção, combates e interações com NPCs

- **MenuSQL**  
  - Interface para comandos SQL integrados

- **Avaliação**  
  - Após o encerramento do jogo, o usuário pode deixar uma nota e comentário sobre sua experiência

---

## 🗂 Menus Detalhados

### 🎮 MenuDoJogo

1. MenuBuscas  
2. MenuCidade  
3. MenuSQL  

### 🔍 MenuBuscas

1. Dados do Protagonista  
2. Dados de Usuário de Persona  
3. Dados de NPC  
4. Acesso à loja de itens  
5. Dar ou equipar itens  
6. Dados de inimigos  
7. Inventário do protagonista  
8. Inventário de um usuário

### 🏙 MenuCidade

1. Locomoção por locais  
2. Busca de personagens  
3. Interações com aliados  
4. Combate (principal mecânica)  
5. Loja de itens  

### 💾 MenuSQL

- Comandos `INSERT`, `UPDATE`, `DELETE`, `SELECT` para até **12 opções** de gerenciamento das tabelas do banco.

---

## ⚔️ Mecânicas Principais

### 1. Combate

- Sistema baseado em turnos  
- Escolha de 3 aliados predefinidos  
- Enfrente **Sombras** ou **vilões humanos**  
- Ações disponíveis:
  - Atacar (físico ou habilidades de Persona)
  - Defender
  - Usar item
  - Fugir

- O protagonista pode trocar de Persona durante o combate  
- Inimigos atacam automaticamente  
- Algumas batalhas usam lógica de fraquezas elementares (*One More*)

### 2. Interações

- Apenas o protagonista interage com NPCs/aliados  
- Evolução de vínculo por Arcana (nível até 10)  
- Interações especiais com:
  - `Yukari`
  - `Mitsuru`
  - `Aigis`
  - `Fuuka`

- Cada nível desbloqueia diálogos e mensagens únicas  
- Sistema semelhante ao de *Social Links*

---

## 🧠 Extras Técnicos

- Arquitetura baseada em **MVC**  
- Uso de **DAO Pattern** para persistência de dados  
- Validação de entradas e tratamento de erros  
- Uso de Threads para garantir imersão  
- Modularização para fácil expansão de funcionalidades  

---

## 📌 Créditos Finais

Inspirado por *Persona 3 Reload* (Atlus)  
Desenvolvido como parte do projeto final das disciplinas Banco de Dados & POO  
