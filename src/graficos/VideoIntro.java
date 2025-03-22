package graficos;

import core.JuegoArrancar;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.layout.StackPane;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;

public class VideoIntro extends PantallaBase implements InterfazReproduccionVideo {
    private JFXPanel videoPanel;
    private MediaPlayer player;

    public VideoIntro(JFrame frame) {
        super(frame);
        videoPanel = new JFXPanel();
        setLayout(new BorderLayout());
        add(videoPanel, BorderLayout.CENTER);
        iniciar();
    }

    @Override
    public void iniciar() {
        Platform.runLater(() -> {
            try {
                // Ruta del video en resources/video/
                File file = new File("resources/video/videoIntro.mp4");
                if (!file.exists()) {
                    System.err.println("No se encontró el archivo de video");
                    return;
                }

                String mediaPath = file.toURI().toURL().toString();
                Media media = new Media(mediaPath);
                player = new MediaPlayer(media);
                MediaView view = new MediaView(player);

                Scene scene = new Scene(new StackPane(view), 800, 600);
                videoPanel.setScene(scene);

                player.setOnEndOfMedia(() -> {
                    ventana.dispose();
                    JuegoArrancar.JuegoArrancar(null);
                });
                player.play();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void detener() {
        if (player != null) {
            player.stop();
        }
    }
}