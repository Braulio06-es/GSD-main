package utils;

import javax.swing.*;
import java.awt.*;

public class BarraCargaPersonalizada extends JProgressBar {
    private int ancho;

    public BarraCargaPersonalizada() {
        super(0,100);
        setPreferredSize(new Dimension(500, 25));
        setStringPainted(false); // Ocultamos el % numerico

        // Fondo Transparente
        setOpaque(false);
        setBackground(new Color(13,15,27,150));

        // Color proceso narajado
        setForeground(new Color(247, 148, 29));

        // Borde reondeado
        setBorder(BorderFactory.createLineBorder(new Color(122, 133, 149), 2));

        // Ajustar el tamaño fijo
        setPreferredSize(new Dimension(500, 25));
        setMaximumSize(new Dimension(500, 25));
        setMinimumSize(new Dimension(500, 25));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Asegura que Swing dibuje correctamente la barra

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el fondo (transparente o con color oscuro)
        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Obtener el progreso actual (evita errores con valores fuera de rango)
        int ancho = (int) (getWidth() * Math.max(0, Math.min(1, getPercentComplete())));

        // Dibujar el progreso con bordes redondeados
        g2d.setColor(getForeground());
        g2d.fillRoundRect(0, 0, ancho, getHeight(), 15, 15);
    }
}
