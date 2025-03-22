package core;

import javax.swing.*;
import java.awt.*;
import personajesCombate.Player;

public class CombatHUD extends JPanel {

    public CombatHUD(CombatPanel cp, Player player) {
        // Usamos un FlowLayout centrado
        setLayout(new FlowLayout(FlowLayout.CENTER));
        // Fondo negro translúcido
        setBackground(new Color(0, 0, 0, 150));

        JButton btnAttack = new JButton("Ataque");
        JButton btnDefend = new JButton("Defender");
        JButton btnEspecial = new JButton("Especial");

        btnAttack.addActionListener(e -> {
            player.setAction("attack");
            cp.playerTurn();
        });

        btnDefend.addActionListener(e -> {
            player.setAction("defend");
            cp.playerTurn();
        });

        btnEspecial.addActionListener(e -> {
            String[] options = {"Postura Soldado", "Postura Guardián", "Tajo Brutal"};
            String message = "Especiales restantes: "
                    + player.getSpecialUsesRemaining() + "/" + player.getTotalSpecials()
                    + "\nElige tu acción especial:";
            int choice = JOptionPane.showOptionDialog(
                    CombatHUD.this,
                    message,
                    "Acción Especial",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            // Si se cierra la ventana (choice == -1), no se hace nada
            if (choice == -1) {
                return;
            }
            if (choice == 0) {
                player.setAction("posturaSoldado");
            } else if (choice == 1) {
                player.setAction("posturaGuardian");
            } else if (choice == 2) {
                player.setAction("tajoBrutal");
            }
            cp.playerTurn();
        });


        add(btnAttack);
        add(btnDefend);
        add(btnEspecial);
    }
}
