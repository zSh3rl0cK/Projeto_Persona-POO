### Alunos: Felipe Tagawa Reis & Pedro Henrique Ribeiro Dias.

Matrículas: 2037 e 529.

Cursos : GEC e GES

## Sobre o Projeto

Esse jogo baseado em Persona 3 Reload é uma aplicação desenvolvida em Java, 

## Estrutura do Banco de Dados

O banco de dados MySQL inclui tabelas para gerenciar raças, classes, personagens, perícias, itens, entre outros. As tabelas principais são:

- `Raca`
- `Classe`
- `Personagem`
- `Pericia`
- `Personagem_Pericia`
- `Item`
- `Arma`
- `Ferramenta`

## Arquivos de Banco de Dados

Dentro da pasta `databaseSQL`, na raiz do projeto, estão os arquivos:

- `db_der.drawio` e `db.drawio`: Diagramas do banco de dados.
- `db.sql`: Script de criação das tabelas.
- `test_cases.sql`: Casos de teste.

## Conexão com o Banco de Dados

O programa espera se conectar a um banco de dados chamado 'javaquest'. As credenciais de acesso padrão são:

- Usuário: `root`
- Senha: `root`

Estas configurações estão definidas no arquivo `ConnectionDAO.java`. É importante garantir que o banco de dados MySQL esteja configurado corretamente com estas credenciais para o funcionamento adequado do sistema.

## Menu de Opções

O sistema conta com um menu interativo que inclui:

1. Criar Personagem
2. Deletar Personagem
3. Ver Todos Personagens
4. Atualizar Personagem
5. Atacar Personagem
6. Sair


