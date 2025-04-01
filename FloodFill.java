import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FloodFill extends JPanel {
    private BufferedImage img;
    private int width, height;
    private Pilha<Point> pilha;
    private Fila<Point> fila;
    private Timer timer;
    private int corAlvo, novaCor;
    private int pixelsPerStep = 10;

    // Callback para notificar conclusão (pode ser configurado externamente)
    private Runnable onComplete;

    public FloodFill(BufferedImage img) {
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
        setPreferredSize(new Dimension(width, height));
    }

    public void setOnComplete(Runnable onComplete) {
        this.onComplete = onComplete;
    }

    public void floodFillPilha(int xInicial, int yInicial, int novaCorRGB) {
        this.pilha = new Pilha<>();
        this.corAlvo = img.getRGB(xInicial, yInicial);
        this.novaCor = novaCorRGB;

        if (corAlvo != novaCor) {
            pilha.push(new Point(xInicial, yInicial));
        }
        animar(true);
    }

    public void floodFillFila(int xInicial, int yInicial, int novaCorRGB) {
        this.fila = new Fila<>();
        this.corAlvo = img.getRGB(xInicial, yInicial);
        this.novaCor = novaCorRGB;

        if (corAlvo != novaCor) {
            fila.enqueue(new Point(xInicial, yInicial));
        }
        animar(false);
    }

    private void animar(boolean isPilha) {
        // Ajuste o delay para um valor perceptível, por exemplo, 50ms
        timer = new Timer(50, e -> {
            if (isPilha) {
                pintarEmPilha();
            } else {
                pintarEmFila();
            }
        });
        timer.start();
    }

    private void pintarEmPilha() {
        for (int i = 0; i < pixelsPerStep; i++) {
            if (pilha.isEmpty()) {
                timer.stop();
                if (onComplete != null) onComplete.run();
                return;
            }
            Point p = pilha.pop();
            pegarVizinhos(p.x, p.y, pilha);
        }
        repaint();
    }

    private void pintarEmFila() {
        for (int i = 0; i < pixelsPerStep; i++) {
            if (fila.isEmpty()) {
                timer.stop();
                if (onComplete != null) onComplete.run();
                return;
            }
            Point p = fila.dequeue();
            pegarVizinhos(p.x, p.y, fila);
        }
        repaint();
    }

    // Sobrecarga para Pilha
    private void pegarVizinhos(int x, int y, Pilha<Point> pilha) {
        if (img.getRGB(x, y) != corAlvo) return;

        img.setRGB(x, y, novaCor);
        adicionarVizinho(x + 1, y, pilha);
        adicionarVizinho(x, y + 1, pilha);
        adicionarVizinho(x - 1, y, pilha);
        adicionarVizinho(x, y - 1, pilha);
    }

    // Sobrecarga para Fila
    private void pegarVizinhos(int x, int y, Fila<Point> fila) {
        if (img.getRGB(x, y) != corAlvo) return;

        img.setRGB(x, y, novaCor);
        adicionarVizinho(x + 1, y, fila);
        adicionarVizinho(x, y + 1, fila);
        adicionarVizinho(x - 1, y, fila);
        adicionarVizinho(x, y - 1, fila);
    }

    // Sobrecarga para Pilha
    private void adicionarVizinho(int x, int y, Pilha<Point> pilha) {
        if (x >= 0 && x < width && y >= 0 && y < height && img.getRGB(x, y) == corAlvo) {
            pilha.push(new Point(x, y));
        }
    }

    // Sobrecarga para Fila
    private void adicionarVizinho(int x, int y, Fila<Point> fila) {
        if (x >= 0 && x < width && y >= 0 && y < height && img.getRGB(x, y) == corAlvo) {
            fila.enqueue(new Point(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }
}
