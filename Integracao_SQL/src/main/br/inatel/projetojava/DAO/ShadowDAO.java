package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.personas.seres.Personas;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ShadowDAO extends ConnectionDAO {

    //Inserir shadow no banco
    public void insertShadow(Personas shadow) {
        connectToDb();
        String sql = "INSERT INTO shadow(nome, nivel, arcana, tipos, fraqueza, resistencia, dano) VALUES(?,?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, shadow.getNome());
            pst.setInt(2, shadow.getNivel());
            pst.setString(3, shadow.getArcana());
            pst.setString(4, String.join(",", shadow.getTipo()));
            pst.setString(5, shadow.getFraqueza());
            pst.setString(6, shadow.getResistencia());
            pst.setDouble(7, shadow.getDano());
            pst.execute();
            // System.out.println("Shadow inserido com sucesso: " + shadow.getNome());
        } catch (SQLException e) {
            System.out.println("Erro ao inserir Shadow: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    //Buscar shadow pelo nome
    public Personas getShadowByName(String nome) {
        connectToDb();
        Personas shadow = null;
        String sql = "SELECT * FROM shadow WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                // CORREÇÃO: Convertendo a string de tipos de volta para ArrayList
                String tiposString = rs.getString("tipos");
                ArrayList<String> tipos = new ArrayList<>();
                if (tiposString != null && !tiposString.isEmpty()) {
                    tipos.addAll(Arrays.asList(tiposString.split(",")));
                }

                shadow = new Personas(
                        rs.getString("nome"),
                        rs.getInt("nivel"),
                        rs.getString("arcana"),
                        tipos,
                        rs.getString("fraqueza"),
                        rs.getString("resistencia"),
                        rs.getInt("dano"),
                        rs.getInt("id")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Shadow: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
        return shadow;
    }

    //Atualizar shadow
    public void updateShadow(Personas shadow) {
        connectToDb();
        String sql = "UPDATE shadow SET nivel=?, arcana=?, tipos=?, fraqueza=?, resistencia=?, dano=? WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, shadow.getNivel());
            pst.setString(2, shadow.getArcana());
            pst.setString(3, String.join(",", shadow.getTipo()));
            pst.setString(4, shadow.getFraqueza());
            pst.setString(5, shadow.getResistencia());
            pst.setDouble(6, shadow.getDano());
            pst.setString(7, shadow.getNome());
            pst.execute();
            System.out.println("Shadow atualizado com sucesso: " + shadow.getNome());
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar Shadow: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    //Deletar shadow
    public void deleteShadow(String nome) {
        connectToDb();
        String sql = "DELETE FROM shadow WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.execute();
            System.out.println("Shadow deletado com sucesso: " + nome);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar Shadow: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    //Verificar se shadow existe
    public boolean shadowExists(String nome) {
        connectToDb();
        String sql = "SELECT COUNT(*) FROM shadow WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Erro ao verificar Shadow: " + e.getMessage());
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }
}