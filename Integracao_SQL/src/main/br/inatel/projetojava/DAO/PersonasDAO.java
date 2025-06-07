package main.br.inatel.projetojava.DAO;

import main.br.inatel.projetojava.Model.personas.seres.Personas;
import main.br.inatel.projetojava.Model.personas.Habilidades;

import java.io.PrintStream;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonasDAO extends ConnectionDAO {
    public PersonasDAO() {
    }

    public void testConnection() {
        this.connectToDb();
    }

    public boolean insertPersona(Personas persona) {
        boolean sucesso = false;
        connectToDb();

        // CORREÇÃO: Incluindo o campo Ativador_idAtivador no INSERT
        String sql = """
        INSERT INTO personas (nome, nivel, arcana, tipo1, tipo2, fraqueza, resistencia, dano, Ativador_idAtivador)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try {
            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pst.setString(1, persona.getNome());
            pst.setInt(2, persona.getNivel());
            pst.setString(3, persona.getArcana());

            // Supondo que você sempre tem dois tipos em `tipo`
            pst.setString(4, persona.getTipo().get(0));
            pst.setString(5, persona.getTipo().size() > 1 ? persona.getTipo().get(1) : null);

            pst.setString(6, persona.getFraqueza());
            pst.setString(7, persona.getResistencia());
            pst.setDouble(8, persona.getDano());

            // CORREÇÃO: Adicionando o parâmetro para Ativador_idAtivador
            // Você precisa ter um getter para o idAtivador na classe Personas
            pst.setInt(9, 1); // Assumindo que existe este método

            pst.executeUpdate();

            rs = pst.getGeneratedKeys();
            if (rs.next()) {
                persona.setId(rs.getInt(1));
            }

            sucesso = true;

        } catch (SQLException e) {
            System.out.println("Erro ao inserir persona: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.out.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }

        return sucesso;
    }

    public boolean updatePersona(int id, Personas persona) {
        this.connectToDb();
        String sql = "UPDATE Personas SET nome = ?, nivel = ?, arcana = ?, tipo = ?, fraqueza = ?, resistencia = ?, dano = ? WHERE id = ?";
        boolean sucesso;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, persona.getNome());
            this.pst.setInt(2, persona.getNivel());
            this.pst.setString(3, persona.getArcana());
            this.pst.setString(4, String.join(",", persona.getTipo()));
            this.pst.setString(5, persona.getFraqueza());
            this.pst.setString(6, persona.getResistencia());
            this.pst.setDouble(7, persona.getDano());
            this.pst.setInt(8, id);
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

    public boolean deletePersona(int id) {
        this.connectToDb();
        String sql = "DELETE FROM Personas WHERE id = ?";
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

    public ArrayList<Personas> selectPersonas() {
        this.connectToDb();
        ArrayList<Personas> personas = new ArrayList<>();
        String sql = "SELECT * FROM Personas";

        try {
            this.st = this.connection.createStatement();
            this.rs = this.st.executeQuery(sql);
            System.out.println("Lista de Personas:");

            while (this.rs.next()) {
                // Parse the tipo field from comma-separated string back to List
                String tipoString = this.rs.getString("tipo");
                List<String> tipoList = Arrays.asList(tipoString.split(","));

                Personas personaAux = new Personas(
                        this.rs.getString("nome"),
                        this.rs.getInt("nivel"),
                        this.rs.getString("arcana"),
                        tipoList,
                        this.rs.getString("fraqueza"),
                        this.rs.getString("resistencia"),
                        this.rs.getDouble("dano")
                );

                // Load habilidades for each persona
                loadHabilidadesForPersona(personaAux);

                PrintStream var10000 = System.out;
                String var10001 = personaAux.getNome();
                var10000.println("Nome: " + var10001 + " | Nível: " + personaAux.getNivel() +
                        " | Arcana: " + personaAux.getArcana());
                System.out.println("--------------------");
                personas.add(personaAux);
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

        return personas;
    }

    // Helper method to load habilidades for a specific persona
    // This would need a separate table for habilidades and a relationship to personas
    public void loadHabilidadesForPersona(Personas persona) {
        this.connectToDb();
        String sql = "SELECT * FROM Habilidades WHERE persona_id = ?";

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, persona.getNome()); // Assuming the persona name is used as the foreign key
            this.rs = this.pst.executeQuery();

            while (this.rs.next()) {
                Habilidades habilidade = new Habilidades(
                        this.rs.getString("nome"),
                        this.rs.getString("tipo"),
                        this.rs.getString("efeito"),
                        this.rs.getInt("dano")
                );
                persona.addHabilidade(habilidade);
            }
        } catch (SQLException exc) {
            System.out.println("Erro: " + exc.getMessage());
        } finally {
            try {
                this.pst.close();
                this.rs.close();
            } catch (SQLException exc) {
                System.out.println("Erro: " + exc.getMessage());
            }
        }
    }

    // Additional method to find a persona by name
    public Personas findPersonaByName(String name) {
        this.connectToDb();
        String sql = "SELECT * FROM Personas WHERE nome = ?";
        Personas persona = null;

        try {
            this.pst = this.connection.prepareStatement(sql);
            this.pst.setString(1, name);
            this.rs = this.pst.executeQuery();

            if (this.rs.next()) {
                String tipoString = this.rs.getString("tipo");
                List<String> tipoList = Arrays.asList(tipoString.split(","));

                persona = new Personas(
                        this.rs.getString("nome"),
                        this.rs.getInt("nivel"),
                        this.rs.getString("arcana"),
                        tipoList,
                        this.rs.getString("fraqueza"),
                        this.rs.getString("resistencia"),
                        this.rs.getDouble("dano")
                );
                loadHabilidadesForPersona(persona);
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

        return persona;
    }
}