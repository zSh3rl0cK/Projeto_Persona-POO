package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.personagens.NPC;
import main.br.inatel.projetojava.Model.personas.seres.Personas;
import main.br.inatel.projetojava.Model.personas.seres.Shadow;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShadowDAO extends ConnectionDAO {

    //Inserir shadow no banco
    public void insertShadow(Shadow shadow) {
        connectToDb();
        String sql = "INSERT INTO shadow(nome, hp,  nivel, arcana, tipos, fraqueza, resistencia, dano) VALUES(?,?,?,?,?,?,?,?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, shadow.getNome());
            pst.setInt(2, shadow.getHp());
            pst.setInt(3, shadow.getNivel());
            pst.setString(4, shadow.getArcana());
            pst.setString(5, String.join(",", shadow.getTipo()));
            pst.setString(6, shadow.getFraqueza());
            pst.setString(7, shadow.getResistencia());
            pst.setDouble(8, shadow.getDano());
            pst.execute();
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

    //Buscar shadow
    public List<Shadow> selectShadow() {
        List<Shadow> shadows = new ArrayList<>();
        connectToDb();
        String sql = "SELECT * FROM shadow";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                String tiposString = rs.getString("tipos");
                List<String> tiposList = Arrays.asList(tiposString.split(","));

                Shadow shadow = new Shadow(
                        rs.getString("nome"),
                        rs.getInt("hp"),
                        rs.getInt("nivel"),
                        rs.getString("arcana"),
                        tiposList,
                        rs.getString("fraqueza"),
                        rs.getString("resistencia"),
                        rs.getInt("dano")
                );
                shadows.add(shadow);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar Shadow: " + e.getMessage());
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return shadows;
    }

    public String selectShadowName(String arcana){
        String nome = null;
        connectToDb();
        String sql = "SELECT nome FROM shadow WHERE arcana=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, arcana);
            rs = pst.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar nome de Shadow: " + e.getMessage());
        }
        return nome;
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