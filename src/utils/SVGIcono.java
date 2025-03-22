package utils;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGUniverse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class SVGIcono {
    public static Image cargarSVGComoImagen(String ruta, int ancho, int alto) {
        try {
            SVGUniverse universoSVG = new SVGUniverse();
            URL url = SVGIcono.class.getClassLoader().getResource(ruta);

            // Verificar que el archivo existe
            if (url == null) {
                throw new Exception("No se encontró el archivo SVG: " + ruta);
            }

            // Cargar el SVG
            SVGDiagram diagrama = universoSVG.getDiagram(url.toURI());

            // Verificar si el SVG se cargó correctamente
            if (diagrama == null) {
                throw new Exception("El archivo SVG no pudo ser procesado correctamente.");
            }

            // Obtener el tamaño original del SVG
            int originalAncho = (int) diagrama.getWidth();
            int originalAlto = (int) diagrama.getHeight();

            // Si el tamaño es inválido, usar valores predeterminados
            if (originalAncho <= 0 || originalAlto <= 0) {
                originalAncho = ancho;
                originalAlto = alto;
            }

            System.out.println("Tamaño original del SVG: " + originalAncho + "x" + originalAlto);

            // Crear la imagen con el tamaño de destino
            BufferedImage imagen = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = imagen.createGraphics();

            // Escalar correctamente el SVG
            g2d.scale((double) ancho / originalAncho, (double) alto / originalAlto);

            // Renderizar el SVG en la imagen escalada
            diagrama.render(g2d);
            g2d.dispose();

            return imagen;
        } catch (Exception e) {
            System.err.println("Error al cargar SVG: " + e.getMessage());
            return null;
        }
    }
}


