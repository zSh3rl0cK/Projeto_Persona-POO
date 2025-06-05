package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.personagens.jogaveis.Protagonista;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProtagonistaDAO extends ConnectionDAO {

    public void testConnection() {
        this.connectToDb();
    }

    public int insertProtagonista(Protagonista protagonista) {
        int idProtagonista = -1; // valor padrão caso dê erro
        connectToDb();

        String sql = "INSERT INTO protagonista (nome, idade, genero, nivel, arcana, hp, sp, saldo, Ativador_idAtivador) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, protagonista.getNome());
            pst.setInt(2, protagonista.getIdade());
            pst.setString(3, protagonista.getGenero());
            pst.setInt(4, protagonista.getNivel());
            pst.setString(5, protagonista.getArcana());
            pst.setDouble(6, protagonista.getHp());
            pst.setDouble(7, protagonista.getSp());
            pst.setDouble(8, protagonista.getSaldo());
            pst.setInt(9, protagonista.ativador.getIdAtivador());

            int affectedRows = pst.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pst.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idProtagonista = generatedKeys.getInt(1);
                }
                generatedKeys.close();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir protagonista: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return idProtagonista;
    }

    public List<Protagonista> selectProtagonista() {
        connectToDb();
        List<Protagonista> protagonistas = new ArrayList<>();

        String sql = "SELECT * FROM protagonista";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);

            while (rs.next()) {
                Protagonista protagonista = new Protagonista(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("genero"),
                        rs.getInt("nivel"),
                        rs.getString("arcana"),
                        rs.getDouble("hp"),
                        rs.getDouble("sp"),
                        rs.getDouble("saldo"),
                        rs.getInt("Ativador_idAtivador"),
                        rs.getInt("idProtagonista")
                );
                protagonistas.add(protagonista);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar protagonistas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return protagonistas;
    }

    public void updateProtagonista(Protagonista protagonista) {
        connectToDb();
        String sql = "UPDATE protagonista SET nome=?, idade=?, genero=?, nivel=?, arcana=?, hp=?, sp=?, saldo=?, Ativador_idAtivador=? WHERE idProtagonista=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, protagonista.getNome());
            pst.setInt(2, protagonista.getIdade());
            pst.setString(3, protagonista.getGenero());
            pst.setInt(4, protagonista.getNivel());
            pst.setString(5, protagonista.getArcana());
            pst.setDouble(6, protagonista.getHp());
            pst.setDouble(7, protagonista.getSp());
            pst.setDouble(8, protagonista.getSaldo());
            pst.setInt(9, protagonista.ativador.getIdAtivador());
            pst.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar protagonista: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public void deleteProtagonista() {
        connectToDb();
        String sql = "DELETE FROM protagonista";
        try {
            st = connection.createStatement();
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar protagonista: " + e.getMessage());
        } finally {
            try {
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}