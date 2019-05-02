package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoMySQL implements Conexao {

    private static ConexaoMySQL INSTANCE;
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    private String SGBD = "MySQL";
    private String servidor;
    private String bancoDados;
    private String usuario;
    private String senha;

    private ConexaoMySQL() {

        if (SGBD.equals("MySQL")) {
            servidor = "localhost";
            bancoDados = "jdbc:mysql://localhost:3306/produto?useTimezone=true&serverTimezone=UTC";
            usuario = "dba";
            senha = "q1w2e3r4";
        }
        connect(servidor, bancoDados, usuario, senha);
    }

    public static ConexaoMySQL getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConexaoMySQL();
            return INSTANCE;
        }
        return INSTANCE;
    }

    private boolean connect(String servidor, String bancoDados, String usuario, String senha) {
        try {

            if (SGBD.equals("MySQL")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
            }

        } catch (Exception ex) {
            System.out.println("SQLException: " + ex.getMessage());
        }

        try {

            conn = DriverManager.getConnection(bancoDados, usuario, senha);
            stmt = conn.createStatement();

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
        return true;

    }

    public Statement getStatment() {
        return this.stmt;
    }

    public Connection getConexao() {
        return this.conn;
    }

    public ResultSet consultar(String query) {
        try {
            rs = stmt.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }

    public void desconect() {
        try {
            stmt.close();
        } catch (SQLException sqlEx) {
            stmt = null;
        }
        try {
            conn.close();
        } catch (SQLException sqlEx) {
            conn = null;
        }
    }

}
