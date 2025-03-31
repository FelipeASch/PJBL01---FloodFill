import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            File input = new File("src/img/input.png");
            BufferedImage img = ImageIO.read(input);
            if (img == null) {
                throw new Exception("Falha ao carregar a imagem.");
            }
            FloodFill floodFill = new FloodFill(img);

            int startX = img.getWidth() / 2;
            int startY = img.getHeight() / 2;
            int fillColor = Color.RED.getRGB();

            mostrarImagem(img, "Imagem Original");


            floodFill.floodFillFila(startX, startY, fillColor);


            File output = new File("src/img/output.png");
            ImageIO.write(img, "png", output);
            System.out.println("Imagem com Flood Fill salva como output.png");

        } catch (Exception e) {
            System.err.println("Erro ao carregar ou salvar a imagem:");
            e.printStackTrace();
        }
    }

    private static void mostrarImagem(BufferedImage img, String titulo) {
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(img.getWidth(), img.getHeight());

        JLabel label = new JLabel(new ImageIcon(img));
        frame.add(label);

        frame.setVisible(true);
    }
}
