import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                File input = new File("src/img/input.png");
                BufferedImage img = ImageIO.read(input);
                if (img == null) {
                    throw new Exception("Erro ao carregar imagem");
                }

                FloodFill floodFill = new FloodFill(img);

                floodFill.setOnComplete(() -> {
                    Timer delaySalvamento = new Timer(50, e -> {
                        try {
                            File output = new File("src/img/output.png");
                            ImageIO.write(img, "png", output);
                            System.exit(0);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    delaySalvamento.setRepeats(false);
                    delaySalvamento.start();

                });


                JFrame frame = new JFrame("PJBL01 - FloodFill - Felipe e Pedro");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(floodFill);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                int startX = img.getWidth() / 2;
                int startY = img.getHeight() / 2;
                int fillColor = Color.MAGENTA.getRGB();



                floodFill.floodFillFila(startX, startY, fillColor);

            } catch (Exception e) {
                System.err.println("Erro ao carregar ou salvar a imagem:");
                e.printStackTrace();
            }
        });
    }
}
