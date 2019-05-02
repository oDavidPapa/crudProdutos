package app;

import dao.ConexaoMySQL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import view.MenuView;

/**
 *
 * @author david
 */
public class Main {

    public static void main(String[] args) throws Exception {

        // PreparedStatement pmst = ConexaoMySQL.getInstance().getConexao().prepareStatement("INSERT INTO produto (idProduto, descricao, preco) VALUES (3,'Coca', 1.99);");
        // pmst.executeUpdate();
        ResultSet rs = ConexaoMySQL.getInstance().getStatment().executeQuery("Select * from produto");

        while (rs.next()) {
             int idProduto = rs.getInt("idProduto");
        String descricao = rs.getString("descricao");
        double preco = rs.getDouble("preco");
        System.out.println(idProduto + descricao + preco);

        }

       

        new MenuView().setVisible(true);
    }

}
