import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class MainFrame extends JFrame{
    private File f;
    private String mode;
    private String fullPath;
    private String directoryPath;
    private String fileBasename;
    private int k, n, m, r, a; // k,n from (k,n)threshold scheme, r - neighborhood radius, m, a - algorithm parameters
    private Random rng = new Random();
    private String encoded;
    private FileWriter fw;
    private Scanner s;
    private String finalConfiguration;
    private boolean result;
    private boolean _ruleNumbers = false, _variables = false, _errorPopup = false;
    private int[] ruleNumbers;
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
         FileNameExtensionFilter filter = new FileNameExtensionFilter("*.txt", "txt");
         fc.setFileFilter(filter);

         browse.addActionListener((ActionEvent browseAction) -> {
             int returnVal = fc.showOpenDialog(browse);
             if (returnVal == JFileChooser.APPROVE_OPTION) {
                 f = fc.getSelectedFile();
                 fullPath = f.getPath();
                 directoryPath = f.getParent();
                 fileBasename = f.getName().replaceFirst("[.][^.]+$", "");
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
         add(new JLabel(new ImageIcon(this.getClass().getResource("images/separator.png"))), gbc);

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

         gbc.gridx = 3;
         JLabel helper = new JLabel(new ImageIcon(this.getClass().getResource("images/helper.png")));
         helper.setToolTipText("<html>1 &le k &le n" + "<br>" + "0 &le r" + "<br>" + "k &le m</html>");
         ToolTipManager.sharedInstance().setInitialDelay(10);
         add(helper, gbc);

         // row 5 - VARIABLES
         gbc.gridy = 5;
         gbc.gridx = 0;
         add(new JLabel("k:"), gbc);
         gbc.gridx = 1;
         JTextField kField = new JTextField();
         kField.setPreferredSize(new Dimension(25, 25));
         add(kField, gbc);
         gbc.gridx = 2;
         add(new JLabel("n:"), gbc);
         gbc.gridx = 3;
         JTextField nField = new JTextField();
         nField.setPreferredSize(new Dimension(25, 25));
         add(nField, gbc);

         // row 6
         gbc.gridy = 6;
         gbc.gridx = 0;
         add(new JLabel("r:"), gbc);
         gbc.gridx = 1;
         JTextField rField = new JTextField();
         rField.setPreferredSize(new Dimension(25, 25));
         add(rField, gbc);

         gbc.gridx = 2;
         add(new JLabel("m:"), gbc);
         gbc.gridx = 3;
         JTextField mField = new JTextField();
         mField.setPreferredSize(new Dimension(25, 25));
         add(mField, gbc);
         JTextArea test = new JTextArea();
         test.setOpaque(false);
         test.setBackground(new Color(0,0,0,0));
         test.setEditable(false);
         test.setFont(new Font(Font.MONOSPACED, Font.BOLD, 12));
         test.setLineWrap(true);

         // row 7 - BUTTONS
         gbc.gridy = 7;
         gbc.gridwidth = 2;
         gbc.gridheight = 1;
         JButton encode = new JButton("Encode!");
         encode.setMnemonic(KeyEvent.VK_ENTER);
         gbc.gridx = 0;
         add(encode, gbc);

         encode.addActionListener((ActionEvent encodeAction) -> {
             _errorPopup = false;
             try {
                 k = Integer.parseInt(kField.getText());
                 n = Integer.parseInt(nField.getText());
                 m = Integer.parseInt(mField.getText());
                 r = Integer.parseInt(rField.getText());
             }
             catch(Exception e){
                 if(!_errorPopup){
                    JOptionPane.showMessageDialog(null,
                         "Provided variables does not meet the requirements. Please try once again.\nSee " + "?" + " for more info.",
                         "Incorrect variables",
                         JOptionPane.ERROR_MESSAGE);
                    _errorPopup = true;
                 }
             }
             if(1 <= k && k <= n && k <= m && a <= 0 && 0 <= r)
                 // todo check if r <= l/2
                 _variables = true;
             if(_variables) {
                 if (encrypt.isSelected())
                     mode = "encrypt";
                 else if (decrypt.isSelected())
                     mode = "decrypt";
                 else
                     mode = "ERROR";
                 try {
                     encoded = encode(fullPath);
                     test.append("mode: " + mode + "\nfile: " + fullPath + "\nk: " + k + ", n: " + n + ", r: " + r + ", m: " + m);
                 }
                 catch (Exception e) {
                     test.append("\nfile not found: " + fullPath);
                 }
                 if(!_ruleNumbers) {
                     ruleNumbers = new int[k - 1];
                     for (int i = 0; i < k - 1; i++) {
                         int o = 0;
                         while (o == 0) {
                             o = rng.nextInt((int) Math.pow(2.0, (double) (2 * r + 1)));
                         }
                         ruleNumbers[i] = o;
                     }
                     _ruleNumbers = true;
                 }
                 test.append("\nrule numbers:" + Arrays.toString(ruleNumbers));

                 if (mode.equals("encrypt")) {
                     ArrayList<String> generatedConfigurations = new ArrayList<>();
                     generatedConfigurations.add(encoded);
                     for (int i = 0; i < k - 1; i++) {
                         StringBuilder sb = new StringBuilder();
                         for (int j = 0; j < encoded.length(); j++) {
                             int a = rng.nextInt(2);
                             sb.append(a);
                         }
                         generatedConfigurations.add(new String(sb));
                     }
                     for (int i = 0; i < m + n - k; i++) {
                         generatedConfigurations.add(nextSequence(r, ruleNumbers, new String[]{(String) generatedConfigurations.toArray()[i], (String) generatedConfigurations.toArray()[i + 1], (String) generatedConfigurations.toArray()[i + 2]}, generatedConfigurations.get(0).length()));
                     }
                     f = new File(directoryPath + "/" + fileBasename + "_shares.txt");
                     try {
                         result = f.exists() ? f.delete() : f.createNewFile();
                         fw = new FileWriter(f, false);
                         for (int i = generatedConfigurations.size() - n; i < generatedConfigurations.size(); i++) {
                             fw.write(generatedConfigurations.get(i) + "\r\n");
                         }
                         fw.close();
                         test.append("\nshares to be distributed have been saved to " + fileBasename + "_shares.txt file");
                     } catch (Exception e) {
                         test.append("\nunable to create _shares file from " + fileBasename);
                     }
                 } else if (mode.equals("decrypt")) {
                     a = rng.nextInt(n - k + 1);
                     ArrayList<String> inverseConfigurations = new ArrayList<>();
                     // take configurations from shares.txt file
                     try {
                         s = new Scanner(new File(fullPath));
                         for (int i = 0; i < k; i++)
                             inverseConfigurations.add(s.nextLine());
                     } catch (Exception e) {
                         test.append("\nfile not found: " + fullPath);
                     }
                     Collections.reverse(inverseConfigurations);

                     int[] ruleNumbersInversed = ruleNumbers.clone();
                     reverseArray(ruleNumbersInversed);
                     for (int i = 0; i < m + a + k - 3; i++) {
                         inverseConfigurations.add(nextSequence(r, ruleNumbersInversed, new String[]{(String) inverseConfigurations.toArray()[i], (String) inverseConfigurations.toArray()[i + 1], (String) inverseConfigurations.toArray()[i + 2]}, inverseConfigurations.get(0).length()));
                     }
                     //decode
                     finalConfiguration = inverseConfigurations.get(inverseConfigurations.size() - 1);
                     f = new File(directoryPath + "/" + fileBasename + "_secret.txt");
                     try {
                         result = f.exists() ? f.delete() : f.createNewFile();
                         fw = new FileWriter(f, false);
                         fw.write(decode(finalConfiguration));
                         fw.close();
                         test.append("\ndecoded secret has been saved to " + fileBasename + "_secret.txt file");
                     } catch (Exception e) {
                         test.append("\nunable to create _secret file from " + fileBasename);
                     }
                 }
                 test.append("\ncompleted!\n");
             }
             else{
                 if(!_errorPopup){
                     JOptionPane.showMessageDialog(null,
                             "Provided variables does not meet the requirements. Please try once again.\nSee " + "?" + " for more info.",
                             "Incorrect variables",
                             JOptionPane.ERROR_MESSAGE);
                     _errorPopup = true;
                 }
             }
         });

         JButton exit = new JButton("Exit!");
         gbc.gridx=2;
         add(exit, gbc);
         exit.addActionListener((ActionEvent exitAction) -> System.exit(0));

         // row 8 - SEPARATOR
         gbc.gridy=8;
         gbc.gridx=0;
         gbc.gridwidth=4;
         add(new JLabel(new ImageIcon(this.getClass().getResource("images/separator.png"))), gbc);

         // row 9 - TEXTAREA
         gbc.gridy=9;
         gbc.gridx=0;
         gbc.gridwidth=4;
         gbc.gridheight=3;

         test.setPreferredSize(new Dimension(300,260));
         add(test, gbc);
    }
    private static String encode(String filePath) throws FileNotFoundException {
        StringBuilder binString = new StringBuilder();
        try (Scanner file = new Scanner(new File(filePath))) {
            while (file.hasNextLine()) {
                String nextLine = file.nextLine();
                char[] charArray = nextLine.toCharArray();
                for (char c : charArray) {
                    String s = Integer.toBinaryString(((int) c));
                    StringBuilder sb = new StringBuilder(s);
                    if (s.length() < 8) {
                        for (int i = 0; i < 8 - s.length(); i++)
                            sb.insert(0, "0");
                    }
                    binString.append(new String(sb));
                }
            }
        }
        return new String(binString);
    }
    private static String decode (String content){
        StringBuilder sb = new StringBuilder();
        int startIndex = 0, endIndex = 8;
        ArrayList<String> stringArrayList = new ArrayList<>();
        while(endIndex != content.length() + 8) {
            stringArrayList.add(content.substring(startIndex, endIndex));
            startIndex = endIndex;
            endIndex += 8;
        }
        for(String s : stringArrayList){
            sb.append((char)Integer.parseInt(s, 2));
        }
        return new String(sb);
    }

    private static int[] dec2bin(int dec, int r){
        int[] binArray = new int[r+2];
        int counter = 0;
        while(dec > 0){
            int a = dec % 2;
            binArray[r + 1 - counter] = a;
            counter++;
            dec /=2 ;
        }
        return binArray;
    }

    private static int nextElement(String[] sequences, int length, int[][] binaryRuleNumbers, int i){
        int Element = 0;
        int counter = 0;
        for(int j = sequences.length - 1; j > 0; j--){
            for(int k = 0; k < binaryRuleNumbers[j - 1].length; k++){
                Element += binaryRuleNumbers[counter][k] * (sequences[j].toCharArray()[(i+k-1+length)%length])%2;
            }
            counter++;
        }
        Element += sequences[0].toCharArray()[i];
        return Element%2;
    }

    private static String nextSequence(int r, int[] ruleNumbers, String[] sequences, int length){
        StringBuilder sb = new StringBuilder();
        int[][] binaryRuleNumbers = new int[ruleNumbers.length][r+2];
        for(int i = 0; i < ruleNumbers.length; i++){
            binaryRuleNumbers[i] = dec2bin(ruleNumbers[i], r);
        }
        for(int i = 0; i < length; i++){
            sb.append(nextElement(sequences, length, binaryRuleNumbers, i));
        }
        return new String(sb);
    }

    private static void reverseArray(int[] arr){
        for(int i = 0; i < arr.length / 2; i++)
        {
            int temp = arr[i];
            arr[i] = arr[arr.length - i - 1];
            arr[arr.length - i - 1] = temp;
        }
    }
}

public class SecretSharing {
    public static void main(String[] args)
    {
        EventQueue.invokeLater(MainFrame::new);
    }
}