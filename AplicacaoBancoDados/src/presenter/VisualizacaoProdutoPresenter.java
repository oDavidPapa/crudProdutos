package presenter;

import dao.ProdutoDAO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Produto;
import view.VisualizacaoProdutoView;

/**
 *
 * @author david
 */
public class VisualizacaoProdutoPresenter {

    private VisualizacaoProdutoView view;
    private DefaultTableModel tablemodel;
    private JComboBox cbBuscar;

    public VisualizacaoProdutoPresenter() throws Exception {
        this.configurarView();

        this.view.getBtnVoltar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                view.dispose();
            }
        });

        this.view.getBtnNovo().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                view.dispose();
                new ManterProdutoPresenter();
            }
        });

        view.getBtnBuscar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    buscar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });

        view.getBtnDetalhe().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    detalhar();
                    view.dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }

            }
        });

        view.getBtnExcluir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    excluir();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage());
                }
            }
        });

    }

    private void detalhar() throws Exception {

        if (view.getTbProdutos().getSelectedRow() >= 0) {
            int codigo = (int) view.getTbProdutos().getValueAt(view.getTbProdutos().getSelectedRow(), 0);
            String descricao = view.getTbProdutos().getValueAt(view.getTbProdutos().getSelectedRow(), 1).toString();
            double preco = (double) view.getTbProdutos().getValueAt(view.getTbProdutos().getSelectedRow(), 2);

            Produto produto = new Produto(codigo, descricao, preco);

            new DetalhePresenter(produto);
        } else {
            throw new Exception("Selecione um produto para detalhar");
        }
    }

    private void buscar() throws Exception {

        if (cbBuscar.getSelectedIndex() == 1) {

            String codigo = view.getTxtBuscar().getText();
            if (codigo.equals("")) {
                throw new Exception("Preencha o campo de busca...");
            } else {

                Produto produto = ProdutoDAO.getProdutoDAOInstance().buscarCodido(Integer.parseInt(codigo));

                this.tablemodel = montarTabela();

                this.tablemodel.addRow(new Object[]{
                    produto.getCodigo(),
                    produto.getDescricao(),
                    produto.getPreco()
                });

                this.view.getTbProdutos().setModel(tablemodel);

            }
        }

        if (cbBuscar.getSelectedIndex() == 2) {

            this.tablemodel = this.montarTabela();
            String pDescricao = this.view.getTxtBuscar().getText();
            if (!pDescricao.equals("")) {
                for (Produto produto : ProdutoDAO.getProdutoDAOInstance().buscarDescricao(pDescricao)) {

                    this.tablemodel.addRow(new Object[]{
                        produto.getCodigo(),
                        produto.getDescricao(),
                        produto.getPreco()
                    });

                    this.view.getTbProdutos().setModel(tablemodel);
                }
            } else {
                throw new Exception("Preencha o campo de busca...");
            }

        }

        if (cbBuscar.getSelectedIndex() == 0) {
            preencherTabela();
        }

    }

    private void excluir() throws Exception {

        if (view.getTbProdutos().getSelectedRow() >= 0) {
            int codigo = (int) view.getTbProdutos().getValueAt(view.getTbProdutos().getSelectedRow(), 0);

            int opcao = JOptionPane.showConfirmDialog(view, "Tem certeza que deseja remover o Produto: " + codigo);
            if (opcao == 0) {
                ProdutoDAO.getProdutoDAOInstance().delete(codigo);
                preencherTabela();

            }
            preencherTabela();

        } else {
            throw new Exception("Selecione um produto para Excluir");
        }
    }

    private void configurarView() throws Exception {
        this.view = new VisualizacaoProdutoView();
        this.preencherTabela();
        this.view.setVisible(true);
        this.cbBuscar = this.view.getCbBuscar();

    }

    private DefaultTableModel montarTabela() {
        this.tablemodel = new DefaultTableModel(new Object[][]{}, new String[]{"Código(#)", "Descrição", "Preço(R$)"}) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        return tablemodel;
    }

    private void preencherTabela() throws Exception {
        this.tablemodel = this.montarTabela();

        for (Produto produto : ProdutoDAO.getProdutoDAOInstance().getTodos()) {

            this.tablemodel.addRow(new Object[]{
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getPreco()
            });

            this.view.getTbProdutos().setModel(tablemodel);
        }

    }

}
