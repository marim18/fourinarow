import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PrintHighscore {

    public void printPvP()
    {
        String input = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/PvPHighscore.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        String line = null;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            input += line + "\n";
        }
        try {
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JTextArea textArea = new JTextArea(input);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 600, 600 ) );
        JOptionPane.showMessageDialog(null,
                scrollPane,
                "PvP Match History:",
                JOptionPane.PLAIN_MESSAGE);
    }

    public void printPvC()
    {
        String input = "";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("src/PvCHighscore.txt"));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        String line = null;
        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            input += line + "\n";
        }
        try {
            reader.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        JTextArea textArea = new JTextArea(input);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        scrollPane.setPreferredSize( new Dimension( 600, 600 ) );
        JOptionPane.showMessageDialog(null,
                scrollPane,
                "PvC Match History:",
                JOptionPane.PLAIN_MESSAGE);
    }
}
