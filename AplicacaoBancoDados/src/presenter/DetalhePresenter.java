package presenter;

import dao.ProdutoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Produto;
import view.CadastroProdutoView;

/**
 *
 * @author david
 */
public class DetalhePresenter {

    private CadastroProdutoView view;

    public DetalhePresenter(Produto produto) {
        configurarView(produto);

        view.getBtnEditar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    editar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });

        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    salvarEdicao();
                    view.dispose();
                    new VisualizacaoProdutoPresenter();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });
    }

    private void configurarView(Produto produto) {

        this.view = new CadastroProdutoView();
        view.setTitle("Detalhes do Produto");

        view.getBtnSalvar().setVisible(false);

        view.getTxtCodigo().setText("" + produto.getCodigo());
        view.getTxtDescricao().setText(produto.getDescricao());
        view.getTxtPreco().setText("" + produto.getPreco());

        view.getTxtCodigo().enable(false);
        view.getTxtDescricao().enable(false);
        view.getTxtPreco().enable(false);

        view.setVisible(true);
    }

    private void editar() throws Exception {
        view.getTxtDescricao().enable(true);
        view.getTxtPreco().enable(true);

        view.getBtnSalvar().setVisible(true);

    }

    private void salvarEdicao() throws Exception {
        
        int codigo = Integer.parseInt(view.getTxtCodigo().getText());
        String descricao = view.getTxtDescricao().getText();
        double preco = Double.parseDouble(view.getTxtPreco().getText());

        Produto produto = new Produto(codigo, descricao, preco);
        ProdutoDAO.getProdutoDAOInstance().update(produto);
        
        

    }

}
