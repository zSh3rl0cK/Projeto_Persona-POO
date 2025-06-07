package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.personas.Habilidades;

import java.io.PrintStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class HabilidadesDAO extends ConnectionDAO {
    public HabilidadesDAO() {}

    public void testConnection(){
        this.connectToDb();
    }

    public boolean insertHabilidade(Habilidades habilidade) {
        this.connectToDb();
        String sql = "INSERT IGNORE INTO habilidades (nome, tipo, efeito, dano) VALUES (?, ?, ?, ?)";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, habilidade.nome());
            this.pst.setString(2, habilidade.tipo());
            this.pst.setString(3, habilidade.efeito());
            this.pst.setDouble(4, habilidade.dano());
            this.pst.execute();
            sucesso = true;
        } catch (SQLException exc) {
            System.out.println("Erro ao inserir habilidade base: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                if (this.pst != null) this.pst.close();
                if (this.connection != null) this.connection.close();
            } catch (SQLException exc) {
                System.out.println("Erro ao fechar recursos: " + exc.getMessage());
            }
        }

        return sucesso;
    }

    public boolean updateHabilidade(int id, Habilidades habilidade) {
        this.connectToDb();
        String sql = "UPDATE Habilidades SET nome = ?, tipo = ?, efeito = ?, dano = ? WHERE id = ?";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, habilidade.nome());
            this.pst.setString(2, habilidade.tipo());
            this.pst.setString(3, habilidade.efeito());
            this.pst.setDouble(4, habilidade.dano());
            this.pst.setInt(5, id);
            this.pst.execute();
            sucesso = true;
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                this.connection.close();
                this.pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return sucesso;
    }

    public boolean deleteHabilidade(int id) {
        this.connectToDb();
        String sql = "DELETE FROM Habilidades WHERE id = ?";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setInt(1, id);
            this.pst.execute();
            sucesso = true;
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                this.connection.close();
                this.pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return sucesso;
    }

    public boolean deleteHabilidadesByPersonaNome(String personaNome) {
        this.connectToDb();
        String sql = "DELETE FROM Habilidades WHERE persona_id = ?";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, personaNome);
            this.pst.execute();
            sucesso = true;
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                this.connection.close();
                this.pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return sucesso;
    }

    public ArrayList<Habilidades> selectHabilidades() {
        this.connectToDb();
        ArrayList<Habilidades> habilidades = new ArrayList<>();
        String sql = "SELECT * FROM Habilidades";

        try {
            this.st = this.connection.createStatement();
            this.rs = this.st.executeQuery(sql);
            System.out.println("Lista de Habilidades:");

            while(this.rs.next()) {
                Habilidades habilidadeAux = new Habilidades(
                        this.rs.getString("nome"),
                        this.rs.getString("tipo"),
                        this.rs.getString("efeito"),
                        this.rs.getInt("dano")
                );

                PrintStream var10000 = System.out;
                String var10001 = habilidadeAux.nome();
                var10000.println("Nome: " + var10001 + " | Tipo: " + habilidadeAux.tipo() +
                        " | Efeito: " + habilidadeAux.efeito() + " | Dano: " + habilidadeAux.dano());
                System.out.println("--------------------");
                habilidades.add(habilidadeAux);
            }
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        } finally {
            try {
                this.connection.close();
                this.st.close();
                this.rs.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return habilidades;
    }

    public ArrayList<Habilidades> selectHabilidadesByPersonaNome(String personaNome) {
        this.connectToDb();
        ArrayList<Habilidades> habilidades = new ArrayList<>();
        String sql = "SELECT * FROM Habilidades WHERE persona_id = ?";

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, personaNome);
            this.rs = this.pst.executeQuery();
            System.out.println("Habilidades da Persona " + personaNome + ":");

            while(this.rs.next()) {
                Habilidades habilidadeAux = new Habilidades(
                        this.rs.getString("nome"),
                        this.rs.getString("tipo"),
                        this.rs.getString("efeito"),
                        this.rs.getInt("dano")
                );

                PrintStream var10000 = System.out;
                String var10001 = habilidadeAux.nome();
                var10000.println("Nome: " + var10001 + " | Tipo: " + habilidadeAux.tipo() +
                        " | Efeito: " + habilidadeAux.efeito() + " | Dano: " + habilidadeAux.dano());
                System.out.println("--------------------");
                habilidades.add(habilidadeAux);
            }
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        } finally {
            try {
                this.connection.close();
                this.pst.close();
                this.rs.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return habilidades;
    }

    public Habilidades findHabilidadeByName(String nome) {
        this.connectToDb();
        String sql = "SELECT * FROM Habilidades WHERE nome = ?";
        Habilidades habilidade = null;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, nome);
            this.rs = this.pst.executeQuery();

            if(this.rs.next()) {
                habilidade = new Habilidades(
                        this.rs.getString("nome"),
                        this.rs.getString("tipo"),
                        this.rs.getString("efeito"),
                        this.rs.getInt("dano")
                );
            }
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        } finally {
            try {
                this.connection.close();
                this.pst.close();
                this.rs.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return habilidade;
    }

    public boolean transferHabilidadeToPersona(String habilidadeNome, String personaNome) {
        this.connectToDb();
        String sql = "UPDATE Habilidades SET persona_id = ? WHERE nome = ?";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, personaNome);
            this.pst.setString(2, habilidadeNome);
            this.pst.execute();
            sucesso = true;
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
            sucesso = false;
        } finally {
            try {
                this.connection.close();
                this.pst.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }

        return sucesso;
    }
}
