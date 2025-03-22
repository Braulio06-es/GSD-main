package graficos;

import utils.EfectoFuego;
import utils.ElementoGrafico;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends PantallaBase {
    private JButton btnJugar, btnOpciones, btnSalir;

    public MenuPrincipal(JFrame ventana) {
        super(ventana);

        setLayout(new BorderLayout());

        // Fondo del menú
        ElementoGrafico fondo = new ElementoGrafico("img/Castillo.gif");

        // Creación de los botones
        btnJugar = new JButton("Jugar");
        btnOpciones = new JButton("Opciones");
        btnSalir = new JButton("Salir");

        // Estilo de los botones
        estilizarBoton(btnJugar);
        estilizarBoton(btnOpciones);
        estilizarBoton(btnSalir);

        // Agregar ActionListeners
        btnJugar.addActionListener(e -> iniciarJuego());
        btnOpciones.addActionListener(e -> mostrarOpciones());
        btnSalir.addActionListener(e -> System.exit(0));

        // Crear los marcos individuales
        ElementoGrafico marcoJugar = new ElementoGrafico("img/MarcoBotones.png");
        ElementoGrafico marcoOpciones = new ElementoGrafico("img/MarcoBotones.png");
        ElementoGrafico marcoSalir = new ElementoGrafico("img/MarcoBotones.png");

        // Ajustar tamaños
        Dimension marcoSize = new Dimension(200, 100);
        marcoJugar.setPreferredSize(marcoSize);
        marcoOpciones.setPreferredSize(marcoSize);
        marcoSalir.setPreferredSize(marcoSize);

        // Layout para centrar botones dentro de los marcos con ajuste de posición
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1; // Coloca el botón en una fila más abajo
        gbc.weighty = 1.0; // Empuja el botón hacia abajo dentro del marco
        gbc.insets = new Insets(10, 0, 0, 0); // Ajuste de margen superior para bajar el botón
        gbc.anchor = GridBagConstraints.NORTH; // Anclar el botón hacia abajo dentro del marco

        marcoJugar.setLayout(new GridBagLayout());
        marcoOpciones.setLayout(new GridBagLayout());
        marcoSalir.setLayout(new GridBagLayout());

        // Espaciador vacío para empujar el botón hacia abajo
        marcoJugar.add(Box.createVerticalGlue(), gbc);
        marcoOpciones.add(Box.createVerticalGlue(), gbc);
        marcoSalir.add(Box.createVerticalGlue(), gbc);

        // Agregar los botones ya desplazados
        gbc.gridy = 2; // Asegurar que el botón esté debajo del espaciador
        marcoJugar.add(btnJugar, gbc);
        marcoOpciones.add(btnOpciones, gbc);
        marcoSalir.add(btnSalir, gbc);

        // Panel de marcos
        JPanel panelMarcos = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panelMarcos.setOpaque(false);
        panelMarcos.add(marcoJugar);
        panelMarcos.add(marcoOpciones);
        panelMarcos.add(marcoSalir);

        // Panel inferior con los botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 30));
        panelInferior.setOpaque(false);
        panelInferior.add(panelMarcos);

        // Agregar elementos al fondo y a la pantalla
        fondo.add(panelInferior, BorderLayout.SOUTH);
        add(fondo, BorderLayout.CENTER);
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 20));
        boton.setForeground(Color.WHITE);
        boton.setBorderPainted(false);
        boton.setContentAreaFilled(false);
        boton.setBackground(new Color(0, 0, 0, 0)); // Asegurar transparencia
        boton.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        boton.setFocusPainted(false);
    }

    private void iniciarJuego() {
        System.out.println("Iniciando juego...");
        cambiarPantalla(new VideoIntro(ventana)); // Muestra el video antes del juego
    }

    private void mostrarOpciones() {
        System.out.println("Mostrando opciones...");
        // Aquí iría la lógica para abrir las opciones
    }
}
