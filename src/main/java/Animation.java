
//@Author: mgr Janusz Tuchowski
//Modificated by Kamil Smoleń
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Animation extends JPanel {

    private int x = 500;
    private int y = 500;
    private int updateTimeOut = 20;
    private int[] xAxis, yAxis, xAxisAcc, yAxisAcc;
    private Color[] colors;
    private int itemsNumber;
    private int size = 50;
    private int speed = 5;
    private Timer timer;
    private Random r;

    public Animation(int itemsNumber, int width, int height) {
        this.x = width;
        this.y = height;
        setPreferredSize(new Dimension(x,y));
        this.itemsNumber = itemsNumber;
        this.xAxis = new int[itemsNumber];
        this.yAxis = new int[itemsNumber];
        this.xAxisAcc = new int[itemsNumber];
        this.yAxisAcc = new int[itemsNumber];
        this.colors = new Color[itemsNumber];
        r = new Random();
        for (int i = 0; i < itemsNumber; i++) {
            xAxis[i] = r.nextInt(x - (2 * size)) + size;
            yAxis[i] = r.nextInt(y - (2 * size)) + size;
            xAxisAcc[i] = r.nextInt(speed) + 1;
            yAxisAcc[i] = r.nextInt(speed) + 1;
            colors[i] = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat());
        }
        ActionListener task = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                update();
                repaint();
            }
        };
        timer = new Timer(updateTimeOut, task);
        timer.start();
    }

    private void update() {
        for (int i = 0; i < itemsNumber; i++) {
            xAxis[i] += xAxisAcc[i];
            yAxis[i] += yAxisAcc[i];
            if (xAxis[i] > x - size || xAxis[i] < 0)
                xAxisAcc[i] = -xAxisAcc[i];
            if (yAxis[i] > y - size || yAxis[i] < 0 )
                yAxisAcc[i] = -yAxisAcc[i];
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(new Color(236,236,236));
        for (int i = 0; i < itemsNumber; i++) {
            g.setColor(colors[i]);
            g.fillOval(xAxis[i], yAxis[i], size, size);
        }
    }
}
