package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.itens.equipaveis.Arma;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArmaDAO extends ConnectionDAO {

    public boolean insertArma(Arma arma) {
        connectToDb();
        String sql = "INSERT INTO Arma(nome, tipo, valor, status, dano) values(?, ?, ?, ?, ?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, arma.getNome());
            pst.setString(2, arma.getTipo());
            pst.setDouble(3, arma.getValor());
            pst.setString(4, arma.getStatus());
            pst.setDouble(5, arma.getDano());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir arma: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
                pst.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public List<Arma> selectArmas() {
        List<Arma> armas = new ArrayList<>();
        connectToDb();
        String sql = "SELECT * FROM Arma";

        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Arma arma = new Arma(
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getString("status"),
                        rs.getDouble("dano")
                );
                armas.add(arma);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar armas: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return armas;
    }

    public int selectGunDamage(String nome){
        int damage = -1;
        connectToDb();
        String sql = "SELECT dano FROM Arma WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            rs = pst.executeQuery();
            if (rs.next()) {
                damage = rs.getInt("dano");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar arcana do NPC: " + e.getMessage());
        }
        return damage;
    }

    public boolean updateArma(Arma arma) {
        connectToDb();
        String sql = "UPDATE Arma SET tipo = ?, valor = ?, status = ?, dano = ? WHERE nome = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, arma.getTipo());
            pst.setDouble(2, arma.getValor());
            pst.setString(3, arma.getStatus());
            pst.setDouble(4, arma.getDano());
            pst.setString(5, arma.getNome());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar arma: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
                pst.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public boolean deleteArma(String nome) {
        connectToDb();
        String sql = "DELETE FROM Arma WHERE nome = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar arma: " + e.getMessage());
            return false;
        } finally {
            try {
                connection.close();
                pst.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }


}
