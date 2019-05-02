package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Produto;

/**
 *
 * @author david
 */
public class ProdutoDAO {

    private static ProdutoDAO INSTANCE;
    private final ConexaoMySQL conexao;
    //private final ConexaoSQLite conexao;

    private ProdutoDAO() throws Exception {
      conexao = ConexaoMySQL.getInstance();
       //conexao = ConexaoSQLite.getInstance();
    }

    public static ProdutoDAO getProdutoDAOInstance() throws Exception {

        if (INSTANCE == null) {
            return new ProdutoDAO();
        } else {
            return INSTANCE;
        }
    }

    public void inserir(Produto produto) throws SQLException {

        String sqlInsert = "INSERT INTO produto("
                + "idProduto,"
                + "descricao,"
                + "preco"
                + ") VALUES(?,?,?)"
                + ";";

        try {
            PreparedStatement pstmt = conexao.getConexao().prepareStatement(sqlInsert);

            pstmt.setInt(1, produto.getCodigo());
            pstmt.setString(2, produto.getDescricao());
            pstmt.setDouble(3, produto.getPreco());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("Falha ao cadastrar o produto...");
        }
    }


    public ArrayList<Produto> getTodos() {
        String sqlSelect = "SELECT * FROM produto";
        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            ResultSet rs = conexao.getStatment().executeQuery(sqlSelect);

            while (rs.next()) {

                int idProduto = rs.getInt("idProduto");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");

                Produto p = new Produto(idProduto, descricao, preco);
                produtos.add(p);

            }

            return produtos;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public Produto buscarCodido(int codigo) throws Exception {

        String teste = Integer.toString(codigo);

        if (teste.equals("")) {
            throw new Exception("Preencha os campos de consulta");
        } else {

            String sqlSelect = "SELECT * FROM produto where idProduto = " + codigo;

            try {
                ResultSet rs = conexao.getStatment().executeQuery(sqlSelect);

                int idProduto = rs.getInt("idProduto");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");

                Produto p = new Produto(idProduto, descricao, preco);

                return p;

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        return null;
    }

    public ArrayList<Produto> buscarDescricao(String pDescricao) {
        String sqlSelect = "SELECT * FROM produto where descricao like '" + pDescricao + "%'";
        ArrayList<Produto> produtos = new ArrayList<>();

        try {
            ResultSet rs = conexao.getStatment().executeQuery(sqlSelect);

            while (rs.next()) {

                int idProduto = rs.getInt("idProduto");
                String descricao = rs.getString("descricao");
                double preco = rs.getDouble("preco");

                Produto p = new Produto(idProduto, descricao, preco);
                produtos.add(p);

            }

            return produtos;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public void update(Produto produto) throws Exception {
        if (buscarCodido(produto.getCodigo()) == null) {
            throw new Exception("Produto não pôde ser editado");
        } else {
            String sqlUpdate = "Update produto SET descricao = '" + produto.getDescricao() + "'"
                    + ", preco = '" + produto.getPreco() + "' where idProduto = "+produto.getCodigo();
            try {
                ResultSet rs = conexao.getStatment().executeQuery(sqlUpdate);
            } catch (SQLException e) {
                //System.out.println("TESTE "+e.getMessage());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
    
    public void delete(int codigo) throws Exception{
        
        if (buscarCodido(codigo) == null) {
            throw new Exception("Produto não pôde ser excluído");
        } else {
            String sqlDelete = "Delete from produto where idProduto = "+codigo;
            try {
                ResultSet rs = conexao.getStatment().executeQuery(sqlDelete);
                while( rs.next()){
                    rs.getString(sqlDelete);
                }
                
            } catch (SQLException e) {
               // System.out.println("TESTE "+e.getMessage());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        
    }

}
