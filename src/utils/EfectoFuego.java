package utils;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class EfectoFuego  extends JPanel {
    private ArrayList<Particula> particulas;
    private Timer timer;
    private Random random;

    public EfectoFuego(int cantidad) {
        setOpaque(false);
        random = new Random();
        particulas = new ArrayList<>();
        // Generar partículas
        for (int i = 0; i < cantidad; i++) {
            particulas.add(new Particula(random.nextInt(40), random.nextInt(20) + 40));
        }

        // Animacion con Timer
        timer = new Timer (50 , e-> {
            for (Particula p : particulas) {
                p.actualizar();
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void printComponent(Graphics g) {
        super.printComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Particula p : particulas) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, p.alfa / 255.0f));
            g2d.setColor(new Color(255, random.nextInt(150), 0));
            g2d.fillOval(p.x, p.y, 8, 8);
        }
    }

    private class Particula {
        int x,y;
        int alfa;

        public Particula(int x, int y) {
            this.x = x;
            this.y = y;
            this.alfa = 255;
        }
        public void actualizar(){
            y=-2; // Mueve la parícula hacia arriba
            alfa-=15; // Reduce opacidad
            if (alfa <= 0){
                alfa=255; // Reinicia opacidad
                y=random.nextInt(20) + 40; // Reinicia en posición baja
            }
        }
    }
}
