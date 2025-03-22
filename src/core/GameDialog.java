package core;

import javax.swing.*;

public class GameDialog {
    private JDialog dialog;
    private CombatPanel combatPanel;

    /**
     * Constructor del GameDialog.
     *
     * @param enemyType  Tipo del enemigo.
     * @param enemyIndex Índice del objeto enemigo en el GamePanel.
     * @param gp         Referencia al GamePanel principal.
     */
    public GameDialog(String enemyType, int enemyIndex, GamePanel gp) {
        dialog = new JDialog((JFrame) null, "Combate por Turnos", false);
        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        combatPanel = new CombatPanel(enemyType, enemyIndex, gp);
        dialog.setContentPane(combatPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setResizable(false);
    }

    public void start() {
        dialog.setVisible(true);
    }
}