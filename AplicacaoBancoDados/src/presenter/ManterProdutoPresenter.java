package presenter;

import dao.ProdutoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import model.Produto;
import view.CadastroProdutoView;

/**
 *
 * @author david
 */
public class ManterProdutoPresenter {

    private CadastroProdutoView view;

    public ManterProdutoPresenter() {
        configurarTela();

        view.getBtnSalvar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    salvar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });

        view.getBtnCancelar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                view.dispose();
            }
        });
    }

    private void salvar() throws Exception {

        int codigo = Integer.parseInt(view.getTxtCodigo().getText());
        String descricao = view.getTxtDescricao().getText();
        double preco = Double.parseDouble(view.getTxtPreco().getText());

        if ((codigo > 0) && (!descricao.equals("")) && (preco > 0)) {
            Produto produto = new Produto(codigo, descricao, preco);
            ProdutoDAO.getProdutoDAOInstance().inserir(produto);
            
            JOptionPane.showMessageDialog(view, "Produto : "+ produto.getDescricao()+ " cadastrado com sucesso!");
            view.dispose();
            
            new VisualizacaoProdutoPresenter();
            
        } else {
            throw new Exception("Preencha os Campos Corretamente!");
        }

    }

    private void configurarTela() {
        this.view = new CadastroProdutoView();
        view.setTitle("Cadastrar Produto");
        view.getBtnEditar().setVisible(false);
        view.setVisible(true);
        
    }

}
