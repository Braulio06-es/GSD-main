package core;

import graficos.PantallaCarga;

import javax.swing.*;
import java.awt.*;

public class Juego extends JFrame {
    public Juego() {
        setTitle("Proyecto Inferno");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setContentPane(new PantallaCarga(this)); // Inicia la pantalla de carga
        setVisible(true);
    }
}



