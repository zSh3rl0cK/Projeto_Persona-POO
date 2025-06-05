package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.itens.geral.Itens;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LojadeItensDAO extends ConnectionDAO {

    private static final String TABLE = "loja_itens";

    public void insertItem(Itens item, int itemId, int quantidade) {
        connectToDb();
        String sql = "INSERT INTO " + TABLE + " (item_id, quantidade) VALUES (?, ?) " +
                "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, itemId);
            pst.setInt(2, quantidade);
            pst.execute();
            // System.out.println("Item adicionado ao estoque: " + item.getNome() + " (Qtd: " + quantidade + ")");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    // Método alternativo que busca o ID do item pelo nome
    public void insertItemByName(String nomeItem, int quantidade) {
        connectToDb();

        // Primeiro, busca o ID do item (você pode buscar em arma ou consumiveis)
        String findIdSql = """
            SELECT id, 'ARMA' as tipo FROM Arma WHERE nome = ?
            UNION
            SELECT id, 'CONSUMIVEL' as tipo FROM consumiveis WHERE nome = ?
        """;

        try {
            pst = connection.prepareStatement(findIdSql);
            pst.setString(1, nomeItem);
            pst.setString(2, nomeItem);
            rs = pst.executeQuery();

            if (rs.next()) {
                int itemId = rs.getInt("id");
                String tipo = rs.getString("tipo");

                // Agora insere na loja
                String insertSql = "INSERT INTO " + TABLE + " (item_id, quantidade) VALUES (?, ?) " +
                        "ON DUPLICATE KEY UPDATE quantidade = quantidade + VALUES(quantidade)";

                pst = connection.prepareStatement(insertSql);
                pst.setInt(1, itemId);
                pst.setInt(2, quantidade);
                pst.execute();

                System.out.println("Item " + nomeItem + " (" + tipo + ") adicionado ao estoque");
            } else {
                System.out.println("Item não encontrado: " + nomeItem);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item por nome: " + e.getMessage());
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

    public void atualizarQuantidade(int itemId, int novaQuantidade) {
        connectToDb();
        String sql = "UPDATE " + TABLE + " SET quantidade = ? WHERE item_id = ?";

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, novaQuantidade);
            pst.setInt(2, itemId);
            pst.execute();
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar quantidade: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }

    public Map<Integer, Integer> getItens() {
        Map<Integer, Integer> itens = new HashMap<>();
        connectToDb();
        String sql = "SELECT item_id, quantidade FROM " + TABLE;

        try {
            st = connection.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
                itens.put(rs.getInt("item_id"), rs.getInt("quantidade"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
        return itens;
    }
}
