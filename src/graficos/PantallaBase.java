package graficos;


import utils.SVGIcono;
import javax.swing.*;
import java.awt.*;

public abstract class PantallaBase extends JPanel {
    protected JFrame ventana;

    public PantallaBase(JFrame ventana)  {
        this.ventana = ventana;
        setLayout(new BorderLayout());

        // Establecer el logo en SVG en todas la pantallas
        ventana.setIconImage(SVGIcono.cargarSVGComoImagen("img/logoBlancoOptimizado.svg", 512, 512));
    }

    protected void cambiarPantalla(JPanel nuevaPantalla) {
        ventana.setContentPane(nuevaPantalla);
        ventana.revalidate();
    }
}
