package main.br.inatel.projetojava.DAO;

import java.sql.*;

public abstract class ConnectionDAO {

    Connection connection;         // Conexão com o banco

    // Parâmetros utilizados nas subclasses:

    PreparedStatement pst;         // Comando SQL com parâmetros
    Statement st;                  // Comando SQL simples (sem parâmetros)
    ResultSet rs;                  // Resultado das consultas SQL

    // Informações de acesso ao banco de dados:
    String database = "projeto_persona"; // Nome do BD
    String user = "root";
    String password = "zSh3rl0cK$20"; // zSh3rl0cK$20
    String url = "jdbc:mysql://localhost:3306/" + database;

    // Estabelecer a conexão com o banco:
    public void connectToDb(){
        try {
            connection = DriverManager.getConnection(url, user, password);
            // System.out.println("Conectado ao banco de dados!");

        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
    }
}
