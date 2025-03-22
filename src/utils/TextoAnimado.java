package utils;

import javax.swing.*;
import java.awt.*;

public class TextoAnimado extends JLabel implements  Runnable {
    private String textoCompleto;
    private boolean animado = true;

    public TextoAnimado(String texto) {
        super("", SwingConstants.CENTER);
        this.textoCompleto = texto;
        setFont(new Font("Arial", Font.BOLD, 22));
        setForeground(new Color(122,133,149)); // Color inical (gris acero)
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }
    public void iniciarAnimacion() {
        Thread animacion = new Thread(this);
        animacion.start();
    }

    public void detenerAnimacion() {
        animado = false;
    }

    @Override
    public void run() {
        try {
            while (animado) {
                StringBuilder textoParcial = new StringBuilder();
                for (int i = 0; i < textoCompleto.length() ; i++) {
                    textoParcial.append(textoCompleto.charAt(i));
                    setText(textoParcial.toString());

                    // Cambiamos color progresivamente de gris a naraja
                    int r =(122 + (i * (247 - 122) / textoCompleto.length()));
                    int g =(133 + (i * (148 - 133) / textoCompleto.length()));
                    int b =(149 + (i * (29 - 149) / textoCompleto.length()));
                    setForeground(new Color(r, g, b));

                    Thread.sleep(150); // Controlador de la velocidad de la animación
                }
                Thread.sleep(500); // Pausa antes de iniciar la animación de nuevo

                // Borrar el texto ante de repetir l animacion
                for (int i = textoCompleto.length(); i > 0; i--) {
                    setText(textoCompleto.substring(0, i));
                    Thread.sleep(80);
                }
                Thread.sleep(300);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
