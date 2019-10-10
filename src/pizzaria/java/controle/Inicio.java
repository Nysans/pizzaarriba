package pizzaria.java.controle;

import pizzaria.java.visao.InicioGUI;

public class Inicio {

	private static InicioGUI Inicio;
	
	public static void main(String[] args) {
		lookAndFeel();

		Inicio = new InicioGUI();
		Inicio.getBtnPedir().addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				pedir();
			}
		});
		Inicio.setVisible(true);
	}

	protected static void pedir() {
		Inicio.setVisible(false);
		new ProdutoControle();
	}
	
	public static void reiniciar() {
		Inicio.setVisible(true);
	}
	

	private static void lookAndFeel() {
		try {
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Metal".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(InicioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(InicioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(InicioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(InicioGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
	}
}
