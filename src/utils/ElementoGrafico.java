package utils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class ElementoGrafico extends JPanel {
    private Image imagen;



    public ElementoGrafico(String ruta) {
        setLayout(new BorderLayout()); // Desactivar el layout por defecto
        setOpaque(false); // Permitir transparencia del panel

        // Cargar imagen desde la carpeta resources
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) {
            if(ruta.toLowerCase().endsWith(".gif")){
                // Si es un GIF, lo cargamos como ImgageIcon
               imagen = new ImageIcon(url).getImage();
            }else {
                // Si es una imagen estática, la carga normalmente
                imagen = new ImageIcon(url).getImage();
            }
        } else {
            System.err.println("No se encontró la imagen: " + ruta);
            imagen = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB); // Imagen vacía con tamaño mayor
        }

        // Establecer tamaño por defecto
        setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (imagen != null && getWidth() > 0 && getHeight() > 0) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
