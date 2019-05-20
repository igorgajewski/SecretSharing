import javax.swing.*;
import java.awt.*;
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

        JLabel head = new JLabel("Secret Sharing!");
        gbc.gridy=0;
        gbc.gridx=0;
        gbc.gridwidth=3;
        gbc.fill=GridBagConstraints.PAGE_START;
        add(head, gbc);

        JButton encode = new JButton("Encode!");
        encode.setPreferredSize(new Dimension(100,100));
        gbc.gridy=1;
        gbc.gridx=0;
        gbc.fill=GridBagConstraints.CENTER;
        add(encode, gbc);
        JButton exit = new JButton("Exit!");
        exit.setPreferredSize(new Dimension(100, 100));
        gbc.gridx=1;
        add(exit,gbc);
    }
}

public class SecretSharing {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });
    }
}
