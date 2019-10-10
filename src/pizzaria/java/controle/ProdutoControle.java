package pizzaria.java.controle;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pizzaria.java.exception.PizzariaException;
import pizzaria.java.modelo.Pedido;
import pizzaria.java.visao.ProdutoGUI;

public class ProdutoControle {

    private ProdutoGUI View;
    private Pedido pedido;

    public ProdutoControle() {
        View = new ProdutoGUI();
        View.getBtnPizza().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnPizza();
            }
        });

        View.getBtnBebida().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnBebida();
            }
        });

        View.getBtnCrie().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCrie();
            }
        });

        View.getBtnCarrinho().addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnCarrinho();
            }
        });

        pedido = new Pedido();
        View.setVisible(true);
    }

    protected void btnCarrinho() {
        new CarrinhoControle(pedido, View);
        this.View.setVisible(false);
    }

    protected void btnPizza() {
        try {
            PizzaControle Controle = new PizzaControle(pedido);
            JPanel SubView = Controle.getView();
            setPanelContent(SubView);
        } catch (PizzariaException e) {
            JOptionPane.showMessageDialog(View, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void btnBebida() {
        try {
            BebidaControle Controle = new BebidaControle(pedido);
            JPanel SubView = Controle.getView();
            setPanelContent(SubView);
        } catch (PizzariaException e) {
            JOptionPane.showMessageDialog(View, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void btnCrie() {
        try {
            CriarPizzaControle Controle = new CriarPizzaControle(pedido);
            JPanel SubView = Controle.getView();
            setPanelContent(SubView);
        } catch (PizzariaException e) {
            JOptionPane.showMessageDialog(View, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setPanelContent(JPanel SubView) {
        if (View.getPnPrincipal().getComponentCount() != 0 && View.getPnPrincipal().getComponent(0).getClass() != SubView.getClass()) {
            View.getPnPrincipal().removeAll();
        }

        View.getPnPrincipal().add(SubView);
        SubView.setSize(464, 456);
        SubView.setVisible(true);
        View.repaint();
        View.revalidate();
    }

}
