
//@Author: Kamil Smoleń

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener {

    private int x, y, width, height;
    private JButton scrappBtn;
    private JScrollPane scrollPane;
    private JTextArea textArea;

    public MainWindow() {
        super("ETL DEMO");
        setWindowSize();
        setLocationRelativeTo(null);
        setComponents();
        setResizable(false);
        setLayout(null);
    }

    private void setComponents() {
        Container main = getContentPane();
        JPanel animation = new Animation(80, this.width, this.height);
        animation.setLayout(null);
        animation.setBounds(0, 0, this.width, this.height);

        scrappBtn = new JButton("Scrapp");
        scrappBtn.setBounds( (int)(this.width - 150)/2, 20, 150, 40);
        scrappBtn.addActionListener(this);
        animation.add(scrappBtn);

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(255,255,255,150));
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setBounds(100, 100, this.width - 200, this.height - 280);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(new Color(0,0,0,0));
        scrollPane.setBounds(100, 100, this.width - 200, this.height - 280);
        animation.add(scrollPane);

        main.add(animation);
    }


    private void setWindowSize() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = (int) dim.getWidth();
        this.height = (int) dim.getHeight();
        setBounds(100, 100,  width, height);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(scrappBtn)) {
            Document doc = null;
            try {
                doc = Jsoup.connect("http://en.wikipedia.org/").get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            textArea.append(doc.outerHtml());
        }
    }
}
