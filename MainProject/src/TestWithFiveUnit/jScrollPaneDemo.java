package TestWithFiveUnit;

import javax.swing.*;
import java.awt.*;
public class jScrollPaneDemo {
    
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Scroll Pane Example");
        jFrame.setSize(500, 300);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane();
        JTextArea txtMain = new JTextArea();
        pane.setViewportView(txtMain);
        jFrame.add(pane, BorderLayout.CENTER);
        JButton btnAddText = new JButton("Add Text");
        btnAddText
                .addActionListener(e -> {
                    txtMain.setText(txtMain.getText()
                            + "Example text Example text Example text Example textExample textExample textExample text\n");
                    String text = txtMain.getText();

                });
        jFrame.add(btnAddText, BorderLayout.SOUTH);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}