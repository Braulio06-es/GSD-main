package graficos;

import utils.BarraCargaPersonalizada;
import utils.ElementoGrafico;
import utils.GestorSonido;
import utils.TextoAnimado;
import javax.swing.*;
import java.awt.*;

public class PantallaCarga extends PantallaBase {
    private JProgressBar barraProgreso;
    private TextoAnimado textoCargando;

    public PantallaCarga(JFrame ventana) {
        super(ventana);

        // Imagen de fondo
        ElementoGrafico imagenFondo = new ElementoGrafico("img/FondoPantallaCarga.png");

        // Barra de carga personalizada
        barraProgreso = new BarraCargaPersonalizada();

        // Texto animado
        textoCargando = new TextoAnimado("Cargando...");
        textoCargando.setHorizontalAlignment(SwingConstants.CENTER);
        textoCargando.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Espaciado
        textoCargando.iniciarAnimacion(); // Iniciar animación

        // Panel exclusivo para la barra de carga
        JPanel panelBarra = new JPanel();
        panelBarra.setOpaque(false);
        panelBarra.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelBarra.add(barraProgreso);

        // Panel exclusivo para el texto
        JPanel panelTexto = new JPanel();
        panelTexto.setOpaque(false);
        panelTexto.setLayout(new FlowLayout(FlowLayout.CENTER));
        panelTexto.add(textoCargando);

        // Panel inferior que contiene ambos (texto + barra)
        JPanel panelInferior = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(13, 15, 27)); // Color igual al fondo de la imagen
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelInferior.setOpaque(false);
        panelInferior.setLayout(new GridLayout(2, 1, 0, 5));
        panelInferior.add(panelTexto);
        panelInferior.add(panelBarra);

        // Layout de la pantalla
        setLayout(new BorderLayout());
        add(imagenFondo, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        // Iniciar la carga
        iniciarCarga();
    }

    private void iniciarCarga() {
        // Iniciar sonido de carga
        GestorSonido.reproducirSonido("audio/02_heartbeat.wav");

        // Simulacion de carga SwingWorker
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 100; i+=10) {
                    Thread.sleep(1000); // Simulacion de tiempo de carga
                    barraProgreso.setValue(i); // Actualizar progreso
                }
                return null;
            }

            @Override
            protected void done() {
                GestorSonido.detenerSonido();
                cambiarPantalla(new MenuPrincipal(ventana)); // Cambiara a menu principal al terminar de cargar
                ventana.revalidate();
                ventana.repaint();
            }
        };
        worker.execute();
    }
}
