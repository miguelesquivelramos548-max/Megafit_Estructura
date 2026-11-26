package com.megafit;

/** Punto de entrada del proyecto. Abre el JFrame Form FrmPrincipal. */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(
                        javax.swing.UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignorado) {
                // Se conserva el Look and Feel por defecto de Swing.
            }
            new com.megafit.vistas.FrmPrincipal().setVisible(true);
        });
    }
}
