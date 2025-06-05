package main.br.inatel.projetojava.DAO;

import java.sql.SQLException;
import java.util.ArrayList;

import main.br.inatel.projetojava.Model.itens.auxiliares.Consumiveis;

public class ConsumiveisDAO extends ConnectionDAO {

    public boolean insertConsumivel(Consumiveis consumivel) {
        connectToDb();
        String sql = "INSERT INTO consumiveis (nome, tipo, valor, status) VALUES (?,?,?,?)";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, consumivel.getNome());
            pst.setString(2, consumivel.getTipo());
            pst.setDouble(3, consumivel.getValor());
            pst.setString(4, consumivel.getStatus());
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao inserir consumível: " + e.getMessage());
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

    public ArrayList<Consumiveis> selectConsumivel() {
        ArrayList<Consumiveis> consumiveis = new ArrayList<>();
        connectToDb();
        String sql = "SELECT * FROM consumiveis";
        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                Consumiveis consumivel = new Consumiveis(
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getDouble("valor"),
                        rs.getString("status")
                );
                consumiveis.add(consumivel);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar consumíveis: " + e.getMessage());
        } finally {
            try {
                connection.close();
                st.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return consumiveis;
    }

    public boolean updateConsumivel(Consumiveis consumivel, String nomeAntigo) {
        connectToDb();
        String sql = "UPDATE consumiveis SET nome=?, tipo=?, valor=?, status=? WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, consumivel.getNome());
            pst.setString(2, consumivel.getTipo());
            pst.setDouble(3, consumivel.getValor());
            pst.setString(4, consumivel.getStatus());
            pst.setString(5, nomeAntigo);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar consumível: " + e.getMessage());
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

    public boolean deleteConsumivel(String nome) {
        connectToDb();
        String sql = "DELETE FROM consumiveis WHERE nome=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setString(1, nome);
            pst.execute();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao deletar consumível: " + e.getMessage());
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
