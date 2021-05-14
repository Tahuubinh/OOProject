package test;
import java.text.NumberFormat;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextAttributesEx {
    public static void main(String... args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel contentPane = new JPanel();

        JFormattedTextField ftf = new JFormattedTextField(
                NumberFormat.getNumberInstance());
        ftf.setColumns(10);
        ftf.setFocusLostBehavior(JFormattedTextField.PERSIST);
        ftf.setValue(100);
        ftf.addCaretListener(e -> {
            System.out.println("Text:" + ftf.getText());
        });
        contentPane.add(ftf);
        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
