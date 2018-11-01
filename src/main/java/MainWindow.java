
//@Author: Kamil Smoleń

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;

public class MainWindow extends JFrame implements ActionListener, FocusListener {

    private int x, y, width, height;
    private JButton scrappBtn;
    private JScrollPane scrollPane;
    private JTextArea textArea;
    private JTextField urlTextField, idField, tagField, classField;
    private JLabel urlLab, idLab, tagLab, classLab, idMetLab, tagMetLab, classMetLab;

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


        scrappBtn = new JButton("Scrap HTML");
        scrappBtn.setBounds( (int)(this.width - 150)/2, 20, 150, 30);
        scrappBtn.addActionListener(this);
        animation.add(scrappBtn);

        urlLab = new JLabel("URL:");
        urlLab.setBounds( 50, 70, 50, 20);
        animation.add(urlLab);

        urlTextField = new JTextField();
        urlTextField.setBounds( 100, 70, this.width - 200, 20);
        animation.add(urlTextField);

        idLab = new JLabel("ID:");
        idLab.setBounds( 100, 100, 50, 20);
        animation.add(idLab);

        idField = new JTextField();
        idField.setBounds( 175, 100, (this.width-200)/3 - 50, 20);
        idField.addFocusListener(this);
        animation.add(idField);

        idMetLab = new JLabel("used method: getElementById(String id)");
        idMetLab.setBounds( 135 + (this.width-200)/3, 100, 400, 20);
        animation.add(idMetLab);

        tagLab = new JLabel("TAG:");
        tagLab.setBounds( 100 , 130, 50, 20);
        animation.add(tagLab);

        tagField = new JTextField();
        tagField.setBounds( 175 , 130, (this.width-200)/3 - 50, 20);
        tagField.addFocusListener(this);
        animation.add(tagField);


        tagMetLab = new JLabel("used method: getElementsByTag(String tag)");
        tagMetLab.setBounds( 135 + (this.width-200)/3, 130, 400, 20);
        animation.add(tagMetLab);


        classLab = new JLabel("CLASS:");
        classLab.setBounds( 100, 160, 50, 20);
        animation.add(classLab);

        classField = new JTextField();
        classField.setBounds( 175 , 160, (this.width-200)/3 - 50, 20);
        classField.addFocusListener(this);
        animation.add(classField);


        classMetLab = new JLabel("used method: getElementsByClass(String class)");
        classMetLab.setBounds( 135 + (this.width-200)/3, 160, 400, 20);
        animation.add(classMetLab);


        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(255,255,255,150));
        textArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textArea.setBounds(100, 190, this.width - 200, this.height - 280);
        scrollPane = new JScrollPane(textArea);
        scrollPane.setBackground(new Color(0,0,0,0));
        scrollPane.setBounds(100, 190, this.width - 200, this.height - 280);
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
            textArea.setText("");
            Document doc = null;
            try {
                doc = Jsoup.connect(urlTextField.getText()).get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if(idField.getText().isEmpty() && tagField.getText().isEmpty() && classField.getText().isEmpty()){
                textArea.append(doc.body().html());
            } else if (!idField.getText().isEmpty()){
                try{
                    textArea.append(doc.body().getElementById(idField.getText()+"").html());
                } catch (IllegalArgumentException el){
                    textArea.append("Error message: " + el.getMessage() + " ID");
                }
            }else if (!tagField.getText().isEmpty()){
                Elements foundElements;
                try{
                    foundElements = doc.body().getElementsByTag(tagField.getText()+"");
                    for (Element oneElement : foundElements) {
                        textArea.append(oneElement.html()+"\n");
                    }
                } catch (IllegalArgumentException el){
                    textArea.append("Error message: " + el.getMessage() + " TAG");
                }
            }else if (!classField.getText().isEmpty()){
                Elements foundElements;
                try{
                    foundElements = doc.body().getElementsByTag(classField.getText()+"");
                    for (Element oneElement : foundElements) {
                        textArea.append(oneElement.html()+"\n");
                    }
                } catch (IllegalArgumentException el){
                    textArea.append("Error message: " + el.getMessage() + " CLASS");
                }
            }

        }
    }

    @Override
    public void focusGained(FocusEvent focusEvent) {
        if (focusEvent.getSource().equals(idField)) {
            tagField.setText("");
            classField.setText("");
        } else if (focusEvent.getSource().equals(tagField)){
            idField.setText("");
            classField.setText("");
        } else if (focusEvent.getSource().equals(classField)){
            idField.setText("");
            tagField.setText("");
        }

    }

    @Override
    public void focusLost(FocusEvent focusEvent) {

    }
}
