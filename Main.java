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
                    throw new Exception("Falha ao carregar a imagem.");
                }

                FloodFill floodFill = new FloodFill(img);

                floodFill.setOnComplete(() -> {
                    try {
                        File output = new File("src/img/output.png");
                        ImageIO.write(img, "png", output);
                        System.out.println("Imagem com Flood Fill salva como output.png");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                // Cria o JFrame e adiciona o painel FloodFill
                JFrame frame = new JFrame("Flood Fill");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(floodFill);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // Inicia o Flood Fill (pode escolher entre floodFillPilha ou floodFillFila)
                int startX = img.getWidth() / 2;
                int startY = img.getHeight() / 2;
                int fillColor = Color.RED.getRGB();
                floodFill.floodFillPilha(startX, startY, fillColor);

            } catch (Exception e) {
                System.err.println("Erro ao carregar ou salvar a imagem:");
                e.printStackTrace();
            }
        });
    }
}
