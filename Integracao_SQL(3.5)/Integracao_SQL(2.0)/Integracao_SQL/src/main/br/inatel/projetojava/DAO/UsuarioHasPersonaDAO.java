package main.br.inatel.projetojava.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioHasPersonaDAO extends ConnectionDAO {

    public boolean insertUsuarioHasPersona(int idUsuarios, int idPersona) {
        connectToDb();

        // Primeiro, validar se os IDs existem
        if (!usuarioExiste(idUsuarios)) {
            System.out.println("Erro: Usuario com ID " + idUsuarios + " não existe!");
            return false;
        }

        if (!personaExiste(idPersona)) {
            System.out.println("Erro: Persona com ID " + idPersona + " não existe!");
            return false;
        }

        String sql = "INSERT INTO usuario_has_persona (idUsuarios, idPersona) VALUES (?, ?)";
        boolean sucesso = false;

        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idUsuarios);
            pst.setInt(2, idPersona);
            pst.execute();
            // System.out.println("Relação Usuario-Persona inserida com sucesso! (Usuario: " + idUsuarios + ", Persona: " + idPersona + ")");
            sucesso = true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir relação: " + e.getMessage());
            e.printStackTrace(); // Para ver o stack trace completo

        } finally {
            try {
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }

        return sucesso;
    }

    // Método auxiliar para verificar se usuario existe
    private boolean usuarioExiste(int idUsuarios) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE idUsuarios = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idUsuarios);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar usuario: " + e.getMessage());
        }
        return false;
    }

    // Método auxiliar para verificar se persona existe
    private boolean personaExiste(int idPersona) {
        String sql = "SELECT COUNT(*) FROM personas WHERE idPersona = ?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idPersona);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar persona: " + e.getMessage());
        }
        return false;
    }

    public void deleteUsuarioPersona(int idUsuario, int idPersona) {
        connectToDb();
        String sql = "DELETE FROM usuario_has_persona WHERE usuario_id=? AND persona_id=?";
        try {
            pst = connection.prepareStatement(sql);
            pst.setInt(1, idUsuario);
            pst.setInt(2, idPersona);
            pst.execute();
            System.out.println("Relação Usuario-Persona deletada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar relação: " + e.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }


}
