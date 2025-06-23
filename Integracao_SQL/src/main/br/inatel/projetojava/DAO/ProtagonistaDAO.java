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
            pst.setInt(9, protagonista.getAtivador().getIdAtivador());

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

    public boolean existeProtagonista(String nome) {
        connectToDb();
        boolean existe = false;

        String sql = "SELECT COUNT(*) FROM protagonista WHERE nome = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();

            if (rs.next()) {
                existe = rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar protagonista: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return existe;
    }

    /*
      Insere protagonista apenas se não existir um com o mesmo nome
     */
    public int insertProtagonistaSemDuplicata(Protagonista protagonista) {
        // Verifica se já existe
        if (existeProtagonista(protagonista.getNome())) {
            /*
            System.out.println("❌ Protagonista '" + protagonista.getNome() + "' já existe no banco de dados!");
            System.out.println("   Use o menu de atualização se quiser modificar os dados.");
            */
            return -1; // Retorna -1 para indicar que não foi inserido
        }

        // Se não existe, insere normalmente
        int idInserido = insertProtagonista(protagonista);
        if (idInserido > 0) {
            System.out.println("✅ Protagonista '" + protagonista.getNome() + "' inserido com sucesso! ID: " + idInserido);
        }

        return idInserido;
    }

    /*
     Busca protagonista por nome
     */
    public Protagonista buscarProtagonistaPorNome(String nome) {
        connectToDb();
        Protagonista protagonista = null;

        String sql = "SELECT * FROM protagonista WHERE nome = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();

            if (rs.next()) {
                protagonista = new Protagonista(
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
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar protagonista: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return protagonista;
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
            pst.setInt(9, protagonista.getAtivador().getIdAtivador());
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