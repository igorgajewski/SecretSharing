import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class MainFrame extends JFrame{
    private File f;
    private String mode;
    private String fullPath;
    private String directoryPath;
     MainFrame() {
         super("Secret Sharing");
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         setVisible(true);
         setLayout(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
         setSize(new Dimension(360, 640));
         setResizable(false);

         // row 0 - TITLE
         gbc.gridy = 0;
         JLabel head = new JLabel("Secret Sharing!");
         gbc.gridx = 0;
         gbc.gridwidth = 4;
         add(head, gbc);

         // row 1 - SELECT FILE LABEL
         gbc.gridy = 1;
         gbc.gridx = 0;
         gbc.gridwidth = 1;
         add(new JLabel("Select file: "), gbc);

         // row 2 - FILE CHOOSER
         gbc.gridy = 2;
         gbc.gridx = 0;
         gbc.gridwidth = 3;
         JTextField path = new JTextField();
         path.setPreferredSize(new Dimension(260, 25));
         add(path, gbc);
         JButton browse = new JButton("...");
         JFileChooser fc = new JFileChooser();

         browse.addActionListener((ActionEvent browseAction) -> {
             int returnVal = fc.showOpenDialog(browse);
             if (returnVal == JFileChooser.APPROVE_OPTION) {
                 f = fc.getSelectedFile();
                 fullPath = f.getPath();
                 directoryPath = f.getParent();
                 path.setText(fullPath);
             }
         });
         browse.setPreferredSize(new Dimension(25, 25));
         gbc.gridx = 3;
         gbc.gridwidth = 1;
         add(browse, gbc);

         // row 3 SEPARATOR
         gbc.gridy=3;
         gbc.gridx=0;
         gbc.gridwidth=4;
         add(new JLabel(new ImageIcon("src/separator.png")), gbc);

         // row 4 - ENCRYPT/DECRYPT RADIO
         gbc.gridy = 4;
         gbc.gridx = 0;
         gbc.gridwidth=1;
         JRadioButton encrypt = new JRadioButton("Encrypt");
         add(encrypt, gbc);

         gbc.gridx = 2;
         JRadioButton decrypt = new JRadioButton("Decrypt");
         add(decrypt, gbc);

         ButtonGroup operations = new ButtonGroup();
         operations.add(encrypt);
         operations.add(decrypt);

         // row 5 - VARIABLES
         gbc.gridy = 5;
         gbc.gridx = 0;
         add(new JLabel("k:"), gbc);
         gbc.gridx = 1;
         JTextField k = new JTextField();
         k.setPreferredSize(new Dimension(25, 25));
         add(k, gbc);
         gbc.gridx = 2;
         add(new JLabel("n:"), gbc);
         gbc.gridx = 3;
         JTextField n = new JTextField();
         n.setPreferredSize(new Dimension(25, 25));
         add(n, gbc);

         // row 6
         gbc.gridy = 6;
         gbc.gridx = 0;
         add(new JLabel("r:"), gbc);
         gbc.gridx = 1;
         JTextField r = new JTextField();
         r.setPreferredSize(new Dimension(25, 25));
         add(r, gbc);

         gbc.gridx = 2;
         add(new JLabel("m:"), gbc);
         gbc.gridx = 3;
         JTextField m = new JTextField();
         m.setPreferredSize(new Dimension(25, 25));
         add(m, gbc);
         JTextArea test = new JTextArea();
         test.setOpaque(false);
         test.setBackground(new Color(0,0,0,0));
         test.setEditable(false);

         // row 7 - BUTTONS
         gbc.gridy = 7;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         JButton encode = new JButton("Encode!");
         encode.setMnemonic(KeyEvent.VK_ENTER);
         gbc.gridx = 0;
         add(encode, gbc);

         encode.addActionListener((ActionEvent encodeAction) -> {
             if (encrypt.isSelected())
                 mode = "encrypt";
             else if (decrypt.isSelected())
                 mode = "decrypt";
             else
                 mode = "ERROR";

             test.setText("mode: " + mode + "\npath: " + fullPath + "\ndirectory path: " + directoryPath + "\nk: " + k.getText() + ", n: " + n.getText() + ", r: " + r.getText() + ", m: " + m.getText());

         });

         JButton exit = new JButton("Exit!");
         gbc.gridx=2;
         add(exit, gbc);
         exit.addActionListener((ActionEvent exitAction) -> System.exit(0));

         // row 8 - SEPARATOR
         gbc.gridy=8;
         gbc.gridx=0;
         gbc.gridwidth=4;
         add(new JLabel(new ImageIcon("src/separator.png")), gbc);

         // row 9 - TEXTAREA
         gbc.gridy=9;
         gbc.gridx=0;
         gbc.gridwidth=4;
         gbc.gridheight=3;

         test.setPreferredSize(new Dimension(260,260));
         add(test, gbc);
    }
}

public class SecretSharing {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(MainFrame::new);
    }
}