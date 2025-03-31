import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;

public class FloodFill extends JPanel {
    private BufferedImage img;
    private int width, height;
    private Pilha<Point> pilha;
    private Fila<Point> fila;
    private Timer timer;
    private int corAlvo, novaCor;
    private int pixelsPerStep = 10;

    public FloodFill(BufferedImage img){
        this.img = img;
        this.width = img.getWidth();
        this.height = img.getHeight();
    }

    public void floodFillPilha(int xInicial, int yInicial, int novaCorRGB) {
        this.pilha = new Pilha<>();
        this.corAlvo = img.getRGB(xInicial,yInicial);
        this.novaCor = novaCorRGB;

        if(corAlvo != novaCor) {
            pilha.push(new Point(xInicial,yInicial));
        }

        animar(true);
    }
    public void floodFillFila(int xInicial, int yInicial, int novaCorRGB){
        this.fila = new Fila<>();
        this.corAlvo = img.getRGB(xInicial,yInicial);
        this.novaCor = novaCorRGB;

        if(corAlvo != novaCor) {
            fila.enqueue(new Point(xInicial,yInicial));
        }

        animar(false);
    }
    private void animar(boolean isPilha){


        timer = new Timer(15, e -> {
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
                return;
            }

            Point p = pilha.pop();
            pegarVizinhos(p.x, p.y, pilha);
        }

        repaint();
    }
    private void pintarEmFila(){
        for (int i = 0; i < pixelsPerStep; i++) {
            if (fila.isEmpty()) {
                timer.stop();
                return;
            }

            Point p = fila.dequeue();
            pegarVizinhos(p.x, p.y, fila);
        }

        repaint();
    }
    private void pegarVizinhos(int x, int y, Pilha<Point> pilha) {
        if (img.getRGB(x, y) != corAlvo) return;

        img.setRGB(x, y, novaCor);

        adicionarVizinho(x + 1, y, pilha);
        adicionarVizinho(x, y + 1, pilha);
        adicionarVizinho(x - 1, y, pilha);
        adicionarVizinho(x, y - 1, pilha);
    }
    private void pegarVizinhos(int x, int y, Fila<Point> fila) {
        if (img.getRGB(x, y) != corAlvo) return;

        img.setRGB(x, y, novaCor);

        adicionarVizinho(x + 1, y, fila);
        adicionarVizinho(x - 1, y, fila);
        adicionarVizinho(x, y + 1, fila);
        adicionarVizinho(x, y - 1, fila);
    }
    private void adicionarVizinho(int x, int y, Pilha<Point> pilha) {
        if (x >= 0 && x < width && y >= 0 && y < height && img.getRGB(x, y) == corAlvo) {
            pilha.push(new Point(x, y));
        }
    }
    private void adicionarVizinho(int x, int y, Fila<Point> fila) {
        if (x >= 0 && x < width && y >= 0 && y < height && img.getRGB(x, y) == corAlvo) {
            fila.enqueue(new Point(x, y));
        }
    }
}
