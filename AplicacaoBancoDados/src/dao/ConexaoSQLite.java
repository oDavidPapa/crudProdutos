
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author david
 */

public class ConexaoSQLite implements Conexao {
    
    private Connection connection = null;
    private static ConexaoSQLite instance;
    private Statement stmt = null;
    private String url = "jdbc:sqlite:src/data/produto.db";
    
    
    private String sqlProduto = "CREATE TABLE IF NOT EXISTS produto(\n"
            + "idProduto integer PRIMARY KEY NOT NULL,\n"
            + "descricao text NOT NULL,\n"
            + "preco double NOT NULL\n"
            + ");";
    
    
     private ConexaoSQLite() throws SQLException {
        try {

            this.connection = DriverManager.getConnection(this.url);
            this.stmt = this.connection.createStatement();
            this.stmt.execute(this.sqlProduto);

        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static ConexaoSQLite getInstance() throws Exception {
        if (instance == null) {
            instance = new ConexaoSQLite();
        }
        return instance;
    }

    public Connection connect() throws SQLException {
        return this.connection;
    }

    public void desconect() throws SQLException {
        connection.close();
    }

    public void desconect(Connection connetion, Statement statement) throws SQLException {
        connetion.close();
        statement.close();
    }

    public Statement getStatment() {
        return this.stmt;
    }

    public Connection getConexao() {
        return this.connection;
    }

}
