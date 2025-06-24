package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.personagens.NPC;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NPCDAO extends ConnectionDAO {

    public boolean insertNPC(NPC npc) {
        connectToDb();
        String sql = "INSERT INTO NPC(nome, idade, genero, ocupacao, arcana) VALUES(?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, npc.getNome());
            pst.setInt(2, npc.getIdade());
            pst.setString(3, npc.getGenero());
            pst.setString(4, npc.getOcupacao());
            pst.setString(5, npc.getArcana());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir NPC: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public List<NPC> selectNPC() {
        List<NPC> npcs = new ArrayList<>();
        connectToDb();
        String sql = "SELECT * FROM NPC";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                NPC npc = new NPC(
                        rs.getString("nome"),
                        rs.getInt("idade"),
                        rs.getString("genero"),
                        rs.getString("ocupacao"),
                        rs.getString("arcana")
                );
                npcs.add(npc);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar NPCs: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return npcs;
    }

    public String selectArcana(String nome) {
        String arcana = null;
        connectToDb();
        String sql = "SELECT arcana FROM NPC WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                arcana = rs.getString("arcana");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar arcana do NPC: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return arcana;
    }

    public boolean updateNPC(NPC npc) {
        connectToDb();
        String sql = "UPDATE NPC SET idade=?, genero=?, ocupacao=?, arcana=? WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, npc.getIdade());
            pst.setString(2, npc.getGenero());
            pst.setString(3, npc.getOcupacao());
            pst.setString(4, npc.getArcana());
            pst.setString(5, npc.getNome());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar NPC: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public boolean deleteNPC(String nome) {
        connectToDb();
        String sql = "DELETE FROM NPC WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar NPC: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
