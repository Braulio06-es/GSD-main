package utils;

import javax.sound.sampled.*;
import java.net.URL;

public class GestorSonido {
    private static Clip clip;

    public static void reproducirSonido(String ruta) {
        try {
            // Obtener la ruta del archivo de audio
            URL url = GestorSonido.class.getClassLoader().getResource(ruta);

            // Verificar si la ruta es nula
            if (url == null) {
                System.err.println("No se encontró el archivo de audio: " + ruta);
                return;
            }

            // Imprimir la ruta real
            System.out.println("Ruta del archivo de audio: " + url);

            // Cargar y reproducir el audio
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir sonido en bucle

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void detenerSonido() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
