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
                File f = fc.getSelectedFile();
                path.setText(f.getPath());
            }
        });
        browse.setPreferredSize(new Dimension(25,25));
        gbc.gridx=3;
        gbc.gridwidth=1;
        add(browse,gbc);

        // row 3
        gbc.gridy=3;
        gbc.gridwidth=2;
        JButton encode = new JButton("Encode!");
        gbc.gridx=0;
        add(encode, gbc);
        JButton exit = new JButton("Exit!");
        gbc.gridx=2;
        add(exit, gbc);
        exit.addActionListener((ActionEvent exitAction) -> System.exit(0));
    }
}

public class SecretSharing {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(MainFrame::new);
    }
}
