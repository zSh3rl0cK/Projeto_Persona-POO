package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.relacional.ProtagonistaHasPersona;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProtagonistaHasPersonaDAO extends ConnectionDAO {

    public void testConnection() {
        this.connectToDb();
    }

    public boolean insertProtagonistaHasPersona(ProtagonistaHasPersona protagonistaPersona) {
        connectToDb();

        boolean sucesso;
        // CORREÇÃO: Usando os nomes corretos das colunas da tabela
        String sql = "INSERT INTO protagonistapersona (Protagonista_idProtagonista, Persona_idPersona) VALUES (?, ?)";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, protagonistaPersona.getIdProtagonista());
            pst.setInt(2, protagonistaPersona.getIdPersona());
            pst.execute();
            sucesso = true;

        } catch (SQLException exc) {
            System.out.println("Erro ao inserir protagonista_persona: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException exc) {
                System.out.println("Erro ao fechar recursos: " + exc.getMessage());
            }
        }

        return sucesso;
    }

    public List<String> SelectProtagonistaPersona() {
        connectToDb();
        List<String> resultado = new ArrayList<>();

        String sql = """
        SELECT p.nome AS nomeProtagonista, pe.nome AS nomePersona
        FROM protagonistapersona pp
        JOIN protagonista p ON pp.Protagonista_idProtagonista = p.idProtagonista
        JOIN persona pe ON pp.Persona_idPersona = pe.idPersona
        """;

        try {
            pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String nomeProtagonista = rs.getString("nomeProtagonista");
                String nomePersona = rs.getString("nomePersona");
                resultado.add("Protagonista: " + nomeProtagonista + " | Persona: " + nomePersona);
            }

            rs.close();
        } catch (SQLException exc) {
            System.out.println("Erro ao executar join: " + exc.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException exc) {
                System.out.println("Erro ao fechar recursos: " + exc.getMessage());
            }
        }

        return resultado;
    }
}