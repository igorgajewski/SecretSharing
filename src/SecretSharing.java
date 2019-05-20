import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

class MainFrame extends JFrame{
     MainFrame(){
        super("Secret Sharing");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        setSize(new Dimension(360, 640));
        setResizable(false);

        // row 0
        gbc.gridy=0;
        JLabel head = new JLabel("Secret Sharing!");
        gbc.gridx=0;
        gbc.gridwidth=4;
        add(head, gbc);

        // row 1
         gbc.gridy=1;
         gbc.gridx=0;
         gbc.gridwidth=1;

         add(new JLabel("Select file: "), gbc);
        // row 2
        gbc.gridy=2;
        gbc.gridx=0;
        gbc.gridwidth=3;
        JTextField path = new JTextField();
        path.setPreferredSize(new Dimension(260, 25));
        add(path, gbc);
        JButton browse = new JButton("...");
        JFileChooser fc = new JFileChooser();
        browse.addActionListener((ActionEvent browseAction) -> {
            int returnVal = fc.showOpenDialog(browse);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File f = fc.getSelectedFile();
                path.setText(f.getPath());
            }
        });
        browse.setPreferredSize(new Dimension(25,25));
        gbc.gridx=3;
        gbc.gridwidth=1;
        add(browse,gbc);

        // row 3
         gbc.gridy=3;
         gbc.gridx=0;
         add(new JLabel("k:"), gbc);
         gbc.gridx=1;
         JTextField k = new JTextField();
         k.setPreferredSize(new Dimension(25,25));
         add(k, gbc);
         gbc.gridx=2;
         add(new JLabel("n:"), gbc);
         gbc.gridx=3;
         JTextField n = new JTextField();
         n.setPreferredSize(new Dimension(25,25));
         add(n, gbc);
        // row 4
         gbc.gridy=4;
         gbc.gridx=0;
         add(new JLabel("r:"), gbc);
         gbc.gridx=1;
         JTextField r = new JTextField();
         r.setPreferredSize(new Dimension(25,25));
         add(r, gbc);

         gbc.gridx=2;
         add(new JLabel("m:"), gbc);
         gbc.gridx=3;
         JTextField m = new JTextField();
         m.setPreferredSize(new Dimension(25,25));
         add(m, gbc);

         JTextArea test = new JTextArea("TEST");
         test.setEditable(false);
         // row 5
         gbc.gridy=5;
         gbc.gridwidth=2;
         gbc.gridheight=1;
         JButton encode = new JButton("Encode!");
         gbc.gridx=0;
         add(encode, gbc);
         encode.addActionListener((ActionEvent encodeAction) -> test.setText("path: "+path.getText()+"\r\nk: "+k.getText()+", n: "+n.getText()+", r: "+r.getText()+", m: "+m.getText()));

         JButton exit = new JButton("Exit!");
         gbc.gridx=2;
         add(exit, gbc);
         exit.addActionListener((ActionEvent exitAction) -> System.exit(0));

         // row 6
         gbc.gridy=6;
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
